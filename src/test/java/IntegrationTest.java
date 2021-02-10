import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests different dataflows of the CI server
 */
public class IntegrationTest {
    /**
     * Tests integration of cloning repo, building and sending email notification.
     */
    @Test
    public void testEmailProcess(@TempDir File tempDir) throws Exception {

        // Arrange, Act
        String payloadJson =  new Scanner(this.getClass().getResourceAsStream("real-payload.JSON"))
                .useDelimiter("\\Z").next();
        Payload payload = Payload.parse(payloadJson);

        RepoSnapshot rs = new RepoSnapshot(payload.getRepoName(), payload.getUrl(), payload.getCommitHash());
        File repo = rs.cloneFiles(tempDir);

        Report report = GradleHandler.build(repo);

        Mailserver mailserver = new Mailserver();
        SendMail sendmail = new SendMail();
        mailserver.useGmailSMTP();
        MimeMessage message = sendmail.sendMail(report, payload, mailserver, mailserver.getSendermail(), "Test");

        // Assert
        assertTrue(message.getContent().toString().toLowerCase().contains("success"));
    }
}
