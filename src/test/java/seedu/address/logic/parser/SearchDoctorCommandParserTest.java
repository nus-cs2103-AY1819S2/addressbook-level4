package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import java.util.Arrays;

import seedu.address.logic.commands.SearchDoctorCommand;
import seedu.address.logic.commands.SearchPatientCommand;
import seedu.address.model.person.DoctorNameContainsKeywordsPredicate;
import seedu.address.model.person.PatientNameContainsKeywordsPredicate;

public class SearchDoctorCommandParserTest {

    private SearchDoctorCommandParser parser = new SearchDoctorCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchDoctorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SearchDoctorCommand expectedSearchDoctorCommand =
                new SearchDoctorCommand(new DoctorNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchDoctorCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchDoctorCommand);
    }

}
