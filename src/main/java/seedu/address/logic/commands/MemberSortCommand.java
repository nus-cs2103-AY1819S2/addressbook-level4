package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Creates an SortCommand to sort the member's list based on a given criteria.
 * Criteria is case-insensitive.
 */
public class MemberSortCommand extends MemberCommand {
    public static final String COMMAND_WORD = "memberSort";
    public static final String COMMAND_ALIAS = "mSort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all members in the members' list based on "
            + "the specified attribute (case-insensitive).\n"
            + "Parameters: KEYWORD...\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_MEMBER_SORT_SUCCESS = "Members' list successfully sorted!";

    private final String input;

    public MemberSortCommand(String input) {
        this.input = input;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortAddressBook(input);
        return new CommandResult(
                String.format(MESSAGE_MEMBER_SORT_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberSortCommand // instanceof handles nulls
                && input.equals(((MemberSortCommand) other).input)); // state check
    }
}
