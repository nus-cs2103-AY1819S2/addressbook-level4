package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.DeletePatientCommand;
import seedu.address.model.patient.Nric;

public class DeletePatientParserTest {

    private DeletePatientParser parser;

    @Before
    public void init() {
        parser = new DeletePatientParser();
    }

    @Test
    public void invalidCommand() {
        // no prefixes
        assertParseFailure(parser, "", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        // invalid prefix
        assertParseFailure(parser, " A/S9123456A", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        // invalid Nric
        seedu.address.testutil.Assert.assertThrows(IllegalArgumentException.class, ()->parser.parse(
                " r/S11111111A"));
    }

    @Test
    public void validCommand() {
        assertParseSuccess(parser, " r/S1234567A", new DeletePatientCommand(new Nric("S1234567A")));
    }

}
