/*
 * This file contains layout for all the charts
 */
package observer_java_GUI.src.charts_panels;
/*
 * @author Aman Rathore
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class chart_layout_panel extends JPanel {
    public JPanel top_input_panel;
    private GridBagConstraints c;

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

    public void add_chart_panel(JPanel chart_panel_to_add) {
        add(chart_panel_to_add, c);
    }

}
