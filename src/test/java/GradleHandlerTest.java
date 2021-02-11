import org.junit.jupiter.api.Test;

import javax.mail.internet.MimeMessage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for unit testing of gradle build task.
 */
public class GradleHandlerTest {
    /**
     * Builds a gradle project that should result in a successful build.
     * Expected result: the build succeeds.
     */
    @Test
    public void testGradleProjectBuildSuccess() {
        // Arrange
        try {
            String projectPath = "GradleTestProjects/CompilableProject";
            // https://stackoverflow.com/questions/28673651/how-to-get-the-path-of-src-test-resources-directory-in-junit
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());
            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertTrue(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Builds a gradle project that should result in a failed build.
     * Expected result: the build fails.
     */
    @Test
    public void testGradleProjectBuildFail() {
        // Arrange
        String projectPath = "GradleTestProjects/NotCompilableProject";
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());
            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertFalse(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Executes the gradle build task of a project that
     * should result in passing tests.
     * Expected result: the build succeeds.
     */
    @Test
    public void testGradleProjectTestSuccess() {
        // Arrange
        String projectPath = "GradleTestProjects/PassingTestsProject";
        try {
            // https://stackoverflow.com/questions/28673651/how-to-get-the-path-of-src-test-resources-directory-in-junit
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());

            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertTrue(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Executes the gradle build task of a project that
     * should result in failing tests.
     * Expected result: the build fails.
     */
    @Test
    public void testGradleProjectTestFail() {
        // Arrange
        String projectPath = "GradleTestProjects/FailingTestsProject";
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());

            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertFalse(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }
}
