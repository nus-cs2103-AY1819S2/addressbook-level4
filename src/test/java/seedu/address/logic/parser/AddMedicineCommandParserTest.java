package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.AddMedicineCommand;

public class AddMedicineCommandParserTest {

    private AddMedicineCommandParser parser = new AddMedicineCommandParser();

    @Test
    public void parse_validArgsForNewMedicine_returnsAddMedicineCommand() {
        assertParseSuccess(parser, "root\\test\\test2 panaddol p/3.03 q/40",
                new AddMedicineCommand(
                        new String[] {"root", "test", "test2"}, "panaddol",
                        Optional.of(40), Optional.of(new BigDecimal("3.03"))));
    }

    @Test
    public void parse_validArgsForExistingMedicine_returnsAddMedicineCommand() {
        assertParseSuccess(parser, "root\\test bonjela",
                new AddMedicineCommand(
                        new String[] {"root", "test"}, "bonjela", Optional.empty(), Optional.empty()));
    }

    @Test
    public void parse_invalidArgsWithRedundantInfo_throwParseException() {
        assertParseFailure(parser, "root\\test bonjela op p/2.34 q/50",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidBigDecimalFormat_throwParseException() {
        assertParseFailure(parser, "root\\test bonjela p/2.3.4 q/20",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_invalidArgsForNewMedicineWithoutQuantity_throwParseException() {
        assertParseFailure(parser, "root\\test\\test2 panaddol p/20.3",
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_argsWithoutMedicine_throwsParseException() {
        assertParseFailure(parser, "root\\test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }
}
