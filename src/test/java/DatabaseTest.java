import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    /**
     * Test that asserts that a valid set of column values was successfully
     * inserted into the database
     * Expected result: True
     */
    @Test
    public void testInsertingValidValues() throws SQLException {
        CommitStructure commit = new CommitStructure();
        commit.setCommitID("8dceabc1bb55a5d04f281d4c8c1f7441d80c5ddq");
        commit.setBuildDate("2021-02-05 21:22:04");
        commit.setBuildResult(true);
        commit.setBuildLogs("BUILD SUCCESSFUL in 2s");
        MysqlDatabase con = new MysqlDatabase();
        assertTrue(MysqlDatabase.insertCommitToDatabase(con.getConnection() , commit));
    }

    /**
     * Test to show all of the rows in the database.
     * Expected result: True (if there exists rows in the table, we should receive data)
     */
    @Test
    public void testSelectingAllRowsWhenDatabaseIsNotEmpty() throws SQLException {
        MysqlDatabase con = new MysqlDatabase();
        List<CommitStructure> commits = MysqlDatabase.selectAllCommits(con.getConnection());
        assertTrue(!commits.isEmpty());
    }
}
