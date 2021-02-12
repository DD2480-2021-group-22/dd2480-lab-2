import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentBuilderTest {

    /**
     * This test checks that the commit hashes are inserted into the html document.
     * HTML validation is performed by sending a request to https://validator.w3.org/nu/
     * and the response contains a HTML document representing the web page if a HTML
     * validation is performed at https://validator.w3.org/ manually. The HTML document
     * returned should not contain the string "there were error", otherwise the validation fails.
     */
    @Test
    public void testWriteDoc(){

        List<CommitStructure> commits = new ArrayList<>();
        commits.add(new CommitStructure("23424324432424", "11/02-20",
                "logloglogsfsssfsfsfsfsffs", true));
        commits.add(new CommitStructure("79979779977979", "11/02-20",
                "logloglogsfsssf645646465466sfsffs", true));

        DocumentBuilder db = new DocumentBuilder();


        try {
            String html = db.writeDoc(commits);
            assertTrue(html.contains("23424324432424"));
            assertTrue(html.contains("79979779977979"));

            //https://stackoverflow.com/questions/23737300/how-to-validate-html-using-java-getting-issues-with-jsoup-library

            Document initialHtmlDoc = Jsoup.parse(html);
            Document validatedHtmlDoc = Jsoup.connect("https://validator.w3.org/nu/")
                    .header("content-type", "text/html; charset=UTF-8")
                    .requestBody(initialHtmlDoc.html())
                    .post();

            assertFalse(validatedHtmlDoc.toString().toLowerCase().contains("there were errors"));
        } catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
