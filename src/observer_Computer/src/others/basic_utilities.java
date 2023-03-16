/**
 * This file contains an important class named basic utilities which implements the threading class of java and helps in jobs like getting data from the master device
 */
package src.others;

import java.lang.Thread;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class basic_utilities implements Runnable {
    public String received_data = "No data";
    private Socket socket;
    public boolean socket_connected = false;

    public basic_utilities() {
        Thread thread = new Thread(this);
        thread.start();
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
        try {
            FileWriter fileWriter = new FileWriter("streaming_data.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(string_to_append + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while appending the data to the file.");
            e.printStackTrace();
        }
    }
}
