package seedu.travel.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
        cardPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });
    }

    /**
     * Creates a bar chart
     */
    @SuppressWarnings("unchecked")
    private void createBarChart(List<CountryChart> countryList) {
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < countryList.size(); i++) {
            CountryChart country = countryList.get(i);
            String countryName = country.getChartCountryCode().toString();
            series.getData().add(new XYChart.Data(countryName, country.getTotal()));
        }

        countryBarChart.setTitle("Number of Places Visited for Each Country");
        countryBarChart.getYAxis().setLabel("Number of Places");
        countryBarChart.getXAxis().setLabel("Country");
        countryBarChart.setLegendVisible(false);
        countryBarChart.autosize();
        countryBarChart.getData().add(series);
        countryBarChart.setStyle("-fx-padding: 45 10 45 10;");
    }

    /**
     * Creates a pie chart
     */
    private void createPieChart(List<RatingChart> ratingList) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < ratingList.size(); i++) {
            RatingChart rating = ratingList.get(i);
            String ratingName = rating.getChartRating().toString();
            pieChartData.add(new PieChart.Data(ratingName + " Stars", rating.getTotal()));
        }

        ratingPieChart.setTitle("Number of Places Visited for Each Rating Category");
        ratingPieChart.setData(pieChartData);
        ratingPieChart.setStyle("-fx-padding: 45 0 45 0;");
    }

    /**
     * Creates a line chart
     */
    @SuppressWarnings("unchecked")
    private void createLineChart(List<YearChart> yearList) {
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < yearList.size(); i++) {
            YearChart year = yearList.get(i);
            String yearName = year.getChartYear();
            series.getData().add(new XYChart.Data(yearName, year.getTotal()));
        }

        yearLineChart.setTitle("Number of Places Visited for Each Year");
        yearLineChart.getYAxis().setLabel("Number of Places");
        yearLineChart.getXAxis().setLabel("Year");
        yearLineChart.setLegendVisible(false);
        yearLineChart.getData().add(series);
        yearLineChart.setStyle("-fx-padding: 45 10 45 10;");
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
