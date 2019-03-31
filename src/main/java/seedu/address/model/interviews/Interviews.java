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

    private int maxInterviewsADay = 1;

    private final HashMap<Calendar, List<Person>> interviewsHashMap;
    private final ArrayList<Calendar> availableDates;

    public Interviews() {
        this.interviewsHashMap = new HashMap<>();
        this.availableDates = new ArrayList<>();
    }

    /**
     * Helper method for test.
     */
    protected Interviews(HashMap<Calendar, List<Person>> interviewsHashMap) {
        this.interviewsHashMap = interviewsHashMap;
        this.availableDates = new ArrayList<>();
    }

    /**
     * Generates a interviews date list where there are multiple interviewees in a day.
     */
    public void generate(List<Person> persons) throws InterviewsPresentException {
        if (!interviewsHashMap.isEmpty()) {
            throw new InterviewsPresentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar = nextAvailableday(calendar);
        interviewsHashMap.put(calendar, new ArrayList<>());
        for (Person person : persons) {
            List<Person> personList = interviewsHashMap.get(calendar);
            if (personList.size() < maxInterviewsADay) {
                personList.add(person);
            } else {
                calendar = nextAvailableday(calendar);
                interviewsHashMap.put(calendar, new ArrayList<>());
                interviewsHashMap.get(calendar).add(person);
            }
        }
    }

    public void setBlockOutDates(List<Calendar> availableDates) {
        for (Calendar dates : availableDates) {
            this.availableDates.add((Calendar) dates.clone());
        }
    }

    public void setInterviews(Interviews other) {
        this.maxInterviewsADay = other.maxInterviewsADay;
        this.interviewsHashMap.clear();
        other.interviewsHashMap.forEach(((calendar, personList) ->
                this.interviewsHashMap.put(calendar, personList)));
        this.availableDates.clear();
        for (Calendar calendar : other.availableDates) {
            this.availableDates.add(calendar);
        }
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
            stringBuilder.append(currentCalendar.get(Calendar.DATE) + "/" + (currentCalendar.get(Calendar.MONTH)+1)
                    + ": ");
            for (Person person : currentPersonList) {
                stringBuilder.append(person.getName() + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }

    protected HashMap<Calendar, List<Person>> getInterviewsHashMap() {
        return interviewsHashMap;
    }

    /**
     * Returns a new instance of the next available day in calendar format.
     */
    private Calendar nextAvailableday(Calendar calendar) {
        Calendar result = (Calendar) calendar.clone();
        result.add(Calendar.DATE,1);
        while ((result.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (
                result.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            result.add(Calendar.DATE, 1);
        }
        return result;
    }
}
