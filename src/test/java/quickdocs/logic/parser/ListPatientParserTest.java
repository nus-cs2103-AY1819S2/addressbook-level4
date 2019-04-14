package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickdocs.testutil.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.commands.ListPatientCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.tag.Tag;

public class ListPatientParserTest {

    private ListPatientParser parser;

    @Before
    public void init() {
        parser = new ListPatientParser();
    }

    @Test
    public void parseListPatient_noArgs_success() {
        String userInput = " ";
        assertParseSuccess(parser, userInput, new ListPatientCommand());
    }

    @Test
    public void parseListPatient_index_success() {
        String userInput = "1";
        assertParseSuccess(parser, userInput, new ListPatientCommand(1));
    }

    @Test
    public void parseListPatient_invalidIndex_failure() {
        //invalid index
        String userInput = "a";
        assertParseFailure(parser, userInput, ListPatientParser.INDEX_NUMERIC);
    }

    @Test
    public void parseListPatient_indexOverflow_throwParseException() {
        String userInput = "2147483649";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseListPatient_negativeIndex_throwParseException() {
        String userInput2 = "-2147483649";
        assertThrows(ParseException.class, () -> parser.parse(userInput2));
    }

    @Test
    public void parseListPatient_indexLongOverflow_throwNumberFormatException() {
        // QuickDocs detect integer overflow using a long variable
        // if the value goes beyond long's max value, which is 2^63
        // exception throwing will be delegated to Java's NumberFormatException
        String userInput3 = "92233720368547758011";
        assertThrows(NumberFormatException.class, () -> parser.parse(userInput3));
    }

    // even if the name does not seemed to follow the constraints of name, we still allow it
    // to be parsed in to allow pattern checking when searching for records
    // same applies for nric
    @Test
    public void parseListPatient_listByName_success() {
        String userInput = " n/Be";
        assertParseSuccess(parser, userInput, new ListPatientCommand("Be", true));
    }

    @Test
    public void parseListPatient_listByNric_success() {
        String userInput = " r/S92";
        assertParseSuccess(parser, userInput, new ListPatientCommand("S92", false));
    }

    @Test
    public void parseListPatient_listByTag_success() {
        String userInput = " t/Diabetes";
        Tag tag = new Tag("Diabetes");
        assertParseSuccess(parser, userInput, new ListPatientCommand(tag));
    }

    @Test
    public void parseListPatient_invalidTag_failure() {
        String userInput = " t/D1@b3t35";
        assertThrows(IllegalArgumentException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseListPatient_multiArgs_success() {
        // QuickDocs only allow 1 parameter listing as of v1.4
        // only first parameter will be used to filter search result

        String userInput = " n/abc r/S1234567A t/Diabetes";

        ListPatientCommand lpc = new ListPatientCommand();

        try {
            lpc = parser.parse(userInput);
        } catch (ParseException e) {
            Assert.assertEquals(lpc.getConstructedBy(), 1);
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
