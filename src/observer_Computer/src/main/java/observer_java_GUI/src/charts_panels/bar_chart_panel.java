/**
A bar chart panel that displays a bar chart using the JFreeChart library.
@see ChartPanel
@see CategoryPlot
@see my_literals
@author Aman Rathore
@version 1.1
*/
package observer_java_GUI.src.charts_panels;

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

import observer_java_GUI.src.others.my_literals;

public class bar_chart_panel extends ChartPanel {
    private static final long serialVersionUID = 1L;

    private DefaultCategoryDataset dataset;
    private CategoryPlot plot;

    /**
     * Creates a new instance of the bar chart panel with the specified title,
     * category axis label,
     * value axis label, and orientation.
     *
     * @param title               the chart title
     * @param category_axis_label the label for the category axis
     * @param value_axis_label    the label for the value axis
     * @param orientation         the orientation of the chart
     * @param min_value           the minimum value bar chart can plot, Use
     *                            Double.MIN_VALUE of don't want to fix any range
     * @param max_value           the maximum value bar chart can plot, Use
     *                            Double.MAX_VALUE of don't want to fix any range
     */
    public bar_chart_panel(String title, String category_axis_label, String value_axis_label,
            PlotOrientation orientation, double min_value, double max_value) {
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
        chart.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));

        plot = chart.getCategoryPlot();
        ValueAxis range_axis = plot.getRangeAxis();
        plot.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("PLOT BACKGROUND COLOR")));
        plot.setRangeGridlinePaint(Color.decode((String) my_literals.CONSTANTS.get("GRIDLINE COLOR")));
        range_axis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        range_axis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(true);
        renderer.setSeriesPaint(0, Color.decode((String) my_literals.CONSTANTS.get("SERIES COLOR")));
        ((NumberAxis) range_axis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        range_axis.setRange(min_value, max_value);
        this.setChart(chart);

    }

    /**
     * Sets the value of a data item in the chart's dataset.
     *
     * @param value     the value to be set
     * @param rowKey    the row key of the data item
     * @param columnKey the column key of the data item
     */
    public void set_value(double value, String rowKey, String columnKey) {
        dataset.setValue(value, rowKey, columnKey);
    }
}
