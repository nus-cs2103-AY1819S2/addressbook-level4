package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextFlow resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.getChildren().clear();
        for (Pair<String, String> parsedResult : parseText(feedbackToUser)) {
            String text;
            String header;
            String style;

            text = parsedResult.getKey();
            header = parsedResult.getValue();
            //TODO: Link style to original css file
            if (header == null || !header.equals("!e")) {
                style = "-fx-fill: WHITE";
            } else {
                style = "-fx-fill: #F70D1A";
            }
            Text t = new Text();
            t.setText(text);
            t.setStyle(style);
            resultDisplay.getChildren().add(t);
        }
    }

    /**
     * Parse userFeedback from other methods
     * @param input original text from userFeedback
     * @return text line by line with format header
     */
    private Pair<String, String>[] parseText(String input) {
        String[] lines = input.split("\n", 0);
        Pair<String, String>[] result = new Pair[lines.length];
        int i = 0;
        for (String line : lines) {
            String header = parseHeader(line);
            if (header != null) {
                line = line.replaceFirst(header, "");
            }
            if (i != result.length - 1) {
                line = line + "\n";
            }

            result[i++] = new Pair(line, header);
        }
        return result;
    }

    private String parseHeader(String input) {
        if (input.charAt(0) == '\\' || input.charAt(0) != '!') { return null; }
        return input.substring(0, 2);
    }

}