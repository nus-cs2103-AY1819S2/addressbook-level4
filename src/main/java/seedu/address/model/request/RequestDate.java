package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Request's date in the request book.
 * Guarantees: immutable
 */
public class RequestDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in the format dd-MM-yyyy HH:mm:ss and it "
            + "should be a valid date.";

    private static final SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private Date date = null;

    /**
     * Constructs a {@code RequestDate}.
     *
     * @param requestDate A valid Request Date.
     */
    public RequestDate(String requestDate) {

        requireNonNull(requestDate);

        try {
            sf.setLenient(false);
            date = sf.parse(requestDate);
        } catch (ParseException e) {
            checkArgument(false, MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * @param orderDate The date to check for validity.
     * @return true if the orderDate is valid, false otherwise.
     */
    public static boolean isValidDate(String orderDate) {
        try {
            sf.setLenient(false);
            sf.parse(orderDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public Date getDate() {
        return this.date;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-YYYY, hh:mm a");
        return dateFormat.format(this.date);
    }

    @Override
    public String toString() {
        return sf.format(this.date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestDate // instanceof handles nulls
                && date.equals(((RequestDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
