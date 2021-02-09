import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DocumentBuilder {

    public DocumentBuilder() {
    }

    public void writeDoc() {
        String start = "<!DOCTYPE html>\n<html>\n";
        String body = "<body>\n<h1>My First Heading</h1>\n<p>My first paragraph.</p>\n</body>\n";
        String end = "</html>";

        File f = new File("/Users/keivanmatinzadeh/DD2480/dd2480-lab-2/src/main/index.html");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(start);
            bw.write(body);
            bw.write(end);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
