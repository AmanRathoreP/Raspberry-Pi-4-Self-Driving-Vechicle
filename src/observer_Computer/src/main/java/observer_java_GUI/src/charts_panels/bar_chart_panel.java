/*
 * 
 */
package observer_java_GUI.src.charts_panels;

/*
 * @author Aman Rathore
 */
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class bar_chart_panel extends ChartPanel {

    private static final long serialVersionUID = 1L;

    private DefaultCategoryDataset dataset;
    private CategoryPlot plot;

    private static final String BACKGROUND_COLOR = "#3c9992";
    private static final String PLOT_BACKGROUND_COLOR = "#d7a98c";
    private static final String GRIDLINE_COLOR = "#000000";
    private static final String SERIES_COLOR = "#0e9ca5";

    public bar_chart_panel(String title,
            String category_axis_label,
            String value_axis_label,
            PlotOrientation orientation) {
        super(null);
        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                category_axis_label,
                value_axis_label,
                dataset,
                orientation,
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.decode(BACKGROUND_COLOR));
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));

        plot = chart.getCategoryPlot();
        ValueAxis range_axis = plot.getRangeAxis();
        plot.setBackgroundPaint(Color.decode(PLOT_BACKGROUND_COLOR));
        plot.setRangeGridlinePaint(Color.decode(GRIDLINE_COLOR));
        range_axis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        range_axis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(true);
        renderer.setSeriesPaint(0, Color.decode(SERIES_COLOR));
        ((NumberAxis) plot.getRangeAxis()).setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        this.setChart(chart);

    }

    public void set_value(double value, String rowKey, String columnKey) {
        dataset.setValue(value, rowKey, columnKey);
    }
}
