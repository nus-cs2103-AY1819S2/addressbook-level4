package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_ANSWER;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_QUESTION;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_1;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_2;
import static seedu.address.testutil.LessonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.LessonBuilder.DEFAULT_OPT_HEADER_1;
import static seedu.address.testutil.TypicalLessonList.LESSON_DEFAULT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class AddLessonParserTest {
    public static final String NAME = " " + PREFIX_LESSON_NAME + DEFAULT_NAME;
    public static final String EMPTY_NAME = " " + PREFIX_LESSON_NAME + " ";
    public static final String NAME_OTHER = " " + PREFIX_LESSON_NAME + "Trivia";
    public static final String CORE_QUESTION = " " + PREFIX_CORE_QUESTION + DEFAULT_CORE_HEADER_1;
    public static final String EMPTY_CORE_QUESTION = " " + PREFIX_CORE_QUESTION + " ";
    public static final String CORE_ANSWER = " " + PREFIX_CORE_ANSWER + DEFAULT_CORE_HEADER_2;
    public static final String EMPTY_CORE_ANSWER = " " + PREFIX_CORE_ANSWER + " ";
    public static final String EMPTY_CORE = " " + PREFIX_CORE + " ";
    public static final String OPT_1 = " " + PREFIX_OPTIONAL + DEFAULT_OPT_HEADER_1;
    public static final String EMPTY_OPT = " " + PREFIX_OPTIONAL + " ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddLessonParser addLessonParser = new AddLessonParser();
    private Lesson expectedLesson = new LessonBuilder(LESSON_DEFAULT).withNoCards().build();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        // normal format
        assertParseSuccess(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + CORE_ANSWER + OPT_1, new AddLessonCommand(expectedLesson));

        // unordered format
        assertParseSuccess(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_ANSWER + OPT_1 + CORE_QUESTION, new AddLessonCommand(expectedLesson));

        // no preamble
        // normal format
        assertParseSuccess(addLessonParser, NAME
                + CORE_QUESTION + CORE_ANSWER + OPT_1, new AddLessonCommand(expectedLesson));

        // unordered format
        assertParseSuccess(addLessonParser, CORE_QUESTION + OPT_1 + CORE_ANSWER
                + NAME, new AddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_optionalFieldPrefixesMissing_success() {
        // zero optionals
        expectedLesson = new LessonBuilder(LESSON_DEFAULT).withNoOptionalHeaders().withNoCards().build();
        assertParseSuccess(addLessonParser, NAME + CORE_QUESTION + CORE_ANSWER,
                new AddLessonCommand(expectedLesson));
    }

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
                        + PREFIX_CORE + CORE_ANSWER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        assertParseFailure(addLessonParser, NAME
                        + CORE_QUESTION + PREFIX_CORE,
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
    public void parse_multipleQuestionsPresent_throwsIllegalArgumentException() {
        String expectedMessage = String.format(MESSAGE_INVALID_INPUT, PREFIX_CORE_QUESTION);

        // multiple questions -> parse exception thrown
        // 2 questions side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_QUESTION + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);

        // 2 questions not side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_QUESTION + CORE_ANSWER + CORE_QUESTION,
                expectedMessage);

        // 3 questions side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_QUESTION + CORE_QUESTION
                        + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);

        // 4 questions not side by side
        assertParseFailure(
                addLessonParser, CORE_QUESTION + NAME + CORE_QUESTION
                        + CORE_ANSWER + CORE_QUESTION + OPT_1 + CORE_QUESTION,
                expectedMessage);
    }

    @Test
    public void parse_multipleAnswersPresent_throwsIllegalArgumentException() {
        String expectedMessage = String.format(MESSAGE_INVALID_INPUT, PREFIX_CORE_ANSWER);
        // 2 questions side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_ANSWER + CORE_ANSWER + CORE_QUESTION,
                expectedMessage);

        // 2 questions not side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_ANSWER + CORE_QUESTION + CORE_ANSWER,
                expectedMessage);

        // 3 questions side by side
        assertParseFailure(
                addLessonParser, NAME + CORE_ANSWER + CORE_ANSWER
                        + CORE_ANSWER + CORE_QUESTION,
                expectedMessage);

        // 4 questions not side by side
        assertParseFailure(
                addLessonParser, CORE_ANSWER + NAME + CORE_ANSWER
                        + CORE_QUESTION + CORE_ANSWER + OPT_1 + CORE_ANSWER,
                expectedMessage);
    }

    @Test
    public void parse_multipleAnswerAndQuestionPresent_throwsIllegalArgumentException() {
        // Error message for duplicate 'q/' has higher precedence than error message for duplicate 'a/'
        String expectedMessageForQ = String.format(MESSAGE_INVALID_INPUT, PREFIX_CORE_QUESTION);

        // Normal format with 2 questions and 2 answers -> failure
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + CORE_ANSWER + CORE_QUESTION + CORE_ANSWER,
                expectedMessageForQ);

        // Unordered format with 2 questions and 3 answers -> failure
        assertParseFailure(
                addLessonParser, CORE_ANSWER + NAME + CORE_ANSWER
                        + CORE_QUESTION + CORE_QUESTION + OPT_1 + CORE_ANSWER + CORE_ANSWER,
                expectedMessageForQ);
    }

    @Test
    public void parse_multipleNameAndQuestionPresent_throwsIllegalArgumentException() {
        // Error message for duplicate 'n/' has higher precedence than error message for duplicate 'q/'
        String expectedMessageForN = String.format(MESSAGE_INVALID_INPUT, PREFIX_LESSON_NAME);

        // Normal format with 2 names and 2 questions -> failure
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME + NAME
                        + CORE_QUESTION + CORE_QUESTION + CORE_ANSWER,
                expectedMessageForN);

        // 3 names and 2 questions -> failure
        assertParseFailure(
                addLessonParser, CORE_ANSWER + NAME + CORE_QUESTION
                        + CORE_QUESTION + NAME + NAME + CORE_QUESTION,
                expectedMessageForN);
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
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_CORE);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + EMPTY_CORE_QUESTION + CORE_ANSWER, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, EMPTY_CORE_QUESTION + CORE_ANSWER
                + NAME + OPT_1, expectedMessageForC);
    }

    @Test
    public void parse_emptyAnswerSupplied_throwsIllegalArgumentException() {
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_CORE);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + EMPTY_CORE_ANSWER, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION
                + NAME + OPT_1 + EMPTY_CORE_ANSWER, expectedMessageForC);
    }

    @Test
    public void parse_emptyOptionalSupplied_throwsIllegalArgumentException() {
        String expectedMessageForO = String.format(MESSAGE_EMPTY_INPUT, PREFIX_OPTIONAL);

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
        String expectedMessageForC = String.format(MESSAGE_EMPTY_INPUT, PREFIX_CORE);

        // normal format
        assertParseFailure(addLessonParser, PREAMBLE_WHITESPACE + NAME
                + CORE_QUESTION + EMPTY_CORE_ANSWER + EMPTY_OPT, expectedMessageForC);

        // unordered format
        assertParseFailure(addLessonParser, CORE_QUESTION
                + NAME + OPT_1 + CORE_ANSWER + EMPTY_CORE + EMPTY_OPT, expectedMessageForC);
    }
}
