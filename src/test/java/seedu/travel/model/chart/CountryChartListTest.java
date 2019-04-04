package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.model.chart.exceptions.CountryChartNotFoundException;
import seedu.travel.model.place.CountryCode;

public class CountryChartListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_validCountryCode_addSuccessful() {
        CountryChartList countryChartList = new CountryChartList();
        CountryChart firstCountryChart = new CountryChart(new CountryCode("JPN"), 1);
        CountryChart secondCountryChart = new CountryChart(new CountryCode("SGP"), 1);

        countryChartList.add(firstCountryChart); // add new country code
        assertFalse(countryChartList.asUnmodifiableObservableList().isEmpty());

        countryChartList.add(firstCountryChart); // add duplicate valid country code
        assertEquals(countryChartList.asUnmodifiableObservableList().get(0).getChartCountryCode(),
                new CountryCode("JPN"));
        assertEquals(countryChartList.asUnmodifiableObservableList().get(0).getTotal(), 2);

        countryChartList.add(secondCountryChart); // add new valid country code to non-empty list
        assertEquals(countryChartList.asUnmodifiableObservableList().get(1).getChartCountryCode(),
                new CountryCode("SGP"));
        assertEquals(countryChartList.asUnmodifiableObservableList().get(1).getTotal(), 1);

    }

    @Test
    public void remove_validCountryCode_removeSuccessful() {
        CountryChartList countryChartList = new CountryChartList();
        CountryChart firstCountryChart = new CountryChart(new CountryCode("JPN"), 1);
        CountryChart secondCountryChart = new CountryChart(new CountryCode("SGP"), 1);

        countryChartList.add(firstCountryChart);
        countryChartList.add(firstCountryChart);
        countryChartList.add(firstCountryChart);
        countryChartList.add(secondCountryChart);
        countryChartList.add(secondCountryChart);

        countryChartList.remove(secondCountryChart);

        CountryChartList expectedCountryChartList = new CountryChartList();
        expectedCountryChartList.add(firstCountryChart);
        expectedCountryChartList.add(firstCountryChart);
        expectedCountryChartList.add(firstCountryChart);
        expectedCountryChartList.add(secondCountryChart);

        assertEquals(countryChartList, expectedCountryChartList);

    }

    @Test
    public void remove_invalidCountryCode_throwsCountryChartNotFoundException() {
        CountryChartList countryChartList = new CountryChartList();
        CountryChart firstCountryChart = new CountryChart(new CountryCode("JPN"), 1);
        CountryChart secondCountryChart = new CountryChart(new CountryCode("SGP"), 1);

        countryChartList.add(firstCountryChart);
        countryChartList.add(firstCountryChart);
        countryChartList.add(firstCountryChart);
        countryChartList.add(secondCountryChart);
        countryChartList.add(secondCountryChart);

        thrown.expect(CountryChartNotFoundException.class);
        thrown.expectMessage(CountryChartList.MESSAGE_COUNTRY_CHART_NOT_FOUND);

        countryChartList.remove(new CountryChart(new CountryCode("GBR"), 4));
    }
}
