package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.FILTERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_LACK_FILTERNAME;
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
        System.out.print(" " + FILTERNAME_DESC);
        assertParseSuccess(parser, " " + FILTERNAME_DESC,
            new DeleteFilterCommand(EMPTY, VALID_FILTERNAME));
        assertParseSuccess(parser, APPLICANT_NAME + " " + FILTERNAME_DESC,
            new DeleteFilterCommand(APPLICANT, VALID_FILTERNAME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 fn/1", String.format(JobListName.MESSAGE_CONSTRAINTS,
            DeleteFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + DeleteFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN));
    }

    @Test
    public void parse_lackFilterName_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_LACK_FILTERNAME,
            DeleteFilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + DeleteFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN));
    }
}
