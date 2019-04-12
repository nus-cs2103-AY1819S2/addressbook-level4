package seedu.address.model.interviews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import seedu.address.model.interviews.exceptions.InterviewsPresentException;
import seedu.address.model.person.Person;

/**
 * Represents the association class between person and calendar.
 */
public class Interviews {

    public static final int SECOND = 0;
    public static final int MINUTE = 0;
    public static final int HOUR = 0;
    public static final int MILLISECOND = 0;

    private int maxInterviewsADay = 2;

    private final HashMap<Calendar, List<Person>> interviewsHashMap;
    private final ArrayList<Calendar> blockOutDates;

    public Interviews() {
        this.interviewsHashMap = new HashMap<>();
        this.blockOutDates = new ArrayList<>();
    }

    /**
     * Helper method for test.
     */
    protected Interviews(HashMap<Calendar, List<Person>> interviewsHashMap) {
        this.interviewsHashMap = interviewsHashMap;
        this.blockOutDates = new ArrayList<>();
    }

    /**
     * Generates a interviews date list where there are multiple interviewees in a day.
     */
    public void generate(List<Person> persons) throws InterviewsPresentException {
        if (!interviewsHashMap.isEmpty()) {
            throw new InterviewsPresentException();
        }
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DATE);
        Calendar calendar = new GregorianCalendar(year, month, day);
        calendar = nextAvailableDay(calendar);
        interviewsHashMap.put(calendar, new ArrayList<>());
        for (Person person : persons) {
            List<Person> personList = interviewsHashMap.get(calendar);
            if (personList.size() < maxInterviewsADay) {
                personList.add(person);
            } else {
                calendar = nextAvailableDay(calendar);
                interviewsHashMap.put(calendar, new ArrayList<>());
                interviewsHashMap.get(calendar).add(person);
            }
        }
    }

    public void setBlockOutDates(List<Calendar> availableDates) {
        for (Calendar dates : availableDates) {
            this.blockOutDates.add((Calendar) dates.clone());
        }
    }

    public void setInterviews(Interviews other) {
        this.maxInterviewsADay = other.maxInterviewsADay;
        this.interviewsHashMap.clear();
        other.interviewsHashMap.forEach(((calendar, personList) ->
                this.interviewsHashMap.put(calendar, new ArrayList<>(personList))));
        this.blockOutDates.clear();
        for (Calendar calendar : other.blockOutDates) {
            this.blockOutDates.add(calendar);
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
        String result = "";
        while (!calendarPriorityQueue.isEmpty()) {
            Calendar currentCalendar = calendarPriorityQueue.poll();
            List<Person> currentPersonList = interviewsHashMap.get(currentCalendar);
            result += currentCalendar.get(Calendar.DATE) + "/" + (currentCalendar.get(Calendar.MONTH) + 1)
                    + "/" + currentCalendar.get(Calendar.YEAR) + ": ";
            for (Person person : currentPersonList) {
                result += person.getName() + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result += "\n\n";
        }
        return result.trim();
    }

    /**
     * Removes the person from the interviewsHashMap.
     * @param person to be removed from interviewsHashMap.
     * @return true if person to be removed is present, else returns false.
     */
    public boolean removePerson(Person person) {
        Collection<List<Person>> listOfPersonList = interviewsHashMap.values();
        for (List<Person> personList : listOfPersonList) {
            if (personList.remove(person)) {
                return true;
            }
        }
        return false;
    }

    protected HashMap<Calendar, List<Person>> getInterviewsHashMap() {
        return interviewsHashMap;
    }

    /**
     * Returns a new instance of the next available day in calendar format.
     */
    private Calendar nextAvailableDay(Calendar calendar) {
        Calendar result = (Calendar) calendar.clone();
        result.add(Calendar.DATE, 1);
        while ((result.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                || (result.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                || (containsDate(blockOutDates, result))) {
            result.add(Calendar.DATE, 1);
        }
        return result;
    }

    /**
     * Checks if the calendarList contains the date.
     */
    protected static boolean containsDate(List<Calendar> calendarList, Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);
        for (Calendar calendar : calendarList) {
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DATE) == day) {
                return true;
            }
        }
        return false;
    }
}
