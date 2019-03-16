package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparators.PatientComparator;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleValidArg_throwsParseException() {
        assertParseFailure(parser, " name dob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgUnspecifiedOrder_returnsSortCommand() throws ParseException {
        parse_validParameter("name", " name", false);
        parse_validParameter("phone", " phone", false);
        parse_validParameter("email", " email", false);
        parse_validParameter("address", " address", false);
        parse_validParameter("dob", " dob", false);
        parse_validParameter("nric", " nric", false);
    }

    @Test
    public void parse_validArgAscendingOrder_returnsSortCommand() throws ParseException {
        parse_validParameter("name", " name asce", false);
        parse_validParameter("phone", " phone asce", false);
        parse_validParameter("email", " email asce", false);
        parse_validParameter("address", " address asce", false);
        parse_validParameter("dob", " dob asce", false);
        parse_validParameter("nric", " nric asce", false);
    }

    @Test
    public void parse_validArgReverseOrder_returnsSortCommand() throws ParseException {
        parse_validParameter("name", " name desc", true);
        parse_validParameter("phone", " phone desc", true);
        parse_validParameter("email", " email desc", true);
        parse_validParameter("address", " address desc", true);
        parse_validParameter("dob", " dob desc", true);
        parse_validParameter("nric", " nric desc", true);
    }

    @Test
    public void parse_validArgInvalidOrder_throwsParseException() {
        assertParseFailure(parser, " name something", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " phone @#$@%g", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE));
    }

    /**
     * Wrapper function to test parsing to a sortCommand
     * @param parameter parameter type
     * @param isReverse order of sorting
     */
    public void parse_validParameter(String parameter, String userInput, boolean isReverse) throws ParseException {
        SortCommand expectedSortCommand = new SortCommand(PatientComparator.getPatientComparator(parameter),
            parameter, isReverse);
        assertParseSuccess(parser, userInput, expectedSortCommand);
    }
}
