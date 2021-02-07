import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler
{
    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(javax.servlet.http.HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // see https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest
        String reqString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Payload payload = Payload.parse(reqString);
        System.out.println(new Gson().toJson(payload));

        File dir = Files.createTempDirectory("temp").toFile();
        try {
            File repo = new RepoSnapshot(payload).cloneFiles(dir);
            System.out.printf("Repo cloned to %s\n", repo);
            System.out.println("Files in it: ");
            System.out.println(Arrays.toString(repo.listFiles()));
        } catch (Exception e) {
            System.out.println("Failed to clone repo");
        }

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code

        response.getWriter().println("CI job done");
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}