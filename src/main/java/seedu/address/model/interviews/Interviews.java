package seedu.address.model.interviews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import seedu.address.model.interviews.exceptions.InterviewsPresentException;
import seedu.address.model.person.Person;

/**
 * Represents the association class between person and calendar.
 */
public class Interviews {

    private int maxInterviewsADay = 2;

    private final HashMap<Calendar, List<Person>> interviewsHashMap = new HashMap<>();

    public Interviews() {}

    /**
     * Generates a interviews date list where there are multiple interviewees in a day.
     */
    public void generate(List<Person> persons) throws InterviewsPresentException {
        if (!interviewsHashMap.isEmpty()) {
            throw new InterviewsPresentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        interviewsHashMap.put(calendar, new ArrayList<>());
        for (Person person : persons) {
            List<Person> personList = interviewsHashMap.get(calendar);
            if (personList.size() < maxInterviewsADay) {
                personList.add(person);
            } else {
                calendar = (Calendar) calendar.clone();
                calendar.add(Calendar.DATE, 1);
                interviewsHashMap.put(calendar, new ArrayList<>());
                interviewsHashMap.get(calendar).add(person);
            }
        }
    }

    public void setInterviews(Interviews other) {
        this.maxInterviewsADay = other.maxInterviewsADay;
        this.interviewsHashMap.clear();
        other.interviewsHashMap.forEach(((calendar, personList) ->
                this.interviewsHashMap.put(calendar, personList)));
    }

    public void clear() {
        interviewsHashMap.clear();
    }

    public void setMaxInterviewsADay(int maxInterviewsADay) {
        this.maxInterviewsADay = maxInterviewsADay;
    }

    @Override
    public String toString() {
        PriorityQueue<Calendar> calendarPriorityQueue = new PriorityQueue<>(interviewsHashMap.keySet());
        StringBuilder stringBuilder = new StringBuilder();
        while (!calendarPriorityQueue.isEmpty()) {
            Calendar currentCalendar = calendarPriorityQueue.poll();
            List<Person> currentPersonList = interviewsHashMap.get(currentCalendar);
            stringBuilder.append(currentCalendar.get(Calendar.DATE) + "/" + currentCalendar.get(Calendar.MONTH) + ": ");
            for (Person person : currentPersonList) {
                stringBuilder.append(person.getName() + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }
}
