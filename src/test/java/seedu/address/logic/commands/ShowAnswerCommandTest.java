package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;

import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyView;

public class ShowAnswerCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private StudyView studyView;

    @Before
    public void initializeStudyView() {
        model.studyDeck(getTypicalDeck());
        assertTrue(model.isAtStudyView());
        studyView = (StudyView) model.getViewState();
        expectedModel.studyDeck(getTypicalDeck());
        assertTrue(expectedModel.isAtStudyView());
    }
    //TODO
    //    @Test
    //    public void execute_showAnswer_success() {
    //        CommandResult expectedCommandResult = new CommandResult("");
    //        StudyView studyView = (StudyView) expectedModel.getViewState();
    //        studyView.setUserAnswer(VALID_ANSWER_HELLO);
    //        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
    //        assertCommandSuccess(new ShowAnswerCommand(VALID_ANSWER_HELLO), model, commandHistory,
    //                             expectedCommandResult, expectedModel);
    //    }

}
