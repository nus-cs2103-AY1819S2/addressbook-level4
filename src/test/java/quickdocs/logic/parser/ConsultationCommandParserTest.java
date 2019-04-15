package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import quickdocs.logic.commands.ConsultationCommand;


public class ConsultationCommandParserTest {

    private ConsultationCommandParser parser = new ConsultationCommandParser();

    @Test
    public void parseConsult_invalidParses_failure() {
        String args = " ";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);

        args = "";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);

        args = " n/S9123456A";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);

        args = " S9123456A";
        assertParseFailure(parser, args, ConsultationCommandParser.INVALID_CONSULTATION_ARGUMENTS);
    }

    @Test
    public void parseConsult_validParses_failure() {
        String args = " r/S9123456A";
        assertParseSuccess(parser, args, new ConsultationCommand("S9123456A"));
    }
}
