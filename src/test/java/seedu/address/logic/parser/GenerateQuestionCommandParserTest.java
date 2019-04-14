package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyView;
import seedu.address.model.UserPrefs;

/**
 * Test whether commandword or rating is valid
 */
public class GenerateQuestionCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private GenerateQuestionCommandParser parser;

    @Before
    public void initialize() {
        model.studyDeck(getTypicalDeck());
        assertTrue(model.isAtStudyView());
        parser = new GenerateQuestionCommandParser((StudyView) model.getViewState());
    }

    @Test
    public void parse_validArgs_returnsGenerateQuestionCommand() {
        assertParseSuccess(parser, "1", new GenerateQuestionCommand((StudyView) model.getViewState(), 1));
        assertParseSuccess(parser, "5", new GenerateQuestionCommand((StudyView) model.getViewState(), 5));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", GenerateQuestionCommandParser.MESSAGE_INVALID_RATING);
        assertParseFailure(parser, "1.5", GenerateQuestionCommandParser.MESSAGE_INVALID_RATING);
    }

    @Test
    public void parse_outOfBoundArgs_throwsParseException() {
        assertParseFailure(parser, "0", GenerateQuestionCommandParser.MESSAGE_EXCEED_RATING);
        assertParseFailure(parser, "6", GenerateQuestionCommandParser.MESSAGE_EXCEED_RATING);
    }
}
