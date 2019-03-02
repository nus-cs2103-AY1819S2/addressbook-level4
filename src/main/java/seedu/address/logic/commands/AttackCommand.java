package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Attacks a cell on the board.
 */
public class AttackCommand extends Command {


	@Override
	public CommandResult execute(Model model, CommandHistory history) throws CommandException {
		requireNonNull(model);

		return null;
	}
}
