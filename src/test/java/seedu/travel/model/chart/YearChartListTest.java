package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.model.chart.exceptions.YearChartNotFoundException;

public class YearChartListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_validYear_addSuccessful() {
        YearChartList yearChartList = new YearChartList();
        YearChart firstYearChart = new YearChart("2014", 1);
        YearChart secondYearChart = new YearChart("2017", 1);

        yearChartList.add(firstYearChart); // add new year
        assertFalse(yearChartList.asUnmodifiableObservableList().isEmpty());

        yearChartList.add(firstYearChart); // add duplicate valid year
        assertEquals(yearChartList.asUnmodifiableObservableList().get(0).getChartYear(), "2014");
        assertEquals(yearChartList.asUnmodifiableObservableList().get(0).getTotal(), 2);

        yearChartList.add(secondYearChart); // add new valid year to non-empty list
        assertEquals(yearChartList.asUnmodifiableObservableList().get(1).getChartYear(), "2017");
        assertEquals(yearChartList.asUnmodifiableObservableList().get(1).getTotal(), 1);
    }

    @Test
    public void remove_validYear_removeSuccessful() {
        YearChartList yearChartList = new YearChartList();
        YearChart firstYearChart = new YearChart("2014", 1);
        YearChart secondYearChart = new YearChart("2017", 1);
        yearChartList.add(firstYearChart);
        yearChartList.add(firstYearChart);
        yearChartList.add(firstYearChart);
        yearChartList.add(secondYearChart);
        yearChartList.add(secondYearChart);

        yearChartList.remove(secondYearChart);

        YearChartList expectedYearChartList = new YearChartList();
        expectedYearChartList.add(firstYearChart);
        expectedYearChartList.add(firstYearChart);
        expectedYearChartList.add(firstYearChart);
        expectedYearChartList.add(secondYearChart);

        assertEquals(yearChartList, expectedYearChartList);

    }

    @Test
    public void remove_invalidYear_throwsYearChartNotFoundException() {
        YearChartList yearChartList = new YearChartList();
        YearChart firstYearChart = new YearChart("2014", 1);
        YearChart secondYearChart = new YearChart("2017", 1);

        yearChartList.add(firstYearChart);
        yearChartList.add(firstYearChart);
        yearChartList.add(firstYearChart);
        yearChartList.add(secondYearChart);
        yearChartList.add(secondYearChart);

        thrown.expect(YearChartNotFoundException.class);
        thrown.expectMessage(YearChartList.MESSAGE_YEAR_CHART_NOT_FOUND);

        yearChartList.remove(new YearChart("2018", 4));
    }
}
