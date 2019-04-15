package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.QuizState;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Selects a flashcard identified using it's displayed index from the card collection.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the flashcard identified by the index number used in the displayed flashcard list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_FLASHCARD_SUCCESS = "Selected Flashcard: %1$s, %2$.2f%% success.\n"
        + "SRS Status: %3$s";
    private static final String MESSAGE_IN_QUIZ = "Cannot select in quiz mode.";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }

        List<Flashcard> filteredFlashcardList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= filteredFlashcardList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcard = filteredFlashcardList.get(targetIndex.getZeroBased());
        model.setSelectedFlashcard(flashcard);
        return new CommandResult(String.format(MESSAGE_SELECT_FLASHCARD_SUCCESS, targetIndex.getOneBased(),
            flashcard.getSuccessRate(), flashcard.getQuizSrsStatus()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCommand // instanceof handles nulls
            && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
