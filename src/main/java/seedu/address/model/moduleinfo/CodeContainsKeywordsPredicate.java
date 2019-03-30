package seedu.address.model.moduleinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ModuleInfo}'s {@code code} matches any of the keywords given.
 */
public class CodeContainsKeywordsPredicate implements Predicate<ModuleInfo> {

    private final List<String> keywords;
    private final List<String> defaultKeywords = new ArrayList<>();


    public CodeContainsKeywordsPredicate(List<String> keywords) {
        if (keywords == null) {
            defaultKeywords.add("CS1010");
            defaultKeywords.add("CS2103T");
            this.keywords = defaultKeywords;
        } else {
            this.keywords = keywords;
        }
    }

    @Override
    public boolean test(ModuleInfo module) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getCodeString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
