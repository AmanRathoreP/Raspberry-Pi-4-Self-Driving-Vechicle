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

public class pie_chart_panel extends ChartPanel {
    private static final long serialVersionUID = 1L;

    private DefaultPieDataset<String> dataset;

    private static final String BACKGROUND_COLOR = "#3C9992";
    private static final String PLOT_BACKGROUND_COLOR = "#d7a98c";

    public pie_chart_panel(String title) {
        super(null);
        dataset = new DefaultPieDataset<String>();
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        ((PiePlot) chart.getPlot()).setBackgroundPaint(Color.decode(PLOT_BACKGROUND_COLOR));
        chart.setBackgroundPaint(Color.decode(BACKGROUND_COLOR));
        setBackground(Color.decode(BACKGROUND_COLOR));
        this.setChart(chart);
    }

    public void set_value(String value_of, double value) {
        dataset.setValue(value_of, value);
    }
}
