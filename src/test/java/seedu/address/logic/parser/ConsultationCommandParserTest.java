package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConsultationCommand;


public class ConsultationCommandParserTest {

    private ConsultationCommandParser parser = new ConsultationCommandParser();

    @Test
    public void invalidParses() {
        String args = " ";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);

        args = "";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);
    }

    @Test
    public void validParse() {
        String args = " r/S9123456A";
        assertParseSuccess(parser, args, new ConsultationCommand("S9123456A"));
    }
}
