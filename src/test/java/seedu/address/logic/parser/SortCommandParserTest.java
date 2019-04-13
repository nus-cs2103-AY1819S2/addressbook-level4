package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private static final String VALID_INPUT_PROPERTY_EXPIRY = " " + PREFIX_PROPERTY + SortProperty.EXPIRY;
    private static final String VALID_INPUT_PROPERTY_BATCHNUMBER = " " + PREFIX_PROPERTY + SortProperty.BATCHNUMBER;
    private static final String VALID_INPUT_DIRECTION_DESCENDING = " " + PREFIX_DIRECTION + SortDirection.DESCENDING;
    private static final String VALID_INPUT_DIRECTION_ASCENDING = " " + PREFIX_DIRECTION + SortDirection.ASCENDING;
    private static final String INVALID_INPUT_PROPERTY = " " + PREFIX_PROPERTY + "expir";
    private static final String INVALID_INPUT_DIRECTION = " " + PREFIX_DIRECTION + "down";

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no sortProperty
        assertParseFailure(parser, VALID_INPUT_DIRECTION_DESCENDING, MESSAGE_INVALID_FORMAT);

        // no sortDirection
        assertParseFailure(parser, VALID_INPUT_PROPERTY_EXPIRY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Any preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_INPUT_PROPERTY_EXPIRY + VALID_INPUT_DIRECTION_DESCENDING,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid sortProperty
        assertParseFailure(parser, INVALID_INPUT_PROPERTY + VALID_INPUT_DIRECTION_DESCENDING,
                SortProperty.MESSAGE_CONSTRAINTS);

        // invalid sortDirection
        assertParseFailure(parser, VALID_INPUT_PROPERTY_EXPIRY + INVALID_INPUT_DIRECTION,
                SortDirection.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_INPUT_PROPERTY_EXPIRY + VALID_INPUT_DIRECTION_DESCENDING;

        SortCommand expectedCommand = new SortCommand(new InformationPanelSettings(SortProperty.EXPIRY,
                SortDirection.DESCENDING));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = VALID_INPUT_PROPERTY_EXPIRY + VALID_INPUT_DIRECTION_DESCENDING
                + VALID_INPUT_DIRECTION_ASCENDING;

        SortCommand expectedCommand = new SortCommand(new InformationPanelSettings(SortProperty.EXPIRY,
                SortDirection.ASCENDING));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_INPUT_PROPERTY + VALID_INPUT_PROPERTY_EXPIRY + VALID_INPUT_DIRECTION_DESCENDING;

        SortCommand expectedCommand = new SortCommand(new InformationPanelSettings(SortProperty.EXPIRY,
                SortDirection.DESCENDING));

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = INVALID_INPUT_PROPERTY + VALID_INPUT_PROPERTY_EXPIRY + VALID_INPUT_DIRECTION_DESCENDING
                + VALID_INPUT_PROPERTY_BATCHNUMBER;

        expectedCommand = new SortCommand(new InformationPanelSettings(SortProperty.BATCHNUMBER,
                SortDirection.DESCENDING));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
