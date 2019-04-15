package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.APPLICANT_NAME;
import static seedu.address.model.job.JobListName.EMPTY;

import org.junit.Test;

import seedu.address.logic.commands.ClearFilterCommand;
import seedu.address.model.job.JobListName;

public class ClearFilterCommandParserTest {

    private ClearFilterCommandParser parser = new ClearFilterCommandParser();

    @Test
    public void parse_validArgs_returnsClearFilterCommand() {
        assertParseSuccess(parser, " ",
            new ClearFilterCommand(EMPTY));
        assertParseSuccess(parser, APPLICANT_NAME + " ",
            new ClearFilterCommand(APPLICANT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(JobListName.MESSAGE_CONSTRAINTS,
            ClearFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + ClearFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN));
    }
}
