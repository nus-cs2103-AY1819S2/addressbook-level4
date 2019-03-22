package seedu.travel.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.CountryCode;

/**
 * Generates the UI portion of the chart.
 */
public class ChartUi extends Application {

    private static final String FILENAME = "data/countryChart.json";
    private final Logger logger = LogsCenter.getLogger(getClass());

    // final static String austria = "Austria";
    // final static String brazil = "Brazil";
    // final static String france = "France";
    // final static String italy = "Italy";
    // final static String usa = "USA";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Number of Visits to Each Country");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Number of Visits to Each Country");
        xAxis.setLabel("Country");
        yAxis.setLabel("Number of Visits");

        Map<CountryCode, Integer> responses = new HashMap<>();

        try {
            FileReader frCountry = new FileReader(FILENAME);
            JsonReader jsonReader = new JsonReader(frCountry);
            Gson gson = new Gson();
            responses = gson.fromJson(jsonReader, HashMap.class);
            frCountry.close();
            jsonReader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        XYChart.Series seriesCountry = new XYChart.Series();
        seriesCountry.setName("Country");
        Object[] countries = responses.keySet().toArray();
        for (int i = 0; i < responses.size(); i++) {
            String country = (String) countries[i];
            seriesCountry.getData().add(new XYChart.Data(country, responses.get(country)));
            logger.info(country + ", " + responses.get(country) + "\n");
        }

        Scene scene = new Scene(barChart);
        barChart.getData().add(seriesCountry);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
