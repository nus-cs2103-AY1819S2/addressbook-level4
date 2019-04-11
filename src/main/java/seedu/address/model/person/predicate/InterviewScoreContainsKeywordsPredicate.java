package seedu.address.model.person.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class InterviewScoreContainsKeywordsPredicate extends PredicateManager {
    private final List<String> keywords;
    private final int questionNum;

    public InterviewScoreContainsKeywordsPredicate(int questionNum, List<String> keywords) {
        this.questionNum = questionNum;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return (keywords == null) || keywords.stream()
            .anyMatch(keyword -> StringUtil.valueInRange(keyword, person.getInterviewScores(questionNum)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof InterviewScoreContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((InterviewScoreContainsKeywordsPredicate) other).keywords))
            && questionNum == ((InterviewScoreContainsKeywordsPredicate) other).questionNum; // state check
    }

}
