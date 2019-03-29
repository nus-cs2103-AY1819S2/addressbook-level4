package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EncryptCommand;


public class EncryptCommandParserTest {
    private EncryptCommandParser parser = new EncryptCommandParser();

    @Test
    public void parse_missingParts_failure() {
        Index targetIndex = INDEX_SECOND_PDF;
        EncryptCommand expectedCommand = new EncryptCommand(targetIndex, "hi");
        assertParseSuccess(parser, "2 hi", expectedCommand);
        /*// no index specified
        assertParseFailure(parser, NAME_1_VALID, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + DEADLINE_DESC_READY, MESSAGE_INVALID_FORMAT);*/
    }
}
