import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testInsertingValidValues() throws SQLException {
        CommitStructure commit = new CommitStructure();
        commit.setCommitID("8dceabc1bb55a5d04f281d4c8c1f7441d80c5ddc");
        commit.setBuildDate("2021-02-05 21:22:04");
        commit.setTestResult(false);
        commit.setBuildResult(true);
        commit.setBuildLogs("BUILD SUCCESSFUL in 2s");
        MysqlDatabase.insertCommitToDatabase(commit);
    }

    @Test
    public void testSelectingAllRows() throws SQLException {
        List<CommitStructure> commits = MysqlDatabase.selectAllCommits();
        for(int i = 0 ; i<commits.size() ; i++)
            commits.get(i).printAllValues();
    }
}
