package seedu.address.model.interviews;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Calendar;
import java.util.Set;

import org.junit.Test;


public class InterviewsTest {

    @Test
    public void generate() {
        Interviews interviews = new Interviews();
        interviews.generate(getTypicalPersons());
        Set<Calendar> calendars = interviews.getInterviewsHashMap().keySet();
        for (Calendar calendar : calendars) {
            assertFalse(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
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
