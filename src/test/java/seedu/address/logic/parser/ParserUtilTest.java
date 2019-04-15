package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndices.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndices.INDEX_SECOND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.quiz.QuizMode;

public class ParserUtilTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_SECOND, ParserUtil.parseIndex("  2  "));
    }

    @Test
    public void parserUtil() throws ParseException {
        final String count = "1";
        final String wrongCount = "asd";

        ParserUtil parserUtil = new ParserUtil();
        int expectedCount = parserUtil.parseCount(count);
        assertEquals(expectedCount, 1);
        String trimmedCount = wrongCount.trim();
        thrown.expectMessage("Count of number should be a valid integer less than MAX_INTEGER (2147483647).");
        parserUtil.parseCount(trimmedCount);
    }
    @Test
    public void parserSmallCount() throws ParseException {
        final String count = "0";
        ParserUtil parserUtil = new ParserUtil();
        String trimmedCount = count.trim();
        thrown.expectMessage("Count should not be less than 1 in a single lesson.");
        parserUtil.parseCount(trimmedCount);
    }
    @Test
    public void parserLargeCount() throws ParseException {
        final String count = "129084913749871398471936571369587198347981";
        ParserUtil parserUtil = new ParserUtil();
        String trimmedCount = count.trim();
        thrown.expectMessage("Count of number should be a valid integer less than MAX_INTEGER (2147483647).");
        parserUtil.parseCount(trimmedCount);
    }
    @Test
    public void parserMode_throwParserException() throws ParseException {
        final String mode = "learn";
        final String mode2 = "review";
        final String wrongMode = "akjfk";
        ParserUtil parserUtil = new ParserUtil();
        QuizMode expectedMode = parserUtil.parseMode(mode);
        assertEquals(expectedMode, QuizMode.LEARN);
        QuizMode expectedMode2 = parserUtil.parseMode(mode2);
        assertEquals(expectedMode2, QuizMode.REVIEW);
        String trimmedMode = wrongMode.trim().toUpperCase();
        thrown.expectMessage("Mode of quiz is not acceptable. You can choose from: "
                + "learn, preview, review and difficult.");
        parserUtil.parseMode(trimmedMode);
    }
}
