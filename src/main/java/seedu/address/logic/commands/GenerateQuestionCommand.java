package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows the answer to the question
 */
public class GenerateQuestionCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Answer";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.generateCard();
        model.setCurrentStudyState(Model.studyState.QUESTION);
        model.updateTextShown();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
