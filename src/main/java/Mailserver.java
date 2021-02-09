/**
 * Mailserver object that contains all information about the SMTP server used for outgoing mail.
 */
public class Mailserver {
    String host;
    int port;
    boolean debug;
    String username;
    String password;
    String sendermail;


    public Mailserver() {
        this.host = host;
        this.port = port;
        debug= true;
        this.username = username;
        this.password = password;
        this.sendermail = sendermail;
    }

    /**
     * Sets default mailserver to gmails SMTP:
     */
    public void useGmailSMTP(){
        this.setNewMailServer("smtp.gmail.com", 465, "dd2480.lab2.group22@gmail.com", "Group_22","dd2480.lab2.group22@gmail.com" );
    }

    /**
     * Sets default mailserver to mailtrap SMTP.
     */
    public void useMailTrap(){
        this.setNewMailServer( "smtp.mailtrap.io", 2525, "6189bbfe46e3f3", "7fcb468baea694","CI-Server@kth.se" );
    }

    /**
     * Takes in variables that are needed for a SMTP server and set that information to the new current SMTP server.
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param senderEmail
     */
    public void setNewMailServer(String host, int port, String username, String password, String senderEmail){
        this.host 	= host;
        this.port	= port;
        this.debug	= true;

        this.username = username;
        this.password = password;

        this.sendermail = senderEmail;
    }

    /**
     * Here begins setters and getters.
     *
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * @return
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getSendermail() {
        return sendermail;
    }

    /**
     *
     * @param sendermail
     */
    public void setSendermail(String sendermail) {
        this.sendermail = sendermail;
    }
}
