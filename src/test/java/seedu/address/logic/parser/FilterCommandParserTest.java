package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GPA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EMAIL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_GPA_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME_REVERSE;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;

public class FilterCommandParserTest {

    public static final String AND_TYPE = "and ";
    public static final String OR_TYPE = "or ";

    public static final String NON_EXISTING_PREFIX = "h<";
    public static final String NON_EXISTING_PREFIX_FILTER_REVERSE = ">h";
    public static final String VALID_NAME1 = PREFIX_FILTER_NAME + VALID_NAME_AMY + PREFIX_FILTER_NAME_REVERSE;
    public static final String MISSING_BEGIN_PREFIX_FILTER_EMAIL = VALID_EMAIL_AMY + PREFIX_FILTER_EMAIL_REVERSE;
    public static final String MISSING_END_PREFIX_FILTER_ADDRESS = PREFIX_FILTER_ADDRESS + VALID_ADDRESS_AMY;
    public static final String MISSING_END_PREFIX_FILTER_EDUCATION = PREFIX_FILTER_EDUCATION + VALID_GPA_AMY;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no process type specified
        assertParseFailure(parser, VALID_NAME1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, AND_TYPE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, OR_TYPE, MESSAGE_INVALID_FORMAT);

        // no type and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPrefixPair_failure() {

        // only beginning prefix is used
        assertParseFailure(parser, AND_TYPE + MISSING_END_PREFIX_FILTER_ADDRESS, MESSAGE_INVALID_FORMAT);

        // only ending prefix is used
        assertParseFailure(parser, OR_TYPE + MISSING_BEGIN_PREFIX_FILTER_EMAIL, MESSAGE_INVALID_FORMAT);

        // false beginning and ending prefix couples are used
        assertParseFailure(parser, AND_TYPE + MISSING_END_PREFIX_FILTER_EDUCATION + PREFIX_FILTER_GPA_REVERSE,
                MESSAGE_INVALID_FORMAT);

        // non-existing prefix is used
        assertParseFailure(parser, OR_TYPE + NON_EXISTING_PREFIX + "someText"
                + NON_EXISTING_PREFIX_FILTER_REVERSE, MESSAGE_INVALID_FORMAT);
    }

    // TODO: More test cases will be added
}
