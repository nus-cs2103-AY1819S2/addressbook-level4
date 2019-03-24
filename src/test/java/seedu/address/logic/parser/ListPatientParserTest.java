package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ListPatientCommand;
import seedu.address.model.tag.Tag;

public class ListPatientParserTest {

    private ListPatientParser parser;

    @Before
    public void init() {
        parser = new ListPatientParser();
    }

    @Test
    public void defaultConstruction() {
        String userInput = " ";
        assertParseSuccess(parser, userInput, new ListPatientCommand());
    }

    @Test
    public void constructionByIndex() {
        String userInput = "1";
        assertParseSuccess(parser, userInput, new ListPatientCommand(1));

        //invalid index
        userInput = "a";
        assertParseFailure(parser, userInput, ListPatientParser.INDEX_NUMERIC);
    }

    // even if the name does not seemed to follow the constraints of name, we still allow it
    // to be parsed in to allow pattern checking when searching for records
    // same applies for nric
    @Test
    public void constructionByName() {

        String userInput = " n/Be";
        assertParseSuccess(parser, userInput, new ListPatientCommand("Be", true));
    }

    @Test
    public void constructionByNric() {
        String userInput = " r/S92";
        assertParseSuccess(parser, userInput, new ListPatientCommand("S92", false));
    }

    @Test
    public void constructionByTag() {
        String userInput = " t/Diabetes";
        Tag tag = new Tag("Diabetes");
        assertParseSuccess(parser, userInput, new ListPatientCommand(tag));
    }

}
