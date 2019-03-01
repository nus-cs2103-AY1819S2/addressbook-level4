package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ListPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListPatientCommand()));
        } catch (ParseException pe) {
            Assert.fail();
        }
    }

    @Test
    public void constructionByIndex() {
        String userInput = "1";
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListPatientCommand(1)));
        } catch (ParseException pe) {
            Assert.fail();
        }

        //invalid index
        userInput = "a";
        assertParseFailure(parser, userInput, "Index should be numeric");
    }

    @Test
    public void constructionByName() {
        String userInput = " n/Be";
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListPatientCommand("Be", true)));
        } catch (ParseException pe) {
            Assert.fail();
        }
    }

    @Test
    public void constructionByNric() {
        String userInput = " r/S92";
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListPatientCommand("S92", false)));
        } catch (ParseException pe) {
            Assert.fail();
        }
    }

    @Test
    public void constructionByTag() {
        String userInput = " t/Diabetes";
        Tag tag = new Tag("Diabetes");
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListPatientCommand(tag)));
        } catch (ParseException pe) {
            Assert.fail();
        }
    }

}
