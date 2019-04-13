package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyView;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.Card;

public class GenerateQuestionCommandTest {
    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initializeStudyView() {
        model.studyDeck(getTypicalDeck());
        assertTrue(model.isAtStudyView());
        expectedModel.studyDeck(getTypicalDeck());
        assertTrue(expectedModel.isAtStudyView());
    }

    @Test
    public void execute_generateQuestion_success() {
        CommandResult expectedCommandResult = new CommandResult("");
        StudyView studyView = (StudyView) model.getViewState();
        Card expectedRatingChanged = studyView.getCurrentCard();

        StudyView expectedStudyView = (StudyView) expectedModel.getViewState();
        expectedStudyView.generateCard();
        expectedStudyView.setCurrentStudyState(StudyView.StudyState.QUESTION);

        assertCommandSuccess(new GenerateQuestionCommand(studyView, VALID_RATING_HELLO), model,
                             commandHistory, expectedCommandResult, expectedModel);
        expectedRatingChanged.resetDifficulty();
    }

}
