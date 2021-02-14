import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to create and generate a report in html.
 */

public class DocumentBuilder {

    private static String start = "<!DOCTYPE html>\n<html lang=\"en-us\">\n<head>\n" +
            "<title>Build Report</title>";
    private static String css = "<style type=\"text/css\">" +
            "h1 {\n" +
            "    padding-top: 40px;\n" +
            "    padding-bottom: 30px;\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "  font-family: Arial, sans-serif;\n" +
            "  background-color: #a7e7fc;\n" +
            "}\n" +
            "\n" +
            ".field {\n" +
            "    display: inline-block;\n" +
            "    padding:0px 10px;\n" +
            "}\n" +
            "\n" +
            ".BuildContainer {\n" +
            "    border: solid;\n" +
            "    margin-bottom: 25px\n" +
            "}\n" +
            "\n" +
            ".BuildDetailsContainer {\n" +
            "    border-bottom: solid;\n" +
            "    margin-bottom: 25px\n" +
            "}\n" +
            "\n" +
            "#currentDiv {\n" +
            "    margin-bottom: -15px;\n" +
            "    margin-left: 100px;\n" +
            "    margin-right: 100px;\n" +
            "}\n" +
            "\n" +
            "#detailsMainDiv {\n" +
            "    margin-left: 100px;\n" +
            "    margin-right: 100px;\n" +
            "}\n" +
            "\n" +
            "#previousDiv {\n" +
            "\n" +
            "    margin-top: 50px;\n" +
            "\n" +
            "}\n" +
            "\n" +
            "</style>";
    private static String mid = "</head>\n<body>\n";
    private static String end = "</body>\n</html>";
    public DocumentBuilder() {
    }

    /**
     * Returns a string representing an HTML file. This string represents an HTML page where each commit
     * in the database is displayed together with it's build date and build status.
     * Each commit is clickable and will take the user to a new page displaying the details, including
     * the build logs for the commit that was clicked on.
     *
     * @param commits   A list of CommitStructures to be displayed in the page
     * @return          A string representing an HTML page
     */
    public static String createMainPage(List<CommitStructure> commits){
        String heading = "<h1 align=\"center\">Build Report</h1>\n";

        StringBuilder build = new StringBuilder("<div id=\"currentDiv\" align=\"center\"><h2> Commits </h2>");

        for (CommitStructure commit : commits) {

            build.append(String.format(
                    "<div class=\"BuildContainer\" align=\"center\">" +
                            "<div class=\"field\"><p><b>Commit id:</b> <a href=\"/commit?commitID=%s\">%s</a></p></div>" +
                            "<div class=\"field\"><p><b>Build date:</b> %s</p></div>" +
                            "<div class=\"field\"><p><b>Build status:</b> %s</p></div>" +
                            "</div>"
                    , commit.getCommitID(), commit.getCommitID(), commit.getBuildDate(),
                    commit.isBuildResult()));
        }
        build.append("</div>");

        return start+css+mid+heading+build+end;
    }

    /**
     * Returns a string representing an HTML file. This string represents an HTML page where the details
     * of the selected commit is displayed, including it's build logs.
     *
     * @param commit    A CommitStructure object
     * @return          A string representing an HTML page
     */
    public static String createBuildDetails(CommitStructure commit){
        String logs = commit.getBuildLogs();

        String heading = "<h1 align=\"center\">Build Details</h1>\n";

        String details = String.format(
                "<div id=\"detailsMainDiv\" align=\"center\">" +
                        "<div class=\"BuildDetailsContainer\" align=\"center\">" +
                        "<div class=\"field\"><p><b>Commit id:</b><p>%s</p></div>" +
                        "<div class=\"field\"><p><b>Build date:</b> %s</p></div>" +
                        "<div class=\"field\"><p><b>Build status:</b> %s</p></div>" +
                        "</div>" +
                        "</div>"
                , commit.getCommitID(), commit.getBuildDate(),
                commit.isBuildResult());

        String logData = "<div id=\"currentDiv\" align=\"center\"><h2> Log Data </h2>" +
                "<div class=\"field\"><p>" + logs + "</p></div></div>";

        return start+css+mid+heading+details+logData+end;

    }

}
