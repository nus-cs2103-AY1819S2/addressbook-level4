package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all flashcards in the card collection to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";
    public static final String MESSAGE_EMPTY = "Nothing to show";
    private static final String MESSAGE_IN_QUIZ = "Cannot list in quiz mode";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(model.getFilteredFlashcardList().size() > 0 ? MESSAGE_SUCCESS : MESSAGE_EMPTY);
    }
}
