/*
 * This file contains a class to record any panel specifically chart panel
 */
package observer_java_GUI.src.charts_panels;
/*
 * @author Aman Rathore
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import observer_java_GUI.src.others.my_literals;

public class panel_recorder extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton button_start_recording;
    private final JButton button_stop_recording;
    private final JButton button_take_chart_image;
    private final JCheckBox checkbox_add_caption;
    private final JLabel label_file_name;
    private final JTextField text_field_file_name;
    private boolean is_recording = false;
    private Thread recorder_thread;
    private JPanel panel_to_record;
    private final JSpinner spinner_fps;
    private final static String[] symbols_to_exclude_form_file_path = { "*", "?", "\"", "<", ">", "|" };
    private final static String[] symbols_to_exclude_form_file_name = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };

    public panel_recorder(JPanel panel_to_record) {
        super(new FlowLayout());
        this.panel_to_record = panel_to_record;
        button_start_recording = new JButton("Start Recording!");
        button_stop_recording = new JButton("Stop Recording!");
        button_take_chart_image = new JButton("Take screenshot of chart!");
        label_file_name = new JLabel("File Name > ");
        text_field_file_name = new JTextField(30);
        checkbox_add_caption = new JCheckBox("Caption");
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
        button_take_chart_image.addActionListener(e -> {
            deal_with_screenshot();
        });
        text_field_file_name.setText(
                "Frame from ${yyyy-MM-dd}; ${MMMM}, ${EEEE} at time ${HH-mm-ss.SSS}");
        checkbox_add_caption.setToolTipText(
                "<html><body>Add caption to the frame?<br>Caption is just the <i>file name</i> itself!</body></html>");
        add(label_file_name);
        add(text_field_file_name);
        add(button_take_chart_image);
        add(button_start_recording);
        add(button_stop_recording);
        add(new JLabel("Fps = "));
        add(spinner_fps);
        add(checkbox_add_caption);
    }

    private void deal_with_recording() {
        // TODO: Deal with the flickering of the Panel while recording

        int result = JOptionPane.showConfirmDialog(this,
                "This won't encode frames to video\n" +
                        "it will just loop the screenshot taking procedure\n" +
                        "<html><body><i>IT IS ALSO GLITCHY</i></body></html>",
                "WARNING!",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result != JOptionPane.OK_OPTION)
            return;

        int fps_of_video = (int) spinner_fps.getValue();

        // Create a new image with the same size as the panel
        recorder_thread = new Thread(() -> {
            while (is_recording) {
                this.deal_with_screenshot();
                try {
                    Thread.sleep((int) (1000 / fps_of_video));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            recorder_thread.interrupt();
        });
        recorder_thread.start();

    }

    private void deal_with_screenshot() {
        String file_name = text_field_file_name.getText();
        String[] path_and_name = split_string_from_occurrence_of_last_dirty_symbol(file_name);
        for (String symbol : symbols_to_exclude_form_file_path) {
            if (path_and_name[0].contains(symbol)) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid input, You can't use Symbols like\n" + Arrays.toString(
                                symbols_to_exclude_form_file_path) + " in file path");
                return;
            }
        }
        for (String symbol : symbols_to_exclude_form_file_name) {
            if (path_and_name[1].contains(symbol)) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid input, You can't use Symbols like\n" + Arrays.toString(
                                symbols_to_exclude_form_file_name) + " in file name" + "\n" + path_and_name[1]);
                return;
            }
        }
        try {
            format_string_with_time(file_name);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new image with the same size as the panel
        BufferedImage image = new BufferedImage(panel_to_record.getWidth(), panel_to_record.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        panel_to_record.paint(g);
        path_and_name[0] = format_string_with_time(path_and_name[0]);
        path_and_name[1] = format_string_with_time(path_and_name[1]);
        String file_path = "";
        File file = new File(path_and_name[0]);
        if (path_and_name[0] == "")
            file_path = path_and_name[1] + ".png";
        else
            file_path = path_and_name[0] + "/" + path_and_name[1] + ".png";
        try {
            if (!file.exists())
                file.mkdirs();
            if (checkbox_add_caption.isSelected())
                image = add_text_to_image(image,
                        path_and_name[1],
                        Color.decode((String) my_literals.CONSTANTS.get("FONT COLOR FOR CHART'S CAPTION")),
                        Color.decode((String) my_literals.CONSTANTS.get("FONT'S OUTLINE COLOR FOR CHART'S CAPTION")),
                        new Font("Arial",
                                Font.PLAIN,
                                (int) my_literals.CONSTANTS.get("FONT SIZE FOR CHART'S CAPTION")),
                        new BasicStroke(((Double) my_literals.CONSTANTS.get("OUTLINE STROKE")).floatValue()));
            ImageIO.write(image, "png",
                    new File(file_path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    e.toString() + "\nPlease check you path and try again"
                            + "\nYour Path is \"" + path_and_name[0] + path_and_name[1] + "\"");
        } finally {
            g.dispose();
        }

    }

    private static String[] split_string_from_occurrence_of_last_dirty_symbol(String str) {
        if (Arrays.stream(new String[] { "/", "\\" }).anyMatch(str::contains)) {
            int splitIndex = Math.max(str.lastIndexOf('/'), str.lastIndexOf('\\'));
            return new String[] { str.substring(0, splitIndex), str.substring(splitIndex + 1) };
        }
        return new String[] { "", str };
    }

    private static String format_string_with_time(String string_with_time_specifiers) {
        if (string_with_time_specifiers == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        StringBuilder formattedString = new StringBuilder();
        int startIndex = 0;
        int endIndex = 0;
        try {
            while (startIndex < string_with_time_specifiers.length()
                    && (startIndex = string_with_time_specifiers.indexOf("${", endIndex)) != -1) {
                formattedString.append(string_with_time_specifiers.substring(endIndex, startIndex));
                endIndex = string_with_time_specifiers.indexOf("}", startIndex);
                String value = string_with_time_specifiers.substring(startIndex + 2, endIndex);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(value);
                formattedString.append(LocalDateTime.now().format(formatter));
                endIndex++;
            }
        } catch (DateTimeParseException e) {
            return "Invalid time format specified";
        }

        formattedString.append(string_with_time_specifiers.substring(endIndex));
        return formattedString.toString();
    }

    private BufferedImage add_text_to_image(BufferedImage image, String text_to_add_on_image,
            Color color_of_text,
            Color outline_color_of_text, Font font_of_text, BasicStroke outline_stroke) {
        int x_position_of_image = 5, y_position_of_image = font_of_text.getSize();
        // Create a new BufferedImage object with the same size as the original image
        BufferedImage watermarkedImage = new BufferedImage(image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = watermarkedImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.setFont(font_of_text);

        Stroke originalStroke = graphics.getStroke();
        RenderingHints originalHints = graphics.getRenderingHints();

        // create a glyph vector from your text
        GlyphVector glyphVector = getFont().createGlyphVector(graphics.getFontRenderContext(), text_to_add_on_image);
        // get the shape object
        Shape textShape = glyphVector.getOutline();
        graphics.translate(x_position_of_image, y_position_of_image);

        // activate anti aliasing for text rendering (if you want it to look nice)
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics.setColor(outline_color_of_text);
        graphics.setStroke(outline_stroke);
        graphics.draw(textShape); // draw outline

        graphics.setColor(color_of_text);
        graphics.fill(textShape); // fill the shape

        // reset to original settings after painting
        graphics.setColor(color_of_text);
        graphics.setStroke(originalStroke);
        graphics.setRenderingHints(originalHints);

        graphics.dispose();
        return watermarkedImage;
    }

}
