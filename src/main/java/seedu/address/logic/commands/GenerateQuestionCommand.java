package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.StudyView;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows the answer to the question
 */
public class GenerateQuestionCommand extends Command {

    private final int rating;
    private final StudyView studyView;

    public GenerateQuestionCommand(StudyView studyView, int rating) {
        this.rating = rating;
        this.studyView = studyView;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        studyView.addRating(rating);
        studyView.generateCard();
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        studyView.updateTextShown();
        return new CommandResult("");
    }
}
