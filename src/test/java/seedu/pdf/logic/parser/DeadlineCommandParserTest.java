package seedu.pdf.logic.parser;

import static junit.framework.TestCase.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.DATE_USER_INPUT_INVALID;
import static seedu.pdf.logic.commands.CommandTestUtil.DATE_USER_INPUT_VALID;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_DONE;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_REMOVE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.logic.parser.exceptions.ParseException;

public class DeadlineCommandParserTest {
    private static final String VALID_DEADLINE_ADD = "1 " + PREFIX_DEADLINE_NEW + DATE_USER_INPUT_VALID;
    private static final String VALID_DEADLINE_DONE = "1 " + PREFIX_DEADLINE_DONE;
    private static final String VALID_DEADLINE_REMOVE = "1 " + PREFIX_DEADLINE_REMOVE;
    private static final String INVALID_DEADLINE_MULTIPLE_ACTIONS = VALID_DEADLINE_ADD + " " + PREFIX_DEADLINE_DONE;
    private static final String INVALID_DEADLINE_PREAMBLE = " " + PREFIX_DEADLINE_DONE;
    private static final String INVALID_DEADLINE_DATE = "1 " + PREFIX_DEADLINE_NEW + DATE_USER_INPUT_INVALID;
    private static final String INVALID_DEADLINE_ACTION = "1 " + "d";
    private static final String NO_DEADLINE_ACTION = "1 ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DeadlineCommandParser parser = new DeadlineCommandParser();

    @Test
    public void parse_validDeadlineNew_success() {
        try {
            parser.parse(VALID_DEADLINE_ADD);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validDeadlineDone_success() {
        try {
            parser.parse(VALID_DEADLINE_DONE);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validDeadlineRemove_success() {
        try {
            parser.parse(VALID_DEADLINE_REMOVE);
        } catch (ParseException e) {
            fail();
        }
    }
    @Test
    public void parse_invalidDeadlineCliDescription_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_DEADLINE_MULTIPLE_ACTIONS);
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_DEADLINE_PREAMBLE);
    }

    @Test
    public void parse_invalidDate_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_DEADLINE_DATE);
    }

    @Test
    public void parse_invalidDeadlineAction_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_DEADLINE_ACTION);
    }

    @Test
    public void parse_noDeadlineAction_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(NO_DEADLINE_ACTION);
    }

}
