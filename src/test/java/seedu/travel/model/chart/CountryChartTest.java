package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.model.place.CountryCode;

public class CountryChartTest {

    @Test
    public void testChartCountryCode() {

        CountryCode firstCountryCode = new CountryCode("JPN");
        CountryChart firstCountryChart = new CountryChart(new CountryCode("JPN"), 4);
        CountryChart secondCountryChart = new CountryChart(new CountryCode("SGP"), 4);

        assertEquals(firstCountryCode, (firstCountryChart.getChartCountryCode()));
        assertNotEquals(firstCountryChart.getChartCountryCode(), (secondCountryChart.getChartCountryCode()));
    }

    @Test
    public void equals() {

        CountryCode firstCountry = new CountryCode("JPN");
        CountryCode secondCountry = new CountryCode("SGP");

        CountryChart firstCountryChartValueFour = new CountryChart(firstCountry, 4);
        CountryChart firstCountryChartValueFive = new CountryChart(firstCountry, 5);
        CountryChart secondCountryChartValueFour = new CountryChart(secondCountry, 4);
        CountryChart secondCountryChartValueFive = new CountryChart(secondCountry, 5);

        // same object -> returns true
        assertTrue(firstCountryChartValueFour.equals(firstCountryChartValueFour));

        // same values -> returns true
        CountryChart firstCountryChartValueFourCopy = new CountryChart(firstCountry, 4);
        assertTrue(firstCountryChartValueFour.equals(firstCountryChartValueFourCopy));

        // different types -> returns false
        assertFalse(firstCountryChartValueFour.equals(1));

        // null -> returns false
        assertFalse(firstCountryChartValueFour.equals(null));

        // different country, same total -> returns false
        assertFalse(firstCountryChartValueFour.equals(secondCountryChartValueFour));

        // same country, different total -> returns false
        assertFalse(firstCountryChartValueFour.equals(firstCountryChartValueFive));

        // different country, different total -> returns false
        assertFalse(firstCountryChartValueFour.equals(secondCountryChartValueFive));
    }

    @Test
    public void testToString() {
        CountryChart testCountryChart = new CountryChart(new CountryCode("JPN"), 4);

        String testSameOutputString = "Country Code: JPN Total: 4";
        String testDifferentOutputString = "Country Code: SGP Total: 6";


        assertEquals(testCountryChart.toString(), testSameOutputString);
        assertNotEquals(testCountryChart.toString(), testDifferentOutputString);

    }
}
