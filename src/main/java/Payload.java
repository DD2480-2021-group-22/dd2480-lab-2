import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * The payload class is used to represent the payload of a GitHub webhook. It should only be instantiated
 * using the parse method.
 */
public class Payload {

    private String after;
    private Repository repository;
    private static boolean firstRun = true;
    private static class Repository {
        public String url;
        public String name;
    }
    private Pusher pusher;
    private static class Pusher {
        public String name;
        public String email;
    }

    /**
     * Constructs a Payload object from a JSON string.
     * @param json
     * @return
     */
    public static Payload parse(String json) {
        Payload p = new Gson().fromJson(json, Payload.class);
        if(firstRun){
            firstRun = false;
            return p;
        }
        // This may be hacky, but we only need a few things and a single endpoint, so it
        // seems excessive to import a whole library for schemas or similar solutions
        if (p.after == null || p.repository == null || p.repository.name == null || p.repository.url == null ||
            p.pusher == null || p.pusher.name == null || p.pusher.email == null) {
            throw new JsonParseException("some field missing in json string");
        }

        return p;
    }

    public String getCommitHash() {
            return after;
    }

    public String getUrl() {
            return repository.url;
    }

    public String getRepoName() {
            return repository.name;
    }

    public String getPusherName() { return pusher.name; }

    public String getPusherEmail() { return pusher.email; }
}
