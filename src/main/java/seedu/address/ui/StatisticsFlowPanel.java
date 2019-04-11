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
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.MonthlyRevenue;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;
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

    public StatisticsFlowPanel(ObservableList<Revenue> revenueObservableList, ScrollPane scrollPane,
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

        int listSize = revenueObservableList.size();
        if (isDaily) {
            // Creates a DailyStatisticsCard for each DailyStatisticsCard and adds to FlowPane
            DailyRevenue dailyRevenue;
            ObservableList<DailyRevenue> dailyRevenueList = FXCollections.observableArrayList();

            while (dailyRevenueList.size() < 30 && listSize != 0) {
                Revenue revenue = revenueObservableList.get(listSize - 1);

                if (dailyRevenueList.isEmpty()) {
                    dailyRevenue = new DailyRevenue(revenue.getDay(), revenue.getMonth(), revenue.getYear(),
                            revenue.getTotalRevenue());
                    dailyRevenueList.add(dailyRevenue);
                    listSize--;
                } else {
                    DailyRevenue lastDailyRevenueAdded = dailyRevenueList.get(dailyRevenueList.size() - 1);
                    int previousDay = Integer.parseInt(lastDailyRevenueAdded.getDay().toString()) - 1;
                    int previousMonth = Integer.parseInt(lastDailyRevenueAdded.getMonth().toString());
                    int previousYear = Integer.parseInt(lastDailyRevenueAdded.getYear().toString());
                    if (previousDay < 1) { // going to previous month
                        previousMonth -= 1;
                        if (previousMonth < 1) { //going to previous year
                            previousYear -= 1;
                            previousMonth = 12;
                        }

                        if (previousMonth == 2) {
                            if (Year.isLeapYear(lastDailyRevenueAdded.getYear().toString())) {
                                previousDay = 29;
                            } else {
                                previousDay = 28;
                            }
                        } else if (previousMonth == 4 || previousMonth == 6 || previousMonth == 9
                                || previousMonth == 11) {
                            previousDay = 30;
                        } else {
                            previousDay = 31;
                        }
                    }

                    dailyRevenue = new DailyRevenue(new Day(Integer.toString(previousDay)),
                            new Month(Integer.toString(previousMonth)), new Year(Integer.toString(previousYear)), 0);

                    if (revenue.isSameRevenue(dailyRevenue)) {
                        dailyRevenue.addToRevenue(revenue.getTotalRevenue());
                        listSize--;
                    }

                    dailyRevenueList.add(dailyRevenue);
                }
            }

            for (DailyRevenue revenue : dailyRevenueList) {
                statisticsFlowPane.getChildren().add(new DailyStatisticsCard(revenue).getRoot());
            }

            dailyRevenueList.addListener((ListChangeListener<DailyRevenue>) c -> {
                statisticsFlowPane.getChildren().clear();
                for (DailyRevenue revenue : dailyRevenueList) {
                    statisticsFlowPane.getChildren().add(new DailyStatisticsCard(revenue).getRoot()); }
            });

        } else if (isMonthly) {
            MonthlyRevenue monthlyRevenue;
            ObservableList<MonthlyRevenue> monthlyRevenueList = FXCollections.observableArrayList();

            while (monthlyRevenueList.size() < 30 && listSize != 0) {
                Revenue revenue = revenueObservableList.get(listSize - 1);
                MonthlyRevenue currentRevenueFromList = new MonthlyRevenue(revenue.getMonth(), revenue.getYear(),
                        revenue.getTotalRevenue());

                if (monthlyRevenueList.isEmpty()) {
                    monthlyRevenueList.add(currentRevenueFromList);
                    listSize--;
                } else {
                    MonthlyRevenue lastMonthlyRevenueAdded = monthlyRevenueList.get(monthlyRevenueList.size() - 1);

                    if (lastMonthlyRevenueAdded.isSameMonthlyRevenue(currentRevenueFromList)) {
                        lastMonthlyRevenueAdded.addToRevenue(currentRevenueFromList.getTotalRevenue());
                        listSize--;
                    } else {
                        int previousMonth = Integer.parseInt(lastMonthlyRevenueAdded.getMonth().toString()) - 1;
                        int previousYear = Integer.parseInt(lastMonthlyRevenueAdded.getYear().toString());
                        if (previousMonth < 1) { //going to previous year
                            previousYear -= 1;
                            previousMonth = 12;
                        }

                        monthlyRevenue = new MonthlyRevenue(new Month(Integer.toString(previousMonth)),
                                new Year(Integer.toString(previousYear)), 0);

                        if (currentRevenueFromList.isSameMonthlyRevenue(monthlyRevenue)) {
                            monthlyRevenue.addToRevenue(currentRevenueFromList.getTotalRevenue());
                            listSize--;
                        }
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

            YearlyRevenue yearlyRevenue;
            ObservableList<YearlyRevenue> yearlyRevenueList = FXCollections.observableArrayList();

            while (yearlyRevenueList.size() < 30 && listSize != 0) {
                Revenue revenue = revenueObservableList.get(listSize - 1);
                YearlyRevenue currentRevenueFromList = new YearlyRevenue(revenue.getYear(), revenue.getTotalRevenue());

                if (yearlyRevenueList.isEmpty()) {
                    yearlyRevenueList.add(currentRevenueFromList);
                    listSize--;
                } else {
                    YearlyRevenue lastYearlyRevenueAdded = yearlyRevenueList.get(yearlyRevenueList.size() - 1);

                    if (lastYearlyRevenueAdded.isSameYearlyRevenue(currentRevenueFromList)) {
                        lastYearlyRevenueAdded.addToRevenue(currentRevenueFromList.getTotalRevenue());
                        listSize--;
                    } else {
                        int previousYear = Integer.parseInt(lastYearlyRevenueAdded.getYear().toString()) - 1;

                        yearlyRevenue = new YearlyRevenue(new Year(Integer.toString(previousYear)), 0);

                        if (currentRevenueFromList.isSameYearlyRevenue(yearlyRevenue)) {
                            yearlyRevenue.addToRevenue(currentRevenueFromList.getTotalRevenue());
                            listSize--;
                        }
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
