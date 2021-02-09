import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendMailTest {


    @BeforeEach
    public void setUp() {
        /**
         * Lower-level test: Tests that all the fields of the MimeMessage that is generated corresponds to the inputs that are given.
         * Top-level integration test is done in the GradleHandlerTest.java.
         * Purpose: Asserts that the created MimeMessage that is sent to the user contains all the correct fields from the Payload object and the Mailserver object.
         * If the fields are correct then the message has the correct structure, the sent mail should arrive to its destination.
         * The test sets assigns a preset value for the variables success, logs, date, runtime and receiver to shared group mail at: dd2480.lab2.group22@gmail.com.
         * assertsEquals(): Returns equal if the message headers corresponds to the correct object data.
         * assertThat(): Asserts that the content (body) of the mail contains the correct data strings.
         * @throws MessagingException
         */
    }
    @Test
    public void headerTestMimeMessage() throws MessagingException, IOException {
        // Arrange
        Mailserver mailserver = new Mailserver();
        Boolean success = false;
        String logs = "logs";
        ZonedDateTime date = ZonedDateTime.now();
        Duration runtime = Duration.ofSeconds(10);
        String sendto = "dd2480.lab2.group22@gmail.com";
        Report report = new Report(success, logs, date, runtime);
        SendMail sendmail = new SendMail();
        Payload payload = new Payload();

        // Act

        MimeMessage message = sendmail.sendMail(report, payload, mailserver, sendto, "HELLO MAILBOX!");

        // Assert
        assertEquals("dd2480.lab2.group22@gmail.com",message.getHeader("To",null));
        assertEquals(mailserver.getSendermail() ,message.getHeader("From", null));
        assertEquals("text/html; charset=UTF-8" ,message.getHeader("Content-Type", null));
        assertThat(message.getHeader("Subject",null), StringContains.containsString("Notification for buildhash: Not Found."));
        assertThat(message.getContent().toString(), StringContains.containsString("HELLO MAILBOX!"));
        assertThat(message.getContent().toString(), StringContains.containsString("Runtime: "+ report.getFormatedRuntime()));
        assertThat(message.getContent().toString(), StringContains.containsString("Date: "+report.getFormatedDate()));
        assertThat(message.getContent().toString(), StringContains.containsString("Logs: "+report.getFormatedLogs()));
    }

    /**
     * Tests that the SendMail.java throws a runtime exception in case of an invalid format for receipiants mailaddress.
     * aaa is not a valid format for destination address, should throw a RuntimeException
     * assertThrows(): Throws exception if invalid destination mail-address: "aaa".
     * @throws MessagingException
     * @throws IOException
     */
    @Test
    public void exceptionTestInvalidSendToFormat() throws MessagingException, IOException {
        // Arrange
        Mailserver mailserver = new Mailserver();
        Boolean success = false;
        String logs = "logs";

        ZonedDateTime date = ZonedDateTime.now();
        Duration runtime = Duration.ofSeconds(10);
        String sendto = "aaa";
        Report report = new Report(success, logs, date, runtime);
        SendMail sendmail = new SendMail();
        Payload payload = new Payload();

        // Act
        // Assert
        assertThrows(RuntimeException.class, () -> {
            MimeMessage message = sendmail.sendMail(report, payload, mailserver, sendto, "HELLO MAILBOX!");
        });
    }

}
