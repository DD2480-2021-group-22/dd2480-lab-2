import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Report class
 *
 * This class contains information about a certain Gradle task execution.
 */
public class Report {

    /**
     * Representing the Gradle task type
     */
    public enum Type {
        TEST, BUILD
    }

    final private Type type;
    final private boolean success;
    final private String logs;
    final private ZonedDateTime date;
    final private Duration runtime;

    /**
     * Creates a Report object
     *
     * @param type The type of report
     * @param success Whether the task succeeded or not
     * @param logs The build or test logs
     * @param date The build or test date
     * @param runtime The runtime of the build or test
     */
    public Report(Type type, boolean success, String logs, ZonedDateTime date, Duration runtime) {
        this.type = type;
        this.success = success;
        this.logs = logs;
        this.date = date;
        this.runtime = runtime;
    }

    /**
     *
     * @return Boolean representing the gradle execution status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @return Logs sssociated with the gradle execution
     */
    public String getLogs() {
        return logs;
    }

    /**
     *
     * @return The gradle execution date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * @return The type of the gradle task
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @return The execution time of the gradle execution
     */
    public Duration getRuntime() {
        return runtime;
    }

}
