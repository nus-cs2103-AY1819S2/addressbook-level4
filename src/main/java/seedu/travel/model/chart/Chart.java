package seedu.travel.model.chart;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.collections.ObservableList;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;

/**
 * Stores chart data into JSON files
 */
public class Chart {

    public static final String FILE_CHART_COUNTRY = "data/countryChart.json";
    public static final String FILE_CHART_RATING = "data/ratingChart.json";
    public static final String FILE_CHART_YEAR = "data/yearChart.json";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public Chart(ObservableList<Place> placeList) {
        Map<CountryCode, Integer> mapCountry = createMapCountry(placeList);
        storeJsonCountry(mapCountry);

        Map<Rating, Double> mapRating = createMapRating(placeList);
        storeJsonRating(mapRating);

        Map<String, Integer> mapYear = createMapYear(placeList);
        storeJsonYear(mapYear);

    }

    /**
     * Returns the year set map
     */
    private Map<String, Integer> createMapYear(ObservableList<Place> placeList) {
        String year;
        Map<String, Integer> mapYear = new HashMap<>();
        for (Place place : placeList) {
            year = place.getDateVisited().getYear();
            if (mapYear.containsKey(year)) {
                mapYear.put(year, mapYear.get(year) + 1);
            } else {
                mapYear.put(year, 1);
            }
        }
        return mapYear;
    }

    /**
     * Returns the rating set map
     */
    private Map<Rating, Double> createMapRating(ObservableList<Place> placeList) {
        Rating rating;
        Map<Rating, Double> mapRating = new HashMap<>();
        for (Place place : placeList) {
            rating = place.getRating();
            if (mapRating.containsKey(rating)) {
                mapRating.put(rating, mapRating.get(rating) + 1.0);
            } else {
                mapRating.put(rating, 1.0);
            }
        }
        return mapRating;
    }

    /**
     * Returns the country set map
     */
    private Map<CountryCode, Integer> createMapCountry(ObservableList<Place> placeList) {
        CountryCode countryCode;
        Map<CountryCode, Integer> mapCountry = new HashMap<>();
        for (Place place : placeList) {
            countryCode = place.getCountryCode();
            if (mapCountry.containsKey(countryCode)) {
                mapCountry.put(countryCode, mapCountry.get(countryCode) + 1);
            } else {
                mapCountry.put(countryCode, 1);
            }
        }
        return mapCountry;
    }

    /**
     * Stores country in Json file
     */
    private void storeJsonCountry(Map<CountryCode, Integer> mapCountry) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriterCountry = new FileWriter(FILE_CHART_COUNTRY);
            gson.toJson(mapCountry, fileWriterCountry);
            fileWriterCountry.flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Stores rating in Json file
     */
    private void storeJsonRating(Map<Rating, Double> mapRating) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriterRating = new FileWriter(FILE_CHART_RATING);
            gson.toJson(mapRating, fileWriterRating);
            fileWriterRating.flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Stores year in Json file
     */
    private void storeJsonYear(Map<String, Integer> mapYear) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriterYear = new FileWriter(FILE_CHART_YEAR);
            gson.toJson(mapYear, fileWriterYear);
            fileWriterYear.flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Returns the hash map of the country
     */
    public Map<CountryCode, Integer> getMapCountry() {
        Map<CountryCode, Integer> mapCountry = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(FILE_CHART_COUNTRY);
            JsonReader jsonReader = new JsonReader(fileReader);
            Gson gson = new Gson();
            mapCountry = gson.fromJson(jsonReader, HashMap.class);
            fileReader.close();
            jsonReader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return mapCountry;
    }

    /**
     * Returns the hash map of the rating
     */
    public Map<Rating, Double> getMapRating() {
        Map<Rating, Double> mapRating = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(FILE_CHART_RATING);
            JsonReader jsonReader = new JsonReader(fileReader);
            Gson gson = new Gson();
            mapRating = gson.fromJson(jsonReader, HashMap.class);
            fileReader.close();
            jsonReader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return mapRating;
    }

    /**
     * Returns the hash map of the year
     */
    public Map<String, Integer> getMapYear() {
        Map<String, Integer> mapYear = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(FILE_CHART_YEAR);
            JsonReader jsonReader = new JsonReader(fileReader);
            Gson gson = new Gson();
            mapYear = gson.fromJson(jsonReader, HashMap.class);
            fileReader.close();
            jsonReader.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
        return mapYear;
    }
}
