/*
 * 
 */
package observer_java_GUI.src.analytical_tools_panels;
/*
* @author Aman Rathore
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import observer_java_GUI.src.charts_panels.chart_layout_panel;
import observer_java_GUI.src.charts_panels.gauge_chart_panel;
import observer_java_GUI.src.charts_panels.panel_recorder;

public class Scene_speed_panel extends chart_layout_panel implements Runnable {
    private gauge_chart_panel speed_left_total;
    private gauge_chart_panel speed_mid_total;
    private gauge_chart_panel speed_right_total;
    private gauge_chart_panel speed_left_percentage;
    private gauge_chart_panel speed_mid_percentage;
    private gauge_chart_panel speed_right_percentage;
    private JPanel panel_for_charts;

    public Scene_speed_panel() {
        speed_left_total = new gauge_chart_panel("Speed Left in Terms of Total", 0, 65536, "of total", 0.63f, 0.95f);
        speed_mid_total = new gauge_chart_panel("Speed Mid in Terms of Total", 0, 65536, "of total", 0.63f, 0.95f);
        speed_right_total = new gauge_chart_panel("Speed Right in Terms of Total", 0, 65536, "of total", 0.63f, 0.95f);
        speed_left_percentage = new gauge_chart_panel("Speed Left in Terms of Percentage", 0, 100, "%", 0.33f, 0.66f);
        speed_mid_percentage = new gauge_chart_panel("Speed Mid in Terms of Percentage", 0, 100, "%", 0.33f, 0.66f);
        speed_right_percentage = new gauge_chart_panel("Speed Right in Terms of Percentage", 0, 100, "%", 0.33f, 0.66f);
        panel_for_charts = new JPanel(new GridLayout(2, 3));
        panel_for_charts.add(speed_left_total);
        panel_for_charts.add(speed_mid_total);
        panel_for_charts.add(speed_right_total);
        panel_for_charts.add(speed_left_percentage);
        panel_for_charts.add(speed_mid_percentage);
        panel_for_charts.add(speed_right_percentage);

        top_input_panel.setLayout(new GridLayout(1, 1));
        top_input_panel.add(new panel_recorder(panel_for_charts));
        add_chart_panel(panel_for_charts);

        speed_left_total.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        speed_mid_total.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        speed_right_total.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        speed_left_percentage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        speed_mid_percentage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        speed_right_percentage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        new Thread(this).start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1031, 494);
    }

    @Override
    public void run() {
        while (true) {
            if (isVisible()) {
                for (float i = 0; i <= 100; i = i + 0.2f) {
                    speed_left_total.set_value((i / 100) * (65536));
                    speed_mid_total.set_value((i / 100) * (65536));
                    speed_right_total.set_value((i / 100) * (65536));
                    speed_left_percentage.set_value(100 - i);
                    speed_mid_percentage.set_value(100 - i);
                    speed_right_percentage.set_value(100 - i);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                for (float i = 100; i > 0; i = i - 0.2f) {
                    speed_left_total.set_value((i / 100) * (65536));
                    speed_mid_total.set_value((i / 100) * (65536));
                    speed_right_total.set_value((i / 100) * (65536));
                    speed_left_percentage.set_value(100 - i);
                    speed_mid_percentage.set_value(100 - i);
                    speed_right_percentage.set_value(100 - i);

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
