package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OPENED_LESSON;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which sets the question and answer core indices of
 * a {@link Lesson} in the {@code List<Lesson> lessons} loaded in memory.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class SetTestCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "setTest";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the 2 test values to be tested for the opened lesson's flashcards.\n"
            + "By default, the first 2 test values are tested as a question-answer pair.\n"
            + "Use this command to change the test values to be tested.\n"
            + "Parameters: INDEX INDEX (starting from 1)\n"
            + "Example: " + COMMAND_WORD + "1 3\n"
            + "Note: The example sets the 1st and 3rd test values as the 2 values to be tested.";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS =
            "Updated lesson's question and answer.\nQuestion: %1$s\nAnswer: %2$s";
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
     * Creates an SetTestCommand to set the question and answer core indices of
     * the specified {@link Lesson}.
     *
     * @param questionIndex the index of the {@link Lesson} to be deleted
     * @param answerIndex ew
     */
    public SetTestCommand(Index questionIndex, Index answerIndex) {
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
            throw new CommandException(MESSAGE_NO_OPENED_LESSON);
        }

        try {
            int qIndex = questionIndex.getZeroBased();
            int aIndex = answerIndex.getZeroBased();
            mgtModel.setOpenedLessonQuestionAnswer(qIndex, aIndex);
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
     * {@link SetTestCommand} attempting to set the same question and answer index.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also a
     * {@link SetTestCommand} attempting to set the same question and answer index.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof SetTestCommand // instanceof handles nulls
                && questionIndex.getZeroBased()
                == ((SetTestCommand) other).questionIndex.getZeroBased()
                && answerIndex.getZeroBased()
                == ((SetTestCommand) other).answerIndex.getZeroBased()));
    }
}
