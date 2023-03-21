/*
 * 
 */
package observer_java_GUI.src.others;

/**
 * @author Aman Rathore
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class my_json_reader_writer {
    /**
     * @author Aman Rathore
     * @apiNote This class helps user to read an write on json
     * @warning it can't read any list from json but it can write it
     */

    public static void write_json_to_file(String json_file_name, Map<String, Object> jsonData) throws IOException {
        FileWriter file_writer = new FileWriter(json_file_name);
        BufferedWriter buffered_writer = new BufferedWriter(file_writer);

        buffered_writer.write("{");
        buffered_writer.newLine();

        int count = 0;
        for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            buffered_writer.write("  \"" + key + "\": ");

            if (value instanceof String) {
                buffered_writer.write("\"" + value + "\"");
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                buffered_writer.write("[");
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    if (element instanceof String) {
                        buffered_writer.write("\"" + element + "\"");
                    } else {
                        buffered_writer.write(element.toString());
                    }
                    if (i < list.size() - 1) {
                        buffered_writer.write(", ");
                    }
                }
                buffered_writer.write("]");
            } else {
                buffered_writer.write(value.toString());
            }

            count++;
            if (count < jsonData.size()) {
                buffered_writer.write(",");
            }
            buffered_writer.newLine();
        }

        buffered_writer.write("}");
        buffered_writer.close();
        file_writer.close();
    }

    public static Map<String, Object> read_json_from_file(String json_file_name) throws IOException {
        Map<String, Object> json_data_map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(json_file_name));
        StringBuilder string_builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            string_builder.append(line);
        }
        String json_string = string_builder.toString();
        json_string = json_string.trim();
        if (json_string.startsWith("{") && json_string.endsWith("}")) {
            json_string = json_string.substring(1, json_string.length() - 1);
            String[] key_value_pairs = json_string.split(",");
            for (String key_value_pair : key_value_pairs) {
                String[] key_value = key_value_pair.split(":");
                String key = key_value[0].trim().replace("\"", "");
                String value = key_value[1].trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                } else if (value.equals("true") || value.equals("false")) {
                    json_data_map.put(key, Boolean.valueOf(value));
                    continue;
                } else if (value.contains(".")) {
                    json_data_map.put(key, Double.valueOf(value));
                    continue;
                } else {
                    json_data_map.put(key, Integer.valueOf(value));
                    continue;
                }
                json_data_map.put(key, value);
            }
        } else {
            reader.close();
            throw new IllegalArgumentException("Invalid JSON format");
        }
        reader.close();
        return json_data_map;
    }
}