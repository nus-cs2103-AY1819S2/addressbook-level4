package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new MergeCommand object
 */
public class MergeCommandParser implements Parser<MergeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MergeCommand parse(String args) throws ParseException {
        try {
            String[] parseArgs = args.trim().split("\\s+");
            Index[] indices = new Index[parseArgs.length];
            for (int i = 0; i < parseArgs.length; i++){
                indices[i] = ParserUtil.parseIndex(parseArgs[i]);
            }
            return new MergeCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE), pe);
        }
    }

}
