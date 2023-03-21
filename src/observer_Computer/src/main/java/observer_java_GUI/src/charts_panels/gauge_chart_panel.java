/*
 * 
 */
package observer_java_GUI.src.charts_panels;
/*
 * @author Aman Rathore
 */

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
import org.jfree.chart.ui.RectangleEdge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.text.DecimalFormat;

public class gauge_chart_panel extends ChartPanel {

    private DefaultValueDataset dataset;
    private JFreeChart chart;
    private MeterPlot plot;
    private static final String LOW_COLOR = "#40c040";
    private static final String MEDIUM_COLOR = "#ffd740";
    private static final String HIGH_COLOR = "#dc143c";
    private static final String NEEDLE_COLOR = "#ffffff";
    private static final String TICK_LABEL_COLOR = "#000000";
    private static final String TICK_PAINT_COLOR = "#808080";
    private static final String VALUE_COLOR = "#000000";
    private static final String BACKGROUND_COLOR = "#000000";
    private static final short ALPHA_FOR_ANIMATION_DURING_TRANSITION = 150;

    public gauge_chart_panel(String title, double min, double max, String unit, float lower_separation,
            float medium_separation) {
        super(null);
        dataset = new DefaultValueDataset();

        plot = new MeterPlot(dataset);
        plot.setDialShape(DialShape.CHORD);
        plot.setMeterAngle(180);
        plot.setRange(new Range(min, max));
        plot.addInterval(new MeterInterval("Low", new Range(min, (max - min) * lower_separation), Color.decode(
                LOW_COLOR),
                new BasicStroke(2.0f), Color.decode(LOW_COLOR)));
        plot.addInterval(
                new MeterInterval("Medium", new Range((max - min) * lower_separation, (max - min) * medium_separation),
                        Color.decode(MEDIUM_COLOR),
                        new BasicStroke(2.0f), Color.decode(MEDIUM_COLOR)));
        plot.addInterval(new MeterInterval("High", new Range((max - min) * medium_separation, max), Color
                .decode(HIGH_COLOR), new BasicStroke(2.0f),
                Color.decode(HIGH_COLOR)));
        plot.setNeedlePaint(Color.decode(NEEDLE_COLOR));
        plot.setTickLabelPaint(Color.decode(TICK_LABEL_COLOR));
        plot.setTickPaint(Color.decode(TICK_PAINT_COLOR));
        plot.setValuePaint(Color.decode(VALUE_COLOR));

        plot.setTickLabelFont(new Font("Dialog", Font.BOLD, 14));
        plot.setTickLabelsVisible(true);
        plot.setValueFont(new Font("Dialog", Font.BOLD, 16));
        plot.setUnits(unit);
        plot.setTickSize(5);
        plot.setTickLabelFormat(new DecimalFormat("#0"));
        chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        this.setChart(chart);
        chart.setBackgroundPaint(Color.decode(BACKGROUND_COLOR));
        TextTitle subtitle = new TextTitle(title, new Font("SansSerif", Font.BOLD, 18));
        subtitle.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(subtitle);
        chart.setTitle((TextTitle) null);
        dataset.addChangeListener(new DatasetChangeListener() {
            @Override
            public void datasetChanged(DatasetChangeEvent event) {
                double current_value = dataset.getValue().doubleValue();
                if (current_value >= (max - min) * medium_separation) {
                    chart.setBackgroundPaint(new Color(
                            Color.decode(HIGH_COLOR).getRed(),
                            Color.decode(HIGH_COLOR).getGreen(),
                            Color.decode(HIGH_COLOR).getBlue(),
                            ALPHA_FOR_ANIMATION_DURING_TRANSITION));
                } else if (current_value >= (max - min) * lower_separation) {
                    chart.setBackgroundPaint(new Color(
                            Color.decode(MEDIUM_COLOR).getRed(),
                            Color.decode(MEDIUM_COLOR).getGreen(),
                            Color.decode(MEDIUM_COLOR).getBlue(),
                            ALPHA_FOR_ANIMATION_DURING_TRANSITION));
                } else {
                    chart.setBackgroundPaint(new Color(
                            Color.decode(LOW_COLOR).getRed(),
                            Color.decode(LOW_COLOR).getGreen(),
                            Color.decode(LOW_COLOR).getBlue(),
                            ALPHA_FOR_ANIMATION_DURING_TRANSITION));
                }
            }

        });
    }

    public void set_value(double value) {
        dataset.setValue(value);
    }
}
