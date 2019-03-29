package seedu.travel.model.chart;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.commons.util.InvalidationListenerManager;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;

/**
 * Stores chart data into JSON files
 */
public class Chart implements Observable {

    public static final String FILE_CHART_COUNTRY = "data/countryChart.json";
    public static final String FILE_CHART_RATING = "data/ratingChart.json";
    public static final String FILE_CHART_YEAR = "data/yearChart.json";

    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Map<CountryCode, Integer> mapCountry;
    private final Map<Rating, Integer> mapRating;
    private final Map<String, Integer> mapYear;

    public Chart(ObservableList<Place> placeList) {
        this.mapCountry = createMapCountry(placeList);
        storeJsonCountry();

        this.mapRating = createMapRating(placeList);
        storeJsonRating();

        this.mapYear = createMapYear(placeList);
        storeJsonYear();
    }

    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the travel book has been modified.
     */
    public void indicateModified() {
        invalidationListenerManager.callListeners(this);
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
    private Map<Rating, Integer> createMapRating(ObservableList<Place> placeList) {
        Rating rating;
        Map<Rating, Integer> mapRating = new HashMap<>();
        for (Place place : placeList) {
            rating = place.getRating();
            if (mapRating.containsKey(rating)) {
                mapRating.put(rating, mapRating.get(rating) + 1);
            } else {
                mapRating.put(rating, 1);
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
    private void storeJsonCountry() {
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
    private void storeJsonRating() {
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
    private void storeJsonYear() {
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
     * Returns country
     */
    public Map<CountryCode, Integer> getMapCountry() {
        return mapCountry;
    }

    /**
     * Returns rating
     */
    public Map<Rating, Integer> getMapRating() {
        return mapRating;
    }

    /**
     * Returns year
     */
    public Map<String, Integer> getMapYear() {
        return mapYear;
    }
}
