
/**
A scatter chart panel that displays a scatter chart using the JFreeChart library.
@see ChartPanel
@see XYPlot
@see my_literals
@author Aman Rathore
@version 1.0
*/
package observer_java_GUI.src.charts_panels;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import observer_java_GUI.src.others.my_literals;

public class scatter_chart_panel extends ChartPanel {
    private static final long serialVersionUID = 1L;

    private XYSeriesCollection dataset;
    private XYPlot plot;
    private List<XYSeries> seriesList;

    /**
     * Creates a new instance of the scatter plot panel with the specified title,
     * x-axis label, y-axis label, and range of values.
     *
     * @param title      the chart title
     * @param xAxisLabel the label for the x-axis
     * @param yAxisLabel the label for the y-axis
     */
    public scatter_chart_panel(String title, String xAxisLabel, String yAxisLabel) {
        super(null);
        dataset = new XYSeriesCollection();
        seriesList = new ArrayList<XYSeries>();

        JFreeChart chart = ChartFactory.createScatterPlot(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset);
        chart.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));

        plot = chart.getXYPlot();
        ValueAxis domainAxis = plot.getDomainAxis();
        ValueAxis rangeAxis = plot.getRangeAxis();
        plot.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("PLOT BACKGROUND COLOR")));
        plot.setDomainGridlinePaint(Color.decode((String) my_literals.CONSTANTS.get("GRIDLINE COLOR")));
        plot.setRangeGridlinePaint(Color.decode((String) my_literals.CONSTANTS.get("GRIDLINE COLOR")));
        domainAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        rangeAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 14));
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        rangeAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        ((NumberAxis) domainAxis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ((NumberAxis) rangeAxis).setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        plot.setRenderer(new XYShapeRenderer());

        this.setChart(chart);
    }

    /**
     * Adds a new data point to the scatter plot.
     *
     * @param x         the x-value of the data point
     * @param y         the y-value of the data point
     * @param seriesKey the key for the series to which the data point belongs
     * @apiNote Try not to create any series in a thread
     */
    public void add_data_point_to_series(double x, double y, String seriesKey) {
        XYSeries series = get_series(seriesKey);
        if (series == null) {
            series = new XYSeries(seriesKey);
            seriesList.add(series);
            try {
                dataset.addSeries(series);
            } catch (Exception e) {
                // ! program reaches here only when user creates the series in a thread
                e.printStackTrace();
            }
        }
        series.add(x, y);
    }

    /**
     * Gets the series with the specified key.
     *
     * @param seriesKey the key for the series to retrieve
     * @return the series with the specified key, or null if the series does not
     *         exist
     */
    private XYSeries get_series(String seriesKey) {
        for (XYSeries series : seriesList) {
            if (series.getKey().equals(seriesKey)) {
                return series;
            }
        }
        return null;
    }

    /**
     * Clear all the plots/series on the ChartPanel.
     */
    public void clear_all_series() {
        seriesList.clear();
        dataset.removeAllSeries();
    }

    /**
     * Clears the series with the specified key.
     *
     * @param seriesKey the key for the series to clear
     */
    public void clear_series(String seriesKey) {
        XYSeries seriesToRemove = null;
        for (XYSeries series : seriesList) {
            if (series.getKey().equals(seriesKey)) {
                seriesToRemove = series;
                break;
            }
        }
        if (seriesToRemove != null) {
            dataset.removeSeries(seriesToRemove);
            seriesList.remove(seriesToRemove);
        }
    }

    /**
     * Gets the number of data points in the series with the specified key.
     *
     * @param seriesKey the key for the series to get the number of data points for
     * @return the number of data points in the series, or 0 if the series does not
     *         exist
     */
    public int get_series_data_point_count(String seriesKey) {
        XYSeries series = get_series(seriesKey);
        if (series == null) {
            return 0;
        } else {
            return series.getItemCount();
        }
    }

}