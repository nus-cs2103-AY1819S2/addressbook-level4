package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;

public class StatsCommandParserTest {

    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "   asdf  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsStatsCommand() {
        // no leading and trailing whitespaces
        FlashcardContainsKeywordsPredicate predicate = new FlashcardContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("Charlie"), Arrays.asList("Robin"));

        StatsCommand expectedStatsCommand = new StatsCommand(predicate);


        assertParseSuccess(parser, " f/Alice Bob b/Charlie t/Robin", expectedStatsCommand);

        // multiple whitespaces between keywords
        // TODO
        // assertParseSuccess(parser, "  f/Alice  \t f/Bob  \t  b/Charlie \t  t/Robin \t", expectedStatsCommand);

        // empty args
        expectedStatsCommand = new StatsCommand();
        assertParseSuccess(parser, "      ", expectedStatsCommand);
    }
}
