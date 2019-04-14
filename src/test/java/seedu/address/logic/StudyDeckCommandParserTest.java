package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class StudyDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, StudyDeckCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private StudyDeckCommandParser parser = new StudyDeckCommandParser((DecksView) model.getViewState());


    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // non-integer index
        assertParseFailure(parser, "1.5", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_FIRST_DECK;
        String userInput = targetIndex.getOneBased() + "";
        StudyDeckCommand expectedCommand = new StudyDeckCommand((DecksView) model.getViewState(),
                                                                targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


