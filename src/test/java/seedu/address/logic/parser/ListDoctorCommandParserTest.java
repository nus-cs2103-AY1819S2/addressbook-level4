package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDoctors.getTypicalDocX_doctor;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.logic.parser.doctor.ListDoctorCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;

public class ListDoctorCommandParserTest {

    private ListDoctorCommandParser parser = new ListDoctorCommandParser();
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }
    /*
    @Test
    public void parse_emptyArg_listAllDoctors() {
        ListDoctorCommand command = new ListDoctorCommand();
        assertParseSuccess(parser, "", command);

        // multiple white spaces
        assertParseSuccess(parser, "     ", command);
    }*/

    @Test
    public void parse_validArg_listReleventDoctors() {
        ListDoctorCommand command = new ListDoctorCommand(
                new DoctorContainsKeywordsPredicate(Arrays.asList("acupunc")));
        assertParseSuccess(parser, "acupunc", command);

        // multiple white spaces before and after the argument
        /*command = new ListDoctorCommand(
                new DoctorContainsKeywordsPredicate(Arrays.asList("acupunc     ")));
        assertParseSuccess(parser, "acupunc    ", command);*/
    }

    @Test
    public void parse_validArgs_listRelevantDoctors() {
        ListDoctorCommand command = new ListDoctorCommand(
                new DoctorContainsKeywordsPredicate(Arrays.asList("acupunc", "ong")));
        assertParseSuccess(parser, "acupunc ong", command);
    }

}
