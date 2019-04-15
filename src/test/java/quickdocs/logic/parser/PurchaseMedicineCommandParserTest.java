package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;

import org.junit.Test;

import quickdocs.logic.commands.PurchaseMedicineCommand;
import quickdocs.logic.commands.PurchaseMedicineViaPathCommand;
import quickdocs.logic.commands.PurchaseMedicineWoPathCommand;

public class PurchaseMedicineCommandParserTest {

    private Parser<PurchaseMedicineCommand> parser = new PurchaseMedicineCommandParser();

    @Test
    public void validViaPathArgument_returnsPurchaseMedicineViaPathCommand() {
        assertParseSuccess(parser,
                "root\\test1\\test2\\med 40 50.0",
                new PurchaseMedicineViaPathCommand(
                        new String[] {"root", "test1", "test2", "med"}, 40, BigDecimal.valueOf(50.0)));
    }

    @Test
    public void validWoPathArgument_returnsPurchaseMedicineWoPathCommand() {
        assertParseSuccess(parser,
                "med 40 500",
                new PurchaseMedicineWoPathCommand("med", 40, BigDecimal.valueOf(500)));
    }

    @Test
    public void invalidArgument_throwsParseException() {
        assertParseFailure(parser, "med 40",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void invalidArgument2_throwsParseException() {
        assertParseFailure(parser, "root\\med 40 med",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
    }
}
