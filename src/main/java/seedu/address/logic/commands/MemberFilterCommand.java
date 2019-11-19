package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.GenderContainsKeywordsPredicate;
import seedu.address.model.person.MajorContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;
import seedu.address.model.person.YearOfStudyContainsKeywordsPredicate;


/**
 * A command that filters the member's list based on a given filter criteria.
 */
public class MemberFilterCommand extends MemberCommand {
    public static final String COMMAND_WORD = "memberFilter";
    public static final String COMMAND_ALIAS = "mFilter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ：Generates a filtered member's list based on a given"
            + " filter criteria. "
            + "Parameters:  CRITERIA [KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " yearofstudy" + " 2";

    public final String[] input;
    public final YearOfStudyContainsKeywordsPredicate yearOfStudyPredicate;
    public final MajorContainsKeywordsPredicate majorPredicate;
    public final GenderContainsKeywordsPredicate genderPredicate;
    public final TagsContainsKeywordsPredicate tagsPredicate;

    public MemberFilterCommand(String[] input) {
        requireNonNull(input[0]);
        requireNonNull(input[1]);
        this.input = input;
        this.majorPredicate = new MajorContainsKeywordsPredicate(input[1]);
        this.genderPredicate = new GenderContainsKeywordsPredicate(input[1]);
        this.tagsPredicate = new TagsContainsKeywordsPredicate(input[1]);
        this.yearOfStudyPredicate =
                new YearOfStudyContainsKeywordsPredicate(input[1]);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (input[0].equalsIgnoreCase("gender")) {
            model.updateFilteredPersonList(genderPredicate);
        } else if (input[0].equalsIgnoreCase("major")) {
            model.updateFilteredPersonList(majorPredicate);
        } else if (input[0].equalsIgnoreCase("yearofstudy")) {
            model.updateFilteredPersonList(yearOfStudyPredicate);
        } else if (input[0].equalsIgnoreCase("tags")) {
            model.updateFilteredPersonList(tagsPredicate);
        }

        model.getFilteredPersonList();

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberFilterCommand); // instanceof handles nulls
    }
}
