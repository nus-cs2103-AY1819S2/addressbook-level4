package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import quickdocs.logic.commands.SetConsultationFeeCommand;

class SetConsultationFeeCommandParserTest {

    private SetConsultationFeeCommandParser parser = new SetConsultationFeeCommandParser();
    private BigDecimal fee = BigDecimal.valueOf(30.00);

    @Test
    void parse_validArgs_returnsSetConsultationFeeCommand() {
        assertParseSuccess(parser, "$30.00",
                new SetConsultationFeeCommand(fee));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "30.",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetConsultationFeeCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetConsultationFeeCommand.MESSAGE_USAGE));
    }
}
