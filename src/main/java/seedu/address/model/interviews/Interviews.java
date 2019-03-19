package seedu.address.model.interviews;

import seedu.address.model.person.Person;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Interviews {

    private final int MAX_INTERVIEWEES_A_DAY = 2;

    private final List<Person> persons;
    private final Calendar calendar;
    private final HashMap<Calendar, List<Person>> interviews;

    public Interviews(List<Person> persons) {
        this.persons = persons;
        this.calendar = Calendar.getInstance();
        this.interviews = new HashMap<>();
    }

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
