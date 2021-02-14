/**
 * Container for all the columns from table "commit" and their getters/setters.
 * Simplifies fetching results from database.
 *
 * Instantiation of class by default constructor and the use of setters to change the values of the fields.
 */
public class CommitStructure {
    private String commitID , buildDate , buildLogs;
    private boolean buildResult;

    public CommitStructure() {}

    public CommitStructure(String commitID, String buildDate, String buildLogs, boolean buildResult){
        this.commitID = commitID;
        this.buildDate = buildDate;
        this.buildLogs = buildLogs;
        this.buildResult = buildResult;
    }

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

    /**
     * Print out all the values of the fields.
     * Method used for debugging.
     */
    public void printAllValues(){
        System.out.println("Commit ID: " + this.getCommitID() +
                "  Build Date: " + this.getBuildDate() +
                "  Build Result: " + this.isBuildResult() +
                "  Build Logs: " + this.getBuildLogs());
    }
}
