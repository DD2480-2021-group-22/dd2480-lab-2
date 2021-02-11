import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class contains information about a certain Gradle build execution.
 */
public class Report {

    final private boolean success;
    final private String logs;
    final private ZonedDateTime date;
    final private Duration runtime;

    /**
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

    public boolean isSuccess() {
        return success;
    }

    public String getLogs() {
        return logs;
    }

    /**
     *
     * @return Formatted logs with linebreak replacement in HTML syntax.
     */
    public String getFormatedLogs(){
        String string = logs;
        // See https://stackoverflow.com/questions/3445326/regex-in-java-how-to-deal-with-newline
        string = string.replaceAll("\r\n|[\r\n]","<br>");
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
     * @return Formatted string of the build date
     */
    public String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a z");
        String formatedstring = formatter.format(date);

        return formatedstring;
    };

    public Duration getRuntime() {
        return runtime;
    }

    /**
     * @return Human readable runtime.
     */
    public String getFormatedRuntime(){
        // https://stackoverflow.com/questions/3471397/how-can-i-pretty-print-a-duration-in-java
        return runtime.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    };

}
