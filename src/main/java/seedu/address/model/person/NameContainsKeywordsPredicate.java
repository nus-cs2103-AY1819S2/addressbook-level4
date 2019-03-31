package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final ArrayList<String> exactSearchList ;
    private final ArrayList<String> fuzzySearchList;
    private final ArrayList<String> wildcardSearchList;

    public NameContainsKeywordsPredicate(List<String> keywords, ArrayList<String> exactSearchList,
                                         ArrayList<String> fuzzySearchList, ArrayList<String> wildcardSearchList) {
        this.keywords = keywords;
        this.exactSearchList = exactSearchList;
        this.fuzzySearchList =fuzzySearchList;
        this.wildcardSearchList = wildcardSearchList;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    String name = person.getName().fullName;
                    if (StringUtil.containsWordIgnoreCase(name, keyword)){
                        if (!exactSearchList.contains(name)){
                            exactSearchList.add(name);
                        }
                        return true;
                    }

                    if (StringUtil.matchFuzzySearch(name, keyword)){
                        if (!fuzzySearchList.contains(name)){
                            fuzzySearchList.add(name);
                        }
                        return true;
                    }

                    if (StringUtil.matchWildcardSearch(name, keyword)){
                        if (!wildcardSearchList.contains(name)){
                            wildcardSearchList.add(name);
                        }
                        return true;
                    }

                    return false;
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
