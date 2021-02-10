import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to create and generate a report in html.
 */

public class DocumentBuilder {

    public DocumentBuilder() {
    }

    /**
     * Generates an HTML report by using bufferedwriter to write string objects,
     * formatted to look like HTML syntax, to a file.
     *
     * @param commitID
     * @param buildDate
     * @param testResult
     * @param buildStatus
     * @param buildLogs
     */
    public void writeDoc(String commitID, String buildDate, String testResult, String buildStatus, String buildLogs) {
        String start = "<!DOCTYPE html>\n<html><head>\n" +
                "<link rel=\"stylesheet\" href=\"styles.css\">\n" +
                "</head>\n<body>\n";
        String heading = "<h1 align=\"center\">Build Report</h1>\n";
        String end = "</body>\n</html>";

        File f = new File("/Users/keivanmatinzadeh/DD2480/dd2480-lab-2/src/Resources/index.html");

        String currentBuild = String.format(
                "<div id=\"currentDiv\" align=\"center\"><h2>Current Build</h2>" +
                        "<div align=\"center\">" +
                        "<div class=\"field\"><p><b>Commit id:</b> %s</p></div>" +
                        "<div class=\"field\"><p><b>Build date:</b> %s</p></div>" +
                        "<div class=\"field\"><p><b>Test result:</b> %s</p></div>" +
                        "<div class=\"field\"><p><b>Build status:</b> %s</p></div>" +
                        "<div class=\"field\"><p><b>Build logs:</b> %s</p></div>" +
                        "</div>" + "</div>"
            , "535532532555", "09/02-21", "Successful", "Successful", "fsfsfdf");

        String previousBuilds = String.format("<div id=\"previousDiv\" align=\"center\"><h3>Previous Builds</h3>" +
                "<form action='VERIFY.php' method='post'>" +
                "<select name=\"PreviousBuilds\" id=\"dropdown\" onchange=\"handleSelect(this.form)\">" +
                "<option value=\"previous build 1\">32525454353242325</option>" +
                "<option value=\"previous build 2\">32525454353242325</option>"+
                "</select></form><p><span id=\"output\"></span></p>" + "</div>\n");

        String script = String.format("<script type=\"text/javascript\">\n" +
                "function handleSelect(myForm)\n" +
                "{ \n" +
                "    var classCode = myForm.PreviousBuilds.value;\n" +
                "    document.getElementById('output').innerText = classCode;\n" +
                "}\n" +
                "</script>");



        //"<div style=\"display: inline-block\"> <img src=\"img2.png\"> </div> </div>"
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(start);
            bw.write(heading);
            bw.write(currentBuild);
            bw.write(previousBuilds);
            bw.write(script);
            bw.write(end);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //commit id	build date	test result (pass/fail)	build (pass/fail)	build logs
}
