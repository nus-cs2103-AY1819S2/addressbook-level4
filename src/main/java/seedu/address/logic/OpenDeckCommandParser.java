package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class OpenDeckCommandParser {

    private DecksView viewState;

    OpenDeckCommandParser(DecksView viewState) {
        this.viewState = viewState;
    }

    public OpenDeckCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new OpenDeckCommand(index, (DecksView)viewState);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenDeckCommand.MESSAGE_USAGE), pe);
        }
    }
}
