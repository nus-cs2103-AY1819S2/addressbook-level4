package seedu.address.model.request;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Nric} contains any of the keywords given.
 */
public class RequestNricContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public RequestNricContainsKeywordPredicate(String nric) {
        keyword = nric.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getName().toString().toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestNricContainsKeywordPredicate // instanceof handles nulls
            && keyword.equals(((RequestNricContainsKeywordPredicate) other).keyword)); // state check
    }
}
