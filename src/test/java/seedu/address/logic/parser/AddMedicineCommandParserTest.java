package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;

import org.junit.Test;

import seedu.address.logic.commands.AddMedicineCommand;

public class AddMedicineCommandParserTest {

    private AddMedicineCommandParser parser = new AddMedicineCommandParser();

    @Test
    public void parse_validArgs_returnsAddMedicineCommand() {
        assertParseSuccess(parser, "root\\test\\test2 panaddol 30 40",
                new AddMedicineCommand(
                        new String[] {"root", "test", "test2"}, "panaddol", 40, new BigDecimal(30)));
    }

    @Test
    public void parse_validArgsWithoutQuantity_returnsAddMedicineCommand() {
        assertParseSuccess(parser, "root\\test\\test2 panaddol 20.3",
                new AddMedicineCommand(
                        new String[] {"root", "test", "test2"}, "panaddol", BigDecimal.valueOf(20.3)
                ));
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
