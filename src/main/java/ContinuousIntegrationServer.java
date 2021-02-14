import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * A continuous integration server which listens to Github push events.
 * The server clones the repository and checks out the last commit of the push event.
 * Tries to build the project using Gradle, this CI sever only supports projects using Gradle.
 * Summarizes the build result and sends an email to the pusher.
 */
public class ContinuousIntegrationServer extends AbstractHandler
{
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
        baseRequest.setHandled(true);

        if (target.equals("/builds")) {
            // generate a html-file containing all builds
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            response.getWriter().println("This page should contain a list of build history");
        } else if (target.substring(0,7).equals("/commit")) {
            // generate a html-file containing build information of the given commit
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            response.getWriter().println("This page should contain info about a build");
        } else { // the handles request is containing the webhook payload
            // see https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
            String reqString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            Payload payload = Payload.parse(reqString);

            File dir = Files.createTempDirectory("temp").toFile();
            try {
                // Clones repo
                File repo = new RepoSnapshot(payload).cloneFiles(dir);
                // Executes gradle build
                Report buildReport = GradleHandler.build(repo);
                // Sends mail
                Mailserver mailserver = new Mailserver();
                mailserver.useGmailSMTP();
                SendMail sendMail = new SendMail();
                sendMail.sendMail(buildReport, payload, mailserver, payload.getPusherEmail(), "Hello");
                // Insert results into database
                CommitStructure newBuild = new CommitStructure(payload.getCommitHash(),
                                                                buildReport.getFormatedDate(),
                                                                buildReport.getFormatedLogs(),
                                                                buildReport.isSuccess());

                database.insertCommitToDatabase(newBuild);

            } catch (Exception e) {
                System.out.println("Failed to process repo: " + e.getMessage());
            }

            response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
            response.getWriter().println("CI job done");
        }
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