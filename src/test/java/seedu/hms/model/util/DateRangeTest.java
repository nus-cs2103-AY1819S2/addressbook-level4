package seedu.hms.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.testutil.Assert;

public class DateRangeTest {
    @Test
    public void constructor() {
        Calendar oneDayBeforeCurrentDate = Calendar.getInstance();
        oneDayBeforeCurrentDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000);
        Calendar zeroDay = Calendar.getInstance();
        zeroDay.setTimeInMillis(0);
        Calendar oneYearAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 364; i++) {
            oneYearAfterCurrentDate.setTimeInMillis(
                    oneYearAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }
        Calendar oneYearPlusOneDayAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 366; i++) {
            oneYearPlusOneDayAfterCurrentDate.setTimeInMillis(
                    oneYearPlusOneDayAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }
        String currentDate = Integer.toString(Calendar.getInstance().getTime().getDate());
        String currentMonth = Integer.toString(Calendar.getInstance().getTime().getMonth() + 1);
        String currentYear = Integer.toString(Calendar.getInstance().getTime().getYear() + 1900);
        String currentDay = String.format("%s/%s/%s", currentDate, currentMonth, currentYear);

        String oneDayBeforeCurrentDateDate = Integer.toString(oneDayBeforeCurrentDate.getTime().getDate());
        String oneDayBeforeCurrentDateMonth = Integer.toString(oneDayBeforeCurrentDate.getTime().getMonth() + 1);
        String oneDayBeforeCurrentDateYear = Integer.toString(oneDayBeforeCurrentDate.getTime().getYear() + 1900);
        String oneDayBeforeCurrentDay = String.format("%s/%s/%s", oneDayBeforeCurrentDateDate,
                oneDayBeforeCurrentDateMonth, oneDayBeforeCurrentDateYear);

        String oneYearAfterCurrentDateDate = Integer.toString(oneYearAfterCurrentDate.getTime().getDate());
        String oneYearAfterCurrentDateMonth = Integer.toString(oneYearAfterCurrentDate.getTime().getMonth() + 1);
        String oneYearAfterCurrentDateYear = Integer.toString(oneYearAfterCurrentDate.getTime().getYear() + 1900);
        String oneYearAfterCurrentDay = String.format("%s/%s/%s", oneYearAfterCurrentDateDate,
                oneYearAfterCurrentDateMonth, oneYearAfterCurrentDateYear);

        String oneYearPlusOneDayAfterCurrentDateDate = Integer.toString(
                oneYearPlusOneDayAfterCurrentDate.getTime().getDate());
        String oneYearPlusOneDayAfterCurrentDateMonth = Integer.toString(
                oneYearPlusOneDayAfterCurrentDate.getTime().getMonth() + 1);
        String oneYearPlusOneDayAfterCurrentDateYear = Integer.toString(
                oneYearPlusOneDayAfterCurrentDate.getTime().getYear() + 1900);
        String oneYearPlusOneDayAfterCurrentDay = String.format("%s/%s/%s", oneYearPlusOneDayAfterCurrentDateDate,
                oneYearPlusOneDayAfterCurrentDateMonth, oneYearPlusOneDayAfterCurrentDateYear);

        Assert.assertThrows(ParseException.class, () -> new DateRange(oneDayBeforeCurrentDay, currentDay));
        Assert.assertThrows(ParseException.class, () -> new DateRange(currentDay, oneYearPlusOneDayAfterCurrentDay));
        Assert.assertThrows(ParseException.class, () -> new DateRange(oneYearPlusOneDayAfterCurrentDay, currentDay));
        Assert.assertThrows(ParseException.class, () -> new DateRange(oneYearAfterCurrentDay, currentDay));
        Assert.assertThrows(ParseException.class, () -> new DateRange("-----", currentDay));
        Assert.assertThrows(ParseException.class, () -> new DateRange(currentDay, "123dsa"));
    }

    @Test
    public void isValidDate() {
        // null hms
        Assert.assertThrows(NullPointerException.class, () -> DateRange.isValidDate(null));


        Calendar oneDayBeforeCurrentDate = Calendar.getInstance();
        oneDayBeforeCurrentDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000);
        Calendar zeroDay = Calendar.getInstance();
        zeroDay.setTimeInMillis(0);
        Calendar oneYearAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 365; i++) {
            oneYearAfterCurrentDate.setTimeInMillis(
                    oneYearAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }
        Calendar oneYearPlusOneDayAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 366; i++) {
            oneYearPlusOneDayAfterCurrentDate.setTimeInMillis(
                    oneYearPlusOneDayAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }

        // valid dates
        assertTrue(DateRange.isValidDate(Calendar.getInstance()));
        //assertTrue(DateRange.isValidDate(oneYearAfterCurrentDate));

        // invalid dates
        assertFalse(DateRange.isValidDate(oneYearPlusOneDayAfterCurrentDate));
        assertFalse(DateRange.isValidDate(oneDayBeforeCurrentDate));
        assertFalse(DateRange.isValidDate(zeroDay));
    }

    @Test
    public void withInDates() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        Calendar twentyDaysAfterCurrentDate = Calendar.getInstance();
        twentyDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        Calendar oneHundredDaysAfterCurrentDate = Calendar.getInstance();
        for (int i = 0; i < 100; i++) {
            oneHundredDaysAfterCurrentDate.setTimeInMillis(
                    oneHundredDaysAfterCurrentDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);
        DateRange dateRange1 = new DateRange(currentDate, twentyDaysAfterCurrentDate);
        DateRange dateRange2 = new DateRange(twentyDaysAfterCurrentDate, oneHundredDaysAfterCurrentDate);
        DateRange dateRange3 = new DateRange(tenDaysAfterCurrentDate, twentyDaysAfterCurrentDate);
        DateRange dateRange4 = new DateRange(currentDate, oneHundredDaysAfterCurrentDate);

        // within
        assertTrue(dateRange.withinDates(dateRange1));
        assertTrue(dateRange.withinDates(dateRange4));
        assertTrue(dateRange1.withinDates(dateRange4));
        assertTrue(dateRange2.withinDates(dateRange4));
        assertTrue(dateRange3.withinDates(dateRange4));
        assertTrue(dateRange.withinDates(dateRange));

        // not within
        assertFalse(dateRange4.withinDates(dateRange));
        assertFalse(dateRange4.withinDates(dateRange1));
        assertFalse(dateRange4.withinDates(dateRange2));
        assertFalse(dateRange4.withinDates(dateRange3));
        assertFalse(dateRange3.withinDates(dateRange2));
        assertFalse(dateRange2.withinDates(dateRange3));
        assertFalse(dateRange.withinDates(dateRange2));
        assertFalse(dateRange.withinDates(dateRange3));
    }

    @Test
    public void isEqual() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        Calendar tenDaysAfterCurrentDate1 = Calendar.getInstance();
        tenDaysAfterCurrentDate1.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);
        DateRange dateRange1 = new DateRange(currentDate, tenDaysAfterCurrentDate1);

        assertTrue(dateRange.equals(dateRange1));
        assertTrue(dateRange.toString().equals(dateRange1.toString()));
        assertTrue(dateRange.equals(dateRange));
        assertFalse(dateRange.equals(currentDate));
    }

    @Test
    public void hashCodeTest() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        Calendar tenDaysAfterCurrentDate1 = Calendar.getInstance();
        tenDaysAfterCurrentDate1.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);
        DateRange dateRange1 = new DateRange(currentDate, tenDaysAfterCurrentDate1);

        assertTrue(dateRange.hashCode() == dateRange1.hashCode());
    }

    @Test
    public void getEachDay() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        Calendar tenDaysAfterCurrentDate1 = Calendar.getInstance();
        tenDaysAfterCurrentDate1.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);
        DateRange dateRange1 = new DateRange(currentDate, tenDaysAfterCurrentDate1);

        assertTrue(dateRange.getEachDay().equals(dateRange1.getEachDay()));
    }

    @Test
    public void get() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);

        assertTrue(currentDate.equals(dateRange.getStartDate()));
        assertTrue(tenDaysAfterCurrentDate.equals(dateRange.getEndDate()));
    }

    @Test
    public void numOfDays() {
        Calendar currentDate = Calendar.getInstance();
        Calendar tenDaysAfterCurrentDate = Calendar.getInstance();
        tenDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 10 * 24 * 60 * 60 * 1000);
        Calendar twentyDaysAfterCurrentDate = Calendar.getInstance();
        twentyDaysAfterCurrentDate.setTimeInMillis(
                currentDate.getTimeInMillis() + 20 * 24 * 60 * 60 * 1000);
        Calendar oneHundredDaysAfterCurretnDate = Calendar.getInstance();
        for (int i = 0; i < 100; i++) {
            oneHundredDaysAfterCurretnDate.setTimeInMillis(
                    oneHundredDaysAfterCurretnDate.getTimeInMillis() + 24 * 60 * 60 * 1000);
        }

        DateRange dateRange = new DateRange(currentDate, tenDaysAfterCurrentDate);
        DateRange dateRange1 = new DateRange(currentDate, twentyDaysAfterCurrentDate);
        DateRange dateRange2 = new DateRange(twentyDaysAfterCurrentDate, oneHundredDaysAfterCurretnDate);
        DateRange dateRange3 = new DateRange(tenDaysAfterCurrentDate, twentyDaysAfterCurrentDate);
        DateRange dateRange4 = new DateRange(currentDate, oneHundredDaysAfterCurretnDate);

        assertTrue(dateRange.numOfDays() == 10);
        assertTrue(dateRange1.numOfDays() == 20);
        assertTrue(dateRange2.numOfDays() == 80);
        assertTrue(dateRange3.numOfDays() == 10);
        assertTrue(dateRange4.numOfDays() == 100);
    }
}
