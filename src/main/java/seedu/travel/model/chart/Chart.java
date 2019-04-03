//package seedu.travel.model.chart;
//
//import static java.util.Objects.requireNonNull;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.logging.Logger;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.stream.JsonReader;
//
//import javafx.collections.ObservableList;
//import seedu.travel.commons.core.LogsCenter;
//import seedu.travel.model.place.CountryCode;
//import seedu.travel.model.place.Place;
//import seedu.travel.model.place.Rating;
//
///**
// * Wraps all data at the chart-book level
// */
//public class Chart {
//
//    public static final String FILE_CHART_COUNTRY = "data/countryChart.json";
//    public static final String FILE_CHART_RATING = "data/ratingChart.json";
//    public static final String FILE_CHART_YEAR = "data/yearChart.json";
//
//    private final Logger logger = LogsCenter.getLogger(getClass());
//    private final Map<CountryCode, Integer>mapCountry;
//    private final Map<Rating, Double> mapRating;
//    private final Map<String, Integer> mapYear;
//
//    public Chart(ObservableList<Place> placeList) {
//        requireNonNull(placeList);
//        this.mapCountry = createMapCountry(placeList);
//        storeJsonCountry(mapCountry);
//
//        this.mapRating = createMapRating(placeList);
//        storeJsonRating(mapRating);
//
//        this.mapYear = createMapYear(placeList);
//        storeJsonYear(mapYear);
//    }
//
//    /**
//     * Returns the country data from travelBuddy in the form of a map data structure.
//     */
//    private Map<CountryCode, Integer> createMapCountry(ObservableList<Place> placeList) {
//        CountryCode countryCode;
//        Map<CountryCode, Integer> mapCountry = new HashMap<>();
//        for (Place place : placeList) {
//            countryCode = place.getCountryCode();
//            if (mapCountry.containsKey(countryCode)) {
//                mapCountry.put(countryCode, mapCountry.get(countryCode) + 1);
//            } else {
//                mapCountry.put(countryCode, 1);
//            }
//        }
//        return mapCountry;
//    }
//
//    /**
//     * Returns the rating data from travelBuddy in the form of a map data structure.
//     */
//    private Map<Rating, Double> createMapRating(ObservableList<Place> placeList) {
//        Rating rating;
//        Map<Rating, Double> mapRating = new HashMap<>();
//        for (Place place : placeList) {
//            rating = place.getRating();
//            if (mapRating.containsKey(rating)) {
//                mapRating.put(rating, mapRating.get(rating) + 1.0);
//            } else {
//                mapRating.put(rating, 1.0);
//            }
//        }
//        return mapRating;
//    }
//
//    /**
//     * Returns the year data from travelBuddy in the form of a map data structure.
//     */
//    private Map<String, Integer> createMapYear(ObservableList<Place> placeList) {
//        String year;
//        Map<String, Integer> mapYear = new HashMap<>();
//        for (Place place : placeList) {
//            year = place.getDateVisited().getYear();
//            if (mapYear.containsKey(year)) {
//                mapYear.put(year, mapYear.get(year) + 1);
//            } else {
//                mapYear.put(year, 1);
//            }
//        }
//        return mapYear;
//    }
//
//    /**
//     * Stores the country data into a Json file
//     */
//    private void storeJsonCountry(Map<CountryCode, Integer> mapCountry) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            FileWriter fileWriterCountry = new FileWriter(FILE_CHART_COUNTRY);
//            gson.toJson(mapCountry, fileWriterCountry);
//            fileWriterCountry.flush();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//    }
//
//    /**
//     * Stores the rating data into a Json file
//     */
//    private void storeJsonRating(Map<Rating, Double> mapRating) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            FileWriter fileWriterRating = new FileWriter(FILE_CHART_RATING);
//            gson.toJson(mapRating, fileWriterRating);
//            fileWriterRating.flush();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//    }
//
//    /**
//     * Stores the year data into a Json file
//     */
//    private void storeJsonYear(Map<String, Integer> mapYear) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try {
//            FileWriter fileWriterYear = new FileWriter(FILE_CHART_YEAR);
//            gson.toJson(mapYear, fileWriterYear);
//            fileWriterYear.flush();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//    }
//
//    /**
//     * Retrieves the country data from the JSON file and converts into a hash map structure
//     */
//    public Map<CountryCode, Integer> getMapCountry() {
//        Map<CountryCode, Integer> mapCountry = new HashMap<>();
//        try {
//            FileReader fileReader = new FileReader(FILE_CHART_COUNTRY);
//            JsonReader jsonReader = new JsonReader(fileReader);
//            Gson gson = new Gson();
//            mapCountry = gson.fromJson(jsonReader, HashMap.class);
//            fileReader.close();
//            jsonReader.close();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//        return mapCountry;
//    }
//
//    /**
//     * Retrieves the rating data from the JSON file and converts into a hash map structure
//     */
//    public Map<Rating, Double> getMapRating() {
//        Map<Rating, Double> mapRating = new HashMap<>();
//        try {
//            FileReader fileReader = new FileReader(FILE_CHART_RATING);
//            JsonReader jsonReader = new JsonReader(fileReader);
//            Gson gson = new Gson();
//            mapRating = gson.fromJson(jsonReader, HashMap.class);
//            fileReader.close();
//            jsonReader.close();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//        return mapRating;
//    }
//
//    /**
//     * Retrieves the year data from the JSON file and converts into a hash map structure
//     */
//    public Map<String, Integer> getMapYear() {
//        Map<String, Integer> mapYear = new HashMap<>();
//        try {
//            FileReader fileReader = new FileReader(FILE_CHART_YEAR);
//            JsonReader jsonReader = new JsonReader(fileReader);
//            Gson gson = new Gson();
//            mapYear = gson.fromJson(jsonReader, HashMap.class);
//            fileReader.close();
//            jsonReader.close();
//        } catch (IOException e) {
//            logger.warning(e.getMessage());
//        }
//        return mapYear;
//    }
//
//    /**
//     * Returns true if both charts have the same identity and data fields.
//     * This defines a stronger notion of equality between two places.
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof Chart)) {
//            return false;
//        }
//
//        Chart otherPlace = (Chart) other;
//        return otherPlace.getMapCountry().equals(getMapCountry())
//                && otherPlace.getMapRating().equals(getMapRating())
//                && otherPlace.getMapYear().equals(getMapYear());
//    }
//
//    @Override
//    public int hashCode() {
//        // use this method for custom fields hashing instead of implementing your own
//        return Objects.hash(mapCountry, mapRating, mapYear);
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append("Chart")
//                .append("\nCountries: ")
//                .append(getMapCountry().toString())
//                .append("\nRatings: ")
//                .append(getMapRating().toString())
//                .append("\nYears: ")
//                .append(getMapYear().toString());
//        return builder.toString();
//    }
//}
