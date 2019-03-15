package seedu.address.model.moduleinfo;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ModuleInfo}'s {@code code} matches any of the keywords given.
 */
public class CodeContainsKeywordsPredicate implements Predicate<ModuleInfo> {

    private final List<String> keywords;

    public CodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ModuleInfo moduleInfo) {
        return keywords.stream()
<<<<<<< HEAD
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(moduleInfo.getCode().toString(), keyword));
=======
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getCodeString(), keyword));
>>>>>>> 3d2b0fdedd70c3e794a0bc7fa1c3f9905fb97b6d
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
