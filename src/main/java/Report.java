import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Report class
 *
 * This class contains information about a certain Gradle build execution.
 */
public class Report {

    final private boolean success;
    final private String logs;
    final private ZonedDateTime date;
    final private Duration runtime;

    /**
     * Creates a Report object
     *
     * @param success Whether the build succeeded or not
     * @param logs The build logs
     * @param date The build date
     * @param runtime The runtime of the build
     */
    public Report(boolean success, String logs, ZonedDateTime date, Duration runtime) {
        this.success = success;
        this.logs = logs;
        this.date = date;
        this.runtime = runtime;
    }

    /**
     *
     * @return Boolean representing the build status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return Logs associated with the build
     */
    public String getLogs() {
        return logs;
    }

    /**
     *
     * @return The build date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     *
     * @return The execution time of the build
     */
    public Duration getRuntime() {
        return runtime;
    }

}
