/*
 * 
 */
package src.others;

/**
 * @author Aman Rathore
 */

import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class my_literals {
    /**
     * @author Aman Rathore
     * @apiNote Helps in managing all the initial constants of the app however once
     *          the app restarts the initial constants will also be reset if their
     *          new or desired value is not stored in the configuration file
     */
    // TODO: Display a dialog to user when json file format if not correct or
    // sometning is wrong with it. Currently if format is incorrect or if someting
    // is wrong with it then it is been log to the file but make sure to display
    // info too

    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String CONFIG_FILE_PATH = Paths.get(System.getProperty("user.dir"), CONFIG_FILE_NAME)
            .toString();
    private final static my_logger logger = new my_logger("logs\\logs.log");
    private static boolean USE_CONFIG_FILE = true;

    /* constants started */
    public static Map<String, Object> CONSTANTS = new HashMap<String, Object>() {
        {
            put("MAIN WINDOW WIDTH", 16 * 75);
            put("MAIN WINDOW HEIGHT", 9 * 75);
            put("MAXIMUM NUMBER OF LOGS IN LOG WINDOW", 100);
            put("DEFAULT IP ADDRESS OF SOCKET CONNECTION WINDOW", "127.0.0.1");
            put("DEFAULT PORT OF SOCKET CONNECTION WINDOW", 8080);
            put("MAXIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW", 59);
            put("MINIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW", 0);
            put("DEFAULT SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW", 20);
            put("MINIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW", 0);
            put("MAXIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW", 13);
            put("DEFAULT MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW", 2);
            put("COLOR OF PROGRESS BARS IN NON ACTIVE STATE OF SOCKET CONNECTION WINDOW", "#E02652");
            put("COLOR OF PROGRESS BARS IN ACTIVE STATE OF SOCKET CONNECTION WINDOW", "#A3B8CC");
            put("USE JAVA SWING IN-BUILD FILE EXPLORER", true);
        }
    };
    /* constants finished */

    public static boolean update_literals(boolean use_of_config_file)
            throws FileNotFoundException, IOException {
        /**
         * @author Aman Rathore
         * @apiNote Update the value of the values of the constants according to the
         *          configuration file provided
         * @return true if the literals are updated according to config file
         * @return false if the literals are not updated according to config file due to
         *         some security or IO reasons etc.
         * @param use_of_config_file put it true if you want to use a config file
         * @throws FileNotFoundException if Json file is not present, use
         *                               reset_config() method to create a config file
         * @throws IOException           if Json file can't be read/write may be due to
         *                               security reasons
         **/

        USE_CONFIG_FILE = use_of_config_file;
        if (USE_CONFIG_FILE) {
            try {
                Map<String, Object> temp_constants_for_comparison = src.others.my_json_reader_writer
                        .read_json_from_file(CONFIG_FILE_PATH);
                if (CONSTANTS.keySet().equals(temp_constants_for_comparison.keySet())) {
                    CONSTANTS = temp_constants_for_comparison;
                } else {
                    // * There are not all the values in the json so do not read it
                    throw new IllegalStateException(
                            "Not all the constants are present in the json file\nTry resetting the json file");
                }
                return true;
            } catch (Exception e) {
                logger.addLog(e.toString(), logger.log_level.SEVERE);
                return false;
            }
        } else {
            return false;
        }
    }

    @SuppressWarnings("resource")
    public static void reset_config() throws IOException {
        /**
         * @author Aman Rathore
         * @apiNote if the configuration file is not present it creates that file. and
         *          if file is present but missing some of the properties then it will
         *          repair the file using variables from the running instance of app
         * @throws IOException if configuration file can't be read/write may be due to
         *                     security reasons
         **/
        try {
            new FileReader(CONFIG_FILE_PATH);
        } catch (Exception e) {
            logger.addLog(e.toString(), logger.log_level.WARNING);
            logger.addLog("creating new configuration file");
            new File(CONFIG_FILE_PATH).createNewFile();
            new FileReader(CONFIG_FILE_PATH);
            logger.addLog("new configuration file created");
        }
        src.others.my_json_reader_writer.write_json_to_file(CONFIG_FILE_PATH, CONSTANTS);
    }
}