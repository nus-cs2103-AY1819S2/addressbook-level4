package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDecks.getTypicalTopDeck;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.FindDeckCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;

public class FindDeckCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private FindDeckCommandParser parser;

    @Before
    public void initialize() {
        assertTrue(model.isAtDecksView());
        parser = new FindDeckCommandParser((DecksView) model.getViewState());
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeckCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindDeckCommand expectedFindCommand =
                new FindDeckCommand((DecksView) model.getViewState(), new DeckNameContainsKeywordsPredicate(Arrays
                        .asList("John", "Doe")));
        assertParseSuccess(parser, "John Doe", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n John \n \t Doe  \t", expectedFindCommand);
    }

}
