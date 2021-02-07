
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Setup mail servver
 * Takes in object of Payload, and user
 * Sends mail to user
 */


public class SendMail {

    public SendMail() {

    }
    public void SendMail( Mailserver mailserver, String to, String subject, String content ) {

        // Set Properties
        Properties props = new Properties();

        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.host", mailserver.getHost() );
        props.put( "mail.smtp.port", mailserver.getPort() );
        props.put( "mail.smtp.starttls.enable", "true" );
        props.put( "mail.debug", mailserver.debug );
        props.put( "mail.smtp.socketFactory.port", mailserver.getPort() );
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put( "mail.smtp.socketFactory.fallback", "false" );
        props.put( "mail.smtp.ssl.trust", mailserver.getHost() );

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
            message.setSubject( subject );

            // Content
            message.setContent( content, "text/html; charset=utf-8" );

            Transport.send( message );

        }
        catch( MessagingException exc ) {

            throw new RuntimeException( exc );
        }
    }

    public static void main( String[] args ) {
        Mailserver mailserver = new Mailserver();
        SendMail sendmail = new SendMail();

        sendmail.SendMail(mailserver, "binxin@kth.se", "DD2480 TEST", "HELLO MAILBOX!" );
    }


}
