
import com.google.gson.JsonParseException;

import java.io.IOException;
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

/**
 * Setup mail server
 * Takes in object of Payload, and user
 * Sends mail to user
 * See javax.mail for method documentation.
 */


public class SendMail {

    public SendMail() {

    }
    public MimeMessage SendMail( Payload payload, Mailserver mailserver, String to, String content ) {
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

        messagebody = CreateMessage(payload, content);
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
    private String CreateSubject(Payload payload){
        String name = payload.getName();
        String url = payload.getUrl();
        String commithash = payload.getCommitHash();
        StringBuilder subjectstring = new StringBuilder();
        subjectstring.append("Notification: "+ commithash);
        String tempstring = subjectstring.toString();

        return tempstring;
    }

    private String CreateMessage(Payload payload, String message){
        String name = payload.getName();
        String url = payload.getUrl();
        String commithash = payload.getCommitHash();
        StringBuilder httmlbuilder = new StringBuilder();
        httmlbuilder.append("<h3>Notiication for <span style=\"background-color: #914c53; color: #ffffff; padding: 0 3px;\">DD2480-lab-2</span></h3>");
        httmlbuilder.append("<p>"+ message + "</p>");
        httmlbuilder.append("<p>Name: "+ name + "</p>");
        httmlbuilder.append("<p>URL: "+ url + "</p>");
        httmlbuilder.append("<p>Commithash: "+ commithash + "</p>");
        String html = httmlbuilder.toString();

        return html;
    }
}
