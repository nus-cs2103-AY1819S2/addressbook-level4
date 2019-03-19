package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_CORE_HEADER;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_OPT_HEADER;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_1;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_2;
import static seedu.address.testutil.LessonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.LessonBuilder.DEFAULT_OPT_HEADER_1;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;

import org.junit.Test;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

public class AddLessonParserTest {
    public static final String LESSON_NAME = " " + PREFIX_LESSON_NAME + DEFAULT_NAME;
    public static final String LESSON_NAME_OTHER = " " + PREFIX_LESSON_NAME + "Trivia";
    public static final String LESSON_CORE_1 = " " + PREFIX_LESSON_CORE_HEADER + DEFAULT_CORE_HEADER_1;
    public static final String LESSON_CORE_2 = " " + PREFIX_LESSON_CORE_HEADER + DEFAULT_CORE_HEADER_2;
    public static final String LESSON_OPT_1 = " " + PREFIX_LESSON_OPT_HEADER + DEFAULT_OPT_HEADER_1;

    private AddLessonParser addLessonParser = new AddLessonParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder(LESSON_DEFAULT).withNoCards().build();

        // whitespace only preamble
        assertParseSuccess(addLessonParser, PREAMBLE_WHITESPACE + LESSON_NAME
                + LESSON_CORE_1 + LESSON_CORE_2 + LESSON_OPT_1, new AddLessonCommand(expectedLesson));

        // multiple names - last name accepted
        assertParseSuccess(addLessonParser, LESSON_NAME_OTHER + LESSON_NAME
                + LESSON_CORE_1 + LESSON_CORE_2 + LESSON_OPT_1, new AddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero optionals
        Lesson expectedLesson = new LessonBuilder(LESSON_DEFAULT).withNoOptionalHeaders().withNoCards().build();
        assertParseSuccess(addLessonParser, LESSON_NAME + LESSON_CORE_1 + LESSON_CORE_2,
                new AddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(addLessonParser, DEFAULT_NAME + LESSON_CORE_1 + LESSON_CORE_2,
                expectedMessage);

        // missing 1 core prefix
        assertParseFailure(addLessonParser, LESSON_NAME + DEFAULT_CORE_HEADER_1 + LESSON_CORE_2,
                expectedMessage);

        assertParseFailure(addLessonParser, LESSON_NAME + LESSON_CORE_1 + DEFAULT_CORE_HEADER_2,
                expectedMessage);

        // missing 2 core prefixes
        assertParseFailure(addLessonParser, LESSON_NAME + DEFAULT_CORE_HEADER_1 + DEFAULT_CORE_HEADER_2,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(addLessonParser, DEFAULT_NAME + DEFAULT_CORE_HEADER_1 + DEFAULT_CORE_HEADER_2,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(addLessonParser, PREAMBLE_NON_EMPTY + LESSON_NAME_OTHER + LESSON_NAME
                        + LESSON_CORE_1 + LESSON_CORE_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // missing name
        assertParseFailure(addLessonParser, PREFIX_LESSON_NAME
                        + LESSON_CORE_1 + LESSON_CORE_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        // empty core string specified
        assertParseFailure(addLessonParser, LESSON_NAME
                        + PREFIX_LESSON_CORE_HEADER + LESSON_CORE_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));

        assertParseFailure(addLessonParser, LESSON_NAME
                        + LESSON_CORE_1 + PREFIX_LESSON_CORE_HEADER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }
}
