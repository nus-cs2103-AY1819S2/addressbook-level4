package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_CAP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_LAB_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_LECTURE_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_PREPARATION_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_PROJECT_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAX_TUTORIAL_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_CAP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_LAB_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_LECTURE_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_PREPARATION_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_PROJECT_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_TUTORIAL_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_CAP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_LAB_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_LECTURE_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_PREPARATION_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_PROJECT_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAX_TUTORIAL_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_CAP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_LAB_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_LECTURE_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_PREPARATION_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_PROJECT_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_TUTORIAL_HOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_CAP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_LAB_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_LECTURE_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_PREPARATION_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_PROJECT_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_TUTORIAL_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_CAP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_LAB_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_LECTURE_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_PREPARATION_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_PROJECT_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_TUTORIAL_HOUR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SEMESTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SEMESTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SEMESTER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetSemesterLimitCommand;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.Semester;
import seedu.address.testutil.EditSemesterLimitDescriptorBuilder;

public class SetSemesterLimitCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetSemesterLimitCommand.MESSAGE_USAGE);

    private SetSemesterLimitCommandParser parser = new SetSemesterLimitCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MAX_LECTURE_HOUR, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "Y2S2", SetSemesterLimitCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // not a semester
        assertParseFailure(parser, "word" + MIN_CAP_DESC, MESSAGE_INVALID_FORMAT);

        // a number
        assertParseFailure(parser, "2" + MIN_CAP_DESC, MESSAGE_INVALID_FORMAT);

        // invalid semester 3
        assertParseFailure(parser, "Y3Y3" + MIN_CAP_DESC, MESSAGE_INVALID_FORMAT);

        // invalid year 6
        assertParseFailure(parser, "Y6Y1" + MIN_CAP_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "Y2Y1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "Y1Y1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_CAP_DESC, CapAverage.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_CAP_DESC, CapAverage.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_LECTURE_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_LECTURE_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_TUTORIAL_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_TUTORIAL_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_LAB_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_LAB_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_PROJECT_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_PROJECT_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_PREPARATION_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "Y1S1" + INVALID_MAX_PREPARATION_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);


        // invalid min lecture hour followed by valid max lecture hour
        assertParseFailure(parser, "Y1S1" + INVALID_MIN_LECTURE_HOUR_DESC
                + MAX_LECTURE_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);

        // valid min lecture hours followed by invalid max lecture hours
        assertParseFailure(parser, "Y1S1" + MIN_LECTURE_HOUR_DESC
                + INVALID_MAX_LECTURE_HOUR_DESC, Hour.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Semester targetIndex = INDEX_SECOND_SEMESTER;
        String userInput = targetIndex.name() + MIN_CAP_DESC + MAX_CAP_DESC + MIN_LECTURE_HOUR_DESC
                + MAX_LECTURE_HOUR_DESC + MIN_TUTORIAL_HOUR_DESC + MAX_TUTORIAL_HOUR_DESC + MIN_LAB_HOUR_DESC
                + MAX_LAB_HOUR_DESC + MIN_PROJECT_HOUR_DESC + MAX_PROJECT_HOUR_DESC + MIN_PREPARATION_HOUR_DESC
                + MAX_PREPARATION_HOUR_DESC;

        SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinCap(VALID_MIN_CAP)
                .withMaxCap(VALID_MAX_CAP)
                .withMinLectureHour(VALID_MIN_LECTURE_HOUR)
                .withMaxLectureHour(VALID_MAX_LECTURE_HOUR)
                .withMinTutorialHour(VALID_MIN_TUTORIAL_HOUR)
                .withMaxTutorialHour(VALID_MAX_TUTORIAL_HOUR)
                .withMinLabHour(VALID_MIN_LAB_HOUR)
                .withMaxLabHour(VALID_MAX_LAB_HOUR)
                .withMinProjectHour(VALID_MIN_PROJECT_HOUR)
                .withMaxProjectHour(VALID_MAX_PROJECT_HOUR)
                .withMinPreparationHour(VALID_MIN_PREPARATION_HOUR)
                .withMaxPreparationHour(VALID_MAX_PREPARATION_HOUR)
                .build();
        SetSemesterLimitCommand expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Semester targetIndex = INDEX_FIRST_SEMESTER;
        String userInput = targetIndex.name() + MAX_CAP_DESC + MIN_LECTURE_HOUR_DESC;

        SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMaxCap(VALID_MAX_CAP)
                .withMinLectureHour(VALID_MIN_LECTURE_HOUR).build();
        SetSemesterLimitCommand expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // min project hour
        Semester targetIndex = INDEX_THIRD_SEMESTER;
        String userInput = targetIndex.name() + MIN_PROJECT_HOUR_DESC;
        SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinProjectHour(VALID_MIN_PROJECT_HOUR)
                .build();
        SetSemesterLimitCommand expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // min cap
        userInput = targetIndex.name() + MIN_CAP_DESC;
        descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinCap(VALID_MIN_CAP)
                .build();
        expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // min lecture hour
        userInput = targetIndex.name() + MIN_LECTURE_HOUR_DESC;
        descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinLectureHour(VALID_MIN_LECTURE_HOUR)
                .build();
        expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // max lecture hour
        userInput = targetIndex.name() + MAX_LECTURE_HOUR_DESC;
        descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMaxLectureHour(VALID_MAX_LECTURE_HOUR)
                .build();
        expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // max project hour
        userInput = targetIndex.name() + MAX_PROJECT_HOUR_DESC;
        descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMaxProjectHour(VALID_MAX_PROJECT_HOUR).build();
        expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Semester targetIndex = INDEX_FIRST_SEMESTER;
        String userInput = targetIndex.name() + MIN_CAP_DESC
                + MAX_LECTURE_HOUR_DESC + MIN_LECTURE_HOUR_DESC
                + MIN_LAB_HOUR_DESC + MAX_LAB_HOUR_DESC + MAX_LECTURE_HOUR_DESC
                + MIN_LECTURE_HOUR_DESC + MIN_LAB_HOUR_DESC + MIN_CAP_DESC
                + MAX_TUTORIAL_HOUR_DESC + MIN_TUTORIAL_HOUR_DESC;

        SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinCap(VALID_MIN_CAP)
                .withMinLectureHour(VALID_MIN_LECTURE_HOUR)
                .withMaxLectureHour(VALID_MAX_LECTURE_HOUR)
                .withMinTutorialHour(VALID_MIN_TUTORIAL_HOUR)
                .withMaxTutorialHour(VALID_MAX_TUTORIAL_HOUR)
                .withMinLabHour(VALID_MIN_LAB_HOUR)
                .withMaxLabHour(VALID_MAX_LAB_HOUR)
                .build();
        SetSemesterLimitCommand expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Semester targetIndex = INDEX_FIRST_SEMESTER;
        String userInput = targetIndex.name() + INVALID_MIN_CAP_DESC + MIN_CAP_DESC;
        SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinCap(VALID_MIN_CAP)
                .build();
        SetSemesterLimitCommand expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.name() + MIN_TUTORIAL_HOUR_DESC
                + INVALID_MIN_CAP_DESC + MAX_TUTORIAL_HOUR_DESC
                + MIN_CAP_DESC;
        descriptor = new EditSemesterLimitDescriptorBuilder()
                .withMinCap(VALID_MIN_CAP)
                .withMinTutorialHour(VALID_MIN_TUTORIAL_HOUR)
                .withMaxTutorialHour(VALID_MAX_TUTORIAL_HOUR).build();
        expectedCommand = new SetSemesterLimitCommand(
                Index.fromZeroBased(targetIndex.getIndex()), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
