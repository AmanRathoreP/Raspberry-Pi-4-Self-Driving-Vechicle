/*
 * 
 */
package observer_java_GUI.src.others;

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
            put("MAXIMUM NUMBER OF LOGS IN LOG WINDOW", 1000);
            put("MINIMUM NUMBER OF LOGS IN LOG WINDOW", 2);
            put("DEFAULT NUMBER OF LOGS IN LOG WINDOW", 250);
            put("STEPS TO TAKE WHILE INCREMENTING THE NUMBER OF LOGS IN LOG WINDOW", 15);
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
            // TODO put("APP UI MANAGER", "javax.swing.plaf.metal.MetalLookAndFeel");
            // This includes file explorer too
            put("USE SYSTEM\'S UI MANAGER", false);
            put("LOW COLOR", "#40c040");
            put("MEDIUM COLOR", "#ffd740");
            put("HIGH COLOR", "#dc143c");
            put("NEEDLE COLOR", "#ffffff");
            put("TICK LABEL COLOR", "#000000");
            put("TICK PAINT COLOR", "#808080");
            put("VALUE COLOR", "#000000");
            put("BACKGROUND COLOR", "#33ccff");
            put("PLOT BACKGROUND COLOR", "#009999");
            put("GRIDLINE COLOR", "#000000");
            put("SERIES COLOR", "#0e9ca5");
            put("FONT COLOR FOR CHART'S CAPTION", "#000000");
            put("FONT'S OUTLINE COLOR FOR CHART'S CAPTION", "#ffffff");
            put("FONT SIZE FOR CHART'S CAPTION", 21);
            put("OUTLINE STROKE", 2.284);
        }
    };
    /* constants finished */
    /* constants' tooltip started */
    public static Map<String, String> CONSTANTS_TOOLTIP = new HashMap<String, String>() {
        {
            put("MAIN WINDOW WIDTH",
                    "<html><body>Default Width of any panel if preferred size is not provided!<br>Default value is <b>16 * 75</b></body></html>");
            put("MAIN WINDOW HEIGHT",
                    "<html><body>Default Height of any panel if preferred size is not provided!<br>Default value is <b>9 * 75</b></body></html>");
            put("MAXIMUM NUMBER OF LOGS IN LOG WINDOW", "<html><body>Default value is <b>1000</b></body></html>");
            put("MINIMUM NUMBER OF LOGS IN LOG WINDOW", "<html><body>Default value is <b>2</b></body></html>");
            put("DEFAULT NUMBER OF LOGS IN LOG WINDOW", "<html><body>Default value is <b>250</b></body></html>");
            put("STEPS TO TAKE WHILE INCREMENTING THE NUMBER OF LOGS IN LOG WINDOW",
                    "<html><body>Default value is <b>15</b></body></html>");
            put("DEFAULT IP ADDRESS OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>127.0.0.1</b></body></html>");
            put("DEFAULT PORT OF SOCKET CONNECTION WINDOW", "<html><body>Default value is <b>8080</b></body></html>");
            put("MAXIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>59</b></body></html>");
            put("MINIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>0</b></body></html>");
            put("DEFAULT SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>20</b></body></html>");
            put("MINIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>0</b></body></html>");
            put("MAXIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>13</b></body></html>");
            put("DEFAULT MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>2</b></body></html>");
            put("COLOR OF PROGRESS BARS IN NON ACTIVE STATE OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>#E02652</b></body></html>");
            put("COLOR OF PROGRESS BARS IN ACTIVE STATE OF SOCKET CONNECTION WINDOW",
                    "<html><body>Default value is <b>#A3B8CC</b></body></html>");
            put("USE SYSTEM\'S UI MANAGER", "<html><body>Default value is <b>false</b></body></html>");
            put("LOW COLOR", "<html><body>Default value is <b>#40c040</b></body></html>");
            put("MEDIUM COLOR", "<html><body>Default value is <b>#ffd740</b></body></html>");
            put("HIGH COLOR", "<html><body>Default value is <b>#dc143c</b></body></html>");
            put("NEEDLE COLOR",
                    "<html><body>Color of the needle which is used in the gauge chart etc.<br>Default value is <b>#ffffff</b></body></html>");
            put("TICK LABEL COLOR", "<html><body>Default value is <b>#000000</b></body></html>");
            put("TICK PAINT COLOR", "<html><body>Default value is <b>#808080</b></body></html>");
            put("VALUE COLOR", "<html><body>Default value is <b>#000000</b></body></html>");
            put("BACKGROUND COLOR", "<html><body>Default value is <b>#33ccff</b></body></html>");
            put("PLOT BACKGROUND COLOR", "<html><body>Default value is <b>#009999</b></body></html>");
            put("GRIDLINE COLOR",
                    "<html><body>This is the color of the grids which are visible in the charts<br>Default value is <b>#000000</b></body></html>");
            put("SERIES COLOR", "<html><body>Default value is <b>#0e9ca5</b></body></html>");
            put("FONT COLOR FOR CHART'S CAPTION", "<html><body>Default value is <b>#000000</b></body></html>");
            put("FONT'S OUTLINE COLOR FOR CHART'S CAPTION",
                    "<html><body>Default value is <b>#ffffff</b></body></html>");
            put("FONT SIZE FOR CHART'S CAPTION", "<html><body>Default value is <b>21</b></body></html>");
            put("OUTLINE STROKE",
                    "<html><body>This is the width of the outline of the test which will be rendered on the image if you want caption<br>Default value is <b>2.284</b></body></html>");
        }
    };
    /* constants' tooltip finished */

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
                Map<String, Object> temp_constants_for_comparison = observer_java_GUI.src.others.my_json_reader_writer
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
        observer_java_GUI.src.others.my_json_reader_writer.write_json_to_file(CONFIG_FILE_PATH, CONSTANTS);
    }
}