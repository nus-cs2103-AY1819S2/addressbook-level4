package seedu.address.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Customer's identification number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
        "Date of Birth should be of the format dd/mm/yyyy ";
    public static final String VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{4})";
    private static Pattern pattern;
    private static Matcher matcher;
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid identification number.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        value = dob;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDob(String test) {

        LocalDate currentDate = LocalDate.now();
        int current_year = currentDate.getYear();
        pattern = Pattern.compile(VALIDATION_REGEX);
        matcher = pattern.matcher(test);

        if(matcher.matches()){

            matcher.reset();

            if(matcher.find()){

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));
                if(year >= current_year)
                    return false;

                if (("31").equals(day) &&
                    (("4").equals(month) || ("6").equals(month) || ("9").equals(month) ||
                        ("11").equals(month) || ("04").equals(month) || ("06").equals(month) ||
                        ("09").equals(month))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (("2").equals(month) || ("02").equals(month)) {
                    //leap year
                    if(year % 4==0){
                        if(("30").equals(day) || ("31").equals(day)){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(("29").equals(day)||("31").equals(day)||("30").equals(day)){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateOfBirth // instanceof handles nulls
            && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
