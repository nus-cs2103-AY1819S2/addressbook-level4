package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.MonthlyRevenue;
import seedu.address.model.statistics.YearlyRevenue;

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
                statisticsFlowPane.getChildren().clear();
                for (DailyRevenue dailyRevenue : dailyRevenueObservableList) {
                    statisticsFlowPane.getChildren().add(new DailyStatisticsCard(dailyRevenue).getRoot());
                }
            });
        } else if (isMonthly) {
            //TODO: Creates a single MonthlyStatisticsCard for DailyStatisticsCard of the same month and adds to Flow
            ObservableList<MonthlyRevenue> monthlyRevenueList = FXCollections.observableArrayList();

            //ArrayList<MonthlyRevenue> monthlyRevenueArrayList = new ArrayList<>();
            MonthlyRevenue monthlyRevenue;
            for (DailyRevenue dailyRevenue : dailyRevenueObservableList) {

                if (monthlyRevenueList.isEmpty()) {
                    monthlyRevenue = new MonthlyRevenue(dailyRevenue.getMonth(), dailyRevenue.getYear(),
                            dailyRevenue.getTotalDailyRevenue());
                    monthlyRevenueList.add(monthlyRevenue);
                } else {
                    monthlyRevenue = monthlyRevenueList.get(monthlyRevenueList.size() - 1);
                    if (dailyRevenue.getYear().equals(monthlyRevenue.getYear()) && dailyRevenue.getMonth().equals
                            (monthlyRevenue.getMonth())) {
                        monthlyRevenue.addToRevenue(dailyRevenue.getTotalDailyRevenue());
                    } else {
                        monthlyRevenue = new MonthlyRevenue(dailyRevenue.getMonth(), dailyRevenue.getYear(),
                                dailyRevenue.getTotalDailyRevenue());
                        monthlyRevenueList.add(monthlyRevenue);
                    }
                }
            }

            for (MonthlyRevenue revenue : monthlyRevenueList) {
                statisticsFlowPane.getChildren().add(new MonthlyStatisticsCard(revenue).getRoot());
            }

            monthlyRevenueList.addListener((ListChangeListener<MonthlyRevenue>) c -> {
                statisticsFlowPane.getChildren().clear();
                for (MonthlyRevenue revenue : monthlyRevenueList) {
                    statisticsFlowPane.getChildren().add(new MonthlyStatisticsCard(revenue).getRoot());
                }
            });

        } else if (isYearly) {
            //TODO: Creates a single YearlyStatisticsCard for DailyStatisticsCard of the same year and adds to the Flow Pane

            ObservableList<YearlyRevenue> yearlyRevenueList = FXCollections.observableArrayList();

            //ArrayList<MonthlyRevenue> monthlyRevenueArrayList = new ArrayList<>();
            YearlyRevenue yearlyRevenue;
            for (DailyRevenue dailyRevenue : dailyRevenueObservableList) {

                if (yearlyRevenueList.isEmpty()) {
                    yearlyRevenue = new YearlyRevenue(dailyRevenue.getYear(), dailyRevenue.getTotalDailyRevenue());
                    yearlyRevenueList.add(yearlyRevenue);
                } else {
                    yearlyRevenue = yearlyRevenueList.get(yearlyRevenueList.size() - 1);
                    if (yearlyRevenue.getYear().equals(dailyRevenue.getYear())) {
                        yearlyRevenue.addToRevenue(dailyRevenue.getTotalDailyRevenue());
                    } else {
                        yearlyRevenue = new YearlyRevenue(dailyRevenue.getYear(), dailyRevenue.getTotalDailyRevenue());
                        yearlyRevenueList.add(yearlyRevenue);
                    }
                }
            }

            for (YearlyRevenue revenue : yearlyRevenueList) {
                statisticsFlowPane.getChildren().add(new YearlyStatisticsCard(revenue).getRoot());
            }

            yearlyRevenueList.addListener((ListChangeListener<YearlyRevenue>) c -> {
                statisticsFlowPane.getChildren().clear();
                for (YearlyRevenue revenue : yearlyRevenueList) {
                    statisticsFlowPane.getChildren().add(new YearlyStatisticsCard(revenue).getRoot());
                }
            });
        }
    }
}
