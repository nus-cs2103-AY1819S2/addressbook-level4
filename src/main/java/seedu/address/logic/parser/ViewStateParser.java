package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * State-dependent parser. Should only handle commands that require information held by the state.
 * So global commands like `help` which are available regardless of the state should throw so that
 * they can be handled by TopDeckParser.
 */
public interface ViewStateParser {
    Command parse(String commandWord, String arguments) throws ParseException;
}
