package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.commands.ListConsultationCommand;
import seedu.address.logic.parser.exceptions.ParseException;


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

            // get NRIC was short circuited by index checking, so NRIC test will be separated out
            ListConsultationCommand command1 = parser.parse(userInput);
            ListConsultationCommand command2 = new ListConsultationCommand("S1234567A");
            Assert.assertTrue(command1.getNric().equals(command2.getNric()));

            ListConsultationCommand command3 = new ListConsultationCommand("S1234567B");
            Assert.assertFalse(command1.getNric().equals(command3.getNric()));

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
