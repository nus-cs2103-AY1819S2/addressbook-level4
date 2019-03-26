package seedu.address.ui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.chart.AreaChart;
import seedu.address.model.CardFolder;
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

    public ReportDisplay(ReadOnlyCardFolder cardFolder) {
        super(FXML);

        displayTitle(cardFolder.getFolderName(), cardFolder.getFolderScores().size());
        displayGraph(cardFolder.getFolderScores());
        displayQuestions(cardFolder.getCardList());

        reportDisplay.getChildren().clear();
        reportDisplay.getChildren().add(folderName);
        reportDisplay.getChildren().add(graph);
        reportDisplay.getChildren().add(questionsDisplay);

    }

    private void displayGraph(List<Double> folderScores) {
        final XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        for (int x = 0; x < folderScores.size(); x = x + 1) {
            series.getData().add(new XYChart.Data<Integer, Double>(x, folderScores.get(x)));
        }
        graph.getData().add(series);
    }

    private void displayQuestions(ObservableList<Card> cards) {
        String result = "";
        for (Card card: cards) {
            result += card.getQuestion() + " ";
            result += card.getScore();
            result += "\n";
        }
        questionsDisplay.setText(result);
    }

    private void displayTitle(String name, int numScores) {
        folderName.setText("Report for " + name + "\nLast " + numScores + " scores");
    }
}

