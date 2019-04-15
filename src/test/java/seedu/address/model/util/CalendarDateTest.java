package seedu.address.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalendarDateTest {

    @Test
    public void isValidCalendarDate() {

        //invalid months
        assertFalse(CalendarDate.isValidDayInMonth("11/13/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("11/00/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("11/23/2019"));

        //invalid days
        assertFalse(CalendarDate.isValidDayInMonth("40/12/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("32/12/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("00/12/2019"));

        //test 31 on months with less than 31 days
        assertFalse(CalendarDate.isValidDayInMonth("31/02/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("31/04/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("31/06/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("31/09/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("31/11/2019"));

        //testing 29th February on Leap Years
        assertTrue(CalendarDate.isValidDayInMonth("29/02/2016"));
        assertTrue(CalendarDate.isValidDayInMonth("29/02/2020"));
        assertTrue(CalendarDate.isValidDayInMonth("29/02/2024"));

        //testing 29th February on Non-Leap Years
        assertFalse(CalendarDate.isValidDayInMonth("29/02/2019"));
        assertFalse(CalendarDate.isValidDayInMonth("29/02/2022"));
        assertFalse(CalendarDate.isValidDayInMonth("29/02/2035"));

        //testing invalid days in months using alternate methods
        assertFalse(CalendarDate.isValidDayInMonth("this", 31));
        assertFalse(CalendarDate.isValidDayInMonth("this", 0));
        assertFalse(CalendarDate.isValidDayInMonth("next", 0));
        assertFalse(CalendarDate.isValidDayInMonth("next", 32));
        assertFalse(CalendarDate.isValidDayInMonth("last", 32));

        //testing valid days in month using alternate methods
        assertTrue(CalendarDate.isValidDayInMonth("this", 30));
        assertTrue(CalendarDate.isValidDayInMonth("last", 30));
        assertTrue(CalendarDate.isValidDayInMonth("next", 30));
    }
}
