package seedu.address.model.interviews;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.*;

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
    public void generateExcludeBlockOut() {
        Interviews interviews = new Interviews();
        List<Calendar> dates = new ArrayList<>();
        Calendar date = new GregorianCalendar(2019, 3, 4);
        dates.add(date);
        interviews.setBlockOutDates(dates);
        interviews.generate(getTypicalPersons());
        Set<Calendar> calendars = interviews.getInterviewsHashMap().keySet();
        for (Calendar calendar : calendars) {
            assertFalse(dates.contains(calendar));
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
