import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.revwalk.DepthWalk;

/**
 * A continuous integration server which listens to Github push events.
 * The server clones the repository and checks out the last commit of the push event.
 * Tries to build the project using Gradle, this CI sever only supports projects using Gradle.
 * Summarizes the build result and sends an email to the pusher.
 */

public class ContinuousIntegrationServer extends AbstractHandler {
    private MysqlDatabase database;

    public ContinuousIntegrationServer() {

        database = new MysqlDatabase();
    }
    /**
     * Handles incoming requests to the server.
     *
     * @param target
     * @param baseRequest
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");

        response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);

        try {
            List<CommitStructure> commits = database.selectAllCommits();

            if (target.equals("/builds")) {
                String html = DocumentBuilder.createMainPage(commits);
                response.getWriter().println(html);
                response.flushBuffer();

            } else if (target.equals("/commit")) {
                String commitID = request.getParameter("commitID");
                CommitStructure commit = database.selectSpecificRow(commitID);
                if(commit==null){
                    response.getWriter().println("404: The selected commit ID is non existant");
                }
                else{
                    String html = DocumentBuilder.createBuildDetails(commit);
                    response.getWriter().println(html);
                }
                response.flushBuffer();


            } else {
                baseRequest.setHandled(true);

                // see https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
                String reqString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

                Payload payload = Payload.parse(reqString);

                File dir = Files.createTempDirectory("temp").toFile();
                try {
                    // Clones repo
                    System.out.println("Cloning repo...");
                    File repo = new RepoSnapshot(payload).cloneFiles(dir);
                    System.out.println("Repo cloned");
                    // Executes gradle build
                    System.out.println("Start building...");
                    Report buildReport = GradleHandler.build(repo);
                    System.out.println("Build finished");
                    // Sends mail
                    System.out.println("Sending results...");
                    Mailserver mailserver = new Mailserver();
                    mailserver.useGmailSMTP();
                    SendMail sendMail = new SendMail();
                    sendMail.sendMail(buildReport, payload, mailserver, payload.getPusherEmail(), "Hello");
                    System.out.println("Email sent");
                    // Insert results into database
                    System.out.println("Insert build results into database..");
                    CommitStructure newBuild = new CommitStructure(payload.getCommitHash(),
                                                                buildReport.getFormatedDate(),
                                                                buildReport.getFormatedLogs(),
                                                                buildReport.isSuccess());

                    database.insertCommitToDatabase(newBuild);
                    System.out.println("Database updated!");

                } catch (Exception e) {
                    System.out.println("Failed to process repo: " + e.getMessage());
                }

                response.getWriter().println("CI job done");
                System.out.println("CI job done");
            }
        } catch(SQLException e){e.printStackTrace();}

    }

    // used to start the CI server in command line

    /**
     * Starts the server.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}