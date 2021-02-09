import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.Duration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendMailTest {

    /**
     * Lower-level test: Tests that all the fields of the MimeMessage that is generated corresponds to the inputs that are given.
     * Top-level integration test is done in the GradleHandlerTest.java.
     * Purpose: Asserts that the created MimeMessage that is sent to the user contains all the correct fields from the Payload object and the Mailserver object.
     * If the fields are correct then the message has the correct structure, the sent mail should arrive to its destination.
     * The test sets assigns a preset value for the variables success, logs, date, runtime and receiver to shared group mail at: dd2480.lab2.group22@gmail.com.
     * @throws MessagingException
     */
    @Test
    public void payloadIsParsedCorrectly() throws MessagingException {
        // Arrange
        Mailserver mailserver = new Mailserver();
        Boolean success = false;
        String logs = "logs";
        ZonedDateTime date = ZonedDateTime.now();
        Duration runtime = Duration.ofSeconds(10);
        String sendto = "dd2480.lab2.group22@gmail.com";

        // Act
        Report report = new Report(success, logs, date, runtime);
        SendMail sendmail = new SendMail();
        MimeMessage message = sendmail.SendMail(report,mailserver, sendto, "HELLO MAILBOX!" );

        // Assert
        assertEquals("dd2480.lab2.group22@gmail.com",message.getHeader("To",null));
        assertEquals(mailserver.getSendermail() ,message.getHeader("From", null));
        assertEquals("text/html; charset=UTF-8" ,message.getHeader("Content-Type", null));
        assertThat(message.getHeader("Subject",null), StringContains.containsString(report.getDate().toString()));
        try { assertThat(message.getContent().toString(), StringContains.containsString("HELLO MAILBOX!"));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("Runtime: "+ report.getRuntime()));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("Date: "+report.getDate()));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("Logs: "+report.getLogs()));
        } catch (IOException e) { e.printStackTrace(); }

    }
}
