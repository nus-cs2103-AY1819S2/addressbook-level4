package seedu.address.model.datetime;

import java.time.format.DateTimeFormatter;

/**
 * An interface to create proper Date classes for our program usage.
 */
public interface DateBuilder {
    String VALIDATION_REGEX = "^(((0[1-9]|[1-2][0-9]|3[0,1])-(01|03|05|07|08|10|12))|"
            + "((0[1-9]|[1-2][0-9]|30)-(04|06|09|11))|((0[1-9]|[1-2]["
            + "0-9])-(02)))-(\\d{4})$";

    String DATE_FORMAT = "dd-MM-yyyy";

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    String[] MONTHS = {"", "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"};

    boolean equals(Object o);

    String toString();

    int hashCode();
}
