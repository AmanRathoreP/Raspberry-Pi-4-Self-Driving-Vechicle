/*
 * This file contains a class to record any panel specifically chart panel
 */
package observer_java_GUI.src.charts_panels;
/*
 * @author Aman Rathore
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class panel_recorder extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton button_start_recording;
    private final JButton button_stop_recording;
    private final JLabel label_file_name;
    private final JTextField text_field_file_name;
    private boolean is_recording = false;
    private Thread recorder_thread;
    private JPanel panel_to_record;
    private final JSpinner spinner_fps;
    private final static String[] symbols_to_exclude = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };

    public panel_recorder(JPanel panel_to_record) {
        super(new FlowLayout());
        this.panel_to_record = panel_to_record;
        button_start_recording = new JButton("Start Recording!");
        button_stop_recording = new JButton("Stop Recording!");
        label_file_name = new JLabel("File Name > ");
        text_field_file_name = new JTextField(30);
        spinner_fps = new JSpinner(new SpinnerNumberModel(10, 1, 30, 3));
        spinner_fps.setToolTipText("FPS for recording");
        spinner_fps.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = (int) spinner_fps.getValue();
                int max = (int) ((SpinnerNumberModel) spinner_fps.getModel()).getMaximum();
                if (value > max) {
                    spinner_fps.setValue(max);
                }
            }
        });

        button_start_recording.addActionListener(e -> {
            deal_with_recording();
            is_recording = true;
            button_stop_recording.setEnabled(true);
            button_start_recording.setEnabled(false);
            text_field_file_name.setEditable(false);
            // spinner_fps.setEditor(false);
        });
        button_stop_recording.setEnabled(false);
        text_field_file_name.setToolTipText(
                "<html><table><thead><tr><th>Symbol</th><th>Meaning</th><th>Presentation</th><th>Examples</th></tr></thead><tbody><tr><td>G</td><td>era</td><td>text</td><td>AD; Anno Domini; A</td></tr><tr><td>u</td><td>year</td><td>year</td><td>2004; 04</td></tr><tr><td>y</td><td>year-of-era</td><td>year</td><td>2004; 04</td></tr><tr><td>D</td><td>day-of-year</td><td>number</td><td>189</td></tr><tr><td>M/L</td><td>month-of-year</td><td>number/text</td><td>7; 07; Jul; July; J</td></tr><tr><td>d</td><td>day-of-month</td><td>number</td><td>10</td></tr><tr><td>Q/q</td><td>quarter-of-year</td><td>number/text</td><td>3; 03; Q3; 3rd quarter</td></tr><tr><td>Y</td><td>week-based-year</td><td>year</td><td>1996; 96</td></tr><tr><td>w</td><td>week-of-week-based-year</td><td>number</td><td>27</td></tr><tr><td>W</td><td>week-of-month</td><td>number</td><td>4</td></tr><tr><td>E</td><td>day-of-week</td><td>text</td><td>Tue; Tuesday; T</td></tr><tr><td>e/c</td><td>localized day-of-week</td><td>number/text</td><td>2; 02; Tue; Tuesday; T</td></tr><tr><td>F</td><td>week-of-month</td><td>number</td><td>3</td></tr><tr><td>a</td><td>am-pm-of-day</td><td>text</td><td>PM</td></tr><tr><td>h</td><td>clock-hour-of-am-pm</td><td>(1-12) number</td><td>12</td></tr><tr><td>K</td><td>hour-of-am-pm</td><td>(0-11) number</td><td>0</td></tr><tr><td>k</td><td>clock-hour-of-am-pm</td><td>(1-24) number</td><td>0</td></tr><tr><td>H</td><td>hour-of-day</td><td>(0-23) number</td><td>0</td></tr><tr><td>m</td><td>minute-of-hour</td><td>number</td><td>30</td></tr><tr><td>s</td><td>second-of-minute</td><td>number</td><td>55</td></tr><tr><td>S</td><td>fraction-of-second</td><td>fraction</td><td>978</td></tr><tr><td>A</td><td>milli-of-day</td><td>number</td><td>1234</td></tr><tr><td>n</td><td>nano-of-second</td><td>number</td><td>987654321</td></tr><tr><td>N</td><td>nano-of-day</td><td>number</td><td>1234000000</td></tr><tr><td>V</td><td>time-zone ID</td><td>zone-id</td><td>America/Los_Angeles; Z; -08:30</td></tr><tr><td>z</td><td>time-zone name</td><td>zone-name</td><td>Pacific Standard Time; PST</td></tr><tr><td>O</td><td>localized zone-offset</td><td>offset-O</td><td>GMT+8; GMT+08:00; UTC-08:00;</td></tr><tr><td>X</td><td>zone-offset 'Z' for zero</td><td>offset-X</td><td>Z; -08; -0830; -08:30; -083015; -08:30:15;</td></tr><tr><td>x</td><td>zone-offset</td><td>offset-x</td><td>+0000; -08; -0830; -08:30; -083015; -08:30:15;</td></tr><tr><td>Z</td><td>zone-offset</td><td>offset-Z</td><td>+0000; -0800; -08:00;</td></tr></tbody></table></html>");
        button_stop_recording.addActionListener(e -> {
            is_recording = false;
            button_start_recording.setEnabled(true);
            button_stop_recording.setEnabled(false);
            text_field_file_name.setEditable(true);
            ;
        });
        text_field_file_name.setText(
                "Frame from ${yyyy-MM-dd}; ${MMMM}, ${EEEE} at time ${HH-mm-ss.SSS}");
        add(label_file_name);
        add(text_field_file_name);
        add(button_start_recording);
        add(button_stop_recording);
        add(new JLabel("Fps = "));
        add(spinner_fps);
    }

    private void deal_with_recording() {
        // TODO: Deal with the flickering of the Panel while recording
        String file_name = text_field_file_name.getText();
        for (String symbol : symbols_to_exclude) {
            if (file_name.contains(symbol)) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid input, You can't use Symbols like\n" + Arrays.toString(symbols_to_exclude));
                return;
            }
        }
        try {
            format_string_with_time(file_name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int width = panel_to_record.getWidth();
        int height = panel_to_record.getHeight();

        // Create a new image with the same size as the panel
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        recorder_thread = new Thread(() -> {
            while (is_recording) {
                Graphics g = image.getGraphics();
                panel_to_record.paint(g);
                try {
                    ImageIO.write(image, "png", new File(format_string_with_time(file_name) + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep((int) (1000 / (int) spinner_fps.getValue()));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            recorder_thread.interrupt();
        });
        recorder_thread.start();

    }

    private static String format_string_with_time(String string_with_time_specifiers) {
        String output = "";
        int startIndex = 0;
        int endIndex = 0;
        while (startIndex < string_with_time_specifiers.length()
                && (startIndex = string_with_time_specifiers.indexOf("${", endIndex)) != -1) {
            output += string_with_time_specifiers.substring(endIndex, startIndex);
            endIndex = string_with_time_specifiers.indexOf("}", startIndex);
            String value = string_with_time_specifiers.substring(startIndex + 2, endIndex);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(value);
            output += LocalDateTime.now().format(formatter);
            endIndex++;
        }
        return output;
    }

}
