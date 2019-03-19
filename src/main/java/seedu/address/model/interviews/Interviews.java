package seedu.address.model.interviews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Represents the association class between person and calendar.
 */
public class Interviews {

    private static final int MAX_INTERVIEWEES_A_DAY = 2;

    private final List<Person> persons;
    private final Calendar calendar;
    private final HashMap<Calendar, List<Person>> interviews;

    public Interviews(List<Person> persons) {
        this.persons = persons;
        this.calendar = Calendar.getInstance();
        this.interviews = new HashMap<>();
    }

    /**
     * Generates a interviews date list where there are multiple interviewees in a day.
     */
    public void generate() {
        calendar.add(Calendar.DATE, 1);
        interviews.put(calendar, new ArrayList<>());
        for (Person person : persons) {
            List<Person> personList = interviews.get(calendar);
            if (personList.size() < MAX_INTERVIEWEES_A_DAY) {
                personList.add(person);
            } else {
                calendar.add(Calendar.DATE, 1);
                interviews.put(calendar, new ArrayList<>());
                interviews.get(calendar).add(person);
            }
        }
    }
}
