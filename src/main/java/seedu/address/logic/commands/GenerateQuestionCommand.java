package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.StudyView;

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
    public CommandResult execute(Model model, CommandHistory history) {
        studyView.getCurrentCard().addDifficulty(rating);
        studyView.generateCard();
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateQuestionCommand // instanceof handles nulls
                && rating == ((GenerateQuestionCommand) other).rating);
    }
}
