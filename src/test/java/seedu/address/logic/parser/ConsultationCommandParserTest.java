package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConsultationCommand;


public class ConsultationCommandParserTest {

    private ConsultationCommandParser parser;

    @Test
    public void invalidParses() {
        String args = " ";
        parser = new ConsultationCommandParser();
        assertParseFailure(parser, args, "Some details are left out, please retype the command");

        args = " r/S9123456A";
        assertParseSuccess(parser, args, new ConsultationCommand("S9123456A"));
    }
}
