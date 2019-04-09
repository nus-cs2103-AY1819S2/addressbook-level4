package seedu.travel.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.travel.model.ChartBook;
import seedu.travel.model.chart.CountryChart;
import seedu.travel.model.chart.RatingChart;
import seedu.travel.model.chart.YearChart;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class ChartCard extends UiPart<Region> {

    private static final String FXML = "ChartListCard.fxml";

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
    public ChartCard(ChartBook chartBook) {
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

        countryBarChart.setTitle("Number of Places Visited for Each Country");
        countryBarChart.getYAxis().setLabel("Number of Places");
        countryBarChart.getXAxis().setLabel("Country");
        countryBarChart.setLegendVisible(false);
        countryBarChart.getYAxis().setTickLabelsVisible(false);
        countryBarChart.autosize();
        countryBarChart.getData().add(series);
        countryBarChart.setStyle("-fx-padding: 10 10 30 10;");

        for (int i = 0; i < tempCountryList.size(); i++) {
            CountryChart country = tempCountryList.get(i);
            String countryName = country.getChartCountryCode().toString();
            XYChart.Data<String, Number> data = new XYChart.Data<>(countryName, country.getTotal());

            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        // adds a label to the top of each bar
                        displayLabelForData(data);
                    }
                }
            });

            series.getData().add(data);
        }
    }

    /**
     * Adds a label to the top of each bar
     */
    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");

        // adds a text label to the parent of each bar node
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                Group parentGroup = (Group) newValue;
                parentGroup.getChildren().add(dataText);
            }
        });

        // dynamically positions the text label based on the bar and text's bounds each time the bar is resized.
        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                dataText.setLayoutX(
                        Math.round(newValue.getMinX() + newValue.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                dataText.setLayoutY(
                        Math.round(newValue.getMinY() - dataText.prefHeight(-1) * 0.5));
            }
        });
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
        if (!(other instanceof ChartCard)) {
            return false;
        }

        // state check
        ChartCard card = (ChartCard) other;
        return chartBook.equals(card.chartBook);
    }
}
