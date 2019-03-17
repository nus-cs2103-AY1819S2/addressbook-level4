package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.DailyRevenue;

/**
 * The Browser Panel for the statistics.
 */

public class StatisticsFlowPanel extends UiPart<Region> {

    private static final String FXML = "StatisticsFlowPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(StatisticsFlowPanel.class);

    private final boolean isDaily;

    private final boolean isMonthly;

    private final boolean isYearly;

    @FXML
    private FlowPane statisticsFlowPane;

    public StatisticsFlowPanel(ObservableList<DailyRevenue> dailyRevenueObservableList, ScrollPane scrollPane,
                               boolean isDaily, boolean isMonthly, boolean isYearly) {
        super(FXML);

        this.isDaily = isDaily;
        this.isMonthly = isMonthly;
        this.isYearly = isYearly;

        // To prevent triggering events for typing inside the loaded FlowPane.
        getRoot().setOnKeyPressed(Event::consume);

        statisticsFlowPane.setHgap(1);
        statisticsFlowPane.setVgap(1);
        statisticsFlowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        statisticsFlowPane.prefHeightProperty().bind(scrollPane.heightProperty());

        if (isDaily) {
            // Creates a DailyStatisticsCard for each DailyStatisticsCard and adds to FlowPane
            for (DailyRevenue dailyRevenue : dailyRevenueObservableList) {
                statisticsFlowPane.getChildren().add(new DailyStatisticsCard(dailyRevenue).getRoot());
            }

            dailyRevenueObservableList.addListener((ListChangeListener<DailyRevenue>) c -> {
                while (c.next()) {
                    if (c.wasUpdated()) {
                        logger.info("The List has been updated but not rendered");
                    }
                }
            });
        } else if (isMonthly) {
            //TODO: Creates a single MonthlyStatisticsCard for DailyStatisticsCard of the same month and adds to Flow
            // Pane

        } else if (isYearly) {
        //TODO: Creates a single YearlyStatisticsCard for DailyStatisticsCard of the same year and adds to the Flow Pane

        }
    }
}
