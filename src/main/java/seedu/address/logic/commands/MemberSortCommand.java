package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.SortCriteriaContainsKeywordPredicate;
import seedu.address.model.Model;

/**
 * Creates an SortCommand to sort the member's list based on a given criteria.
 * Criteria is case-insensitive.
 */
public class MemberSortCommand extends Command {
    public static final String COMMAND_WORD = "memberSort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all members in the members' list based on "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_MEMBER_SORT_SUCCESS = "Members' list successfully sorted!";

    private final SortCriteriaContainsKeywordPredicate predicate;

    public MemberSortCommand(SortCriteriaContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortAddressBook(predicate);
        return new CommandResult(
                String.format(MESSAGE_MEMBER_SORT_SUCCESS));
    }


}
