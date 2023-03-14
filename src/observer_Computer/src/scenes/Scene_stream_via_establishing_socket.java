/**
 * 
 */
package src.scenes;

/**
 * @author Aman Rathore
 *
 */

import java.text.DecimalFormat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class Scene_stream_via_establishing_socket extends JPanel {
    /**
     * @author Aman Rathore
     *
     */
    private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    private static final int DEFAULT_PORT = 8080;
    private static final short MAX_SECONDS_IN_SLIDER = 60;
    private static final short MIN_SECONDS_IN_SLIDER = 10;
    private static final short MAJOR_SECONDS_IN_SLIDER = 10;
    private static final short MINOR_SECONDS_IN_SLIDER = 5;
    private static final short DEFAULT_SECONDS_IN_SLIDER = 20;
    private static final short MIN_MINUTES_IN_SLIDER = 1;
    private static final short MAX_MINUTES_IN_SLIDER = 13;
    private static final short MAJOR_MINUTES_IN_SLIDER = 4;
    private static final short MINOR_MINUTES_IN_SLIDER = 2;
    private static final short DEFAULT_MINUTES_IN_SLIDER = 2;

    public Scene_stream_via_establishing_socket() {
        // TODO: use different styles of cursors for different stuff
        setLayout(new GridLayout(0, 3));
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        JPanel panel_left = new JPanel();
        JPanel panel_center = new JPanel();
        JPanel panel_right = new JPanel();
        panel_center.setLayout(new GridLayout(3, 0));

        panel_counter panel_center_top = new panel_counter();
        JPanel panel_center_mid = new JPanel();
        panel_connecting_animation panel_center_bottom = new panel_connecting_animation();

        panel_center_mid.setLayout(new GridLayout(3, 0));

        JPanel panel_center_mid_top = new JPanel();
        JPanel panel_center_mid_mid = new JPanel();
        JPanel panel_center_mid_bottom = new JPanel();

        panel_center_mid_top.setLayout(new GridLayout(2, 0, 10, 10));

        JTextArea text_area_ip_address = new JTextArea(DEFAULT_IP_ADDRESS);
        JTextArea text_area_port = new JTextArea(String.valueOf(DEFAULT_PORT));
        JButton button_start_server = new JButton("Start Server");
        JButton button_schedule_server_start = new JButton("Schedule Server Start");

        text_area_ip_address.setFont(new Font("Arial", Font.BOLD, 20));
        text_area_port.setFont(new Font("Arial", Font.BOLD, 20));

        panel_center_mid_top.add(text_area_ip_address);
        panel_center_mid_top.add(text_area_port);
        panel_center_mid_mid.add(button_start_server);
        panel_center_mid_bottom.add(button_schedule_server_start);

        panel_center_mid.add(panel_center_mid_top);
        panel_center_mid.add(panel_center_mid_mid);
        panel_center_mid.add(panel_center_mid_bottom);

        panel_center.add(panel_center_top);
        panel_center.add(panel_center_mid);
        panel_center.add(panel_center_bottom);

        panel_left.setBorder(new EmptyBorder(15, 15, 15, 0));
        panel_right.setBorder(new EmptyBorder(15, 0, 15, 15));
        add(panel_left);
        add(panel_center);
        add(panel_right);

        panel_left.setLayout(new GridLayout(0, 2));
        JProgressBar progress_bar_seconds_left = new JProgressBar(JProgressBar.VERTICAL);
        progress_bar_seconds_left.setValue(50);
        progress_bar_seconds_left.setStringPainted(true);
        JSlider slider_seconds_left = new JSlider(JSlider.VERTICAL, MIN_SECONDS_IN_SLIDER, MAX_SECONDS_IN_SLIDER,
                DEFAULT_SECONDS_IN_SLIDER);
        slider_seconds_left.setMinorTickSpacing(MINOR_SECONDS_IN_SLIDER);
        slider_seconds_left.setMajorTickSpacing(MAJOR_SECONDS_IN_SLIDER);
        slider_seconds_left.setPaintTicks(true);
        slider_seconds_left.setPaintLabels(true);
        slider_seconds_left.setPreferredSize(new Dimension(70, 10));
        panel_left.add(progress_bar_seconds_left);
        panel_left.add(slider_seconds_left);

        panel_right.setLayout(new GridLayout(0, 2));
        JProgressBar progress_bar_minutes_right = new JProgressBar(JProgressBar.VERTICAL);
        progress_bar_minutes_right.setValue(50);
        progress_bar_minutes_right.setStringPainted(true);
        JSlider slider_minutes_right = new JSlider(JSlider.VERTICAL, MIN_MINUTES_IN_SLIDER, MAX_MINUTES_IN_SLIDER,
                DEFAULT_MINUTES_IN_SLIDER);
        slider_minutes_right.setMinorTickSpacing(MINOR_MINUTES_IN_SLIDER);
        slider_minutes_right.setMajorTickSpacing(MAJOR_MINUTES_IN_SLIDER);
        slider_minutes_right.setPaintTicks(true);
        slider_minutes_right.setPaintLabels(true);
        panel_right.add(slider_minutes_right);
        panel_right.add(progress_bar_minutes_right);

        button_schedule_server_start.addActionListener(e -> panel_center_top.startIn(9000));
    }
}

