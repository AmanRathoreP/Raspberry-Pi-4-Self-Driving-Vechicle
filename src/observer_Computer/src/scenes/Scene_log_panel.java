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

import src.others.my_literals;

public class Scene_log_panel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea logTextArea;

    public Scene_log_panel() {
        super(new BorderLayout());

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
        // TODO: Also add a keyboard shortcut to stop the logging process while
        // scrolling
        // TODO: Add option of selecting multiple logs and then copy them at once
        // TODO: add tooltip on every msg so that when any log is appeded to the log
        // panel and when hover it shows that when that msg was added
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        // Remove oldest logs if maximum number of lines is exceeded
        int numLines = logTextArea.getLineCount();
        if (numLines > ((int) my_literals.CONSTANTS.get("MAXIMUM NUMBER OF LOGS IN LOG WINDOW"))) {
            logTextArea.replaceRange("", logTextArea.getLineStartOffset(0),
                    logTextArea.getLineEndOffset(
                            numLines - ((int) my_literals.CONSTANTS.get("MAXIMUM NUMBER OF LOGS IN LOG WINDOW"))));
        }
    }

}
