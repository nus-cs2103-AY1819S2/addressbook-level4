package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOBNAME;
import static seedu.address.logic.commands.CommandTestUtil.JOBNAME_SE;
import static seedu.address.logic.commands.CommandTestUtil.JOBNAME_TEACHER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalJobs.TEACHER;

import org.junit.Test;

import seedu.address.logic.commands.CreateJobCommand;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.testutil.JobBuilder;

public class CreateJobCommandParserTest {
    private CreateJobCommandParser parser = new CreateJobCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Job expectedJob = new JobBuilder(TEACHER).withName(VALID_JOB_NAME_TEACHER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + JOBNAME_TEACHER,
                new CreateJobCommand(expectedJob));

        // multiple names - last name accepted
        assertParseSuccess(parser, JOBNAME_SE + JOBNAME_TEACHER,
                new CreateJobCommand(expectedJob));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJobCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_JOB_NAME_TEACHER,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_JOBNAME, JobName.MESSAGE_CONSTRAINTS);
    }
}
