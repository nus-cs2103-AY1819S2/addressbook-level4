package seedu.address.model.flashcard;

import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Represents a Flashcard's proficiency level on how well the user does in the quiz mode.
 */
public class Proficiency {
    public static final String VALIDATION_REGEX = "inactive until \\d+ proficiency level \\d+$";
    public static final String MESSAGE_CONSTRAINTS = "Proficiency string format must be in the form of: "
        + "inactive until <date until card can be reviewed> proficiency level <proficiency level>";

    public static final String NOW_ACTIVE = "Active";
    public static final String INACTIVE_UNTIL_IN_DAYS = "Inactive until %d day(s)";
    public static final String INACTIVE_UNTIL_IN_HOURS = "Inactive until %d hour(s)";
    public static final String INACTIVE_UNTIL_IN_MINUTES = "Inactive until %d minute(s)";
    public static final String ACTIVE_IN_UNDER_A_MINUTE = "Will be active in under a minute.";

    private final Calendar timeUntilReview;
    private final int proficiencyLevel;

    public Proficiency(Calendar timeUntilReview, int proficiencyLevel) {
        if (proficiencyLevel < 0) {
            throw new IllegalArgumentException("Proficiency level cannot be negative");
        }
        this.timeUntilReview = timeUntilReview;
        this.proficiencyLevel = proficiencyLevel;
    }

    public Proficiency() {
        this.timeUntilReview = Calendar.getInstance();
        this.proficiencyLevel = 0;
    }

    public Proficiency(String fromString) {
        Scanner sc = new Scanner(fromString);

        sc.next(); // string: inactive
        sc.next(); // string: until
        timeUntilReview = Calendar.getInstance();
        timeUntilReview.setTimeInMillis(sc.nextLong());
        sc.next(); // string: proficiency
        sc.next(); // string: level
        proficiencyLevel = sc.nextInt();
    }

    /**
     * Returns if a given string is a valid proficiency format.
     */
    public static boolean isValidProficiency(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        Proficiency dummy = new Proficiency(test);
        return dummy.proficiencyLevel >= 0;
    }

    /**
     * @return True if this flashcard can be reviewed in the current session.
     */
    public boolean isIncludedInCurrentQuiz() {
        Calendar now = Calendar.getInstance();
        return !now.before(timeUntilReview);
    }

    /**
     *
     * @return The quiz status, either active or inactive. Will display the time until it became active.
     */
    public String getQuizSrsStatus() {
        long millis = timeUntilReview.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        if (millis <= 0) {
            return NOW_ACTIVE;
        }

        long daysLeft = TimeUnit.MILLISECONDS.toDays(millis);
        if (daysLeft > 0) {
            return String.format(INACTIVE_UNTIL_IN_DAYS, daysLeft);
        }

        long hoursLeft = TimeUnit.MILLISECONDS.toHours(millis);
        if (hoursLeft > 0) {
            return String.format(INACTIVE_UNTIL_IN_HOURS, hoursLeft);
        }

        long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(millis);
        if (minutesLeft > 0) {
            return String.format(INACTIVE_UNTIL_IN_MINUTES, minutesLeft);
        }

        return ACTIVE_IN_UNDER_A_MINUTE;
    }

    /**
     * update the proficiency after a quiz is finished.
     *
     * @param isSuccess does the user guess the card from the quiz correctly.
     */
    public Proficiency quizAttempt(boolean isSuccess) {
        int rProficiencyLevel = proficiencyLevel;
        if (isSuccess) {
            rProficiencyLevel++;
        } else {
            rProficiencyLevel = 0;
        }
        Calendar now = Calendar.getInstance();
        if (rProficiencyLevel != 0) {
            now.add(Calendar.DATE, rProficiencyLevel);
        }
        return new Proficiency(now, rProficiencyLevel);
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Proficiency // instanceof handles nulls
            && isAlmostEqualCalendar(timeUntilReview, ((Proficiency) other).timeUntilReview)
            && proficiencyLevel == ((Proficiency) other).proficiencyLevel); // state check
    }

    @Override
    public String toString() {
        return String.format("inactive until %d proficiency level %d", timeUntilReview.getTimeInMillis(),
            proficiencyLevel);
    }

    private boolean isAlmostEqualCalendar(Calendar a, Calendar b) {
        int oneHourInMillis = 3600 * 1000;
        return Math.abs(a.getTimeInMillis() - b.getTimeInMillis()) < oneHourInMillis;
    }
}
