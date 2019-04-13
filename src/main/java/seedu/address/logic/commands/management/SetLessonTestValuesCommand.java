package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CARD_VIEW_COMMAND;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which sets the 2 test values to be tested for
 * the opened {@link Lesson} in the {@code List<Lesson> lessons} loaded in memory.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class SetLessonTestValuesCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "set";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the 2 test values to be tested.\n"
            + "Parameters: INDEX INDEX (both starting from 1)\n"
            + "Example: " + COMMAND_WORD + " 1 3\n"
            + "Note: When the lesson is started in quiz mode, these 2 values will be tested.";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS =
            "Updated lesson's tested values.\nTested value #1: %1$s\nTested value #2: %2$s";
    /**
     * The question index to be set when {@link #execute(Model, CommandHistory)}
     * is called.
     */
    private final Index questionIndex;
    /**
     * The answer index to be set when {@link #execute(Model, CommandHistory)}
     * is called.
     */
    private final Index answerIndex;
    /**
     * Creates an SetLessonTestValuesCommand to set the question and answer core indices of
     * the specified {@link Lesson}.
     *
     * @param questionIndex the index of the {@link Lesson} to be deleted
     * @param answerIndex ew
     */
    public SetLessonTestValuesCommand(Index questionIndex, Index answerIndex) {
        this.questionIndex = questionIndex;
        this.answerIndex = answerIndex;
    }

    /**
     * Executes the command which sets the question and answer core indices of
     * a {@link Lesson} in the {@code List<Lesson> lessons} loaded in memory.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if invalid index supplied or if the {@code model} passed in
     * is not a {@link ManagementModel}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        // Can only set question and answer when in opened lesson
        if (!mgtModel.isThereOpenedLesson()) {
            throw new CommandException(MESSAGE_CARD_VIEW_COMMAND);
        }

        try {
            int qIndex = questionIndex.getZeroBased();
            int aIndex = answerIndex.getZeroBased();
            mgtModel.setOpenedLessonTestValues(qIndex, aIndex);
            List<String> cores = mgtModel.getOpenedLessonCoreHeaders();
            String question = cores.get(qIndex);
            String answer = cores.get(aIndex);
            return new CommandResult(String.format(MESSAGE_SUCCESS, question, answer));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    /**
     * Returns true if {@code other} is the same object or if it is also a
     * {@link SetLessonTestValuesCommand} attempting to set the same 2 test values as question and answer.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also a
     * {@link SetLessonTestValuesCommand} attempting to set the same 2 test values as question and answer.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SetLessonTestValuesCommand)) {
            return false;
        }

        SetLessonTestValuesCommand otherCommand = (SetLessonTestValuesCommand) other;

        return (otherCommand.answerIndex.getZeroBased() + otherCommand.questionIndex.getZeroBased()
            == questionIndex.getZeroBased() + answerIndex.getZeroBased());
    }
}
