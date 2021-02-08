import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * GradleHandler class
 *
 * This class handles execution of Gradle tasks.
 */
public class GradleHandler {

    private static Report executeTask(File dir, String task, Report.Type type) {
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
            connection.newBuild().setStandardOutput(outputStream).forTasks(task).run();
            runtime = Duration.between(start, Instant.now());
        } catch(BuildException e) {
            runtime = Duration.between(start, Instant.now());
            success = false;
        } finally {
            connection.close();
        }

        return new Report(type, success, outputStream.toString(), date, runtime);
    }

    /**
     * The build method takes a target directory containing a Gradle project
     * and tries to execute the build task.
     *
     * @param dir File object representing the target directory
     * @return a Report object containing information about the build
     */
    public static Report build(File dir) {
        return executeTask(dir, "build", Report.Type.BUILD);
    }

    /**
     * The test method takes a target directory containing a Gradle project
     * and tries to execute the test task.
     *
     * @param dir File object representing the target directory
     * @return a Report object containing information about the test
     */
    public static Report test(File dir) {
        return executeTask(dir, "test", Report.Type.TEST);
    }
}
