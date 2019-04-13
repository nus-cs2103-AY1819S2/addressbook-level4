package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.APPLICANT_NAME;

import org.junit.Test;

import seedu.address.logic.commands.DeleteFilterCommand;

public class DeleteFilterCommandParserTest {

    private DeleteFilterCommandParser parser = new DeleteFilterCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteFilterCommand() {
        assertParseSuccess(parser, APPLICANT_NAME + " " + VALID_FILTERNAME,
            new DeleteFilterCommand(APPLICANT, VALID_FILTERNAME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteFilterCommand.MESSAGE_USAGE));
    }
}
