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

    public DocumentBuilder() {
    }

    /**
     * Generates an HTML report by using bufferedwriter to write string objects,
     * formatted to look like HTML syntax, to a file.
     */
    public String writeDoc() throws SQLException {
        MysqlDatabase myDb = new MysqlDatabase();
        List<CommitStructure> mockCommits = myDb.selectAllCommits();
        String start = "<!DOCTYPE html>\n<html><head>\n" +
                "<link rel=\"stylesheet\" href=\"styles.css\">\n" +
                "</head>\n<body>\n";
        String heading = "<h1 align=\"center\">Build Report</h1>\n";
        String css = "<style>" +
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
                "#currentDiv {\n" +
                "    margin-bottom: -15px;\n" +
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
        String end = "</body>\n</html>";

        //So that we atleast have something to show if database is empty
        mockCommits.add(new CommitStructure("23424324432424", "11/02-20",
                "logloglogsfsssfsfsfsfsffs", true));

        String script = "<script type=\"text/javascript\">\n" +
                "function handleSelect(myForm)\n" +
                "{ \n" +
                "    var classCode = myForm.PreviousBuilds.value;\n" +
                "    document.getElementById('output').innerText = classCode;\n" +
                "}\n" +
                "</script>";

        StringBuilder build = new StringBuilder("<div id=\"currentDiv\" align=\"center\"><h2> Commits </h2>");

        for (CommitStructure mockCommit : mockCommits) {
            build.append(String.format(
                    "<div class=\"BuildContainer\" align=\"center\" id='%s' onClick='redirect(this.id)'>" +
                            "<div class=\"field\"><p><b>Commit id:</b> %s</p></div>" +
                            "<div class=\"field\"><p><b>Build date:</b> %s</p></div>" +
                            "<div class=\"field\"><p><b>Build status:</b> %s</p></div>" +
                            "</div>"
                    , mockCommit.getCommitID(), mockCommit.getCommitID(), mockCommit.getBuildDate(),
                    mockCommit.isBuildResult()));
        }
        build.append("</div>");

        String redirectScript = "<script type=\"text/javascript\">\n" +
                "function redirect(commitID){\n" +
                "window.location.href = 'http://localhost:8080/commit?commitID='+commitID;\n" +
                "}" +
                "</script>";

        return start+heading+css+build+script+redirectScript+end;
    }

    //commit id	build date	test result (pass/fail)	build (pass/fail)	build logs
}
