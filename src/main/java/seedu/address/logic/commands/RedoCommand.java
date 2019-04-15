package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s card collection to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!\n"
            + "Redo %s command";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    private static final String MESSAGE_IN_QUIZ = "Cannot redo in quiz mode.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }

        if (!model.canRedoCardCollection()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String commandText = model.redoCardCollection();
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandText));
    }
}
