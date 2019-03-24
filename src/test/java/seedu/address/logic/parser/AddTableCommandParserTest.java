package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.AddTableCommand;
import seedu.address.model.table.TableStatus;

public class AddTableCommandParserTest {
    private AddTableCommandParser parser = new AddTableCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<TableStatus> singleTableStatusList = new ArrayList<>();
        singleTableStatusList.add(new TableStatus("0/4"));
        List<TableStatus> multipleTableStatusList = new ArrayList<>();
        multipleTableStatusList.add(new TableStatus("0/4"));
        multipleTableStatusList.add(new TableStatus("0/5"));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "4", new AddTableCommand(singleTableStatusList));

        // multiple tables
        assertParseSuccess(parser, "4 5", new AddTableCommand(multipleTableStatusList));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTableCommand.MESSAGE_USAGE);

        // missing inputs
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid number of seats
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTableCommand.MESSAGE_USAGE));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "a " + "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTableCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "4", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTableCommand.MESSAGE_USAGE));
    }
}
