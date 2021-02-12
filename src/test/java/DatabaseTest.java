import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the database features of the project.
 */
public class DatabaseTest {

    DB db;
    Connection connection;
    MysqlDatabase mysqlDatabase;

    private CommitStructure getSampleCommit() {
        CommitStructure commit = new CommitStructure();
        commit.setCommitID("8dceabc1bb55a5d04f281d4c8c1f7441d80c5ddq");
        commit.setBuildDate("2021-02-05 21:22:04");
        commit.setBuildResult(true);
        commit.setBuildLogs("BUILD SUCCESSFUL in 2s");
        return commit;
    }

    /**
     * Sets up a temporary database connection using MariaDB4j.
     * @throws ManagedProcessException
     * @throws SQLException
     */
    @BeforeEach
    public void setUp() throws ManagedProcessException, SQLException {
        // See https://github.com/vorburger/MariaDB4j
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(0); // Setting port to 0 to let configBuilder choose a free open port.
        db = DB.newEmbeddedDB(configBuilder.build());
        db.start();
        db.source("database.sql");
        connection = DriverManager.getConnection(db.getConfiguration().getURL("test"));
        mysqlDatabase = new MysqlDatabase(connection);
    }

    /**
     * Closes database connections
     * @throws SQLException
     * @throws ManagedProcessException
     */
    @AfterEach
    public void tearDown() throws SQLException, ManagedProcessException {
        connection.close();
        db.stop();
    }


    /**
     * Test that asserts that a valid set of column values was successfully
     * inserted into the database
     * Expected result: True
     */
    @Test
    public void testInsertingValidValues() throws SQLException {
        CommitStructure commit = getSampleCommit();
        MysqlDatabase mysqlDatabase = new MysqlDatabase(connection);
        assertTrue(mysqlDatabase.insertCommitToDatabase(commit));
    }

    /**
     * Test to show all of the rows in the database, and to see that
     * the commitID inserted into the database matches the commitID retrieved.
     * Expected result: Non-empty list (if there exists rows in the table, we
     * should receive data) with the same commit as the inserted one.
     */
    @Test
    public void testSelectingAllRowsWhenNotEmpty() throws SQLException {
        // Arrange
        CommitStructure commit = getSampleCommit();
        // Act
        mysqlDatabase.insertCommitToDatabase(commit);
        List<CommitStructure> commits = mysqlDatabase.selectAllCommits();
        // Assert
        assertFalse(commits.isEmpty());
        assertEquals(commits.get(0).getCommitID() , commit.getCommitID());
    }

    /**
     * Test to show all of the rows in the database.
     * Expected result: Empty list (if there are no rows in the table, we should not receive data)
     */
    @Test
    public void testSelectingAllRowsWhenEmpty() throws SQLException {
        // Arrange
        CommitStructure commit = getSampleCommit();
        // Act
        List<CommitStructure> commits = mysqlDatabase.selectAllCommits();
        // Assert
        assertTrue(commits.isEmpty());
    }

    /**
     * Test where a specific row is selected from the database.
     * The specific row is selected by giving the primary key of the row
     * which is the commitID.
     */
    @Test
    public void testSelectSpecificRow() throws SQLException {
        // Arrange
        CommitStructure commit = getSampleCommit();
        String commitID = commit.getCommitID();
        // Act
        mysqlDatabase.insertCommitToDatabase(commit);
        CommitStructure commits = mysqlDatabase.selectSpecificRow(commitID);
        // Assert
        assertEquals(commits.getCommitID() , commitID);
    }
}
