package seedu.address.model.moduleinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ModuleInfo}'s {@code code} matches any of the keywords given.
 */
public class CodeContainsKeywordsPredicate implements Predicate<ModuleInfo> {

    private static final String SPLITTER_REGEX = "\\+";
    private static final String COMBINATION_REGEX = ".*?\\+.*?";
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

    public String[] splitPredicate(String keyword) {
        return keyword.split(SPLITTER_REGEX);
    }

    /**
     * Checks if a combination of words is found in the search target
     * @param keywordList
     * @param module
     * @return result boolean to see if all the words matches
     */
    public boolean combinationSearch(String[] keywordList, ModuleInfo module) {
        boolean result = false;

        for (int i = 0; i < keywordList.length; i++) {
            if (!StringUtil.containsWordIgnoreCase(module.getTitleString(), keywordList[i])) {
                break;
            }

            if (i >= keywordList.length - 1) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean test(ModuleInfo module) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    if (keyword.matches(COMBINATION_REGEX)) {
                        return combinationSearch(splitPredicate(keyword), module);
                    } else {
                        return StringUtil.containsWordIgnoreCase(module.getCodeString(), keyword)
                                || StringUtil.containsWordIgnoreCase(module.getTitleString(), keyword);
                    }
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
