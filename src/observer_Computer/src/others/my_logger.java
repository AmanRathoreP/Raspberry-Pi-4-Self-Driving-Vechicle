/**
 * 
 */
package src.others;

/**
 * @author Aman Rathore
 *
 */

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.LogRecord;
import java.io.IOException;

public class my_logger {
    private static final Logger LOGGER = Logger.getLogger(my_logger.class.getName());
    private Handler fileHandler;
    private boolean log_data = true;
    private final static Formatter simpleFormatter = new SimpleFormatter() {
        private static final String format = "[%1$tF %1$tT] [%2$s] [%3$s] %4$s %n";

        @Override
        public String format(LogRecord record) {
            return String.format(format, new Date(record.getMillis()), record.getLevel(),
                    record.getSourceClassName(), record.getMessage());
        }
    };

    public Level log_level;
    /*
     * INFO: An informational level of logging, used for messages that provide
     * useful information about the application during runtime.
     * 
     * WARNING: A warning level of logging, used for messages that indicate
     * potential problems or issues that should be addressed.
     * 
     * SEVERE: A severe level of logging, used for messages that indicate serious
     * problems or errors that require immediate attention.
     */

    public my_logger(String logFileName) {
        /*
         * This constructor initializes the logger with the specified log file name.
         * If the file does not exist, it will set `log_data` to false
         * If some of the `SecurityException` occurs then it will set `log_data` to
         * false
         * If `log_data` is set to false then no logging will occurs
         */
        try {
            this.fileHandler = new FileHandler(logFileName);
        } catch (SecurityException e) {
            e.printStackTrace();
            this.log_data = false;
        } catch (IOException e) {
            e.printStackTrace();
            this.log_data = false;

        }
        if (this.log_data) {
            this.fileHandler.setFormatter(simpleFormatter);
            LOGGER.addHandler(this.fileHandler);
        }
    }

    public my_logger() {
        /*
         * It by default sets the log file name to "logs\logs.log"
         * This constructor initializes the logger with the specified log file name.
         * If the file does not exist, it will set `log_data` to false
         * If some of the `SecurityException` occurs then it will set `log_data` to
         * false
         * If `log_data` is set to false then no logging will occurs
         */
        this("logs\\logs.log");
    }

    public void addLog(String message, Level log_level) {
        /*
         * This method logs the specified message with logging specified levels
         * if `log_data` is false then it won't log anything
         * INFO: For messages that provide useful information about the application.
         * WARNING: For messages that indicate issues that should be addressed.
         * SEVERE: For messages that indicate serious problems or errors.
         */
        if (this.log_data) {
            LOGGER.log(log_level, message);
        }
    }

    public void addLog(String message) {
        /*
         * This method logs the specified message with the default logging level (INFO)
         * if `log_data` is false then it won't log anything
         */
        this.addLog(message, Level.INFO);
    }

}
