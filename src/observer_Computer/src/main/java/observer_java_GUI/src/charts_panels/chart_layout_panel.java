/**

This file contains the layout for all the charts. It extends JPanel class.
<p>The class creates a panel with a grid bag layout and allows adding chart panels to it. 
The grid bag layout is set to have two rows, where the top row is reserved for the input panel
and the bottom row is used to add chart panels.
<p>The class provides a method "add_chart_panel" to add chart panels to the bottom row of the grid. 
<p>Usage example:
<pre>{@code
chart_layout_panel chartPanel = new chart_layout_panel();
JPanel pieChartPanel = new pie_chart_panel("My Pie Chart");
chartPanel.add_chart_panel(pieChartPanel);
}</pre>
@see JPanel
@see GridBagConstraints
@author Aman Rathore
@version 1.0
*/
package observer_java_GUI.src.charts_panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class chart_layout_panel extends JPanel {
    /*
     * The panel that will be used for user inputs.
     */
    public JPanel top_input_panel;
    /*
     * GridBagConstraints object for setting constraints to the panel components.
     */
    private GridBagConstraints c;

    /**
     * Constructs a new chart layout panel with a grid bag layout.
     */
    public chart_layout_panel() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        top_input_panel = new JPanel();
        add(top_input_panel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
    }

    /**
     * Adds a chart panel to the layout panel.
     * 
     * @param chart_panel_to_add the chart panel to add to the layout panel
     */
    public void add_chart_panel(JPanel chart_panel_to_add) {
        add(chart_panel_to_add, c);
    }

}
