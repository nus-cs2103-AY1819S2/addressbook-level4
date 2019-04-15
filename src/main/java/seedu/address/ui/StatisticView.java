package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 *  Initializes the Statistics Pop-up Window.
 * @author bos10
 */
public class StatisticView extends UiPart<Stage> {
    public static final String WINDOW_TITLE = "Gameplay Summary";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "StatisticView.fxml";

    @FXML
    private StackPane statisticView;

    /**
     * Constructor for the pop-up window.
     * @param root
     * @param statsData
     */
    public StatisticView(Stage root, XYChart.Series<String, Number> statsData) {
        super(FXML, root);
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> statsChart = new BarChart<>(xAxis, yAxis);
        statsChart.getData().add(statsData);
        statsChart.setLegendVisible(false);
        statsChart.setTitle(WINDOW_TITLE);
        xAxis.setLabel("Your Statistics");
        yAxis.setLabel("");
        statisticView.getChildren().add(statsChart);

    }
    /**
     * Opens pop-up window for statistics.
     */
    public void show() {
        logger.fine("Displaying Statistics");
        getRoot().show();
    }
}
