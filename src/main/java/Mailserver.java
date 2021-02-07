public class Mailserver {
    String host;
    int port;
    boolean debug;
    String username;
    String password;
    String sendermail;

    public Mailserver() {
        host = "smtp.mailtrap.io";
        port = 2525;
        debug= true;
        username= "6189bbfe46e3f3";
        password= "7fcb468baea694";
        sendermail= "binxin@kth.se";
    }

    public void setNewMailServer(String host, int port, String username, String password, String senderEmail){
        this.host 	= host;
        this.port	= port;
        this.debug	= true;

        this.username = username;
        this.password = password;

        this.sendermail = senderEmail;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSendermail() {
        return sendermail;
    }

    public void setSendermail(String sendermail) {
        this.sendermail = sendermail;
    }
}
