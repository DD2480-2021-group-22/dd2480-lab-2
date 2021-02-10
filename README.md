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
This is the structure of the source code for the project. The illustration was taken from [here](https://stackoverflow.com/questions/41638654/java-project-folder-structure-in-intellij-idea).
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
    │       └── ReportSnapshot.java
    │       └── SendMail.java
    │   
    └── test
        └── java
            └── DatabaseTest.java
            └── GradleHandlerTest.java
            └── PayloadTest.java
            └── RepoSnapshotTest.java
            └── SendMailTest.java
       
```

### Usage
The CI server is located at [src/main/java/ContinuousIntegrationServer.java](src/main/java/ContinuousIntegrationServer.java). 
The server must be hosted on a machine and can be set up in a git-compatible repository which has support for webhooks.  

### Contributions

**Binxin Su:**

**Johan Grundberg**
- Implementation of `GradleHandler` and corresponding tests.

**Kani Yildirim**

**Keivan Matinzadeh**

**Kitty Thai**
- Implementation of `GradleHandler` and corresponding tests.
- Bug fix #22.
- Add implementation of `Payload` to retrieve name and email about the pusher.
 