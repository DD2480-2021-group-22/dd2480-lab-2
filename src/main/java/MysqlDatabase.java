import org.eclipse.jgit.revwalk.DepthWalk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a instance of a MySQL database used by the CI server.
 */
public class MysqlDatabase {
    private Connection connection;

    /**
     * Create a new MysqlDatabase using the default connection.
     */
    MysqlDatabase(){
        this.connection = connectToDB();
    }

    /**
     * Create a new MysqlDatabase from an existing SQL connection.
     * @param connection
     */
    public MysqlDatabase(Connection connection){
        this.connection = connection;
    }

    /**
     * Creates a Connection object and connects it to localhost mysql server
     * @return Connection object
     */
    private Connection connectToDB() {
        try{
            //See https://www.javatpoint.com/example-to-connect-to-the-mysql-database
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException classNotFound) {
                classNotFound.printStackTrace();
            }
            Connection connection = null;

            //Try connecting to database 'test' with username=root and password=root.
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false","root","root");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            return connection;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Inserts the given CommitStructure object into the database
     * @param commit The CommitStructure Object with set values for inserting into database
     * @return Boolean that signals if the insert was successful or not
     */
    public boolean insertCommitToDatabase(CommitStructure commit) throws SQLException {
        if(connection!=null){
            //Use prepared statements for security purposes
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO commit VALUES(?,?,?,?)");
            preparedStatement.setString(1, commit.getCommitID());
            preparedStatement.setString(2, commit.getBuildDate());
            preparedStatement.setBoolean(3, commit.isBuildResult());
            preparedStatement.setString(4, commit.getBuildLogs());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            //Result > 0 => successfully inserted into database
            return (result>0);
        }
        else{
            System.out.println("Connection is null!");
        }
        return false;
    }

    /**
     * Selects all rows from the table "commit" in the database.
     * @return Returns a list with all of the rows read as CommitStructure objects
     */
    public List<CommitStructure> selectAllCommits() throws SQLException {
        List <CommitStructure> commits = new ArrayList<CommitStructure>();
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM commit");
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                CommitStructure currentCommit = new CommitStructure();
                currentCommit.setCommitID(result.getString(1));
                currentCommit.setBuildDate(result.getString(2));
                currentCommit.setBuildResult(result.getBoolean(3));
                currentCommit.setBuildLogs(result.getString(4));
                commits.add(currentCommit);
            }
            result.close();
            preparedStatement.close();
        }
        else{
            System.out.println("Connection is null!");
        }
        return commits;
    }


}

