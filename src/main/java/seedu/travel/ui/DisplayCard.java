package seedu.travel.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.chart.Chart;
import seedu.travel.model.place.CountryCode;

/**
 * An UI component that displays information of a {@code Place}.
 */
public class DisplayCard extends UiPart<Region> {

    private static final String FXML = "DisplayListCard.fxml";

    public final Chart chart;
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox cardPane;
    @FXML
    private BarChart countryBarChart;

    @SuppressWarnings("unchecked")
    public DisplayCard(Chart chart) {
        super(FXML);
        this.chart = chart;

        //final CategoryAxis xAxis = new CategoryAxis();
        //final NumberAxis yAxis = new NumberAxis();
        //final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        //barChart.setTitle("Number of Places Visited for Each Country");
        //xAxis.setLabel("Country");
        //yAxis.setLabel("Number of Places");

        Map<CountryCode, Integer> responsesCountry = new HashMap<>();

        try {
            FileReader fileReaderCountry = new FileReader("data/countryChart.json");
            JsonReader jsonReaderCountry = new JsonReader(fileReaderCountry);
            Gson gson = new Gson();
            responsesCountry = gson.fromJson(jsonReaderCountry, HashMap.class);
            fileReaderCountry.close();
            jsonReaderCountry.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        XYChart.Series seriesCountry = new XYChart.Series();
        seriesCountry.setName("Country");
        Object[] countries = responsesCountry.keySet().toArray();
        for (int i = 0; i < responsesCountry.size(); i++) {
            String country = (String) countries[i];
            seriesCountry.getData().add(new XYChart.Data(country, responsesCountry.get(country)));
            logger.info(country + ", " + responsesCountry.get(country) + "\n");
        }

        //barChart.getData().add(seriesCountry);
        countryBarChart.setTitle("Number of Places Visited for Each Country");
        countryBarChart.getData().add(seriesCountry);
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
