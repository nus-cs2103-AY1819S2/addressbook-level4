package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndices.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndices.INDEX_SECOND_LESSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.quiz.Quiz;

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
        assertEquals(INDEX_FIRST_LESSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_SECOND_LESSON, ParserUtil.parseIndex("  2  "));
    }

    @Test
    public void parserUtil() {
        final String name = "01-01-learn";
        final String count = "1";
        final String mode = "learn";
        ParserUtil parserUtil = new ParserUtil();
        String expectedName = parserUtil.parseName(name);
        assertEquals(expectedName, name);
        int expectedCount = parserUtil.parseCount(count);
        assertEquals(expectedCount, 1);
        Quiz.Mode expectedMode = parserUtil.parseMode(mode);
        assertEquals(expectedMode, Quiz.Mode.LEARN);
    }
}
