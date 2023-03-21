package observer_java_GUI.src.scenes;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Scene_about_panel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JEditorPane editor_pane_for_html;

    public Scene_about_panel() {
        setLayout(new BorderLayout());
        editor_pane_for_html = new JEditorPane();
        editor_pane_for_html.setContentType("text/html");
        editor_pane_for_html.setText(
                "<html><body>" +
                        "<h1>Contributing Guide</h1>"
                        +
                        "<p>Thank you for considering contributing to our Predictive Analytical Controller!</p>" +
                        "<p>First note we have a code of conduct, please follow it in all your interactions with the program files.</p>"
                        +
                        "<p>We welcome any type of contribution, <em>not only code</em>. You can help with:</p>" +
                        "<ul>" +
                        "<li><strong>QA</strong>: File bug reports, the more details you can give the better (e.g. images or videos)</li>"
                        +
                        "<li><strong>New Features</strong>: You can suggest an modifications or just ask for advancements in the old features of th software.</li>"
                        +
                        "<li><strong>Code</strong>: Take a look at the open issues on GitHub. Even if you can&#39;t write the code yourself, you can comment on them, showing that you care about a given issue matters. It helps us to handel them</li>"
                        +
                        "</ul>" +
                        "<h1 id=\"author\">Author Information</h1>" +
                        "<ul>" +
                        "<li>@Aman --> https://www.github.com/AmanRathoreP<ul>" +
                        "<li>GitHub --> https://www.github.com/AmanRathoreM</li>" +
                        "<li>Telegram --> https://t.me/aman0864</li>" +
                        "<li>Email -&gt; <em>aman.proj.rel@gmail.com</em></li>" +
                        "</ul>" +
                        "</li>" +
                        "</ul>" +
                        "<h1 id=\"author\">License</h1>" +
                        "<p>MIT License --> https://choosealicense.com/licenses/mit/</p>" +
                        "<p>Copyright (c) 2022-2023, <i><b>Aman Rathore</i></b></p>" +
                        "<p>Permission is hereby granted, free of charge, to any person obtaining a copy" +
                        "of this software and associated documentation files (the &quot;Software&quot;), to deal" +
                        "in the Software without restriction, including without limitation the rights" +
                        "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell" +
                        "copies of the Software, and to permit persons to whom the Software is" +
                        "furnished to do so, subject to the following conditions:</p>" +
                        "<p>The above copyright notice and this permission notice shall be included in all" +
                        "copies or substantial portions of the Software.</p>" +
                        "<p>THE SOFTWARE IS PROVIDED &quot;AS IS&quot;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR" +
                        "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY," +
                        "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE" +
                        "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER" +
                        "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM," +
                        "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE" +
                        "SOFTWARE.</p>" +
                        "</body></html>");

        editor_pane_for_html.setEditable(false);
        JScrollPane scroll_pane = new JScrollPane(editor_pane_for_html);
        add(scroll_pane, BorderLayout.CENTER);
    }

}
