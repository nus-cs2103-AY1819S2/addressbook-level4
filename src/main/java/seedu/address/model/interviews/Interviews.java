package seedu.address.model.interviews;

import java.util.*;

import seedu.address.model.person.Person;

/**
 * Represents the association class between person and calendar.
 */
public class Interviews {

    private static final int MAX_INTERVIEWEES_A_DAY = 2;

    private final HashMap<Calendar, List<Person>> interviews;

    public Interviews() {
        this.interviews = new HashMap<>();
    }

    /**
     * Generates a interviews date list where there are multiple interviewees in a day.
     */
    public void generate(List<Person> persons) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        interviews.put(calendar, new ArrayList<>());
        for (Person person : persons) {
            List<Person> personList = interviews.get(calendar);
            if (personList.size() < MAX_INTERVIEWEES_A_DAY) {
                personList.add(person);
            } else {
                calendar = (Calendar) calendar.clone();
                calendar.add(Calendar.DATE, 1);
                interviews.put(calendar, new ArrayList<>());
                interviews.get(calendar).add(person);
            }
        }
    }

    @Override
    public String toString() {
        PriorityQueue<Calendar> calendarPriorityQueue = new PriorityQueue<>(interviews.keySet());
        StringBuilder stringBuilder = new StringBuilder();
        while (!calendarPriorityQueue.isEmpty()) {
            Calendar currentCalendar = calendarPriorityQueue.poll();
            List<Person> currentPersonList = interviews.get(currentCalendar);
            stringBuilder.append(currentCalendar.get(Calendar.DATE) + "/" + currentCalendar.get(Calendar.MONTH) + ": ");
            for (Person person : currentPersonList) {
                stringBuilder.append(person.getName() + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }
}
