/**
 * This file contains an important class named basic utilities which implements the threading class of java and helps in jobs like getting data from the master device
 */
package observer_java_GUI.src.others;

/**
 * @author Aman Rathore
 *
 */

import java.lang.Thread;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class basic_utilities implements Runnable {
    public String received_data = "No data";
    private Socket socket;
    public boolean socket_connected = false;

    public basic_utilities() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public basic_utilities(boolean start_reading_socket) {
    }

    public void start_server(String port_number_for_socket) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.valueOf(port_number_for_socket));
        System.out.println("Server is listening on port " + port_number_for_socket);
        this.socket = serverSocket.accept();
        System.out.println("Socket connected");
        this.socket_connected = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // * Receive data from Python program continuously
                InputStream input = socket.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    this.received_data = new String(buffer, 0, bytesRead);
                    append_data_to_file(this.received_data);
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private static void append_data_to_file(String string_to_append) {
        append_string_data_only(DateTimeFormatter.ofPattern("hh:mm:ss.SSS a").format(LocalTime.now())
                + " --> "
                + string_to_append
                + "\n");
    }

    private static void append_string_data_only(String string_to_append) {
        /*
         * @apiNote this function will append that data to file as it is.
         */
        try {
            FileWriter fileWriter = new FileWriter("streaming_data.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string_to_append);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the data to the file.");
            e.printStackTrace();
        }
    }

    private static String read_data_from_file(String file_name) {
        String last_line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String line = reader.readLine();
            while (line != null) {
                last_line = line;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return last_line;
    }

    public static String get_received_data() {
        /*
         * gets data from the master via file saved by the socket using stuff
         */
        return read_data_from_file("streaming_data.txt");
    }

    public static void append_data_string_to_file_according_to_data_string_time_stamp(String long_full_string_of_data)
            throws ParseException, InterruptedException {

        String[] lines = long_full_string_of_data.split("\\r?\\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS a");
        Date prevTimestamp = null;

        for (String line : lines) {
            String[] parts = line.split(" --> ");
            Date time_stamp_of_data_string = dateFormat.parse(parts[0]);
            String data_string = parts[1];

            if (prevTimestamp != null)
                Thread.sleep(time_stamp_of_data_string.getTime() - prevTimestamp.getTime());

            append_string_data_only(parts[0]
                    + " --> "
                    + data_string
                    + "\n");
            prevTimestamp = time_stamp_of_data_string;
        }
    }
}
