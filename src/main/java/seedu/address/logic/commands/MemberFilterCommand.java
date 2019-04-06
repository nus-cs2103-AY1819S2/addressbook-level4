package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAROFSTUDY;

public class MemberFilterCommand extends MemberCommand {
    public static final String COMMAND_WORD = "memberFilter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ：Generates a filtered list of members based on a given"
            + " filter criteria. "
            + "Parameters:  CONDITION_PREFIX (PREFIX_GENDER OR PREFIX_MAJOR OR PREFIX_TAG OR PREFIX_YEAROFSTUDY)"
            + "CONDITION.\n"

            + "Example: " + COMMAND_WORD + PREFIX_YEAROFSTUDY + " 2";

    public final MemberFilterPredicate predicate;

    public MemberFilterCommand(MemberFilterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMemberList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberFilterCommand // instanceof handles nulls
                && predicate.equals(((MemberFilterCommand) other).predicate)); // state check
    }
}