class panel_connecting_animation extends JPanel implements ActionListener {

    private Timer timer;
    private String[] connecting_symbols = { ".", "/", "-", "\\" };
    private static short symbol_index = 0;
    private static short dot_count = 0;

    public panel_connecting_animation() {
        timer = new Timer(250, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Monospaced", Font.BOLD, 33);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();

        String connecting_text = "Connecting " + connecting_symbols[symbol_index];

        for (int i = 0; i < dot_count; i++) {
            connecting_text += ".";
        }

        int text_width = fm.stringWidth(connecting_text);
        int text_height = fm.getHeight();

        int x = (getWidth() - text_width) / 2;
        int y = (getHeight() - text_height) / 2;

        g2d.drawString(connecting_text, x, y);

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        symbol_index = (short) ((symbol_index + 1) % connecting_symbols.length);

        if (dot_count < 3) {
            dot_count++;
        } else {
            dot_count = 0;
        }

        repaint();
    }
}

class panel_counter extends JPanel implements ActionListener {

    private long start_time;
    private static Timer timer;
    private JLabel label;
    private DecimalFormat formatter;

    public panel_counter() {
        super();
        formatter = new DecimalFormat("00");
        label = new JLabel("+00:00:00", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setForeground(new Color(158, 0, 71));
        add(label);
    }

    public void start() {
        start_time = System.currentTimeMillis();
        timer = new Timer(1000, this);
        timer.start();
        label.setText("-00:00:00");
        label.setForeground(new Color(0, 158, 71));
    }

    public void stop() {
        timer.stop();
        label.setText("+00:00:00");
        label.setForeground(new Color(158, 0, 71));
    }

    public void startIn(long delay_in_millie_seconds) {
        Timer countdown_timer = new Timer(1000, new ActionListener() {
            long remaining_time = delay_in_millie_seconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                remaining_time -= 1000;

                if (remaining_time <= 0) {
                    ((Timer) e.getSource()).stop();
                    start();
                } else {
                    label.setText(String.format("-%02d:%02d:%02d", (remaining_time
                            / (1000 * 60 * 60)), ((remaining_time / (1000 * 60)) % 60),
                            ((remaining_time / 1000)
                                    % 60)));
                }
            }
        });

        countdown_timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long elapsed_time = System.currentTimeMillis() - start_time;

        // * int seconds = (int) ((elapsed_time / 1000) % 60);
        // * int minutes = (int) ((elapsed_time / (1000 * 60)) % 60);
        // * int hours = ((int) ((elapsed_time / (1000 * 60 * 60)) % 24));

        label.setText(
                "-" + formatter.format((int) ((elapsed_time / (1000 * 60 * 60)
                        % 24))) + ":" + formatter.format((int) ((elapsed_time / (1000 * 60))
                                % 60))
                        + ":" +
                        formatter.format((int) ((elapsed_time / 1000) % 60)));
    }
}
