/**
 * This file contains an important class named basic utilities which implements the threading class of java and helps in jobs like getting data from the master device
 */
package src;

import java.lang.Thread;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class basic_utilities implements Runnable {
    private int port_number_for_socket;
    public String received_data = "No data";

    public basic_utilities(int port_number) {
        this.port_number_for_socket = port_number;
    }

    public basic_utilities() {
        this.port_number_for_socket = 5000;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port_number_for_socket);
            System.out.println("Server is listening on port " + this.port_number_for_socket);
            Socket socket = serverSocket.accept();
            System.out.println("Socket connected");

            // * Receive data from Python program continuously
            InputStream input = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                this.received_data = new String(buffer, 0, bytesRead);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
