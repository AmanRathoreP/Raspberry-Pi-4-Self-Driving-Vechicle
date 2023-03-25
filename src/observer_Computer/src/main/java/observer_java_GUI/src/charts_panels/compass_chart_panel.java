/*
 * 
 */
package observer_java_GUI.src.charts_panels;

/*
 * @author Aman Rathore
 */
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CompassPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultValueDataset;

import observer_java_GUI.src.others.my_literals;

public class compass_chart_panel extends ChartPanel {
    public static final String[] NEEDLE_TYPES = { "Arrow", "Line", "Long", "Pin", "Plum", "Pointer", "Ship", "Wind" };
    private DefaultValueDataset compassData = new DefaultValueDataset(Double.valueOf(0.0));
    private DefaultValueDataset shipData = new DefaultValueDataset(Double.valueOf(0.0));
    private CompassPlot compassPlot = new CompassPlot(this.compassData);
    private JFreeChart chart;

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

    public void set_value(float direction_value, float wind_value) {
        this.compassData.setValue(Double.valueOf(wind_value));
        this.shipData.setValue(Double.valueOf(direction_value));
    }
}