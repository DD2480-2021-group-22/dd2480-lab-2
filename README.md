# dd2480-lab-2
### Continuous Integration Server
This repository contains an implementation of a Continuous Integration Server with features specified in assignment 
\#2 in the course DD2480 Software Engineering Fundamentals. A brief description of the features; a push to the repo which 
the CI server is connected to will trigger a build and execution of tests, the results from the build will then be sent 
to the pusher as an email. The implementation is produced by group 22 and is based on the sample code skeleton provided in 
[this repository](https://github.com/KTH-DD2480/smallest-java-ci). The core server implementation is available in the 
file [src/main/java/ContinuousIntegrationServer.java](src/main/java/ContinuousIntegrationServer.java).

### Build Tool
 
#### Gradle
Gradle can be used to build and test the project. To do this, run `gradle build` in the root of the repo. 

### Documentation
For documentation on the different functions used, see code comments and especially javadoc comments. 

### Commit message conventions
1. Each commit should reference an issue in the first line. To do this, use the "#" sign followed by the issue number. For example, a commit message might start with the line "fix #1". 

2. When referencing a commit, exactly one of the following prefixes should be used: 
    1. "feat": New feature for the user, not a new feature for build script.
    1. "fix": Bug fix for the user, not a fix to a build script.
    1. "doc": Changes to the documentation.
    1. "style": Formatting, missing semi colons, etc; no production code change.
    1. "refactor": Refactoring production code, eg. renaming a variable.
    1. "config": Changes to config files.
    1. "test": Adding missing tests, refactoring tests; no production code change.
    1. "chore": Updating grunt tasks etc; no production code change.


 ### Project structure
 This is the structure of the source code for the project. The illustration is taken from [here](https://stackoverflow.com/questions/41638654/java-project-folder-structure-in-intellij-idea).
 ```
 .
 │  
 └── src
     ├── main
     │   └── java 
     │       └── CommitStructure.java
     │       └── ContinuousIntegrationServer.java
     │       └── GradleHandler.java  
     │       └── Mailserver.java
     │       └── MysqlDatabase.java
     │       └── Payload.java
     │       └── Report.java
     │       └── RepoSnapshot.java
     │       └── SendMail.java
     │   
     └── test
         └── java
         │   └── DatabaseTest.java
         │   └── GradleHandlerTest.java
         │   └── PayloadTest.java
         │   └── IntegrationTest.java
         │   └── RepoSnapshotTest.java
         │   └── SendMailTest.java
         └── resources
             └── GradleTestProjects/..
             └── database.sql
             └── invalid-repository-email-payload.JSON
             └── no-url-payload.JSON
             └── real-payload.JSON
             └── valid-payload.JSON
             
 
 ```

### Usage
The CI server is located at [src/main/java/ContinuousIntegrationServer.java](src/main/java/ContinuousIntegrationServer.java). 
The server must be hosted on a machine and can be set up for any public GitHub repository which has support for webhooks. In order
for the notification emails to work, the pusher must have a public GitHub email address. To set up the server for a repo, one runs 
the server and then configures a webhook with the server address as "Payload URL", "application/json" as "Content type", the "Active" 
checkbox ticked and for just the `push` event. Then, whenever a push is made, the CI server checks out the last commit and tries to 
build and test the project using the gradle build task, and then sends the results to the pusher's email address. 

### Grading Criteria

**P3 Notifications:**  The implementaion of mail utilizes javax.mail library to send email to the commit issuer.
The server information is stored and could also be set in `Mailserver.java` and is passed in as argument for `SendMail.java`
together with the objects `report` and `payload`. The `SendMail` class then formats the information and sends a notification to
the specified mail-address, notifying the user about build status, build date, build duration and build logs.

***Testing:*** Lower-level testing is done through a local unit test in `SendMailTest` with fixed variable, sending a notification
to the senders own mailaddress. The generated MimeMessage is then checked that all correct fields corresponds to the objects Mailserver.java and Report.java.
Top-level testing is done through a integration test, where the notification of the builds are sent to the commit issuer.

### Contributions

 **Binxin Su:** 
 - Implements P3 Notification #9 by adding `SendMail.java` class that sets properties for a outgoing SMTP server, 
 generates a MimeMessage with correct format and sends it to a valid address (in integration test that is set to the commit issuer).
 - Adds `Mailserver.java` class that contains SMTP server information with corresponding getters and setters.
 - Implements unit tests for the generated MimeMessage, testing that fields of the MimeMessage corresponds to the inputs of the `Report` and 
  `Payload` classes #9.
  - Adds getters and formatting of date, duration and logs in `Report.java`.

**Johan Grundberg**
- Implementation of `GradleHandler` and corresponding tests.
- Initial implementation of `Payload` with parsing and corresponding tests. 
- Implementation of the `RepoSnapshot` class with code for cloning and checking out the repo, as well as corresponding tests. 
- Helped refactor `MysqlDatabase` and corresponding tests to support testing with no dependencies on MySQL. 
- Helped with integration of cloning, building and email tasks and writing the integration test ensuring all of these work together. 

**Kani Yildirim**

**Keivan Matinzadeh**

**Kitty Thai**
- Implementation of `GradleHandler` and corresponding tests.
- Bug fix #22.
- Add implementation of `Payload` to retrieve name and email about the pusher.


 


