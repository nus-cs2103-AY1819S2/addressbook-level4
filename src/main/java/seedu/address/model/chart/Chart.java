package seedu.address.model.chart;

import java.util.Map;

import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Rating;

public class Chart {

    private final Map<CountryCode, Integer> mapCountry;
    private final Map<Rating, Integer> mapRating;

    public Chart(Map<CountryCode, Integer> mapCountry, Map<Rating, Integer> mapRating) {
        this.mapCountry = mapCountry;
        this.mapRating = mapRating;
        System.out.println(mapCountry.toString());
        System.out.println(mapRating.toString());
    }

    public Map<CountryCode, Integer> getCountryChart() {
        return mapCountry;
    }

    public Map<Rating, Integer> getRatingChart() {
        return mapRating;
    }
}
