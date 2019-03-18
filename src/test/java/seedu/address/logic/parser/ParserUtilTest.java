package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.modelmanager.quiz.Quiz;

public class ParserUtilTest {
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
