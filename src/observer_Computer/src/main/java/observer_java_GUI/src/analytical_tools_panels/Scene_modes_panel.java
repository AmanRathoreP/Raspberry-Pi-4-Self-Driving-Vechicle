/*
 * 
 */
package observer_java_GUI.src.analytical_tools_panels;

/*
 * @author Aman Rathore
 */
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

import org.jfree.chart.plot.PlotOrientation;

import observer_java_GUI.src.charts_panels.pie_chart_panel;
import observer_java_GUI.src.charts_panels.bar_chart_panel;
import observer_java_GUI.src.charts_panels.chart_layout_panel;
import observer_java_GUI.src.charts_panels.panel_recorder;

public class Scene_modes_panel extends chart_layout_panel implements Runnable {
    private static final long serialVersionUID = 1L;
    private pie_chart_panel pie_chart_of_total_time;
    private pie_chart_panel pie_chart_of_n_time;
    private bar_chart_panel live_bar_chart = new bar_chart_panel("Speed in different wheels in \'n\' time",
            "Wheels area",
            "Speed in terms of % of max speed",
            PlotOrientation.HORIZONTAL, -100, 100);
    private Time_input_panel n_input_panel;

    public Scene_modes_panel() {
        super();

        JPanel panel_for_charts = new JPanel(new GridLayout(2, 0));
        JPanel pie_chart_panel = new JPanel(new GridLayout(1, 2));
        pie_chart_of_total_time = new pie_chart_panel("In Total time");
        pie_chart_of_n_time = new pie_chart_panel("In \'n\' time");
        pie_chart_panel.add(pie_chart_of_total_time);
        pie_chart_panel.add(pie_chart_of_n_time);
        panel_for_charts.add(pie_chart_panel);

        panel_for_charts.add(live_bar_chart);
        n_input_panel = new Time_input_panel();
        top_input_panel.setLayout(new GridLayout(2, 1));
        top_input_panel.add(n_input_panel);
        top_input_panel.add(new panel_recorder(panel_for_charts));
        add_chart_panel(panel_for_charts);

        live_bar_chart.set_value(5, "Average Speed", "Left");
        live_bar_chart.set_value(5, "Top Speed", "Left");
        live_bar_chart.set_value(-50, "Current Speed", "Left");
        live_bar_chart.set_value(5, "Average Speed", "Combine");
        live_bar_chart.set_value(5, "Top Speed", "Combine");
        live_bar_chart.set_value(5, "Current Speed", "Combine");
        live_bar_chart.set_value(1.4, "Average Speed", "Right");
        live_bar_chart.set_value(1, "Top Speed", "Right");
        live_bar_chart.set_value(-1, "Current Speed", "Right");

        pie_chart_of_n_time.set_value("Right Drive", 0.7);
        pie_chart_of_n_time.set_value("Front Drive", 1.7);
        pie_chart_of_n_time.set_value("Left Drive", 1.7);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 100; i++) {
                pie_chart_of_total_time.set_value("Right Drive", i * 1.1);
                pie_chart_of_total_time.set_value("Front Drive", i);
                pie_chart_of_total_time.set_value("Left Drive", 72.9);
                live_bar_chart.set_value(i * 1.4, "Top Speed", "Left");
                pie_chart_of_n_time.set_value("Right Drive", i * 0.02);
                try {
                    Thread.sleep(n_input_panel.getTimeInMillis());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 100; i++) {
                pie_chart_of_total_time.set_value("Right Drive", i * 0.7);
                pie_chart_of_total_time.set_value("Front Drive", i * 1.1);
                pie_chart_of_total_time.set_value("Left Drive", i);
                live_bar_chart.set_value(i / 1.4, "Average Speed", "Left");
                pie_chart_of_n_time.set_value("Right Drive", (i) * 0.02);
                try {
                    Thread.sleep(n_input_panel.getTimeInMillis());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 826);
    }

}

class Time_input_panel extends JPanel {
    private final JSpinner hoursSpinner;
    private final JSpinner minutesSpinner;
    private final JSpinner secondsSpinner;
    private final JSpinner millisecondsSpinner;

    public Time_input_panel() {
        super(new FlowLayout());

        // Create spinner models
        SpinnerNumberModel hoursModel = new SpinnerNumberModel(0, 0, 23, 1);
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerNumberModel secondsModel = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerNumberModel millisecondsModel = new SpinnerNumberModel(10, 0, 999, 1);

        // Create spinners
        hoursSpinner = new JSpinner(hoursModel);
        minutesSpinner = new JSpinner(minutesModel);
        secondsSpinner = new JSpinner(secondsModel);
        millisecondsSpinner = new JSpinner(millisecondsModel);

        // Add labels
        add(new JLabel("<html><font face=\"Arial\" size=\"4\">Adjust value of \'n\'  >>></font></html>"));
        add(new JLabel("Milliseconds:"));
        add(millisecondsSpinner);
        add(new JLabel("Seconds:"));
        add(secondsSpinner);
        add(new JLabel("Minutes:"));
        add(minutesSpinner);
        add(new JLabel("Hours:"));
        add(hoursSpinner);
    }

    public long getTimeInMillis() {
        long hours = (int) hoursSpinner.getValue() * 3600000L;
        long minutes = (int) minutesSpinner.getValue() * 60000L;
        long seconds = (int) secondsSpinner.getValue() * 1000L;
        long milliseconds = (int) millisecondsSpinner.getValue();
        return hours + minutes + seconds + milliseconds;
    }
}