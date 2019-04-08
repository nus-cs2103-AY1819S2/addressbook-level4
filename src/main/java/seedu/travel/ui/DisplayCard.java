package seedu.travel.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.travel.model.ChartBook;
import seedu.travel.model.chart.CountryChart;
import seedu.travel.model.chart.RatingChart;
import seedu.travel.model.chart.YearChart;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayListCard.fxml";

    public final ChartBook chartBook;

    @FXML
    private VBox cardPane;

    @FXML
    private BarChart countryBarChart;

    @FXML
    private PieChart ratingPieChart;

    @FXML
    private LineChart yearLineChart;

    @SuppressWarnings("unchecked")
    public DisplayCard(ChartBook chartBook) {
        super(FXML);
        requireNonNull(chartBook);
        this.chartBook = chartBook;

        List<CountryChart> countryList = chartBook.getCountryList();
        createBarChart(countryList);

        List<RatingChart> ratingList = chartBook.getRatingList();
        createPieChart(ratingList);

        List<YearChart> yearList = chartBook.getYearList();
        createLineChart(yearList);

        // disables selection for all cards
        cardPane.addEventFilter(MouseEvent.ANY, Event::consume);
    }

    /**
     * Creates a bar chart
     */
    @SuppressWarnings("unchecked")
    private void createBarChart(List<CountryChart> countryList) {
        List<CountryChart> tempCountryList = new ArrayList<>(countryList);

        tempCountryList.sort(Comparator.comparingInt(CountryChart::getTotal).reversed());

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < tempCountryList.size(); i++) {
            CountryChart country = tempCountryList.get(i);
            String countryName = country.getChartCountryCode().toString();
            series.getData().add(new XYChart.Data(countryName, country.getTotal()));
        }

        countryBarChart.setTitle("Number of Places Visited for Each Country");
        countryBarChart.getYAxis().setLabel("Number of Places");
        countryBarChart.getXAxis().setLabel("Country");
        countryBarChart.setLegendVisible(false);
        countryBarChart.autosize();
        countryBarChart.getData().add(series);
        countryBarChart.setStyle("-fx-padding: 10 10 30 10;");
    }

    /**
     * Creates a pie chart
     */
    private void createPieChart(List<RatingChart> ratingList) {
        List<RatingChart> tempRatingList = new ArrayList<>(ratingList);

        tempRatingList.sort(Comparator.comparing(o -> o.getChartRating().toString()));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < tempRatingList.size(); i++) {
            RatingChart rating = tempRatingList.get(i);
            String ratingName = rating.getChartRating().toString();
            if (i == 0) {
                pieChartData.add(new PieChart.Data(ratingName + " Star", rating.getTotal()));
            } else {
                pieChartData.add(new PieChart.Data(ratingName + " Stars", rating.getTotal()));
            }
        }

        ratingPieChart.setTitle("Number of Places Visited for Each Rating Category");
        ratingPieChart.setStyle("-fx-padding: 30 10 30 30;");
        ratingPieChart.setLegendSide(Side.LEFT);
        ratingPieChart.setClockwise(true);
        ratingPieChart.setLabelsVisible(true);
        ratingPieChart.setStartAngle(90);
        ratingPieChart.setData(pieChartData);
    }

    /**
     * Creates a line chart
     */
    @SuppressWarnings("unchecked")
    private void createLineChart(List<YearChart> yearList) {
        List<YearChart> tempYearList = new ArrayList<>(yearList);

        tempYearList.sort(Comparator.comparing(YearChart::getChartYear));

        XYChart.Series series = new XYChart.Series();

        int startYear = Integer.parseInt(tempYearList.get(0).getChartYear());

        int i = 0;
        while (i < tempYearList.size()) {
            YearChart year = tempYearList.get(i);
            String yearName = year.getChartYear();
            if (Integer.parseInt(yearName) == startYear) {
                series.getData().add(new XYChart.Data(yearName, year.getTotal()));
                startYear++;
                i++;
            } else {
                series.getData().add(new XYChart.Data(String.valueOf(startYear), 0));
                startYear++;
            }
        }

        yearLineChart.setTitle("Number of Places Visited for Each Year");
        yearLineChart.getYAxis().setLabel("Number of Places");
        yearLineChart.getXAxis().setLabel("Year");
        yearLineChart.setLegendVisible(false);
        yearLineChart.getData().add(series);
        yearLineChart.setStyle("-fx-padding: 30 10 30 10;");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayCard)) {
            return false;
        }

        // state check
        DisplayCard card = (DisplayCard) other;
        return chartBook.equals(card.chartBook);
    }
}
