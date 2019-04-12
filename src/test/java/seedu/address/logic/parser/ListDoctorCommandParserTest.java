package seedu.address.logic.parser;

import org.junit.Test;

import seedu.address.logic.commands.ListDoctorCommand;

public class ListDoctorCommandParserTest {

    private ListDoctorCommandParser parser = new ListDoctorCommandParser();

    @Test
    public void parse_emptyArg_listAllDoctors() {
        ListDoctorCommand command = new ListDoctorCommand();
        // assertParseSuccess(parser, ListDoctorCommand.COMMAND_WORD, command);
    }

}
