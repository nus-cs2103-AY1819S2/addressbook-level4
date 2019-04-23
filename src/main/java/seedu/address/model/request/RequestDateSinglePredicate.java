package seedu.address.model.request;

import java.util.Date;
import java.util.function.Predicate;


/**
 * Tests that a {@code Request}'s {@code Date} matches any of the keywords given.
 *
 * @@author daviddl9
 */
public class RequestDateSinglePredicate implements Predicate<Request> {
    private final Date keyword;

    public RequestDateSinglePredicate(Date date) {
        keyword = date;
    }

    @Override
    public boolean test(Request request) {
        return request.getRequestDate().getDate().equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestDateSinglePredicate // instanceof handles nulls
            && keyword.equals(((RequestDateSinglePredicate) other).keyword)); // state check
    }
}
