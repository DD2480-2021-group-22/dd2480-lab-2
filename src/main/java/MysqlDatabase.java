import org.eclipse.jgit.revwalk.DepthWalk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlDatabase {
    public static Connection connectToDB (){
        try{
            //Try getting an object from class com.mysql.jdbc.Driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException classNotFound) {
                classNotFound.printStackTrace();
            }
            Connection connection = null;

            //Try connecting to database 'test' with username=root and password=root.
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false","root","root");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return connection;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static void insertCommitToDatabase(CommitStructure commit) throws SQLException {
        Connection connection = connectToDB();
        if(connection!=null){
            //Use prepared statements for security purposes
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO commit VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, commit.getCommitID());
            preparedStatement.setString(2, commit.getBuildDate());
            preparedStatement.setBoolean(3, commit.isTestResult());
            preparedStatement.setBoolean(4, commit.isBuildResult());
            preparedStatement.setString(5, commit.getBuildLogs());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            assert connection != null;
            connection.close();
        }
        else{
            System.out.println("Connection is null!");
        }
    }

    public static List<CommitStructure> selectAllCommits() throws SQLException {
        List <CommitStructure> commits = new ArrayList<CommitStructure>();
        Connection connection = connectToDB();
        if(connection!=null){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM commit");
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                CommitStructure currentCommit = new CommitStructure();
                currentCommit.setCommitID(result.getString(1));
                currentCommit.setBuildDate(result.getString(2));
                currentCommit.setTestResult(result.getBoolean(3));
                currentCommit.setBuildResult(result.getBoolean(4));
                currentCommit.setBuildLogs(result.getString(5));
                commits.add(currentCommit);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        else{
            System.out.println("Connection is null!");
        }
        return commits;
    }


}

