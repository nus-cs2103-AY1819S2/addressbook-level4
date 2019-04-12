package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyView;

public class GenerateQuestionCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initializeStudyView() {
        model.studyDeck(getTypicalDeck());
        assertTrue(model.isAtStudyView());
        expectedModel.studyDeck(getTypicalDeck());
        assertTrue(expectedModel.isAtStudyView());
    }

    @Test
    public void execute_showAnswer_success() {
        CommandResult expectedCommandResult = new CommandResult("");
        StudyView studyView = (StudyView) expectedModel.getViewState();
        studyView.generateCard();
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        studyView.addRating(VALID_RATING_HELLO);
        assertCommandSuccess(
                new GenerateQuestionCommand((StudyView) model.getViewState(), VALID_RATING_HELLO), model,
                commandHistory, expectedCommandResult, expectedModel);
    }

}
