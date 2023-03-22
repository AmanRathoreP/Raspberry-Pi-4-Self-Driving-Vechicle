/*
create me a Scene_configuration_panel in java swing which will display all configurations options from a map 


below are some requirements it should have a search panel which should avoid accent
 it should be responsive 
  search bar at the top
   add all the options below the search bar and options must be in alphabetic order 
   for the integer it should have those up down arrows for the bool it should have that drop down for hex colors it should have a color picker and for string a text area
    it must have a scroll bar to if options are too much 
    do not forget that search option
    also add a function which will return a map when called publicly containing all the content shown in the option pane with their values
    and do not write name searchPanel or SearchPanel but use names like search_panel 
    */
package observer_java_GUI.src.scenes;
/*
 * @author Aman Rathore
 */

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import observer_java_GUI.src.others.my_json_reader_writer;
import observer_java_GUI.src.others.my_literals;

public class Scene_configuration_panel extends JPanel {

    private Map<String, Object> config_map;
    private JScrollPane scroll_pane;
    private JPanel options_panel;
    private JTextField searchField;

    public Scene_configuration_panel() {
        this.config_map = my_literals.CONSTANTS;
        setLayout(new BorderLayout());

        // Search panel
        JPanel search_panel = new JPanel();
        search_panel.setLayout(new BoxLayout(search_panel, BoxLayout.X_AXIS));
        search_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchField = new JTextField();
        search_panel.add(new JLabel("Search settings here>>> "));
        searchField.getDocument().addDocumentListener(new Search_Field_Listener());
        search_panel.add(searchField);

        // Options panel
        options_panel = new JPanel();
        options_panel.setLayout(new BoxLayout(options_panel, BoxLayout.Y_AXIS));
        options_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        refreshOptionsPanel();
        scroll_pane = new JScrollPane(options_panel);

        // JPanel button_panel = new JPanel(new GridLayout(6, 0, 0, 8));
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));
        JButton export_button = new JButton("Export");
        JButton save_button = new JButton("Save");
        export_button.setSize(250, 100);
        save_button.setSize(250, 100);
        button_panel.add(Box.createVerticalStrut(10));
        search_panel.add(export_button);
        button_panel.add(Box.createVerticalStrut(10));
        search_panel.add(save_button);
        button_panel.add(Box.createVerticalStrut(10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(4, 15, 4, 15));

        // * */ Add components to main panel
        add(search_panel, BorderLayout.NORTH);
        add(scroll_pane, BorderLayout.CENTER);

        // * add listeners to the components
        save_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigMap();
                my_literals.CONSTANTS = config_map;
                short option = (short) JOptionPane.showOptionDialog(null,
                        "Restart app to save changes!",
                        "Restart conformation",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[] { "Cancel", "Close app" }, "default");
                try {
                    my_json_reader_writer.write_json_to_file(my_literals.CONFIG_FILE_PATH, config_map);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (option == JOptionPane.NO_OPTION)
                    System.exit(0);
            }
        });
        export_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfigMap();
                open_file_explorer_and_write_file(my_json_reader_writer.get_string_to_write(config_map));

            }
        });

    }

    private void refreshOptionsPanel() {
        options_panel.removeAll();
        String search_query = searchField.getText().toLowerCase();
        SortedSet<String> sorted_keys = new TreeSet<>(config_map.keySet());
        for (String key : sorted_keys) {
            if (key.toLowerCase().contains(search_query)) {
                Object value = config_map.get(key);
                JPanel option_panel = new JPanel();
                option_panel.setLayout(new BoxLayout(option_panel, BoxLayout.X_AXIS));
                option_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
                Box.createHorizontalGlue();
                JLabel label = new JLabel(key);
                option_panel.add(label);
                if (value instanceof Boolean) {
                    JComboBox<Boolean> bool_combo = new JComboBox<>(new Boolean[] { false, true });
                    bool_combo.setSelectedItem(value);
                    bool_combo.addActionListener(new Bool_Combo_Listener(key));
                    option_panel.add(bool_combo);
                    option_panel.add(Box.createVerticalStrut(25));
                } else if (value instanceof Integer) {
                    JSpinner spinner = new JSpinner(
                            new SpinnerNumberModel((int) value, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
                    spinner.addChangeListener(new Integer_Spinner_Listener(key));
                    option_panel.add(spinner);
                    option_panel.add(Box.createVerticalStrut(25));
                } else if (value instanceof String && ((String) value).startsWith("#")) {
                    Color color = Color.decode((String) value);
                    JPanel color_panel = new JPanel();
                    color_panel.setBackground(color);
                    color_panel.setMaximumSize(new Dimension(10000, 30));
                    color_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    JButton color_button = new JButton("Pick color");
                    color_button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            ColorChooserDialog dialog = new ColorChooserDialog(new JFrame());
                            dialog.setVisible(true);
                            Color color = dialog.getColor();
                            color_panel.setBackground(color);
                        }
                    });
                    option_panel.add(color_panel);
                    option_panel.add(Box.createHorizontalStrut(25));
                    option_panel.add(color_button);
                    option_panel.add(Box.createVerticalStrut(25));
                } else if (value instanceof String) {
                    JTextArea text_area_for_string = new JTextArea((String) value);
                    text_area_for_string.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    text_area_for_string.setLineWrap(false);
                    text_area_for_string.setMaximumSize(new Dimension(10000, 22));
                    text_area_for_string.setRows(1);
                    text_area_for_string.setAlignmentX(Component.LEFT_ALIGNMENT);
                    option_panel.add(text_area_for_string, BorderLayout.CENTER);
                    option_panel.add(Box.createVerticalStrut(25));
                } else {
                    // TODO: do something
                }
                options_panel.add(option_panel);
            }
        }
        options_panel.revalidate();
        options_panel.repaint();
    }

    private class Search_Field_Listener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {

            refreshOptionsPanel();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            refreshOptionsPanel();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            refreshOptionsPanel();
        }
    }

    private class Bool_Combo_Listener implements ActionListener {
        private String key;

        public Bool_Combo_Listener(String key) {
            this.key = key;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Boolean> combo = (JComboBox<Boolean>) e.getSource();
            boolean value = combo.getItemAt(combo.getSelectedIndex());
            config_map.put(key, value);
        }
    }

    private class Integer_Spinner_Listener implements ChangeListener {
        private String key;

        public Integer_Spinner_Listener(String key) {
            this.key = key;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            int value = (int) spinner.getValue();
            config_map.put(key, value);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // TODO Auto-generated method stub
        return new Dimension(16 * 60, 9 * 60);
    }

    private void saveConfigMap() {
        Component[] components = options_panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel option_panel = (JPanel) component;
                JLabel label = (JLabel) option_panel.getComponent(0);
                String key = label.getText();
                Object value = null;
                Component valueComponent = option_panel.getComponent(1);
                if (valueComponent instanceof JComboBox) {
                    JComboBox comboBox = (JComboBox) valueComponent;
                    value = comboBox.getSelectedItem();
                } else if (valueComponent instanceof JSpinner) {
                    JSpinner spinner = (JSpinner) valueComponent;
                    value = spinner.getValue();
                } else if (valueComponent instanceof JPanel) {
                    JPanel color_panel = (JPanel) valueComponent;
                    Color color = color_panel.getBackground();
                    value = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
                } else if (valueComponent instanceof JTextArea) {
                    JTextArea text_area = (JTextArea) valueComponent;
                    value = text_area.getText();
                }
                config_map.put(key, value);
            }
        }
    }

    public void open_file_explorer_and_write_file(String string_to_save) {
        File selected_file = null;
        if (!(Desktop.isDesktopSupported()
                && (!((Boolean) (my_literals.CONSTANTS
                        .get("USE JAVA SWING IN-BUILD FILE EXPLORER")))))) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selected_file = fileChooser.getSelectedFile();
            }
        } else {
            FileDialog fileDialog = new FileDialog((new javax.swing.JFrame()), "Save File", FileDialog.SAVE);
            fileDialog.setVisible(true);
            if (fileDialog.getFile() != null) {
                selected_file = new File(fileDialog.getDirectory(), fileDialog.getFile());
            }
        }

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
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                            new StringSelection(absolute_file_path),
                            null);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error:\n" + e.toString(), "Can't save to file!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}

class ColorChooserDialog extends JDialog {
    private Color color;
    private JColorChooser colorChooser;

    public ColorChooserDialog(JFrame parent) {
        super(parent, "Pick color", true);

        colorChooser = new JColorChooser();
        color = colorChooser.getColor();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            color = colorChooser.getColor();
            setVisible(false);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        getContentPane().add(colorChooser, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public Color getColor() {
        return color;
    }
}