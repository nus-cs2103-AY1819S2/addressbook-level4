package seedu.travel.model.chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Rating;

/**
 * Stores chart data into JSON files
 */
public class Chart {

    private final Logger logger = LogsCenter.getLogger(getClass());

    public Chart(Map<CountryCode, Integer> mapCountry, Map<Rating, Integer> mapRating) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fwCountry = new FileWriter("data/countryChart.json");
            gson.toJson(mapCountry, fwCountry);
            fwCountry.flush();
            FileWriter fwRating = new FileWriter("data/ratingChart.json");
            gson.toJson(mapRating, fwRating);
            fwRating.flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
