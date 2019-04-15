package seedu.pdf.logic.parser;

import static org.junit.Assert.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_INVALID;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_INVALID_FRIEND;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_VALID_LECTURE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.logic.parser.exceptions.ParseException;

public class TagCommandParserTest {

    private static final String VALID_TAG_ADD = "1 " + CliSyntax.PREFIX_TAG_ADD + " "
            + CliSyntax.PREFIX_TAG_NAME.toString() + TAG_VALID_LECTURE;

    private static final String VALID_TAG_REMOVE = "1 " + CliSyntax.PREFIX_TAG_REMOVE + " "
            + CliSyntax.PREFIX_TAG_NAME.toString() + TAG_VALID_LECTURE;

    private static final String INVALID_TAG = " 1 " + CliSyntax.PREFIX_TAG_ADD + " "
            + CliSyntax.PREFIX_TAG_NAME.toString() + TAG_INVALID_FRIEND;

    private static final String INVALID_TAG_PREAMBLE = " " + CliSyntax.PREFIX_TAG_ADD + " "
            + CliSyntax.PREFIX_TAG_NAME.toString() + TAG_VALID_LECTURE;

    private static final String INVALID_TAG_TYPE = " " + "-e" + " "
            + CliSyntax.PREFIX_TAG_NAME.toString() + TAG_VALID_LECTURE;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validTagAdd_success() throws ParseException {
        try {
            parser.parse(VALID_TAG_ADD);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parse_validTagRemove_success() throws ParseException {
        try {
            parser.parse(VALID_TAG_REMOVE);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void parse_invalidTagCliDescription_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(TAG_DESC_INVALID);
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_TAG_PREAMBLE);
    }

    @Test
    public void parse_invalidTag_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_TAG);
    }

    @Test
    public void parse_invalidTagType_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse(INVALID_TAG_TYPE);
    }
}
