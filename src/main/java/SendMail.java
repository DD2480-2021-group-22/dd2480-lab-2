
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.StringBuilder;

import javax.mail.Address;




public class SendMail {
    /**
     * Setup mail server SMTP connection and sends a mail using the context in Report and Mailserver objects.
     * Takes in object of Report, and Mailserver
     * See javax.mail for method documentation.
     */
    public SendMail() {

    }

    /**
     *
     * @param report
     * @param mailserver
     * @param to
     * @param content
     * @return
     */
    public MimeMessage SendMail( Report report, Mailserver mailserver, String to, String content ) {
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

        messagebody = CreateMessage(report, content);
        subjectstring = CreateSubject(report);

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
     *
     * @param report
     * @return
     */
    private String CreateSubject(Report report){
        String date = report.getDate().toString();
        StringBuilder subjectstring = new StringBuilder();
        subjectstring.append("Notification for build date: "+ date);
        String tempstring = subjectstring.toString();

        return tempstring;
    }

    /**
     *
     * @param report
     * @param message
     * @return
     */
    private String CreateMessage(Report report, String message){
        String date = null;
        String logs = null;
        String runtime = null;


        try {
            date = report.getDate().toString();
        } catch (NullPointerException e) {
            date = "Not Found!";
            e.printStackTrace();
        }
        try {
            logs = report.getLogs();
        } catch (NullPointerException e) {
            logs = "Not Found!";
            e.printStackTrace();
        }
        try {
            runtime = report.getRuntime().toString();
        } catch (NullPointerException e) {
            runtime = "Not Found!";
            e.printStackTrace();
        }
        
        StringBuilder httmlbuilder = new StringBuilder();
        httmlbuilder.append("<h3>Notiication for <span style=\"background-color: #914c53; color: #ffffff; padding: 0 3px;\">DD2480-lab-2</span></h3>");

        try {
            if (report.isSuccess() == true) {
                httmlbuilder.append("<p>BUILD: SUCCESS!</P>");
            }
            else if (report.isSuccess() == false) {
                httmlbuilder.append("<p>BUILD: FAILED!</P>");
            }
        } catch (NullPointerException e) {
                httmlbuilder.append("<p>BUILD: ERROR, SOMETHING WENT WRONG!</P>");
            e.printStackTrace();
        }
        httmlbuilder.append("<p>Message: " + message +"</P>");
        httmlbuilder.append("<p>Runtime: " + runtime +"</P>");
        httmlbuilder.append("<p>Date: " + date + "</P>");
        httmlbuilder.append("<p>Logs: " + logs + "</P>");

        String html = httmlbuilder.toString();
        return html;
    }

}
