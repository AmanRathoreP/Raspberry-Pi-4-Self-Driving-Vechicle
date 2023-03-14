/**
 * @author Aman Rathore
 */
package src.others;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import src.others.my_logger;

public class my_literals {
    /**
     * @author Aman Rathore
     * @apiNote Helps in managing all the initial constants of the app however once
     *          the app restarts the initial constants will also be reset if their
     *          new or desired value is not stored in the configuration file
     */
    // TODO: Create a buton to creat configuration file whenever user wants

    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String CONFIG_FILE_PATH = Paths.get(System.getProperty("user.dir"), CONFIG_FILE_NAME)
            .toString();
    private final static my_logger logger = new my_logger("logs\\logs.log");
    private static boolean USE_CONFIG_FILE = true;
    private static JSONObject jsonObject;

    /* constants started */
    public static int WINDOW_SIZE_WIDTH = 16 * 75;
    public static int WINDOW_SIZE_HEIGHT = 9 * 75;
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
                logger.addLog("trying to use config");
                jsonObject = readJsonFile();
                logger.addLog("json obj created");
                WINDOW_SIZE_WIDTH = get_associated_value_from_json("WINDOW_SIZE_WIDTH", WINDOW_SIZE_WIDTH);
                WINDOW_SIZE_HEIGHT = get_associated_value_from_json("WINDOW_SIZE_HEIGHT", WINDOW_SIZE_HEIGHT);
                logger.addLog(jsonObject.toString());
            } catch (Exception e) {
                // File donen't exists on the location
                logger.addLog(e.toString());
                // e.printStackTrace();
                USE_CONFIG_FILE = false;
                return false;
            }
            return true;

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
        JSONObject json_object = new JSONObject();
        json_object.put("WINDOW_SIZE_WIDTH", WINDOW_SIZE_WIDTH);
        json_object.put("WINDOW_SIZE_HEIGHT", WINDOW_SIZE_HEIGHT);
        FileWriter file_writer = new FileWriter(CONFIG_FILE_PATH);
        file_writer.write(json_object.toJSONString());
        file_writer.close();
        logger.addLog("Json object written to the configuration file");
        logger.addLog(json_object.toJSONString());
    }

    private static JSONObject readJsonFile() throws FileNotFoundException, IOException, ParseException {
        JSONObject json_object = new JSONObject();
        if (USE_CONFIG_FILE) {
            JSONParser parser = new JSONParser();
            json_object = (JSONObject) parser.parse(new FileReader(CONFIG_FILE_PATH));
        }
        // * Come here when we do not need to write or read from json file
        return json_object;
    }

    private static <T> T get_associated_value_from_json(String name_of_default_value, T default_value) {
        /*
         * @apiNote this function will get the value from config file and if the config
         * file not have that value then the it will return the default value
         * 
         * @warning it only supports the below given data types
         * - Integer
         * - Double
         * - String
         * - Boolean
         * - Short
         */
        try {
            logger.addLog(new String("trying to read " + name_of_default_value + " from config"));

            if (default_value instanceof String) {
                return (T) jsonObject.getString(name_of_default_value);
            } else if (default_value instanceof Integer) {
                return (T) Integer.valueOf(jsonObject.getString(name_of_default_value));
            } else if (default_value instanceof Double) {
                return (T) Double.valueOf(jsonObject.getString(name_of_default_value));
            } else if (default_value instanceof Boolean) {
                return (T) Boolean.valueOf(jsonObject.getString(name_of_default_value));
            } else if (default_value instanceof Short) {
                return (T) Short.valueOf(jsonObject.getString(name_of_default_value));
            } else {
                logger.addLog("Unknown type-> " + default_value.getClass().getName(), logger.log_level.SEVERE);
                return default_value;
            }
        } catch (Exception e) {
            logger.addLog(new String("Can't read " + name_of_default_value + " from config"));
            logger.addLog(e.toString(), logger.log_level.WARNING);
            return default_value;
        }
    }

}