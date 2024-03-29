/**
A gauge chart panel that displays a single value in a dial or meter shape.
@see ChartPanel
@see MeterPlot
@see my_literals
@author Aman Rathore
@version 1.0
*/
package observer_java_GUI.src.charts_panels;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DefaultValueDataset;

import observer_java_GUI.src.others.my_literals;

import org.jfree.chart.ui.RectangleEdge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.text.DecimalFormat;

/**
 * Creates a gauge chart panel with the specified title, range, unit, and
 * intervals.
 * 
 * @param title             the chart title
 * @param min               the minimum value of the range
 * @param max               the maximum value of the range
 * @param unit              the unit of the gauge chart
 * @param lower_separation  the value at which the "Low" interval begins
 * @param medium_separation the value at which the "Medium" interval begins
 */
public class gauge_chart_panel extends ChartPanel {

    private DefaultValueDataset dataset;
    private JFreeChart chart;
    private MeterPlot plot;

    public gauge_chart_panel(String title, double min, double max, String unit, float lower_separation,
            float medium_separation) {
        super(null);
        dataset = new DefaultValueDataset();

        plot = new MeterPlot(dataset);
        plot.setDialShape(DialShape.CHORD);
        plot.setMeterAngle(180);
        plot.setRange(new Range(min, max));
        plot.addInterval(new MeterInterval("Low", new Range(min, (max - min) * lower_separation), Color.decode(
                (String) my_literals.CONSTANTS.get("LOW COLOR")),
                new BasicStroke(2.0f), Color.decode((String) my_literals.CONSTANTS.get("LOW COLOR"))));
        plot.addInterval(
                new MeterInterval("Medium", new Range((max - min) * lower_separation, (max - min) * medium_separation),
                        Color.decode((String) my_literals.CONSTANTS
                                .get("MEDIUM COLOR")),
                        new BasicStroke(2.0f), Color.decode((String) my_literals.CONSTANTS.get("MEDIUM COLOR"))));
        plot.addInterval(new MeterInterval("High", new Range((max - min) * medium_separation, max), Color
                .decode((String) my_literals.CONSTANTS.get("HIGH COLOR")), new BasicStroke(2.0f),
                Color.decode((String) my_literals.CONSTANTS.get("HIGH COLOR"))));
        plot.setNeedlePaint(Color.decode((String) my_literals.CONSTANTS.get("NEEDLE COLOR FOR GAUGE CHART")));
        plot.setTickLabelPaint(Color.decode((String) my_literals.CONSTANTS.get("TICK LABEL COLOR")));
        plot.setTickPaint(Color.decode((String) my_literals.CONSTANTS.get("TICK PAINT COLOR")));
        plot.setValuePaint(Color.decode((String) my_literals.CONSTANTS.get("VALUE COLOR")));

        plot.setTickLabelFont(new Font("Dialog", Font.BOLD, 14));
        plot.setTickLabelsVisible(true);
        plot.setValueFont(new Font("Dialog", Font.BOLD, 16));
        plot.setUnits(unit);
        plot.setTickSize(5);
        plot.setTickLabelFormat(new DecimalFormat("#0"));
        chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        this.setChart(chart);
        chart.setBackgroundPaint(Color.decode((String) my_literals.CONSTANTS.get("BACKGROUND COLOR")));
        TextTitle subtitle = new TextTitle(title, new Font("SansSerif", Font.BOLD, 18));
        subtitle.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(subtitle);
        chart.setTitle((TextTitle) null);
        dataset.addChangeListener(new DatasetChangeListener() {
            @Override
            public void datasetChanged(DatasetChangeEvent event) {
                chart.setBackgroundPaint(color_gradient_sampler.get_color_from_gradient(
                        Color.decode((String) my_literals.CONSTANTS.get("LOW COLOR")),
                        Color.decode((String) my_literals.CONSTANTS.get("MEDIUM COLOR")),
                        Color.decode((String) my_literals.CONSTANTS.get("HIGH COLOR")),
                        (float) (dataset.getValue().doubleValue() / max)));
            }

        });
    }

    public void set_value(double value) {
        dataset.setValue(value);
    }
}

/**
 * 
 * The ColorGradientSampler class provides a utility method to obtain a color
 * from a gradient, given a start color,
 * 
 * a middle color and an end color, and a value in the range [0.0, 1.0]
 * representing the position along the gradient.
 * 
 * The color returned is calculated by interpolating between the start and end
 * colors using the middle color as a pivot.
 */
class color_gradient_sampler {

    /**
     * 
     * Returns a color from a gradient given a start color, a middle color, an end
     * color, and a value in the range [0.0, 1.0].
     * The color returned is calculated by interpolating between the start and end
     * colors using the middle color as a pivot.
     * 
     * @param startColor  the starting color of the gradient
     * @param middleColor the middle color of the gradient
     * @param endColor    the ending color of the gradient
     * @param value       a value in the range [0.0, 1.0] representing the position
     *                    along the gradient
     * @return the color at the given position along the gradient
     */
    public static Color get_color_from_gradient(Color startColor, Color middleColor, Color endColor, float value) {
        float middleValue = 0.5f; // Value at which the middle color is used
        Color color;
        if (value <= middleValue) {
            float ratio = value / middleValue;
            color = new Color((int) (startColor.getRed() + (middleColor.getRed() - startColor.getRed()) * ratio),
                    (int) (startColor.getGreen() + (middleColor.getGreen() - startColor.getGreen()) * ratio),
                    (int) (startColor.getBlue() + (middleColor.getBlue() - startColor.getBlue()) * ratio));
        } else {
            float ratio = (value - middleValue) / (1.0f - middleValue);
            color = new Color((int) (middleColor.getRed() + (endColor.getRed() - middleColor.getRed()) * ratio),
                    (int) (middleColor.getGreen() + (endColor.getGreen() - middleColor.getGreen()) * ratio),
                    (int) (middleColor.getBlue() + (endColor.getBlue() - middleColor.getBlue()) * ratio));
        }
        return color;
    }
}