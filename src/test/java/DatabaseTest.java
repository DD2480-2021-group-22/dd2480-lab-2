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
     * @param path temporary directory provided by JUnit.
     * @throws ManagedProcessException
     * @throws SQLException
     */
    @BeforeEach
    public void setUp(@TempDir Path path) throws ManagedProcessException, SQLException {
        // See https://github.com/vorburger/MariaDB4j
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(3306); // OR, default: setPort(0); => autom. detect free port
        configBuilder.setDataDir(path.toString()); // just an example
        configBuilder.setDeletingTemporaryBaseAndDataDirsOnShutdown(false);
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
     * Test to show all of the rows in the database.
     * Expected result: Non-empty list (if there exists rows in the table, we should receive data)
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
}
