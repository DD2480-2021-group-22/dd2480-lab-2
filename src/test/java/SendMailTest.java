import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendMailTest {


    /** TEST EXAMPLES:
     *  assertThat(email.getHeader("Content-Type", null)).isEqualTo("text/plain; charset=UTF-8");
     *  assertThat(email.getHeader("In-Reply-To", null)).isNull();
     *  assertThat(email.getHeader("References", null)).isNull();
     *  assertThat(email.getHeader("List-ID", null)).isEqualTo("SonarQube <sonar.nemo.sonarsource.org>");
     *  assertThat(email.getHeader("List-Archive", null)).isEqualTo("http://nemo.sonarsource.org");
     *  assertThat(email.getHeader("From", null)).isEqualTo("SonarQube from NoWhere <server@nowhere>");
     *  assertThat(email.getHeader("To", null)).isEqualTo("<user@nowhere>");
     *  assertThat(email.getHeader("Subject", null)).isEqualTo("[SONARQUBE] Foo");
     *  assertThat((String) email.getContent()).startsWith("Bar");
     *  String result = message.getContent().toString(); FETCHES CONTENT OF MESSAGE
     *  String result = message.getHeader("From",null); FETCHES ANY MAIL HEADER FIELDS
     *  assertThat(delivered).isTrue();
     */

    /**
     * Asserts that the created MimeMessage that is sent to the user contains all the correct fields from the Payload object and the Mailserver object.
     * If the fields are correct then the message has the correct structure, the sent mail should arrive to its destination.
     * @throws MessagingException
     */
    @Test
    public void payloadIsParsedCorrectly() throws MessagingException {
        Mailserver mailserver = new Mailserver();
        Payload payload = new Payload();
        SendMail sendmail = new SendMail();
        MimeMessage message = sendmail.SendMail(payload,mailserver, "dd2480.lab2.group22@gmail.com", "HELLO MAILBOX!" );
        assertEquals("dd2480.lab2.group22@gmail.com",message.getHeader("To",null));
        assertEquals(mailserver.getSendermail() ,message.getHeader("From", null));
        assertEquals("Notification: " + payload.getCommitHash(),message.getHeader("Subject",null));
        assertEquals("text/html; charset=UTF-8" ,message.getHeader("Content-Type", null));
        try { assertThat(message.getContent().toString(), StringContains.containsString("HELLO MAILBOX!"));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("Name: "+payload.getName()));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("URL: "+payload.getUrl()));
        } catch (IOException e) { e.printStackTrace(); }
        try { assertThat(message.getContent().toString(), StringContains.containsString("Commithash: "+payload.getCommitHash()));
        } catch (IOException e) { e.printStackTrace(); }

    }
}
