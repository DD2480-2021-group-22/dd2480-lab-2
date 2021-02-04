import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayloadTest {

    /**
     * Asserts that a correctly formatted push event payload is correctly parsed
     * as a Payload object. The JSON used for this file is available at
     * src/test/resources/valid-payload.JSON. The schema is available at:
     * https://docs.github.com/en/developers/webhooks-and-events/webhook-events-and-payloads#push
     */
    @Test
    public void payloadIsParsedCorrectly() {
        // Arrange
        // See https://stackoverflow.com/questions/3402735/what-is-simplest-way-to-read-a-file-into-string
        String valid =  new Scanner(this.getClass().getResourceAsStream("valid-payload.JSON"))
                .useDelimiter("\\Z").next();
        String hash = "750eb142f8746ca9bb52b6d21156afbea13ad80f";

        // Act
        Payload payload = Payload.parse(valid);

        // Assert
        assertEquals("test-repo", payload.getName(), "failed to parse repo name");
        assertEquals(hash, payload.getCommitHash(), "failed to parse commit hash");
        assertEquals("da24802021group22.com", payload.getUrl(), "failed to parse repo url");
    }

    /**
     * Asserts that invalid payloads, one missing the url and the other with an invalid
     * repository property, cause exceptions to be thrown. The JSON files used are available in the
     * src/test/resources folder.
     */
    @Test
    public void invalidPayloadThrows() {
        // Arrange
        String invalid1 =  new Scanner(this.getClass().getResourceAsStream("invalid-repository-payload.JSON"))
                .useDelimiter("\\Z").next();
        String invalid2 =  new Scanner(this.getClass().getResourceAsStream("no-url-payload.JSON"))
                .useDelimiter("\\Z").next();

        // Act, Assert
        assertThrows(Exception.class, () -> {
            Payload p1 = Payload.parse(invalid1);
        });
        assertThrows(Exception.class, () -> {
            Payload p2 = Payload.parse(invalid2);
        });
    }
}

