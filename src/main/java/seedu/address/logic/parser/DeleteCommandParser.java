package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteHealthWorkerCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePersonCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String INVALID_COMMAND_USAGE = EditCommand.MESSAGE_USAGE + "\n"
            + DeleteHealthWorkerCommand.MESSAGE_USAGE + DeleteRequestCommand.MESSAGE_USAGE;
    /**
     * Parses the given {@code String} of arguments in the context of the DeletePersonCommand
     * and returns an DeletePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        Index index;

        CommandMode commandMode = ArgumentTokenizer.checkMode(args);

        if (commandMode == CommandMode.HEALTH_WORKER) {
            try {
                index = ParserUtil.parseIndex(ArgumentTokenizer.trimMode(args));
                return new DeleteHealthWorkerCommand(index);
            } catch (ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteHealthWorkerCommand.MESSAGE_USAGE));
            }

        } else if (commandMode == CommandMode.REQUEST) {
            try {
                index = ParserUtil.parseIndex(ArgumentTokenizer.trimMode(args));
                return new DeleteRequestCommand(index);
            } catch (ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteRequestCommand.MESSAGE_USAGE));
            }
        }

        throw new ParseException(INVALID_COMMAND_USAGE);
    }
}
