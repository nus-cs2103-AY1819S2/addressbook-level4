package seedu.pdf.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.model.Model;

/**
 * Reverts the {@code model}'s pdf book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoPdfBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoPdfBook();
        model.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
