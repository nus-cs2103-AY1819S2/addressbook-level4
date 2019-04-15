package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.DeleteCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String DELETE_COMMAND_HARD = "hard";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index;
            String[] parseArgs = args.trim().split("\\s+");
            if (parseArgs.length == 1) {
                index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } else if (parseArgs.length == 2) {
                index = ParserUtil.parseIndex(parseArgs[0]);
                if (parseArgs[1].toLowerCase().equals(DELETE_COMMAND_HARD)) {
                    return new DeleteCommand(index, DeleteCommand.DeleteType.Hard);
                } else {
                    throw new ParseException(DeleteCommand.MESSAGE_USAGE);
                }
            } else {
                throw new ParseException(DeleteCommand.MESSAGE_USAGE);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
