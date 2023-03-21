/**
 * 
 */
package observer_java_GUI.src.scenes;

/**
 * @author Aman Rathore
 *
 */

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Desktop;
import java.awt.FileDialog;

import java.util.List;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import observer_java_GUI.src.others.my_literals;

import javax.swing.JPanel;

public class Scene_stream_via_local_file extends JPanel {
    /**
     * @author Aman Rathore
     *
     */
    private static JLabel label_selected_file;
    private static JTextField text_field_for_file_name;
    private static JButton button_select_file;
    private static JButton button_submit_file;
    private static JButton button_delete_file;
    private static File file_selected;
    private final int TEXT_FELID_SIZE_FOR_FILE_PATH_WIDTH = 400;
    private final int TEXT_FELID_SIZE_FOR_FILE_PATH_HEIGHT = 27;

    public Scene_stream_via_local_file() {
        // TODO: When clicking the browse button the java swing's inbuild filemenu is
        // TODO: comming replace it with the OS's default file menu
        // TODO: Create a toggle button which will toggle weather to loop the file
        // TODO: provide at EOF or to give some random or null data
        super(new BorderLayout());
        label_selected_file = new JLabel("Selected file:");
        text_field_for_file_name = new JTextField();
        text_field_for_file_name.setEditable(false);
        text_field_for_file_name.setText("Add file path here");
        text_field_for_file_name.setPreferredSize(new Dimension(TEXT_FELID_SIZE_FOR_FILE_PATH_WIDTH,
                TEXT_FELID_SIZE_FOR_FILE_PATH_HEIGHT));
        text_field_for_file_name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                text_field_for_file_name.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                text_field_for_file_name.replaceSelection("");
            }
        });

        // button_select_file = new JButton("Select File\nDrag & Drop also supported");
        button_select_file = new JButton("<html>Select File<br>Drag & Drop also supported</html>");
        text_field_for_file_name.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable t = evt.getTransferable();
                    List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                    if (files.size() == 1) {
                        File file = files.get(0);
                        if (is_file_type_right(file)) {
                            file_selected = file;
                            text_field_for_file_name.setText(file.getAbsolutePath());
                        } else {
                            file_selected = null;
                            text_field_for_file_name.setText("");
                            show_error_message("Invalid file type. Please select a text or CSV file.");
                        }
                    } else {
                        show_error_message("Please drop only one file at a time.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    show_error_message("Error: " + ex.getMessage());
                }
            }
        });
        button_select_file.addActionListener(e -> {
            if (Desktop.isDesktopSupported()
                    && (!((Boolean) (my_literals.CONSTANTS
                            .get("USE JAVA SWING IN-BUILD FILE EXPLORER"))))) {
                FileDialog file_dialog = new FileDialog((new javax.swing.JFrame()), "Select File", FileDialog.LOAD);
                file_dialog.setVisible(true);
                if (file_dialog.getFile() != null) {
                    File file = new File(file_dialog.getDirectory(), file_dialog.getFile());
                    if (is_file_type_right(file)) {
                        file_selected = file;
                        text_field_for_file_name.setText(file.getAbsolutePath());
                    } else {
                        file_selected = null;
                        text_field_for_file_name.setText("");
                        show_error_message("Invalid file type. Please select a text or CSV file.");
                    }
                }
            } else {
                JFileChooser file_chooser = new JFileChooser();
                file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                file_chooser.setMultiSelectionEnabled(false);
                int result = file_chooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = file_chooser.getSelectedFile();
                    if (is_file_type_right(file)) {
                        file_selected = file;
                        text_field_for_file_name.setText(file.getAbsolutePath());
                    } else {
                        file_selected = null;
                        text_field_for_file_name.setText("");
                        show_error_message("Invalid file type. Please select a text or CSV file.");
                    }
                }
            }
        });

        button_submit_file = new JButton("Submit");
        button_submit_file.addActionListener(e -> {
            if (file_selected != null) {
                StringBuilder string_builder = new StringBuilder();
                // Do something with the selected file
                try (BufferedReader reader = new BufferedReader(new FileReader(file_selected))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        string_builder.append(line);
                        string_builder.append(System.lineSeparator());
                    }
                } catch (IOException ex) {
                    show_error_message("Error reading file: " + ex.getMessage());
                }
                Thread thread_for_adding_data_to_file = new Thread(() -> {
                    try {
                        observer_java_GUI.src.others.basic_utilities
                                .append_data_string_to_file_according_to_data_string_time_stamp(
                                        string_builder.toString());
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                });
                thread_for_adding_data_to_file.start();
            } else {
                show_error_message("Please select a file.");
            }
        });

        // Create the delete button
        button_delete_file = new JButton("Delete");
        button_delete_file.addActionListener(e -> {
            file_selected = null;
            text_field_for_file_name.setText("Add file path here");
        });

        JPanel panel_for_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // panel_for_buttons.add(button_select_file);
        panel_for_buttons.add(button_submit_file);
        panel_for_buttons.add(button_delete_file);

        JPanel panel_for_file_selection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Border border = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel_for_file_selection.setBorder(border);
        panel_for_file_selection.add(label_selected_file);
        panel_for_file_selection.add(text_field_for_file_name);
        panel_for_file_selection.add(button_select_file, BorderLayout.NORTH);

        add(panel_for_file_selection, BorderLayout.CENTER);
        add(panel_for_buttons, BorderLayout.SOUTH);

        setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = evt.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        if (files.size() == 1) {
                            File file = files.get(0);
                            if (is_file_type_right(file)) {
                                file_selected = file;
                                text_field_for_file_name.setText(file.getAbsolutePath());
                            } else {
                                file_selected = null;
                                text_field_for_file_name.setText("");
                                show_error_message("Invalid file type. Please drop a text or CSV file.");
                            }
                        } else {
                            file_selected = null;
                            text_field_for_file_name.setText("");
                            show_error_message("Please drop only one file.");
                        }
                    }
                } catch (Exception ex) {
                    show_error_message("Error dropping file: " + ex.getMessage());
                }
            }
        });
    }

    private boolean is_file_type_right(File file) {
        String name = file.getName();
        return name.endsWith(".txt") || name.endsWith(".csv");
    }

    private void show_error_message(String message) {
        text_field_for_file_name.setForeground(Color.RED);
        text_field_for_file_name.setText(message);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(618, 262);
    }
}
