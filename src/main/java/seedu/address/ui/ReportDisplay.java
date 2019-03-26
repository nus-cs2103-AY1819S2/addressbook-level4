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

    public ReportDisplay(ReadOnlyCardFolder cardFolder) {
        super(FXML);

        displayTitle(cardFolder.getFolderName());
        displayGraph(cardFolder.getFolderScores());
        displayQuestions(cardFolder.getCardList());
        displayTagLine("Last " + cardFolder.getCardList().size() + " scores, latest on the right");

        reportDisplay.getChildren().clear();
        reportDisplay.getChildren().add(folderName);
        reportDisplay.getChildren().add(tagLine);
        reportDisplay.getChildren().add(graph);
        reportDisplay.getChildren().add(questionsDisplay);

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

