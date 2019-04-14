package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.QuizCommand;

public class QuizCommandParserTest {
    private QuizCommandParser parser = new QuizCommandParser();

    @Test
    public void parse_validField_success() {
        // review mode
        assertParseSuccess(parser, "", new QuizCommand());
        assertParseSuccess(parser, QuizCommand.REVIEW_MODE, new QuizCommand());

        // mixed case
        assertParseSuccess(parser, "ReViEw", new QuizCommand());

        // srs mode
        assertParseSuccess(parser, QuizCommand.SRS_MODE, new QuizCommand(true));

        // mixed case
        assertParseSuccess(parser, "SRS", new QuizCommand(true));
        assertParseSuccess(parser, "SrS", new QuizCommand(true));
    }

    @Test
    public void parse_invalidField_fail() {
        // both mode
        assertParseFailure(parser, "srs review", QuizCommand.MESSAGE_QUIZ_FAILURE_UNKNOWN_MODE);

        // invalid mode
        assertParseFailure(parser, "advanced", QuizCommand.MESSAGE_QUIZ_FAILURE_UNKNOWN_MODE);
        assertParseFailure(parser, "srs srs srs", QuizCommand.MESSAGE_QUIZ_FAILURE_UNKNOWN_MODE);
        assertParseFailure(parser, "acquizition is the best", QuizCommand.MESSAGE_QUIZ_FAILURE_UNKNOWN_MODE);

    }
}
