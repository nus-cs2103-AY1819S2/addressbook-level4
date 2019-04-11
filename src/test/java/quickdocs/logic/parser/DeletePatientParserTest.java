package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.commands.DeletePatientCommand;
import quickdocs.model.patient.Nric;
import quickdocs.testutil.Assert;

public class DeletePatientParserTest {

    private DeletePatientParser parser;

    @Before
    public void init() {
        parser = new DeletePatientParser();
    }

    @Test
    public void invalidCommand() {
        // no prefixes
        assertParseFailure(parser, " S11111111A", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        // invalid prefix
        assertParseFailure(parser, " A/S9123456A", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        // invalid Nric
        assertParseFailure(parser, "", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        assertParseFailure(parser, "    ", DeletePatientParser.INVALID_DELETE_ARGUMENT);

        Assert.assertThrows(IllegalArgumentException.class, ()->parser.parse(
                " r/S11111111A"));

        Assert.assertThrows(IllegalArgumentException.class, ()->parser.parse(
                " r/@@@@@@@@@@"));

        // wrong letterings
        Assert.assertThrows(IllegalArgumentException.class, ()->parser.parse(
                " r/S1234567M"));

        Assert.assertThrows(IllegalArgumentException.class, ()->parser.parse(
                " r/F1234567B"));
    }

    @Test
    public void validCommand() {
        assertParseSuccess(parser, " r/S1234567A", new DeletePatientCommand(new Nric("S1234567A")));
    }

}
