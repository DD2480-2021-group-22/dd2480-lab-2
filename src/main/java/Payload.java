import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * The payload class is used to represent the payload of a GitHub webhook. It should only be instantiated
 * using the parse method.
 */
public class Payload {

    // Constructs a Payload object from a JSON string.
    public static Payload parse(String json) {
        Payload p = new Gson().fromJson(json, Payload.class);

        // This may be hacky, but we only need a few things and a single endpoint, so it
        // seems excessive to import a whole library for schemas or similar solutions
        if (p.after == null || p.repository == null || p.repository.name == null || p.repository.url == null) {
            throw new JsonParseException("some field missing in json string");
        }

        return p;
    }

    private String after;
    private Repository repository;
    private static class Repository {
        public String url;
        public String name;
    }

    /**
     *
     * @return the commit hash corresponding to the push.
     */
    public String getCommitHash() {
            return after;
    }

    /**
     *
     * @return the url of the repo where the push was made.
     */
    public String getUrl() {
            return repository.url;
    }

    /**
     *
     * @return the name of the repository where the push was made.
     */
    public String getName() {
            return repository.name;
    }
}
