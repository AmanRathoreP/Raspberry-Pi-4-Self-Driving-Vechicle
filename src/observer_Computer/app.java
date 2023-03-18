/*
 * This is the main app file which combines all the classes to provide the best performance
 */

/**
 * @author Aman Rathore
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import src.scenes.Scene_home_panel;
import src.scenes.Scene_log_panel;
import src.scenes.Scene_stream_via_establishing_socket;
import src.scenes.Scene_stream_via_local_file;
import src.scenes.Scene_about_panel;
import src.scenes.Scene_usage_panel;
import src.others.my_logger;
import src.others.my_literals;
import src.others.basic_utilities;

public class app extends JFrame implements Runnable {
    /**
     * @author Aman Rathore
     */
    private static final long serialVersionUID = 1L;

    private JPanel contentPanel;
    private Scene_log_panel scene_log_panel;
    private Scene_home_panel scene_home_panel;
    private Scene_stream_via_establishing_socket scene_stream_via_establishing_socket;
    private Scene_stream_via_local_file scene_stream_via_local_file;
    private Scene_about_panel scene_about_panel;
    private Scene_usage_panel scene_usage_panel;
    private basic_utilities file_reader = new basic_utilities(false);

    private my_logger logger;

    public static void main(String[] args) throws InterruptedException {

        new app();
    }

    public app() {
        super("My App");
        try {
            System.out.println(my_literals.update_literals(true));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.addLog(e.toString(), logger.log_level.WARNING);
            // e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.addLog(e.toString(), logger.log_level.WARNING);
            // e.printStackTrace();
        }
        this.logger = new my_logger();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize((Integer) my_literals.CONSTANTS.get("MAIN WINDOW WIDTH"),
                (Integer) my_literals.CONSTANTS.get("MAIN WINDOW HEIGHT"));
        setLocationRelativeTo(null);

        // Create reusable menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu menu_actions = new JMenu("Actions");
        JMenuItem menu_item_exit = new JMenuItem("Exit from all instances of app");
        menu_item_exit.addActionListener(e -> System.exit(0));
        menu_actions.add(menu_item_exit);
        JMenuItem menu_item_new_window = new JMenuItem("New Window");
        menu_actions.add(menu_item_new_window);
        menu_item_new_window
                .addActionListener(e -> new app());
        JMenuItem menu_item_reset_config = new JMenuItem("Reset Config File");
        menu_actions.add(menu_item_reset_config);
        menu_item_reset_config.addActionListener(e -> {
            if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
                    "If the configuration file is not present this action will create that file.\r\n"
                            + "If file is present but lacking with some of the properties then it will\r\n"
                            + "repair the file using variables from the running instance of app\r\n"
                            + "If you really want to reset the config file totally then first delete it then run the app and then create it form this option",
                    "Warning!",
                    JOptionPane.OK_CANCEL_OPTION)) {
                try {
                    my_literals.reset_config();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    logger.addLog(e1.toString(), Level.WARNING);
                    e1.printStackTrace();
                }
            } else {
                // * Do not create any configuration file
                logger.addLog("Not creating any type of configuration file it is been cancelled by the user!");
            }
        });

        JMenu menu_navigate = new JMenu("Navigate");
        JMenuItem menu_item_for_home_screen_panel = new JMenuItem("Home");
        menu_item_for_home_screen_panel.addActionListener(e -> show_scene("Home Scene"));
        menu_navigate.add(menu_item_for_home_screen_panel);
        JMenuItem menu_item_for_scene_log_panel = new JMenuItem("Logs");
        menu_item_for_scene_log_panel.addActionListener(e -> show_scene("Log Scene"));
        menu_navigate.add(menu_item_for_scene_log_panel);

        JMenu menu_item_for_streaming = new JMenu("Streaming");
        menu_navigate.add(menu_item_for_streaming);
        JMenuItem menu_item_for_scene_stream_via_establishing_socket = new JMenuItem("Via Socket");
        menu_item_for_scene_stream_via_establishing_socket
                .addActionListener(e -> show_scene("Socket Server Streaming Scene"));
        menu_item_for_streaming.add(menu_item_for_scene_stream_via_establishing_socket);
        JMenuItem menu_item_for_scene_stream_via_local_file = new JMenuItem("Via Local File");
        menu_item_for_scene_stream_via_local_file.addActionListener(e -> show_scene("Local File Streaming Scene"));
        menu_item_for_streaming.add(menu_item_for_scene_stream_via_local_file);

        JMenu menu_help = new JMenu("Help");
        JMenuItem menu_item_for_scene_usage_panel = new JMenuItem("Usage");
        menu_help.add(menu_item_for_scene_usage_panel);
        menu_item_for_scene_usage_panel
                .addActionListener(e -> show_scene("Usage Info Scene"));
        JMenuItem menu_item_for_scene_about_panel = new JMenuItem("About");
        menu_help.add(menu_item_for_scene_about_panel);
        menu_item_for_scene_about_panel
                .addActionListener(e -> show_scene("About Info Scene"));

        menuBar.add(menu_actions);
        menuBar.add(menu_navigate);
        menuBar.add(menu_help);
        setJMenuBar(menuBar);

        // Create content panel with card layout
        contentPanel = new JPanel(new CardLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        scene_log_panel = new Scene_log_panel();
        scene_home_panel = new Scene_home_panel((Integer) my_literals.CONSTANTS.get("MAIN WINDOW WIDTH"),
                (Integer) my_literals.CONSTANTS.get("MAIN WINDOW HEIGHT"),
                100, 10, 5, "This is a home screen");
        new Thread(scene_home_panel).start();
        scene_stream_via_establishing_socket = new Scene_stream_via_establishing_socket();
        Thread thread_for_scene_stream_via_establishing_socket = new Thread(scene_stream_via_establishing_socket);
        thread_for_scene_stream_via_establishing_socket.start();
        scene_stream_via_local_file = new Scene_stream_via_local_file();
        scene_about_panel = new Scene_about_panel();
        scene_usage_panel = new Scene_usage_panel();

        contentPanel.add(scene_home_panel, "Home Scene");
        contentPanel.add(scene_log_panel, "Log Scene");
        contentPanel.add(scene_stream_via_establishing_socket, "Socket Server Streaming Scene");
        contentPanel.add(scene_stream_via_local_file, "Local File Streaming Scene");
        contentPanel.add(scene_about_panel, "About Info Scene");
        contentPanel.add(scene_usage_panel, "Usage Info Scene");

        // * Show initial scene
        show_scene("Socket Server Streaming Scene");
        // * setting the visibility to true
        SwingUtilities.invokeLater(() -> this.setVisible(true));
        new Thread(this).start();
    }

    private void show_scene(String scene_name) {
        ((CardLayout) (contentPanel.getLayout())).show(contentPanel, scene_name);
    }

    @Override
    public void run() {
        while (true) {
            try {
                scene_log_panel.appendLog(file_reader.get_received_data());
            } catch (BadLocationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
