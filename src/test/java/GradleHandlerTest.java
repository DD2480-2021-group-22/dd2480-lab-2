import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GradleHandlerTest {
    /**
     * This test has the purpose to build a gradle project
     * that results in a successful build.
     *
     * The made assertions are:
     *  - The report type is Report.Type.BUILD
     *  - The isSuccess() method returns true
     */
    @Test
    public void testGradleProjectBuildSuccess() {
        // Arrange
        try {
            // https://stackoverflow.com/questions/28673651/how-to-get-the-path-of-src-test-resources-directory-in-junit
            File projectDirectory = new File(getClass().getClassLoader().getResource("GradleTestProjects/CompilableProject").getFile());

            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertTrue(report.isSuccess());
            assertEquals(Report.Type.BUILD, report.getType());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose of running the gradle test task of a project
     * where test should be passing.
     *
     * The made assertions are:
     *  - The report type is Report.Type.TEST
     *  - The isSuccess() method returns true
     */
    @Test
    public void testGradleProjectTestSuccess() {
        // Arrange
        String projectPath = "GradleTestProjects/PassingTestsProject";
        try {
            // https://stackoverflow.com/questions/28673651/how-to-get-the-path-of-src-test-resources-directory-in-junit
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());

            // Act
            Report report = GradleHandler.test(projectDirectory);

            // Assert
            assertTrue(report.isSuccess());
            assertEquals(Report.Type.TEST, report.getType());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose of running the gradle test task of a project
     * where test should be failing.
     *
     * The made assertions are:
     *  - The report type is Report.Type.TEST
     *  - The isSuccess() method returns false
     */
    @Test
    public void testGradleProjectTestFail() {
        // Arrange
        String projectPath = "GradleTestProjects/FailingTestsProject";
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());
            // Act
            Report report = GradleHandler.test(projectDirectory);

            // Assert
            assertFalse(report.isSuccess());
            assertEquals(Report.Type.TEST, report.getType());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose to build a gradle project
     * that results in a failed build.
     *
     * The made assertions are:
     *   - The report type is Report.Type.BUILD
     *   - The isSuccess() method returns false
     */
    @Test
    public void testGradleProjectBuildFail() {
        // Arrange
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource("GradleTestProjects/NotCompilableProject").getFile());
            // Act
            Report report = GradleHandler.build(projectDirectory);

            // Assert
            assertFalse(report.isSuccess());
            assertEquals(Report.Type.BUILD, report.getType());
        } catch (Exception e) {
            fail();
        }
    }
}
