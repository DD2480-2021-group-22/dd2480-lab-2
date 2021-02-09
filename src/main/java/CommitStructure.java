/**
 * Class with all the columns for table "commit" and their getters/setters. Simplifies fetching results from database
 */
public class CommitStructure {
    private String commitID , buildDate , buildLogs;
    private boolean testResult, buildResult;

    public String getCommitID() {
        return commitID;
    }

    public void setCommitID(String commitID) {
        this.commitID = commitID;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getBuildLogs() {
        return buildLogs;
    }

    public void setBuildLogs(String buildLogs) {
        this.buildLogs = buildLogs;
    }

    public boolean isBuildResult() {
        return buildResult;
    }

    public void setBuildResult(boolean buildResult) {
        this.buildResult = buildResult;
    }

    public void printAllValues(){
        System.out.println("Commit ID: " + this.getCommitID() +
                "  Build Date: " + this.getBuildDate() +
                "  Build Result: " + this.isBuildResult() +
                "  Build Logs: " + this.getBuildLogs());
    }
}
