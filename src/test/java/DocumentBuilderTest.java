import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jsoup.Jsoup;
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

            //Whitelist.relaxed() allows for most elements such as div, etc.
            Whitelist whitelist = Whitelist.relaxed();
            whitelist.addTags("head", "html", "style", "body");
            whitelist.addAttributes("div", "class", "align", "id");
            whitelist.addAttributes("a", "href");
            whitelist.addAttributes("h1", "align");
            whitelist.preserveRelativeLinks(true);
            //whitelist.addEnforcedAttribute("a", "href", "/commit?commitID=%s");
            //assertTrue(Jsoup.isValid(html, whitelist));




        } catch(SQLException e){e.printStackTrace();}
    }
}
