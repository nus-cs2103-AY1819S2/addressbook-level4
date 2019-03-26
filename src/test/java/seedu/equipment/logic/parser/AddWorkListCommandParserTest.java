package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.commands.CommandTestUtil.ASSIGNEE_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.ASSIGNEE_DESC_LISTB;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.DATE_DESC_LISTB;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_ASSIGNEE_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.equipment.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.equipment.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_ASSIGNEE_LISTA;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_DATE_LISTA;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.equipment.testutil.TypicalWorkLists.LISTA;

import org.junit.Test;

import seedu.equipment.logic.commands.AddWorkListCommand;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.testutil.WorkListBuilder;

public class AddWorkListCommandParserTest {
    private AddWorkListCommandParser parser = new AddWorkListCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        WorkList expectedWorkList = new WorkListBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_LISTA + ASSIGNEE_DESC_LISTA,
                new AddWorkListCommand(expectedWorkList));

        // multiple dates - last date accepted
        assertParseSuccess(parser, DATE_DESC_LISTA + DATE_DESC_LISTB + ASSIGNEE_DESC_LISTA,
                new AddWorkListCommand(expectedWorkList));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, DATE_DESC_LISTA + ASSIGNEE_DESC_LISTA + ASSIGNEE_DESC_LISTB,
                new AddWorkListCommand(expectedWorkList));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        WorkList expectedWorkList = new WorkListBuilder(LISTA).build();
        assertParseSuccess(parser, DATE_DESC_LISTA, new AddWorkListCommand(expectedWorkList));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorkListCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE_LISTA + ASSIGNEE_DESC_LISTA, expectedMessage);

        // missing assignee prefix
        assertParseFailure(parser, DATE_DESC_LISTA + VALID_ASSIGNEE_LISTA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DATE_LISTA + VALID_ASSIGNEE_LISTA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + ASSIGNEE_DESC_LISTA, Name.MESSAGE_CONSTRAINTS);

        // invalid assignee
        assertParseFailure(parser, DATE_DESC_LISTA + INVALID_ASSIGNEE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DATE_DESC + INVALID_ASSIGNEE_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DATE_DESC_LISTA + ASSIGNEE_DESC_LISTA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWorkListCommand.MESSAGE_USAGE));
    }
}
