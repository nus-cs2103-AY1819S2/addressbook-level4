package seedu.address.model.request;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code RequestStatus} matches the keywords given.
 *
 * @@author daviddl9
 */
public class RequestStatusContainsKeywordPredicate implements Predicate<Request> {
    private final List<RequestStatus> keywords;

    public RequestStatusContainsKeywordPredicate(List<RequestStatus> statuses) {
        keywords = statuses;
    }

    @Override
    public boolean test(Request request) {
        return keywords.stream()
            .anyMatch(keyword -> request.getRequestStatus().equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestStatusContainsKeywordPredicate // instanceof handles nulls
            && keywords.equals(((RequestStatusContainsKeywordPredicate) other).keywords)); // state check
    }
}
