package seedu.address.model.request;

import java.util.function.Predicate;

/**
 * Tests that a {@code Request}'s {@code Name} contains any of the keywords given.
 */
public class RequestNameContainsKeywordPredicate implements Predicate<Request> {
    private final String keyword;

    public RequestNameContainsKeywordPredicate(String name) {
        this.keyword = name.trim().toLowerCase();
    }

    @Override
    public boolean test(Request request) {
        return request.getName().toString().toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RequestNameContainsKeywordPredicate
            && keyword.equals(((RequestNameContainsKeywordPredicate) other).keyword));
    }
}
