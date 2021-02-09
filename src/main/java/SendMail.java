
import org.eclipse.jgit.internal.storage.file.PackLock;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.StringBuilder;

/**
 * Setup a Mimemessage and sends the message via the configured SMTP server in the object Mailserver.
 * Message are generated using the contents in the objects Report and Payload.
 * For documentation: see javax.mail
 */
public class SendMail {

    public SendMail() {

    }

    /**
     * Setup mail server SMTP connection and sends a mail using the context in Report and Mailserver objects.
     * Takes in object of Report, and Mailserver
     * See javax.mail for method documentation.
     *
     * @param report
     * @param mailserver
     * @param to
     * @param content
     * @param payload
     * @return
     */
    public MimeMessage sendMail(Report report, Payload payload, Mailserver mailserver, String to, String content) {
        String messagebody;
        String subjectstring;
        // Set Properties
        mailserver.useGmailSMTP();
        Properties props = new Properties();
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.host", mailserver.getHost() );
        props.put( "mail.smtp.port", mailserver.getPort() );
        props.put( "mail.smtp.starttls.enable", "true" );
        props.put( "mail.debug", mailserver.debug );
        props.put( "mail.smtp.socketFactory.port", mailserver.getPort() );
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put( "mail.smtp.socketFactory.fallback", "false" );
        props.put( "mail.smtp.ssl.trust", mailserver.getHost() );

        // Create html string with content of payload and provided message.

        messagebody = CreateMessage(report, payload, content);
        subjectstring = CreateSubject(payload);

        // Create the Session Object
        Session session = Session.getDefaultInstance(
                props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication( mailserver.getUsername(), mailserver.getPassword() );
                    }
                }
        );

        try {

            MimeMessage message = new MimeMessage( session );

            // From
            message.setFrom( new InternetAddress( mailserver.getSendermail() ) );

            // Reply To
            message.setReplyTo( InternetAddress.parse( mailserver.getSendermail() ) );

            // Recipient
            message.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );

            // Subject
            message.setSubject( subjectstring );

            // Content
            message.setContent( messagebody, "text/html; charset=UTF-8" );

            Transport.send( message );

            return message;
        }
        catch(MessagingException exc ) {
            throw new RuntimeException( exc );
        }
    }

    /**
     * Generates the subject-header.
     *
     * @param payload
     * @return
     */
    private String CreateSubject(Payload payload){
        String commithash = payload.getCommitHash();
        if (commithash == null) {
            commithash = "Not Found.";
        }

        StringBuilder subjectstring = new StringBuilder();
        subjectstring.append("Notification for buildhash: "+ commithash);
        String tempstring = subjectstring.toString();

        return tempstring;
    }

    /**
     * Parse and generate a message string depending on the contents of objects Report and Payload.
     *
     * @param report
     * @param payload
     * @param message
     * @return
     */
    private String CreateMessage(Report report, Payload payload, String message){
        String date = null;
        String logs = null;
        String runtime = null;


        try {
            date = report.getFormatedDate();
        } catch (NullPointerException e) {
            date = "Not Found!";
            e.printStackTrace();
        }
        try {
            logs = report.getFormatedLogs();
        } catch (NullPointerException e) {
            logs = "Not Found!";
            e.printStackTrace();
        }
        try {
            runtime = report.getFormatedRuntime();
        } catch (NullPointerException e) {
            runtime = "Not Found!";
            e.printStackTrace();
        }
        
        StringBuilder htmlbuilder = new StringBuilder();
        htmlbuilder.append("<h3>Notification for <span style=\"background-color: #914c53; color: #ffffff; padding: 0 3px;\">DD2480-lab-2</span></h3>");

        try {
            if (report.isSuccess() == true) {
                htmlbuilder.append("<p>BUILD: SUCCESS!</P>");
            }
            else if (report.isSuccess() == false) {
                htmlbuilder.append("<p>BUILD: FAILED!</P>");
            }
        } catch (NullPointerException e) {
                htmlbuilder.append("<p>BUILD: ERROR, SOMETHING WENT WRONG!</P>");
            e.printStackTrace();
        }
        htmlbuilder.append("<p>Message: " + message +"</P>");
        htmlbuilder.append("<p>Runtime: " + runtime +"</P>");
        htmlbuilder.append("<p>Date: " + date + "</P>");
        htmlbuilder.append("<p>Logs: " + logs + "</P>");

        String html = htmlbuilder.toString();
        return html;
    }
}
