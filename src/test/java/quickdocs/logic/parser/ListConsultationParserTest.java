package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Assert;
import org.junit.Test;

import quickdocs.logic.commands.ListConsultationCommand;
import quickdocs.logic.parser.exceptions.ParseException;


public class ListConsultationParserTest {

    private ListConsultationCommandParser parser = new ListConsultationCommandParser();

    @Test
    public void constructorTest() {
        String userInput = "1";
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListConsultationCommand(1)));
        } catch (ParseException pe) {
            Assert.fail();
        }

        userInput = " r/S1234567A";
        try {
            Assert.assertTrue(parser.parse(userInput).equals(new ListConsultationCommand("S1234567A")));
        } catch (ParseException pe) {
            Assert.fail();
        }

        userInput = "";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.NO_LIST_ARGUMENTS);

        userInput = " ";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.NO_LIST_ARGUMENTS);

        userInput = "a";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.INVALID_INDEX);
    }

}
