package src.scenes;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Scene_usage_panel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JEditorPane editor_pane_for_html;

    public Scene_usage_panel() {
        setLayout(new BorderLayout());
        editor_pane_for_html = new JEditorPane();
        editor_pane_for_html.setContentType("text/html");
        editor_pane_for_html.setText(
                "<html><body>"
                        + "<h2>No Docs available!</h2>"
                        + "<h2>Why?:-(</h2>"
                        + "<h3>The author, Aman, is a highly occupied individual who lacks the bandwidth to craft extensive app documentation. However, the app's user interface is notably straightforward and user-friendly, barring the backend of Analytical tools, which requires a degree of mathematical proficiency. Fear not, for documentation concerning said tools is forthcoming. It would be immensely beneficial if you could lend a hand by crafting documentation for other miscellaneous components of the app. If you seek to gain insight into the inner workings of these powerful, real-time analytical tools, do not hesitate to contact the author, whose contact information is available on their GitHub profile, AmanRathoreP. In general, you can expect a reply within a week or two.</h3>"
                        + "</body></html>");

        editor_pane_for_html.setEditable(false);
        JScrollPane scroll_pane = new JScrollPane(editor_pane_for_html);
        add(scroll_pane, BorderLayout.CENTER);
    }

}