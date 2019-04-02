package seedu.address.model.interviews;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class InterviewsTest {

    @Test
    public void generateNoWeekdays() {
        Interviews interviews = new Interviews();
        interviews.generate(getTypicalPersons());
        Set<Calendar> calendars = interviews.getInterviewsHashMap().keySet();
        for (Calendar calendar : calendars) {
            assertFalse(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
        }
    }

    @Test
    public void generateExcludeBlockOutOneDay() {
        Interviews interviews = new Interviews();
        List<Calendar> dates = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 2);
        interviews.setBlockOutDates(dates);
        interviews.generate(getTypicalPersons());
        Set<Calendar> calendars = interviews.getInterviewsHashMap().keySet();
        for (Calendar calendar : calendars) {
            assertFalse(Interviews.containsDate(dates, calendar));
        }
    }

    @Test
    public void generateExcludeBlockOutDates() {
        Interviews interviews = new Interviews();
        List<Calendar> dates = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            date.add(Calendar.DATE, 1);
            dates.add(date);
            date = (Calendar) date.clone();
        }
        interviews.setBlockOutDates(dates);
        interviews.generate(getTypicalPersons());
        Set<Calendar> calendars = interviews.getInterviewsHashMap().keySet();
        for (Calendar calendar : calendars) {
            assertFalse(Interviews.containsDate(dates, calendar));
        }
    }

    @Test
    public void clear() {
        Interviews interviews = new Interviews();
        interviews.generate(getTypicalPersons());
        interviews.clear();
        assertTrue(interviews.getInterviewsHashMap().isEmpty());
    }
}
