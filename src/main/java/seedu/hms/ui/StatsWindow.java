package seedu.hms.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.stats.Stats;
import seedu.hms.logic.stats.exceptions.ShownItemOutOfBoundException;
import seedu.hms.logic.stats.statsitems.StatsItem;

/**
 * The individual window for displaying statistics.
 */
public class StatsWindow extends UiPart<Stage> {
    private static final String FXML = "StatsWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);

    public final Stats stats;

    @FXML
    private TextArea statsTextArea;

    @FXML
    private ScrollPane statsChartsPane;

    public StatsWindow(Stage root, Stats stats) {
        super(FXML, root);
        requireNonNull(stats);
        this.stats = stats;

        update();
        // Update the text area
        stats.getHms().addListener(observable -> update());
    }

    public StatsWindow(Stats stats) {
        this(new Stage(), stats);
    }

    /**
     * Generates a string for the given stats.
     * @param stats The stats to be formatted as a string.
     * @return A string representing the stats.
     */
    private String generateTextReport(Stats stats) {
        return stats.toTextReport();
    }

    /**
     * Updates the content of the Stats window.
     */
    public void updateTextReport() {
        Platform.runLater(() -> {
            statsTextArea.setText(generateTextReport(stats));
            statsTextArea.setEditable(false);
        });
    }

    /**
     * Generates an ObservableList of PieChart.Data from a StatsItem.
     * @param si The StatsItem to be generated data from.
     * @return The generated ObservableList.
     */
    private ObservableList<PieChart.Data> generateDataForChart(StatsItem si) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        Map<String, Long> m = si.getMap();
        Iterator<Map.Entry<String, Long>> iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Long> entry = iter.next();
            data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        return data;
    }

    /**
     * Generates a PieChart from a StatsItem.
     * @param si The StatsItem to be generated from.
     * @return The generated PieChart.
     */
    private PieChart generateChart(StatsItem si) {
        PieChart chart = new PieChart(generateDataForChart(si));
        chart.setTitle(si.getTitle());
        HBox.setHgrow(chart, Priority.ALWAYS);
        return chart;
    }

    /**
     * Updates the charts in the stats window.
     */
    public void updateCharts(ArrayList<Index> indexList) {
        Platform.runLater(() -> {
            try {
                VBox pane = new VBox();
                pane.setFillWidth(true);
                List<StatsItem> statsitems = stats.getStatsitems();
                for (Index i : indexList) {
                    pane.getChildren().add(generateChart(statsitems.get(i.getZeroBased())));
                }
                statsChartsPane.setContent(pane);
            } catch (IndexOutOfBoundsException e) {
                throw new ShownItemOutOfBoundException();
            }
        });
    }

    /**
     * Updates everything in the stats window.
     */
    public void update(Optional<ArrayList<Index>> optionalIndexList) {
        stats.update(optionalIndexList);
        updateTextReport();
        updateCharts(stats.getShown());
    }

    public void update() {
        update(Optional.empty());
    }

    /*---- Window related ----*/

    /**
     * Shows the stats window.
     */
    public void show() {
        logger.fine("Showing the stats.");
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
