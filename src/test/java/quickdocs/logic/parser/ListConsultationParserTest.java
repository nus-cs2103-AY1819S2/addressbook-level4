package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Assert;
import org.junit.Test;

import quickdocs.logic.commands.ListConsultationCommand;
import quickdocs.logic.parser.exceptions.ParseException;


public class ListConsultationParserTest {

    private ListConsultationCommandParser parser = new ListConsultationCommandParser();

    @Test
    public void listConsultation_invalidIndex_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.NO_LIST_ARGUMENTS);

        userInput = " ";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.NO_LIST_ARGUMENTS);

        userInput = " a";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.INVALID_INDEX);

        userInput = " @";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.INVALID_INDEX);

        userInput = "     ";
        assertParseFailure(parser, userInput, ListConsultationCommandParser.NO_LIST_ARGUMENTS);
    }

    @Test
    public void listConsultation_overflowIndex_throwsNumberFormatException() {
        String userInput = "123456789101112";
        quickdocs.testutil.Assert.assertThrows(NumberFormatException.class, ()-> parser.parse(userInput));
    }

    @Test
    public void listConsultation_validIndex_success() {
        String userInput = "1";
        assertParseSuccess(parser, userInput, new ListConsultationCommand(1));
    }

    @Test
    public void listConsultation_validNric_success() {
        String userInput = " r/S1234567A";
        assertParseSuccess(parser, userInput, new ListConsultationCommand("S1234567A"));
    }

    @Test
    public void equals() {
        String userInput = " r/S1234567A";
        try {
            // nric check
            ListConsultationCommand command1 = parser.parse(userInput);
            ListConsultationCommand command2 = new ListConsultationCommand("S1234567A");

            Assert.assertTrue(command1.equals(command2));

            ListConsultationCommand command3 = new ListConsultationCommand("S1234567B");
            Assert.assertFalse(command1.equals(command3));

            // index equals check
            ListConsultationCommand command4 = new ListConsultationCommand(3);
            ListConsultationCommand command5 = new ListConsultationCommand(3);
            ListConsultationCommand command6 = new ListConsultationCommand(4);

            Assert.assertTrue(command4.equals(command5));
            Assert.assertFalse(command4.equals(command6));

            // own referencing and other object type check
            Assert.assertTrue(command4.equals(command4));
            Assert.assertFalse(command4.equals("abc"));

        } catch (ParseException pe) {
            Assert.fail();
        }
    }

}
