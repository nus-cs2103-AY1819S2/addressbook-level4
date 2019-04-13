package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.FilterCriteriaContainsKeywordPredicate;
import seedu.address.model.Model;
import seedu.address.model.person.GenderContainsKeywordsPredicate;
import seedu.address.model.person.MajorContainsKeywordsPredicate;
import seedu.address.model.person.YearOfStudyContainsKeywordsPredicate;


/**
 * A command that filters the member's list based on a given filter criteria.
 */
public class MemberFilterCommand extends MemberCommand {
    public static final String COMMAND_WORD = "memberFilter";
    public static final String COMMAND_ALIAS = "mFilter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ：Generates a filtered member's list based on a given"
            + " filter criteria. "
            + "Parameters:  CRITERIA KEYWORD [MORE KEYWORDS]\n"

            + "Example: " + COMMAND_WORD + " yearofstudy" + " 2";

    public final FilterCriteriaContainsKeywordPredicate criteria;
    public final YearOfStudyContainsKeywordsPredicate yearOfStudyPredicate;
    public final MajorContainsKeywordsPredicate majorPredicate;
    public final GenderContainsKeywordsPredicate genderPredicate;

    public MemberFilterCommand(FilterCriteriaContainsKeywordPredicate criteria) {
        requireNonNull(criteria);
        this.criteria = criteria;
        this.majorPredicate = new MajorContainsKeywordsPredicate(Arrays.asList(criteria.getFilterKeywords()));
        this.genderPredicate = new GenderContainsKeywordsPredicate(Arrays.asList(criteria.getFilterKeywords()));
        this.yearOfStudyPredicate =
                new YearOfStudyContainsKeywordsPredicate(Arrays.asList(criteria.getFilterKeywords()));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (criteria.toString().equalsIgnoreCase("gender")) {
            model.updateFilteredPersonList(genderPredicate);
        } else if (criteria.toString().equalsIgnoreCase("major")) {
            model.updateFilteredPersonList(majorPredicate);
        } else if (criteria.toString().equalsIgnoreCase("yearofstudy")) {
            model.updateFilteredPersonList(yearOfStudyPredicate);
        }

        model.getFilteredPersonList();

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberFilterCommand // instanceof handles nulls
                && criteria.equals(((MemberFilterCommand) other).criteria)); // state check
    }
}
