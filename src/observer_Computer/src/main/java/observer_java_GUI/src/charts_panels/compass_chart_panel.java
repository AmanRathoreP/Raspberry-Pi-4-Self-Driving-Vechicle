/**

The compass_chart_panel class is a custom ChartPanel that displays a compass chart, which displays the wind direction and the expected direction.
@see ChartPanel
@see CompassPlot
@see my_literals
@author Aman Rathore
@version 1.0
*/
package observer_java_GUI.src.charts_panels;

import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CompassPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultValueDataset;

import observer_java_GUI.src.others.my_literals;

public class compass_chart_panel extends ChartPanel {
        /**
         * The available types of needles to display in the compass chart.
         */
    public static final String[] NEEDLE_TYPES = { "Arrow", "Line", "Long", "Pin", "Plum", "Pointer", "Ship", "Wind" };
    private DefaultValueDataset compassData = new DefaultValueDataset(Double.valueOf(0.0));
    private DefaultValueDataset shipData = new DefaultValueDataset(Double.valueOf(0.0));
    private CompassPlot compassPlot = new CompassPlot(this.compassData);
    private JFreeChart chart;

    /**
     * Constructs a new compass chart panel with a specified title.
     * 
     * @param title the title of the compass chart
     */
    public compass_chart_panel(String title) {
        super(null);
        this.compassPlot.addDataset(this.shipData);
        this.compassPlot.setSeriesNeedle(0, (int) my_literals.CONSTANTS.get(
                "EXPECTED DIRECTION NEEDLE TYPE FOR COMPASS CHART"));
        this.compassPlot.setSeriesNeedle(1, (int) my_literals.CONSTANTS.get(
                "DIRECTION NEEDLE TYPE FOR COMPASS CHART"));
        this.compassPlot.setSeriesPaint(0,
                Color.decode((String) my_literals.CONSTANTS.get("WIND NEEDLE COLOR FOR COMPASS CHART")));
        this.compassPlot.setSeriesOutlinePaint(0,
                Color.decode((String) my_literals.CONSTANTS.get("WIND NEEDLE COLOR FOR COMPASS CHART")));
        this.compassPlot.setSeriesPaint(1,
                Color.decode((String) my_literals.CONSTANTS.get("DIRECTION NEEDLE COLOR FOR COMPASS CHART")));
        this.compassPlot.setSeriesOutlinePaint(1,
                Color.decode((String) my_literals.CONSTANTS.get("DIRECTION NEEDLE COLOR FOR COMPASS CHART")));
        this.compassPlot.setRoseCenterPaint(Color.decode((String) my_literals.CONSTANTS.get("PLOT BACKGROUND COLOR")));
        this.compassPlot.setRoseHighlightPaint(
                Color.decode((String) my_literals.CONSTANTS.get("OUTLINE COLOR")));
        this.compassPlot.setRosePaint(
                Color.decode((String) my_literals.CONSTANTS.get("HIGHLIGHT COLOR")));
        this.chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, this.compassPlot, true);
        this.setChart(this.chart);
        this.chart.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        TextTitle subtitle = new TextTitle(title, new Font("SansSerif", Font.BOLD, 18));
        this.chart.addSubtitle(subtitle);
        this.chart.setTitle((TextTitle) null);
    }

    /**
     * Sets the wind and direction values for the compass chart.
     * 
     * @param direction_value the value of the direction
     * @param wind_value      the value of the wind
     */
    public void set_value(float direction_value, float wind_value) {
        this.compassData.setValue(Double.valueOf(wind_value));
        this.shipData.setValue(Double.valueOf(direction_value));
    }
}