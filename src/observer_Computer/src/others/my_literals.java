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
    // TODO: When some variables are not there in json file; app dosen't runs at all
    // TODO: Create a buton to creat configuration file whenever user wants

    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String CONFIG_FILE_PATH = Paths.get(System.getProperty("user.dir"), CONFIG_FILE_NAME)
            .toString();
    private final static my_logger logger = new my_logger("logs\\logs.log");
    private static boolean USE_CONFIG_FILE = true;

    /* constants started */
    public static int WINDOW_SIZE_WIDTH = 16 * 75;
    public static int WINDOW_SIZE_HEIGHT = 9 * 75;
    /* constants finished */

    public static boolean update_literals(boolean force_use_of_config_file)
            throws FileNotFoundException, IOException {
        /**
         * @author Aman Rathore
         * @apiNote Update the value of the values of the constants according to the
         *          configuration file provided
         * @return true if the literals are updated according to config file
         * @return false if the literals are not updated according to config file due to
         *         some security or IO reasons etc.
         * @param force_use_of_config_file put it true if you want to create or use a
         *                                 config file forcefully(i.e. at any cost)
         * @throws FileNotFoundException if Json file is not present, use
         *                               force_create_json = True if want to create and
         *                               reset the configuration file
         * @throws IOException           if Json file can't be read/write may be due to
         *                               security reasons
         **/
        if (USE_CONFIG_FILE && force_use_of_config_file) {
            JSONObject jsonObject;
            try {
                jsonObject = readJsonFile();
                WINDOW_SIZE_WIDTH = Integer.parseInt(jsonObject.get("WINDOW_SIZE_WIDTH").toString());
                WINDOW_SIZE_HEIGHT = Integer.parseInt(jsonObject.get("WINDOW_SIZE_HEIGHT").toString());
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
         * @apiNote reset the values of the configuration file if the configuration file
         *          is not present it creates that file.
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
}