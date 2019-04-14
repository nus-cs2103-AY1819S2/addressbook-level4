package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddListToJobCommand;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class AddListToJobCommandParserTest {

    private AddListToJobCommandParser parser = new AddListToJobCommandParser();

    @Test
    public void parse_allArgs_returnsMovePersonCommand() {
        assertParseSuccess(parser, " k a jn/Teacher",
                new AddListToJobCommand(new JobName(VALID_JOB_NAME_TEACHER), JobListName.KIV, JobListName.APPLICANT));
    }

    @Test
    public void parse_oneArgJobName_returnsMovePersonCommand() {
        assertParseSuccess(parser, " k jn/Teacher",
                new AddListToJobCommand(new JobName(VALID_JOB_NAME_TEACHER), JobListName.KIV, JobListName.EMPTY));
    }

    @Test
    public void parse_oneArg_returnsMovePersonCommand() {
        assertParseSuccess(parser, " k",
                new AddListToJobCommand(null, JobListName.KIV, JobListName.EMPTY));
    }

    @Test
    public void parse_twoArgs_returnsMovePersonCommand() {
        assertParseSuccess(parser, " k a",
                new AddListToJobCommand(null, JobListName.KIV, JobListName.APPLICANT));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                AddListToJobCommand.MESSAGE_NO_DESTINATION
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddListToJobCommand.MESSAGE_USAGE));
    }

}
