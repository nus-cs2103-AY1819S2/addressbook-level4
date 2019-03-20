package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.StudyView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows the answer to the question
 */
public class GenerateQuestionCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Question";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        StudyView studyView = ((StudyView) model.getViewState());
        studyView.generateCard();
        studyView.setCurrentStudyState(StudyView.studyState.QUESTION);
        studyView.updateTextShown();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
