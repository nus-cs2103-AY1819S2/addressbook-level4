package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag}s matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate extends ContainsKeywordsPredicate<Person> {

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public TagsContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Person person) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream().anyMatch(tag -> tag.getTagName().equals(keyword)));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream().anyMatch(tag -> tag.getTagName()
                    .toLowerCase().equals(keyword)));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> person.getTags().stream().anyMatch(tag -> tag.getTagName().equals(keyword)));

        } else {
            return keywords.stream()
                .allMatch(keyword -> person.getTags().stream().anyMatch(tag -> tag.getTagName()
                    .toLowerCase().equals(keyword)));
        }
    }
}
