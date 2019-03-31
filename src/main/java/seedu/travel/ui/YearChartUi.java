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
public class YearChartUi extends Application {

    private final Logger logger = LogsCenter.getLogger(getClass());

    // final static String austria = "Austria";
    // final static String brazil = "Brazil";
    // final static String france = "France";
    // final static String italy = "Italy";
    // final static String usa = "USA";

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) {
        stage.setTitle("Number of Places Visited for Each Year");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Number of Places Visited for Each Year");
        xAxis.setLabel("Year");
        yAxis.setLabel("Number of Places");

        Map<CountryCode, Integer> responses = new HashMap<>();

        try {
            FileReader frCountry = new FileReader("data/yearChart.json");
            JsonReader jsonReader = new JsonReader(frCountry);
            Gson gson = new Gson();
            responses = gson.fromJson(jsonReader, HashMap.class);
            frCountry.close();
            jsonReader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        XYChart.Series seriesYear = new XYChart.Series();
        seriesYear.setName("Rating");
        Object[] years = responses.keySet().toArray();
        for (int i = 0; i < responses.size(); i++) {
            String year = (String) years[i];
            seriesYear.getData().add(new XYChart.Data(year, responses.get(year)));
            logger.info(year + ", " + responses.get(year) + "\n");
        }

        Scene scene = new Scene(barChart);
        barChart.getData().add(seriesYear);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
