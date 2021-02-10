import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
     * @return Formated logs with linebreak replacement in HTML syntax.
     */
    public String getFormatedLogs(){
        String string = logs;
        string = string.replace(">","<br>>");
        return string;
    };

    /**
     *
     * @return The build date
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     *
     * @return Formated string date
     */
    public String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a z");
        String formatedstring = formatter.format(date);

        return formatedstring;
    };

    /**
     *
     * @return The execution time of the build
     */
    public Duration getRuntime() {
        return runtime;
    }

    /**
     * Taken from: https://stackoverflow.com/questions/3471397/how-can-i-pretty-print-a-duration-in-java
     * @return Formated prettyprint runtime duration.
     */
    public String getFormatedRuntime(){
        return runtime.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    };

}
