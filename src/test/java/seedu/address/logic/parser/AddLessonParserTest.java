package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.Syntax.PREFIX_HINT;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_TEST;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_1;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_2;
import static seedu.address.testutil.LessonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.LessonBuilder.DEFAULT_OPT_HEADER_1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.management.AddLessonCommand;

public class AddLessonParserTest {
    public static final String NAME = " " + PREFIX_LESSON_NAME + DEFAULT_NAME;
    public static final String EMPTY_NAME = " " + PREFIX_LESSON_NAME + " ";
    public static final String NAME_OTHER = " " + PREFIX_LESSON_NAME + "Trivia";
    public static final String CORE_QUESTION = " " + PREFIX_TEST + DEFAULT_CORE_HEADER_1;
    public static final String EMPTY_CORE_QUESTION = " " + PREFIX_TEST + " ";
    public static final String CORE_ANSWER = " " + PREFIX_TEST + DEFAULT_CORE_HEADER_2;
    public static final String EMPTY_CORE_ANSWER = " " + PREFIX_TEST + " ";
    public static final String EMPTY_CORE = " " + PREFIX_TEST + " ";
    public static final String OPT_1 = " " + PREFIX_HINT + DEFAULT_OPT_HEADER_1;
    public static final String EMPTY_OPT = " " + PREFIX_HINT + " ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddLessonParser addLessonParser = new AddLessonParser();

    @Test
    public void parse_compulsoryFieldPrefixesMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(addLessonParser, DEFAULT_NAME + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);

        // missing 1 core prefix
        assertParseFailure(addLessonParser, NAME + DEFAULT_CORE_HEADER_1 + CORE_ANSWER,
                expectedMessage);

        assertParseFailure(addLessonParser, NAME + CORE_QUESTION + DEFAULT_CORE_HEADER_2,
                expectedMessage);

        // missing 2 core prefixes
        assertParseFailure(addLessonParser, NAME + DEFAULT_CORE_HEADER_1 + DEFAULT_CORE_HEADER_2,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(addLessonParser, DEFAULT_NAME + DEFAULT_CORE_HEADER_1 + DEFAULT_CORE_HEADER_2,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(addLessonParser, PREAMBLE_NON_EMPTY + NAME_OTHER + NAME
                        + CORE_QUESTION + CORE_ANSWER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // missing name
        assertParseFailure(addLessonParser, PREFIX_LESSON_NAME
                        + CORE_QUESTION + CORE_ANSWER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // empty core string specified
        assertParseFailure(addLessonParser, NAME
                        + PREFIX_TEST + CORE_ANSWER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        assertParseFailure(addLessonParser, NAME
                        + CORE_QUESTION + PREFIX_TEST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleNamesPresent_throwsIllegalArgumentException() {
        String expectedMessage = String.format(MESSAGE_INVALID_INPUT, PREFIX_LESSON_NAME);

        // multiple names -> parse exception thrown
        assertParseFailure(
                addLessonParser, NAME + NAME + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);

        assertParseFailure(
                addLessonParser, NAME + NAME_OTHER + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);
    }

    @Test
    public void parse_emptyNameSupplied_throwsIllegalArgumentException() {
        String expectedMessageForN = String.format(MESSAGE_EMPTY_INPUT, PREFIX_LESSON_NAME);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + EMPTY_NAME
                + CORE_QUESTION + CORE_ANSWER, expectedMessageForN);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION + CORE_ANSWER
                + EMPTY_NAME, expectedMessageForN);
    }

    @Test
    public void parse_emptyQuestionSupplied_throwsIllegalArgumentException() {
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_TEST);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + EMPTY_CORE_QUESTION + CORE_ANSWER, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, EMPTY_CORE_QUESTION + CORE_ANSWER
                + NAME + OPT_1, expectedMessageForC);
    }

    @Test
    public void parse_emptyAnswerSupplied_throwsIllegalArgumentException() {
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_TEST);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + EMPTY_CORE_ANSWER, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION
                + NAME + OPT_1 + EMPTY_CORE_ANSWER, expectedMessageForC);
    }

    @Test
    public void parse_emptyOptionalSupplied_throwsIllegalArgumentException() {
        String expectedMessageForO = String.format(MESSAGE_EMPTY_INPUT, PREFIX_HINT);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + CORE_ANSWER + EMPTY_OPT, expectedMessageForO);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION
                + NAME + OPT_1 + CORE_ANSWER + EMPTY_OPT, expectedMessageForO);
    }

    @Test
    public void parse_emptyCoreAndOptionalSupplied_throwsIllegalArgumentException() {
        // Error message for empty 'c/' has higher precedence than error message for empty 'o/'
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_TEST);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + EMPTY_CORE_ANSWER + EMPTY_OPT, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION
                + NAME + OPT_1 + CORE_ANSWER + EMPTY_CORE + EMPTY_OPT, expectedMessageForC);
    }
}
