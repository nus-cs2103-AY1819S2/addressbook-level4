package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.equipment.logic.commands.FilterCommand;

import seedu.equipment.model.equipment.EquipmentContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new EquipmentContainsKeywordsPredicate(Arrays.asList("Jurong", "Bedok"),
                        Arrays.asList("jurong", "bedok"), Arrays.asList("30 November", "September"),
                        Arrays.asList("987617", "9293"), Arrays.asList("west", "east"), Arrays.asList("A00", "X10")));
        assertParseSuccess(parser,
                " n/Jurong n/Bedok a/jurong a/bedok pm/30 November pm/September p/987617 p/9293 "
                        + "t/west t/east s/A00 s/X10", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/Jurong \n n/Bedok          "
                + "a/jurong    \n a/bedok \n pm/30 November pm/September p/987617 p/9293 "
                + "                t/west t/east s/A00 s/X10", expectedFilterCommand);
    }

}
