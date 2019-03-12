package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DescriptionCommand;
import seedu.address.model.record.Description;

public class DescriptionCommandParserTest {
    private DescriptionCommandParser parser = new DescriptionCommandParser();
    private final String nonEmptyDescription = "Some description.";

    @Test
    public void parse_indexSpecified_success() {
        // with remark
        Index targetIndex = INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION + nonEmptyDescription;
        DescriptionCommand expectedCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(nonEmptyDescription));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION;
        expectedCommand = new DescriptionCommand(INDEX_FIRST_RECORD, new Description(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_USAGE);

        // no index
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD + " " + nonEmptyDescription, expectedMessage);

        // no parameters
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD, expectedMessage);
    }
}
