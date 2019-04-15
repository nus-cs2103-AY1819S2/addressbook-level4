package seedu.knowitall.ui;

import static seedu.knowitall.model.Model.MIN_FRACTION_ANSWERED_TO_COUNT;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class ReportDisplay extends UiPart<Region> {

    private static final String FXML = "ReportDisplay.fxml";

    private static final String MESSAGE_SCORE_CHANGE_PREFIX = "Your latest score has changed by ";

    private static final int NUM_QUESTIONS_SHOWN = 3;
    private static final int MAX_QUESTION_CHAR = 60;


    // Pixel values used for manipulating string offset (see below)
    private static final int PIXELS_PER_CHARACTER = 6;
    private static final int CHARACTER_OFFSET = 2;

    // number change display colors, in hex
    private static final String CHANGE_POSITIVE = "#47AB6C"; // green
    private static final String CHANGE_NEGATIVE = "#ED553B"; // red
    private static final String CHANGE_SAME = "#BDB76B"; // dark khaki

    @FXML
    private FlowPane container;

    @FXML
    private GridPane reportDisplay;

    @FXML
    private ScrollPane scrollPane;

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
        displayTagLine(cardFolder.getCardList().size());

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
            series.getData().add(new XYChart.Data<Integer, Double>(x + 1, folderScores.get(x)));
        }
        graph.setMaxHeight(250);
        graph.getData().add(series);
    }

    /**
     * Concatenate the questions and their scores to be added below the graph.
     * @param cards To be concatenated.
     */
    private void displayQuestions(ObservableList<Card> cards) {
        String result = "\t\t\t\t\t\t\t\tLowest scoring questions:\n";
        int toShow = Math.min(NUM_QUESTIONS_SHOWN, cards.size());
        String question;
        for (int i = 0; i < toShow; i++) {
            Card card = cards.get(i);
            question = card.getQuestion().fullQuestion;
            if (question.length() > MAX_QUESTION_CHAR) {
                question = question.substring(0, MAX_QUESTION_CHAR) + " ...";
            }
            question = "Question: " + question;
            result += question + " | " + "Score: " + (int) (100 * card.getScore().getAsDouble()) + "%\n";
        }
        questionsDisplay.setText(result);
    }

    private void displayTitle(String name) {
        folderName.setText("Report for " + name);
    }

    private void displayTagLine(int size) {
        String tagline = "Last " + size + " scores, latest on the right.";
        tagline += "\nMinimum " + size / MIN_FRACTION_ANSWERED_TO_COUNT + " question(s) attempt recorded";
        tagLine.setText(tagline);
    }
}

