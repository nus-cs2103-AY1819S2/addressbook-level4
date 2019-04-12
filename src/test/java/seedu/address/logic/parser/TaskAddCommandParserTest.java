package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_WITHPATIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalData.REVIEW;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


public class TaskAddCommandParserTest {

    private TaskAddCommandParser parser = new TaskAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(REVIEW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_NOPATIENT + STARTDATE_DESC_NOPATIENT
                        + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT + ENDTIME_DESC_NOPATIENT
                        + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));

        // multiple titles - last title accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_WITHPATIENT + TITLE_DESC_NOPATIENT
                + STARTDATE_DESC_NOPATIENT + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT + ENDTIME_DESC_NOPATIENT
                + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));

        //multiple startdates - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_NOPATIENT + STARTDATE_DESC_WITHPATIENT
                + STARTDATE_DESC_NOPATIENT + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));

        //enddate not given
        assertParseSuccess(parser, TITLE_DESC_NOPATIENT + STARTDATE_DESC_NOPATIENT
                + STARTTIME_DESC_NOPATIENT + ENDTIME_DESC_NOPATIENT
                + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));

        //patient index given
        assertParseSuccess(parser, TITLE_DESC_NOPATIENT + STARTDATE_DESC_NOPATIENT
                + STARTTIME_DESC_NOPATIENT + ENDTIME_DESC_NOPATIENT
                + PRIORITY_DESC_NOPATIENT + " pat/1", new TaskAddCommand(expectedTask, Index.fromOneBased(1)));

        expectedTask = new TaskBuilder(REVIEW).withStartDate(DateCustom.getToday()).build();

        //today keyword accepted for startdate
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_NOPATIENT + " sd/today"
                + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));

        expectedTask = new TaskBuilder(REVIEW).withStartDate(DateCustom.getToday())
                .withEndDate(DateCustom.getToday()).build();

        //today keyword accepted for enddate
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_NOPATIENT + " sd/today"
                + " ed/today" + STARTTIME_DESC_NOPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, new TaskAddCommand(expectedTask, null));


    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE);

        //missing title prefix
        assertParseFailure(parser, VALID_TITLE_WITHPATIENT + STARTDATE_DESC_WITHPATIENT
                + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, expectedMessage);

        //missing startdate prefix
        assertParseFailure(parser, TITLE_DESC_WITHPATIENT + VALID_STARTDATE_WITHPATIENT
                + ENDDATE_DESC_NOPATIENT + STARTTIME_DESC_NOPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, expectedMessage);

        //missing starttime prefix
        assertParseFailure(parser, TITLE_DESC_WITHPATIENT + STARTDATE_DESC_WITHPATIENT
                + ENDDATE_DESC_NOPATIENT + VALID_STARTTIME_WITHPATIENT
                + ENDTIME_DESC_NOPATIENT + PRIORITY_DESC_NOPATIENT, expectedMessage);

        //missing endtime prefix
        assertParseFailure(parser, TITLE_DESC_WITHPATIENT + STARTDATE_DESC_WITHPATIENT
                + ENDDATE_DESC_NOPATIENT + STARTDATE_DESC_WITHPATIENT
                + VALID_ENDTIME_NOPATIENT + PRIORITY_DESC_NOPATIENT, expectedMessage);
    }
}
