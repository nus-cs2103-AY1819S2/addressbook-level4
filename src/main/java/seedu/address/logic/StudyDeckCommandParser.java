package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DecksView;

/**
 * Parser creates a StudyDeckCommand Object.
 */
public class StudyDeckCommandParser implements Parser<StudyDeckCommand> {

    private DecksView decksView;

    public StudyDeckCommandParser(DecksView decksView) {
        this.decksView = decksView;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the StudyDeckCommand
     * and returns an StudyDeckCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StudyDeckCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new StudyDeckCommand(decksView, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudyDeckCommand.MESSAGE_USAGE), pe);
        }
    }
}
