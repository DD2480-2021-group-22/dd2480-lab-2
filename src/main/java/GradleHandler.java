import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * This class handles execution of Gradle builds.
 */
public class GradleHandler {

    /**
     * The build method takes a target directory containing a Gradle project
     * and tries to execute the build task.
     *
     * @param dir File object representing the target directory
     * @return a Report object containing information about the build
     */
    public static Report build(File dir) {

        // https://docs.gradle.org/current/javadoc/org/gradle/tooling/GradleConnector.html
        ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(dir)
                .connect();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean success = true;
        ZonedDateTime date = ZonedDateTime.now();
        Duration runtime;
        Instant start = Instant.now();
        try {
            connection.newBuild().setStandardOutput(outputStream).forTasks("build").run();
            runtime = Duration.between(start, Instant.now());
        } catch(BuildException e) {
            runtime = Duration.between(start, Instant.now());
            success = false;
        } finally {
            connection.close();
        }

        return new Report(success, outputStream.toString(), date, runtime);
    }
}
