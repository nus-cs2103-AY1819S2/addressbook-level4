package seedu.address.model.request;

import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Date} is within the 2 dates given.
 *
 * @@author daviddl9
 */
public class RequestDateRangePredicate implements Predicate<Request> {
    private final Date lowerDateBoundary;
    private final Date upperDateBoundary;

    public RequestDateRangePredicate(Date lowerDateBoundary, Date upperDateBoundary) {
        this.lowerDateBoundary = lowerDateBoundary;
        this.upperDateBoundary = upperDateBoundary;
    }

    @Override
    public boolean test(Request request) {
        return isWithinDateBoundary(request.getRequestDate().getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestDateRangePredicate // instanceof handles nulls
            && lowerDateBoundary.equals(((RequestDateRangePredicate) other).lowerDateBoundary)
            && upperDateBoundary.equals(((RequestDateRangePredicate) other).upperDateBoundary)); // state check
    }

    private boolean isWithinDateBoundary(Date date) {
        return (date.equals(lowerDateBoundary) || date.equals(upperDateBoundary)
            || (date.after(lowerDateBoundary) && date.before(upperDateBoundary)));
    }
}
