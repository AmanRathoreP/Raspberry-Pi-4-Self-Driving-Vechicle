
/*
* This is the main app file which combines all the classes to provide the best performance
*/
package observer_java_GUI;

/**
 * @author Aman Rathore
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import observer_java_GUI.src.scenes.Scene_home_panel;
import observer_java_GUI.src.scenes.Scene_log_panel;
import observer_java_GUI.src.scenes.Scene_stream_via_establishing_socket;
import observer_java_GUI.src.scenes.Scene_stream_via_local_file;
import observer_java_GUI.src.scenes.Scene_about_panel;
import observer_java_GUI.src.scenes.Scene_usage_panel;
import observer_java_GUI.src.analytical_tools_panels.Scene_speed_panel;
import observer_java_GUI.src.analytical_tools_panels.Scene_modes_panel;
import observer_java_GUI.src.others.my_logger;
import observer_java_GUI.src.others.my_literals;

public class app extends JFrame {
    /**
     * @author Aman Rathore
     */
    private static final long serialVersionUID = 1L;

    private JPanel contentPanel;
    public static Map<String, JPanel> scenes_map = new HashMap<String, JPanel>();

    private my_logger logger;
    private static String current_opened_scene = "Modes Scene";

    public static void main(String[] args) throws InterruptedException {

        new app(current_opened_scene);
    }

    public app(String scene_to_open) {
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((Integer) my_literals.CONSTANTS.get("MAIN WINDOW WIDTH"),
                (Integer) my_literals.CONSTANTS.get("MAIN WINDOW HEIGHT"));
        setLocationRelativeTo(null);

        // Create reusable menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu_actions = new JMenu("Actions");
        JMenu menu_item_exit_options = new JMenu("Exit Options");
        JMenuItem menu_item_exit_all = new JMenuItem("Exit from all instances of app");
        menu_item_exit_all.addActionListener(e -> System.exit(0));
        JMenuItem menu_item_exit_current = new JMenuItem("Close current window of the app");
        menu_item_exit_current.addActionListener(e -> dispose());
        menu_item_exit_options.add(menu_item_exit_all);
        menu_item_exit_options.add(menu_item_exit_current);
        menu_actions.add(menu_item_exit_options);
        JMenuItem menu_item_new_window = new JMenuItem("New Window");
        menu_actions.add(menu_item_new_window);
        menu_item_new_window
                .addActionListener(e -> new app(scene_to_open));
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

        JMenu menu_analytical_tools = new JMenu("Analytical Tools");
        JMenuItem menu_item_for_scene_speed = new JMenuItem("Speed");
        menu_analytical_tools.add(menu_item_for_scene_speed);
        menu_item_for_scene_speed.addActionListener(e -> show_scene("Speed Scene"));
        JMenuItem menu_item_for_scene_modes = new JMenuItem("Modes");
        menu_analytical_tools.add(menu_item_for_scene_modes);
        menu_item_for_scene_modes.addActionListener(e -> show_scene("Modes Scene"));

        menuBar.add(menu_actions);
        menuBar.add(menu_navigate);
        menuBar.add(menu_analytical_tools);
        menuBar.add(menu_help);
        setJMenuBar(menuBar);

        // Create content panel with card layout
        contentPanel = new JPanel(new CardLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        scenes_map.put("Log Scene", new Scene_log_panel());
        scenes_map.put("Home Scene", new Scene_home_panel((Integer) my_literals.CONSTANTS.get("MAIN WINDOW WIDTH"),
                (Integer) my_literals.CONSTANTS.get("MAIN WINDOW HEIGHT"), 100, 10, 5, "This is a home screen"));
        scenes_map.put("Socket Server Streaming Scene", new Scene_stream_via_establishing_socket());
        scenes_map.put("Local File Streaming Scene", new Scene_stream_via_local_file());
        scenes_map.put("About Info Scene", new Scene_about_panel());
        scenes_map.put("Usage Info Scene", new Scene_usage_panel());
        scenes_map.put("Speed Scene", new Scene_speed_panel());
        scenes_map.put("Modes Scene", new Scene_modes_panel());

        contentPanel.add(scenes_map.get("Home Scene"), "Home Scene");
        contentPanel.add(scenes_map.get("Log Scene"), "Log Scene");
        contentPanel.add(scenes_map.get("Socket Server Streaming Scene"), "Socket Server Streaming Scene");
        contentPanel.add(scenes_map.get("Local File Streaming Scene"), "Local File Streaming Scene");
        contentPanel.add(scenes_map.get("About Info Scene"), "About Info Scene");
        contentPanel.add(scenes_map.get("Usage Info Scene"), "Usage Info Scene");
        contentPanel.add(scenes_map.get("Speed Scene"), "Speed Scene");
        contentPanel.add(scenes_map.get("Modes Scene"), "Modes Scene");

        // * Show initial scene
        show_scene(current_opened_scene);
        // * setting the visibility to true
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    private void show_scene(String scene_name) {
        ((CardLayout) (contentPanel.getLayout())).show(contentPanel, scene_name);
        setSize(scenes_map.get(scene_name).getPreferredSize());
        current_opened_scene = scene_name;
    }

}
