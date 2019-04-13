package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public interface ViewStateParser {
    Command parse(String commandWord, String arguments) throws ParseException;
}
