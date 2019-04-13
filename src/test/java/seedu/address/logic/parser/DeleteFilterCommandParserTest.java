package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.APPLICANT_NAME;
import static seedu.address.model.job.JobListName.EMPTY;

import org.junit.Test;

import seedu.address.logic.commands.DeleteFilterCommand;
import seedu.address.model.job.JobListName;

public class DeleteFilterCommandParserTest {

    private DeleteFilterCommandParser parser = new DeleteFilterCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteFilterCommand() {
        assertParseSuccess(parser, " " + VALID_FILTERNAME,
            new DeleteFilterCommand(EMPTY, VALID_FILTERNAME));
        assertParseSuccess(parser, APPLICANT_NAME + " " + VALID_FILTERNAME,
            new DeleteFilterCommand(APPLICANT, VALID_FILTERNAME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(JobListName.MESSAGE_CONSTRAINTS,
            DeleteFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + DeleteFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN));
    }
}
