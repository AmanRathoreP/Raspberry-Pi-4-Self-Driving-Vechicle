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
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import src.others.my_literals;

public class Scene_stream_via_establishing_socket extends JPanel implements Runnable {
    /**
     * @author Aman Rathore
     *
     */

    private JTextArea text_area_ip_address;
    private JTextArea text_area_port;
    private JButton button_start_server;
    private JButton button_schedule_server_start;
    private JProgressBar progress_bar_seconds_left;
    private JProgressBar progress_bar_minutes_right;

    private panel_counter panel_center_top;

    public Scene_stream_via_establishing_socket() {
        // TODO: use different styles of cursors for different stuff
        setLayout(new GridLayout(0, 3));
        // setCursor(new Cursor(Cursor.WAIT_CURSOR));
        JPanel panel_left = new JPanel();
        JPanel panel_center = new JPanel();
        JPanel panel_right = new JPanel();
        panel_center.setLayout(new GridLayout(3, 0));

        panel_center_top = new panel_counter();
        JPanel panel_center_mid = new JPanel();
        panel_connecting_animation panel_center_bottom = new panel_connecting_animation();

        panel_center_mid.setLayout(new GridLayout(3, 0));

        JPanel panel_center_mid_top = new JPanel();
        JPanel panel_center_mid_mid = new JPanel();
        JPanel panel_center_mid_bottom = new JPanel();

        panel_center_mid_top.setLayout(new GridLayout(2, 0, 10, 10));

        text_area_ip_address = new JTextArea(
                ((String) my_literals.CONSTANTS.get("DEFAULT IP ADDRESS OF SOCKET CONNECTION WINDOW")));
        text_area_port = new JTextArea(
                String.valueOf(((int) my_literals.CONSTANTS.get("DEFAULT PORT OF SOCKET CONNECTION WINDOW"))));
        button_start_server = new JButton("Start Server");
        button_schedule_server_start = new JButton("Schedule Server Start");

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

        panel_left.setBorder(new EmptyBorder(15, 15, 15, 20));
        panel_right.setBorder(new EmptyBorder(15, 20, 15, 15));
        add(panel_left);
        add(panel_center);
        add(panel_right);

        panel_left.setLayout(new GridLayout(0, 1));
        progress_bar_seconds_left = new dynamic_progress_bar("Seconds: ",
                ((String) my_literals.CONSTANTS
                        .get("COLOR OF PROGRESS BARS IN ACTIVE STATE OF SOCKET CONNECTION WINDOW")),
                ((String) my_literals.CONSTANTS
                        .get("COLOR OF PROGRESS BARS IN NON ACTIVE STATE OF SOCKET CONNECTION WINDOW")));
        progress_bar_seconds_left
                .setValue(
                        ((Integer) my_literals.CONSTANTS.get("DEFAULT SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_seconds_left.setMinimum(
                ((Integer) my_literals.CONSTANTS.get("MINIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_seconds_left.setMaximum(
                ((Integer) my_literals.CONSTANTS.get("MAXIMUM SECONDS IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_seconds_left.setStringPainted(true);
        panel_left.add(progress_bar_seconds_left);

        panel_right.setLayout(new GridLayout(0, 1));
        progress_bar_minutes_right = new dynamic_progress_bar("Minutes: ",
                ((String) my_literals.CONSTANTS
                        .get("COLOR OF PROGRESS BARS IN ACTIVE STATE OF SOCKET CONNECTION WINDOW")),
                ((String) my_literals.CONSTANTS
                        .get("COLOR OF PROGRESS BARS IN NON ACTIVE STATE OF SOCKET CONNECTION WINDOW")));
        progress_bar_minutes_right
                .setValue(
                        ((Integer) my_literals.CONSTANTS.get("DEFAULT MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_minutes_right
                .setMinimum(
                        ((Integer) my_literals.CONSTANTS.get("MINIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_minutes_right
                .setMaximum(
                        ((Integer) my_literals.CONSTANTS.get("MAXIMUM MINUTES IN SLIDER OF SOCKET CONNECTION WINDOW")));
        progress_bar_minutes_right.setStringPainted(true);
        panel_right.add(progress_bar_minutes_right);

        button_schedule_server_start.addActionListener(e -> {
            panel_center_top.startIn((progress_bar_seconds_left.getValue() * 1000)
                    + (progress_bar_minutes_right.getValue() * 60 * 1000));
            button_schedule_server_start.setEnabled(false);
            button_start_server.setEnabled(false);
            text_area_ip_address.setEnabled(false);
            text_area_port.setEnabled(false);
            ((dynamic_progress_bar) progress_bar_minutes_right).freeze_mouse_actions(true);
            ((dynamic_progress_bar) progress_bar_seconds_left).freeze_mouse_actions(true);
            progress_bar_minutes_right.setString("...");
            Timer countdown_timer = new Timer(1000, new ActionListener() {
                long remaining_time = (progress_bar_seconds_left.getValue() * 1000)
                        + (progress_bar_minutes_right.getValue() * 60 * 1000);

                @Override
                public void actionPerformed(ActionEvent e) {
                    remaining_time -= 1000;
                    if (remaining_time <= 0) {
                        ((Timer) e.getSource()).stop();
                        progress_bar_seconds_left.setValue(0);
                        panel_center_bottom.start_connecting_animation();
                    } else {
                        progress_bar_seconds_left.setValue((int) ((remaining_time / 1000) % 60));
                        progress_bar_minutes_right.setValue((int) ((remaining_time / (1000 * 60)) % 60));
                    }
                }
            });

            countdown_timer.start();
        });
        button_start_server.addActionListener(e -> {
            panel_center_top.start();
            panel_center_bottom.start_connecting_animation();
            button_schedule_server_start.setEnabled(false);
            button_start_server.setEnabled(false);
            text_area_ip_address.setEnabled(false);
            text_area_port.setEnabled(false);
            progress_bar_seconds_left.setValue(0);
            progress_bar_minutes_right.setValue(0);
            ((dynamic_progress_bar) progress_bar_minutes_right).freeze_mouse_actions(true);
            ((dynamic_progress_bar) progress_bar_seconds_left).freeze_mouse_actions(true);
        });

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (dynamic_progress_bar.freeze_mouse_actions == false) {
                panel_center_top.label.setForeground(new Color(158, 0, 71));
                panel_center_top.label
                        .setText(String.format("+%02d:%02d:%02d", (0), (progress_bar_minutes_right.getValue()),
                                (progress_bar_seconds_left.getValue())));
                // System.out.print
            }
        }
    }

}

class panel_connecting_animation extends JPanel implements ActionListener {

    private Timer timer;
    private String[] connecting_symbols = { ".", "/", "-", "\\" };
    private static short symbol_index = 0;
    private static short dot_count = 0;
    private boolean isConnecting = false;
    private boolean isWaiting = true;

    public panel_connecting_animation() {
    }

    public void start_connecting_animation() {
        isConnecting = true;
        isWaiting = false;
        timer = new Timer(250, this);
        timer.start();
    }

    public void stop_connecting_animation() {
        isConnecting = false;
        isWaiting = false;
        timer.stop();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Monospaced", Font.BOLD, 33);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();

        String connecting_text = "";

        if (isConnecting) {
            connecting_text += "Connecting " + connecting_symbols[symbol_index];

            for (int i = 0; i < dot_count; i++) {
                connecting_text += ".";
            }
        } else if (isWaiting) {
            connecting_text += "Waiting";
        } else {
            connecting_text += "Connected";
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
    public JLabel label;
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

    public void startIn(long delay_in_milliseconds) {
        Timer countdown_timer = new Timer(1000, new ActionListener() {
            long remaining_time = delay_in_milliseconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                remaining_time -= 1000;

                if (remaining_time <= 0) {
                    ((Timer) e.getSource()).stop();
                    start();
                } else {
                    label.setText(String.format("+%02d:%02d:%02d", (remaining_time
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

class dynamic_progress_bar extends JProgressBar {
    public static boolean freeze_mouse_actions = false;
    private static String non_active_color;

    public dynamic_progress_bar(String string_that_will_come_in_front_of_value, String active_color,
            String non_active_color) {
        super(JProgressBar.VERTICAL);
        dynamic_progress_bar.non_active_color = non_active_color;
        setForeground(Color.decode(active_color));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setValue(calculate_value(e.getY()));
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setValue(calculate_value(e.getY()));
            }
        });

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setString(string_that_will_come_in_front_of_value + Integer.toString(getValue()));
            }
        });
    }

    private int calculate_value(int mouse_Y_axis_from_top) {
        if (freeze_mouse_actions) {
            return getValue();
        }
        return (((int) ((getMaximum() - getMinimum())
                * ((float) (getHeight() - mouse_Y_axis_from_top) / getHeight()))) + getMinimum());
    }

    public void freeze_mouse_actions(boolean freeze_or_not) {
        dynamic_progress_bar.freeze_mouse_actions = freeze_or_not;
        if (freeze_or_not) {
            setForeground(Color.decode(non_active_color));
        }
    }
}
