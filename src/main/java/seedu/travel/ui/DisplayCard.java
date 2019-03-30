package seedu.travel.ui;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.travel.model.chart.Chart;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Rating;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayListCard.fxml";

    public final Chart chart;

    @FXML
    private VBox cardPane;

    @FXML
    private BarChart countryBarChart;

    @FXML
    private PieChart ratingPieChart;

    @FXML
    private LineChart yearLineChart;

    @SuppressWarnings("unchecked")
    public DisplayCard(Chart chart) {
        super(FXML);
        this.chart = chart;

        Map<CountryCode, Integer> mapCountry = chart.getMapCountry();
        createBarChart(mapCountry);

        Map<Rating, Double> mapRating = chart.getMapRating();
        createPieChart(mapRating);

        Map<String, Integer> mapYear = chart.getMapYear();
        createLineChart(mapYear);
    }

    /**
     * Creates a bar chart
     */
    @SuppressWarnings("unchecked")
    private void createBarChart(Map<CountryCode, Integer> mapCountry) {
        XYChart.Series series = new XYChart.Series();
        Object[] countries = mapCountry.keySet().toArray();

        for (int i = 0; i < mapCountry.size(); i++) {
            String countryName = (String) countries[i];
            series.getData().add(new XYChart.Data(countryName, mapCountry.get(countryName)));
        }

        countryBarChart.setTitle("Number of Places Visited for Each Country");
        countryBarChart.getYAxis().setLabel("Number of Places");
        countryBarChart.getXAxis().setLabel("Country");
        countryBarChart.setLegendVisible(false);
        countryBarChart.getData().add(series);
        countryBarChart.setStyle("-fx-padding: 45 10 45 10;");
    }

    /**
     * Creates a pie chart
     */
    private void createPieChart(Map<Rating, Double> mapRating) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Object[] ratings = mapRating.keySet().toArray();

        for (int i = 0; i < mapRating.size(); i++) {
            String ratingName = (String) ratings[i];
            pieChartData.add(new PieChart.Data(ratingName + " Stars", mapRating.get(ratingName)));
        }

        ratingPieChart.setTitle("Number of Places Visited for Each Rating Category");
        ratingPieChart.setData(pieChartData);
        ratingPieChart.setStyle("-fx-padding: 45 0 45 0;");
    }

    /**
     * Creates a line chart
     */
    @SuppressWarnings("unchecked")
    private void createLineChart(Map<String, Integer> mapYear) {
        XYChart.Series series = new XYChart.Series();
        Object[] years = mapYear.keySet().toArray();

        for (int i = 0; i < mapYear.size(); i++) {
            String year = (String) years[i];
            series.getData().add(new XYChart.Data(year, mapYear.get(year)));
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
        return chart.equals(card.chart);
    }
}
