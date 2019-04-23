package seedu.address.model.request;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Condition} matches any of the keywords given.
 *
 * @@author daviddl9
 */
public class RequestConditionContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public RequestConditionContainsKeywordPredicate(String condition) {
        keyword = condition.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getConditions().stream()
            .anyMatch(condition -> condition.toString().toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequestConditionContainsKeywordPredicate // instanceof handles nulls
            && keyword.equals(((RequestConditionContainsKeywordPredicate) other).keyword)); // state check
    }
}
