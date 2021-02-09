import org.junit.jupiter.api.Test;

import javax.mail.internet.MimeMessage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GradleHandlerTest {
    /**
     * This test has the purpose of building a gradle project
     * that results in a successful build.
     *
     * The made assertions are:
     *  - The isSuccess() method returns true
     */
    @Test
    public void testGradleProjectBuildSuccess() {
        // Arrange
        try {
            String projectPath = "GradleTestProjects/CompilableProject";
            // https://stackoverflow.com/questions/28673651/how-to-get-the-path-of-src-test-resources-directory-in-junit
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());
            Mailserver mailserver = new Mailserver();
            SendMail sendmail = new SendMail();
            // Act
            Report report = GradleHandler.build(projectDirectory);
            //CHANGE THE TO- ADDRESS WHEN PAYLOAD HAVE CORRECT STRUCTURE;
            MimeMessage message = sendmail.SendMail(report, mailserver, "dd2480.lab2.group22@gmail.com,","HELLO MAILBOX!");


            // Assert
            assertTrue(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose of building a gradle project
     * that results in a failed build.
     *
     * The made assertions are:
     *   - The isSuccess() method returns false
     */
    @Test
    public void testGradleProjectBuildFail() {
        // Arrange
        String projectPath = "GradleTestProjects/NotCompilableProject";
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());
            // Act
            Report report = GradleHandler.build(projectDirectory);
            Mailserver mailserver = new Mailserver();
            SendMail sendmail = new SendMail();
            MimeMessage message = sendmail.SendMail(report, mailserver, "dd2480.lab2.group22@gmail.com,","HELLO MAILBOX!");

            // Assert
            assertFalse(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose of running the gradle build task of a project
     * where tests should be passing.
     *
     * The made assertions are:
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
            Report report = GradleHandler.build(projectDirectory);
            Mailserver mailserver = new Mailserver();
            SendMail sendmail = new SendMail();
            MimeMessage message = sendmail.SendMail(report, mailserver, "dd2480.lab2.group22@gmail.com,","HELLO MAILBOX!");

            // Assert
            assertTrue(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * This test has the purpose of running the gradle build task of a project
     * where tests should be failing.
     *
     * The made assertions are:
     *  - The isSuccess() method returns false
     */
    @Test
    public void testGradleProjectTestFail() {
        // Arrange
        String projectPath = "GradleTestProjects/FailingTestsProject";
        try {
            File projectDirectory = new File(getClass().getClassLoader().getResource(projectPath).getFile());

            // Act
            Report report = GradleHandler.build(projectDirectory);
            Mailserver mailserver = new Mailserver();
            SendMail sendmail = new SendMail();
            MimeMessage message = sendmail.SendMail(report, mailserver, "dd2480.lab2.group22@gmail.com,","HELLO MAILBOX!");

            // Assert
            assertFalse(report.isSuccess());
        } catch (Exception e) {
            fail();
        }
    }
}
