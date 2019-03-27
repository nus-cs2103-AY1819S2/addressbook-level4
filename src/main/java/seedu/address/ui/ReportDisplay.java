package seedu.address.ui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class ReportDisplay extends UiPart<Region> {

    private static final String FXML = "ReportDisplay.fxml";

    public static final String MESSAGE_SCORE_CHANGE_PREFIX = "Your latest score has changed by ";

    // Pixel values used for manipulating string offset (see below)
    private static final int PIXELS_PER_CHARACTER = 6;
    private static final int CHARACTER_OFFSET = 2;

    // number change display colors, in hex
    private static final String CHANGE_POSITIVE = "#47AB6C"; // green
    private static final String CHANGE_NEGATIVE = "#ED553B"; // red
    private static final String CHANGE_SAME = "#BDB76B"; // dark khaki

    @FXML
    private GridPane reportDisplay;

    @FXML
    private Label questionsDisplay;

    @FXML
    private AreaChart<Integer, Double> graph;

    @FXML
    private Label folderName;

    @FXML
    private Label tagLine;

    @FXML
    private Label performanceLine;

    @FXML
    private Label numberLine;


    public ReportDisplay(ReadOnlyCardFolder cardFolder) {
        super(FXML);

        displayTitle(cardFolder.getFolderName());
        displayGraph(cardFolder.getFolderScores());
        displayQuestions(cardFolder.getCardList());
        displayTagLine("Last " + cardFolder.getFolderScores().size() + " scores, latest on the right");

        reportDisplay.getChildren().clear();
        reportDisplay.getChildren().add(folderName);
        reportDisplay.getChildren().add(tagLine);
        reportDisplay.getChildren().add(graph);
        reportDisplay.getChildren().add(questionsDisplay);

        if (cardFolder.getFolderScores().size() > 1) {
            displayPerformanceLine(cardFolder.getFolderScores());
            reportDisplay.getChildren().add(performanceLine);
            reportDisplay.getChildren().add(numberLine);
        }

    }

    /**
     * Displays a line indicating change in performance depending on last 2 scores.
     * @param folderScores List of Double of folderScores, at least > 1 in length.
     */
    private void displayPerformanceLine(List<Double> folderScores) {
        assert folderScores.size() > 1;

        // Calculate difference based on last two scores
        double scoreAfter = folderScores.get(folderScores.size() - 1);
        double scoreBefore = folderScores.get(folderScores.size() - 2);
        double difference = Math.abs(scoreAfter - scoreBefore);

        performanceLine.setText(MESSAGE_SCORE_CHANGE_PREFIX);

        /**
         * The number needs to be in a different Label because has its own style i.e. the highlighting and color change.
         * This will translate the Label manually by the correct number of pixels so that it is just to the right of the
         * Label directly preceding this one, i.e. the one containing "...has changed by ".
         */
        numberLine.setTranslateX((MESSAGE_SCORE_CHANGE_PREFIX.length() - CHARACTER_OFFSET) * PIXELS_PER_CHARACTER);

        if (scoreAfter > scoreBefore) {
            numberLine.setStyle("-fx-background-color: " + CHANGE_POSITIVE + ";");
            numberLine.setText(String.format("+%.2f", difference));
        } else if (scoreAfter == scoreBefore) {
            numberLine.setStyle("-fx-background-color: " + CHANGE_SAME + ";");
            numberLine.setText(String.format("=%.2f", difference));
        } else {
            numberLine.setStyle("-fx-background-color: " + CHANGE_NEGATIVE + ";");
            numberLine.setText(String.format("-%.2f", difference));
        }
    }

    /**
     * Adds the list of folderScores (as the Y values) to a graph.
     * @param folderScores List of Double scores to be added
     */
    private void displayGraph(List<Double> folderScores) {
        graph.getData().clear();
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        for (int x = 0; x < folderScores.size(); x = x + 1) {
            series.getData().add(new XYChart.Data<Integer, Double>(x, folderScores.get(x)));
        }
        graph.getData().add(series);
    }

    /**
     * Concatenate the questions and their scores to be added below the graph.
     * @param cards To be concatenated.
     */
    private void displayQuestions(ObservableList<Card> cards) {
        String result = "";
        for (Card card: cards) {
            result += "Question: " + card.getQuestion() + " | ";
            result += "Score: " + card.getScore();
            result += "\n";
        }
        questionsDisplay.setText(result);
    }

    private void displayTitle(String name) {
        folderName.setText("Report for " + name);
    }

    private void displayTagLine(String string) {
        tagLine.setText(string);
    }
}

