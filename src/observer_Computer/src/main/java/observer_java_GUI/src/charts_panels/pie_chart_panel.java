/**

This class represents a panel that displays a pie chart. It extends the ChartPanel class from the JFreeChart library.
<p>
The pie chart panel is initialized with a title for the chart. It uses a DefaultPieDataset to store the data that is displayed in the chart. The data for the chart can be set using the set_value() method.
</p>
<p>
The pie chart panel is designed to use constants defined in the my_literals class for the background colors of the chart and plot.
</p>
<p>
This class can be used as a standalone pie chart panel or can be added to a larger GUI.
</p>
@see ChartPanel
@see DefaultPieDataset
@see my_literals
@author Aman Rathore
@version 1.0
*/
package observer_java_GUI.src.charts_panels;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import observer_java_GUI.src.others.my_literals;

/**
 * A class representing a ChartPanel containing a pie chart.
 */
public class pie_chart_panel extends ChartPanel {
    private static final long serialVersionUID = 1L;

    private DefaultPieDataset<String> dataset;

    /**
     * Constructs a new pie chart panel with a specified title.
     * 
     * @param title the title of the pie chart
     */
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

    /**
     * Sets the value for a particular data element in the pie chart.
     * 
     * @param value_of the label of the data element
     * @param value    the value of the data element
     */
    public void set_value(String value_of, double value) {
        dataset.setValue(value_of, value);
    }
}
