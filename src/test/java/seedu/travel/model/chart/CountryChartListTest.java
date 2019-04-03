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
        CountryCode japan = new CountryCode("JPN");
        CountryCode singapore = new CountryCode("SGP");

        countryChartList.add(japan); // add new country code
        assertFalse(countryChartList.asUnmodifiableObservableList().isEmpty());

        countryChartList.add(japan); // add duplicate valid country code
        assertEquals(countryChartList.asUnmodifiableObservableList().get(0).getChartCountryCode(), japan);
        assertEquals(countryChartList.asUnmodifiableObservableList().get(0).getTotal(), 2);

        countryChartList.add(singapore); // add new valid country code to non-empty list
        assertEquals(countryChartList.asUnmodifiableObservableList().get(1).getChartCountryCode(), singapore);
        assertEquals(countryChartList.asUnmodifiableObservableList().get(1).getTotal(), 1);

    }

    @Test
    public void remove_validCountryCode_removeSuccessful() {
        CountryChartList countryChartList = new CountryChartList();
        CountryCode japan = new CountryCode("JPN");
        CountryCode singapore = new CountryCode("SGP");
        countryChartList.add(japan);
        countryChartList.add(japan);
        countryChartList.add(japan);
        countryChartList.add(singapore);
        countryChartList.add(singapore);

        countryChartList.remove(new CountryChart(singapore, 2));

        CountryChartList expectedCountryChartList = new CountryChartList();
        expectedCountryChartList.add(japan);
        expectedCountryChartList.add(japan);
        expectedCountryChartList.add(japan);
        expectedCountryChartList.add(singapore);

        assertEquals(countryChartList, expectedCountryChartList);

    }

    @Test
    public void remove_invalidCountryCode_throwsCountryChartNotFoundException() {
        CountryChartList countryChartList = new CountryChartList();
        countryChartList.add(new CountryCode("JPN"));
        countryChartList.add(new CountryCode("JPN"));
        countryChartList.add(new CountryCode("JPN"));
        countryChartList.add(new CountryCode("SGP"));
        countryChartList.add(new CountryCode("SGP"));

        thrown.expect(CountryChartNotFoundException.class);
        thrown.expectMessage(CountryChartList.MESSAGE_COUNTRY_CHART_NOT_FOUND);

        countryChartList.remove(new CountryChart(new CountryCode("GBR"), 4));
    }
}
