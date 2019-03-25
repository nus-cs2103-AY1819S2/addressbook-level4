package seedu.address.model.deck;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Card}'s {@code Name} matches any of the keywords given.
 */
public class CardNameContainsKeywordsPredicate implements Predicate<Card> {
    private final List<String> keywords;

    public CardNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Card card) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(card.getQuestion(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CardNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
