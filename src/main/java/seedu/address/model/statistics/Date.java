//package seedu.address.model.statistics;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
//
//public class Date {
//
//    public static final String MESSAGE_CONSTRAINTS =
//            "Day should be in the format <double digit integer>, it should not be blank and should be a valid day";
//
//    /*
//     * The first character of the item code must not be a whitespace,
//     * otherwise " " (a blank string) becomes a valid input.
//     */
//    public static final String VALIDATION_REGEX =
//            "[0-9][0-9]{0,1}[.][0-9][0-9]{0,1}[.][0-9][0-9]{0,1}[0-9]{0,1}[0-9]{0,1}";
//
//    private static boolean isInvalid = false;
//
//    public final String date;
//    private final Day day;
//    private final Month month;
//    private final Year year;
//
//    /**
//     * Constructs a {@code Date}.
//     *
//     * @param date A valid code.
//     */
//    public Date(String date) {
//        requireNonNull(date);
//        this.date = date;
//        this.day = new Day(date.substring(0, 2));
//        this.month = new Month(date.substring(3, 5));
//        this.year = new Year(date.substring(6, 10));
//        checkArgument(isValidDate(day, month, year), MESSAGE_CONSTRAINTS);
//    }
//
//    /**
//     * Returns true if a given string is a valid code.
//     */
//    public static boolean isValidDate(Day day, Month month, Year year) {
//        requireAllNonNull(day, month, year);
//        if (Year.isLeapYear(year.toString())) {
//            return isInvalid;
//        }
//        return test.matches(VALIDATION_REGEX);
//    }
//
//    @Override
//    public String toString() {
//        return date;
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof Date // instanceof handles nulls
//                && date.equals(((Date) other).date)); // state check
//    }
//
//    @Override
//    public int hashCode() {
//        return date.hashCode();
//    }
//}
