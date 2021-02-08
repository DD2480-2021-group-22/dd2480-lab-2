# dd2480-lab-2

##Grading Criteria

**P3 Notifications:** Notifications are implemented using a mocked SMTP server via  [mailtrap](https://mailtrap.io/).
.The CI server sends an email from the mocked SMTP server to a project member. The mail is then caught
in the mocked SMTP server to prevent spam. The implementaion of mail utilizes javax.mail library to send
an email. The server information is stored and could be set in Mailserver.java and is passed in as argument for SendMail.java
together with Payload information. The SendMail class then formats the information and sends a notification to
the specified mail-address.
<br>*Testing*:</br> Testing will probably be done by fetching the sent mail and extracting the information after
a certain time delay to ensure that the mail has arrived in the mailbox.