package src.others;

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
    private Formatter simpleFormatter;

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
        try {
            fileHandler = new FileHandler(logFileName);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        simpleFormatter = new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$s] [%3$s] %4$s %n";

            @Override
            public String format(LogRecord record) {
                return String.format(format, new Date(record.getMillis()), record.getLevel(),
                        record.getSourceClassName(), record.getMessage());
            }
        };
        fileHandler.setFormatter(simpleFormatter);
        LOGGER.addHandler(fileHandler);
    }

    public void addLog(String message, Level log_level) {
        LOGGER.log(log_level, message);
    }

    public void addLog(String message) {
        this.addLog(message, Level.INFO);
    }

}
