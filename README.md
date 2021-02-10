# dd2480-lab-2

##Grading Criteria

**P3 Notifications:**  The implementaion of mail utilizes javax.mail library to send email to the commit issuer.
The server information is stored and could also be set in Mailserver.java and is passed in as argument for SendMail.java
together with report information. The SendMail class then formats the information and sends a notification to
the specified mail-address, notifying the user about build status, build date, build duration and build logs.

***Testing:*** Lower-level testing is done through a local unit test with fixed variable, sending a notification
to the senders own mailaddress. The generated MimeMessage is then checked that all correct fields corresponds to the objects Mailserver.java and Report.java.
Top-level testing is done through a integration test, where the notification of the builds are sent to the commit issuer.
 