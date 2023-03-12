package src.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;

public class Scene_log_panel extends JPanel {

     private static final long serialVersionUID = 1L;
    final private int maxLogs = 500;
    private JTextArea logTextArea;

    public Scene_log_panel() {
        super(new BorderLayout());
        // setPreferredSize(new Dimension(400, 300));

        // Create a border with a title for the panel
        Border border = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1);
        Border titledBorder = BorderFactory.createTitledBorder(border, "Log",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14), Color.BLUE);
        setBorder(titledBorder);

        // Create a text area to display logs
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);

        // Add a scroll pane to the text area for scrolling
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void appendLog(String message) throws BadLocationException {
        /**
         * Appends a log message to the text area.
         */
        // TODO: Stop the logging process when user is scrolling the logs
        // TODO: Add a button for the user to stop the logging
        // TODO: Also add a keyboard shotcut to stop the logging process while scrolling
        // TODO: Add option of selecting multiple logs and then copy them at once
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        // Remove oldest logs if maximum number of lines is exceeded
        int numLines = logTextArea.getLineCount();
        if (numLines > maxLogs) {
            int startOffset = logTextArea.getLineStartOffset(0);
            int endOffset = logTextArea.getLineEndOffset(numLines - maxLogs);
            logTextArea.replaceRange("", startOffset, endOffset);
        }
    }

}
