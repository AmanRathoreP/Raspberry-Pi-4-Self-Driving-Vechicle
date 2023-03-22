/*
 * 
 */
package observer_java_GUI.src.charts_panels;

/*
 * @author Aman Rathore
 */
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import observer_java_GUI.src.others.my_literals;

public class pie_chart_panel extends ChartPanel {
    private static final long serialVersionUID = 1L;

    private DefaultPieDataset<String> dataset;

    public pie_chart_panel(String title) {
        super(null);
        dataset = new DefaultPieDataset<String>();
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        ((PiePlot) chart.getPlot())
                .setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("PLOT BACKGROUND COLOR")));
        chart.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        setBackground(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        this.setChart(chart);
    }

    public void set_value(String value_of, double value) {
        dataset.setValue(value_of, value);
    }
}
