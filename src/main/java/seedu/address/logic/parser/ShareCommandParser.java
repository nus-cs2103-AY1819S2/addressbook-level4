package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;

import java.io.File;

import seedu.address.logic.commands.ShareCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser implements Parser<ShareCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShareCommand parse(String args) throws ParseException {
        String path = args.trim();

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ParseException(String.format(MESSAGE_INVALID_PATH, ShareCommand.MESSAGE_USAGE));
        }

        return new ShareCommand(path);
    }

}
