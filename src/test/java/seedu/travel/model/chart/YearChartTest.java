package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.model.place.DateVisited;

public class YearChartTest {

    @Test
    public void testChartYear() {

        DateVisited firstDateVisited = new DateVisited("03/04/2015");
        YearChart firstYearChart = new YearChart("2015", 4);
        YearChart secondYearChart = new YearChart("1999", 4);

        assertEquals(firstDateVisited.getYear(), (firstYearChart.getChartYear()));
        assertNotEquals(firstYearChart.getChartYear(), (secondYearChart.getChartYear()));
    }

    @Test
    public void equals() {

        DateVisited firstDateVisited = new DateVisited("03/04/2015");
        DateVisited secondDateVisited = new DateVisited("05/07/2014");

        YearChart firstYearValueFour = new YearChart(firstDateVisited.getYear(), 4);
        YearChart firstYearValueFive = new YearChart(firstDateVisited.getYear(), 5);
        YearChart secondYearValueFour = new YearChart(secondDateVisited.getYear(), 4);
        YearChart secondYearValueFive = new YearChart(secondDateVisited.getYear(), 5);

        // same object -> returns true
        assertTrue(firstYearValueFour.equals(firstYearValueFour));

        // same values -> returns true
        YearChart firstYearValueFourCopy = new YearChart(firstDateVisited.getYear(), 4);
        assertTrue(firstYearValueFour.equals(firstYearValueFourCopy));

        // different types -> returns false
        assertFalse(firstYearValueFour.equals(1));

        // null -> returns false
        assertFalse(firstYearValueFour.equals(null));

        // different country, same total -> returns false
        assertFalse(firstYearValueFour.equals(secondYearValueFour));

        // same country, different total -> returns false
        assertFalse(firstYearValueFour.equals(firstYearValueFive));

        // different country, different total -> returns false
        assertFalse(firstYearValueFour.equals(secondYearValueFive));
    }

    @Test
    public void testToString() {
        YearChart testYear = new YearChart("2014", 4);

        String testSameOutputString = "Year: 2014 Total: 4";
        String testDifferentOutputString = "Year: 2013 Total: 6";

        assertEquals(testYear.toString(), testSameOutputString);
        assertNotEquals(testYear.toString(), testDifferentOutputString);

    }
}
