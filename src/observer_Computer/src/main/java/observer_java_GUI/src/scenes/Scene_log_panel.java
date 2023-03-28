/**
 * 
 */
package observer_java_GUI.src.scenes;

/**
 * @author Aman Rathore
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import java.util.Map;
import java.util.HashMap;

import observer_java_GUI.src.others.my_literals;

public class Scene_log_panel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTextArea logTextArea;
    private JButton button_pause_resume_logging;
    dynamic_combo_box_sort_of_list combo_box_for_clearing_logs;
    dynamic_combo_box_sort_of_list combo_box_for_copying_logs;
    dynamic_combo_box_sort_of_list combo_box_for_saving_logs;
    SpinnerModel spinner_model_for_log_capacity;
    JSpinner spinner_for_log_capacity;
    private int number_of_logs_on_log_screen = (int) my_literals.CONSTANTS.get("DEFAULT NUMBER OF LOGS IN LOG WINDOW");
    public boolean log_stuff = true;
    private String log_buffer = "";

    public Scene_log_panel() {
        super(new BorderLayout());

        button_pause_resume_logging = new JButton("Pause/Resume Logging");
        button_pause_resume_logging.addActionListener(e -> {
            if (log_stuff)
                log_stuff = false;
            else
                log_stuff = true;
        });

        Map<String, Runnable> clear_logs_options = new HashMap<>();
        clear_logs_options.put("Clear all Logs", () -> clear_all_logs());
        clear_logs_options.put("Clear Buffer Logs", () -> clear_buffer_logs());
        clear_logs_options.put("Clear Screen Logs", () -> clear_on_screen_logs());
        combo_box_for_clearing_logs = new dynamic_combo_box_sort_of_list(clear_logs_options);
        Map<String, Runnable> copy_logs_options = new HashMap<>();
        copy_logs_options.put("Copy all Logs", () -> copy_all_logs_to_clipboard());
        copy_logs_options.put("Copy Buffer Logs", () -> copy_buffer_logs_to_clipboard());
        copy_logs_options.put("Copy Screen Logs", () -> copy_on_screen_logs_to_clipboard());
        combo_box_for_copying_logs = new dynamic_combo_box_sort_of_list(copy_logs_options);
        Map<String, Runnable> save_logs_options = new HashMap<>();
        save_logs_options.put("Save all Logs", () -> save_all_logs_to_file());
        save_logs_options.put("Save Buffer Logs", () -> save_buffer_logs_to_file());
        save_logs_options.put("Save Screen Logs", () -> save_on_screen_logs_to_file());
        combo_box_for_saving_logs = new dynamic_combo_box_sort_of_list(save_logs_options);

        spinner_model_for_log_capacity = new SpinnerNumberModel(
                (int) my_literals.CONSTANTS.get("DEFAULT NUMBER OF LOGS IN LOG WINDOW"),
                (int) my_literals.CONSTANTS.get("MINIMUM NUMBER OF LOGS IN LOG WINDOW"),
                (int) my_literals.CONSTANTS.get("MAXIMUM NUMBER OF LOGS IN LOG WINDOW"),
                (int) my_literals.CONSTANTS.get("STEPS TO TAKE WHILE INCREMENTING THE NUMBER OF LOGS IN LOG WINDOW"));
        spinner_for_log_capacity = new JSpinner(spinner_model_for_log_capacity);

        spinner_for_log_capacity.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                number_of_logs_on_log_screen = (int) spinner_for_log_capacity.getValue();
            }
        });

        JPanel panel_for_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel_for_buttons.add(spinner_for_log_capacity);
        panel_for_buttons.add(combo_box_for_clearing_logs);
        panel_for_buttons.add(combo_box_for_copying_logs);
        panel_for_buttons.add(combo_box_for_saving_logs);
        panel_for_buttons.add(button_pause_resume_logging);

        add(panel_for_buttons, BorderLayout.SOUTH);

        // Create a border with a title for the panel
        Border border = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1);
        Border titledBorder = BorderFactory.createTitledBorder(border, "Log",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14), Color.BLUE);
        setBorder(titledBorder);

        // Create a text area to display logs
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        // Add a scroll pane to the text area for scrolling
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);
        new Thread(this).start();
    }

    public void appendLog(String message) throws BadLocationException {
        /**
         * Appends a log message to the text area.
         */
        // TODO: Stop the logging process when user is scrolling the logs
        // TODO: Also add a keyboard shortcut to stop the logging process while
        // scrolling
        // TODO: add tooltip on every msg so that when any log is appeded to the log
        // panel and when hover it shows that when that msg was added
        if (log_stuff) {
            logTextArea.append(log_buffer + message + "\n");
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
            // Remove oldest logs if maximum number of lines is exceeded
            int numLines = logTextArea.getLineCount();
            if (numLines > number_of_logs_on_log_screen)
                logTextArea.replaceRange("", logTextArea.getLineStartOffset(0),
                        logTextArea.getLineEndOffset(
                                numLines - number_of_logs_on_log_screen));

            log_buffer = "";
        } else
            log_buffer = log_buffer + message + "\n";

    }

    public void clear_buffer_logs() {
        log_buffer = "";
    }

    public void clear_on_screen_logs() {
        logTextArea.setText("");
    }

    public void clear_all_logs() {
        clear_buffer_logs();
        clear_on_screen_logs();
    }

    private void copy_to_clipboard(String text_to_copy) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text_to_copy), null);

    }

    public void copy_buffer_logs_to_clipboard() {
        copy_to_clipboard(log_buffer);
    }

    public void copy_on_screen_logs_to_clipboard() {
        copy_to_clipboard(logTextArea.getText());
    }

    public void copy_all_logs_to_clipboard() {
        copy_to_clipboard(logTextArea.getText() + log_buffer);
    }

    public void save_buffer_logs_to_file() {
        save_logs_to_file(log_buffer);
    }

    public void save_on_screen_logs_to_file() {
        save_logs_to_file(logTextArea.getText());
    }

    public void save_all_logs_to_file() {
        save_logs_to_file(logTextArea.getText() + log_buffer);
    }

    private void save_logs_to_file(String string_to_save) {
        File selected_file = null;
        JFileChooser file_chooser = new JFileChooser();
        file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file_chooser.setMultiSelectionEnabled(false);
        int result = file_chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
            selected_file = file_chooser.getSelectedFile();

        if (selected_file != null) {
            try {
                FileWriter fileWriter = new FileWriter(selected_file);
                fileWriter.write(string_to_save);
                fileWriter.close();
                String absolute_file_path = selected_file.getAbsolutePath();

                Object[] options = { "Ok", "Open File", "Copy File Path" };
                int choice = JOptionPane.showOptionDialog(null,
                        "<html><body><a href=\"\">"
                                + absolute_file_path
                                + "</a></body></html>",
                        "Open file",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (choice == 1) {
                    try {
                        Desktop.getDesktop().open(new File(absolute_file_path));
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Error:\n" + e.toString(), "Can't open file!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (choice == 2)
                    copy_to_clipboard(absolute_file_path);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error:\n" + e.toString(), "Can't save to file!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(693, 1040);
    }

    @Override
    public void run() {
        while (true) {
            if (isVisible()) {
                try {
                    this.appendLog(observer_java_GUI.src.others.basic_utilities.get_received_data());
                } catch (BadLocationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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

class dynamic_combo_box_sort_of_list extends JComboBox<String> {

    private Map<String, Runnable> string_with_associated_function;

    public dynamic_combo_box_sort_of_list(Map<String, Runnable> string_with_associated_function) {
        super(string_with_associated_function.keySet().toArray(new String[0]));
        this.string_with_associated_function = string_with_associated_function;
    }

    @Override
    public void setSelectedItem(Object item_object) {
        super.setSelectedItem(item_object);
        String selected_item = item_object.toString();

        Runnable function = string_with_associated_function.get(selected_item);
        if (function != null) {
            function.run();
        }
    }
}