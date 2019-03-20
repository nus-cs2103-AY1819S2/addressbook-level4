package seedu.address.model.chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Rating;

/**
 * Generates a chart in TravelBuddy.
 */
public class Chart {

    private final Map<CountryCode, Integer> mapCountry;
    private final Map<Rating, Integer> mapRating;

    public Chart(Map<CountryCode, Integer> mapCountry, Map<Rating, Integer> mapRating) {
        this.mapCountry = mapCountry;
        this.mapRating = mapRating;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // converts map to JSON and writes it to a JSON file
            FileWriter fwCountry = new FileWriter("data/countryChart.json");
            gson.toJson(mapCountry, fwCountry);
            fwCountry.flush();
            FileWriter fwRating = new FileWriter("data/ratingChart.json");
            gson.toJson(mapRating, fwRating);
            fwRating.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<CountryCode, Integer> getCountryChart() {
        return mapCountry;
    }

    public Map<Rating, Integer> getRatingChart() {
        return mapRating;
    }
}
