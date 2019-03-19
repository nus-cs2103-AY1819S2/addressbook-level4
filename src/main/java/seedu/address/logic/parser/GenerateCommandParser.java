package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GenerateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GenerateCommand object
 */
public class GenerateCommandParser implements Parser<GenerateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenerateCommand
     * and returns an GenerateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GenerateCommand parse(String keyword) throws ParseException {
        requireNonNull(keyword);
        String trimmedKeyword = keyword.trim();
        if (!isValidKeyword(trimmedKeyword)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenerateCommand.MESSAGE_USAGE));
        }

        // get the readOnlyAddressBook
        // loop through each entry
        // have a 2D-array that stores the country code and the number of times the traveler has visited it.

        return new GenerateCommand(keyword.toLowerCase());
    }

    private boolean isValidKeyword(String keyword) {
        return keyword.equalsIgnoreCase(GenerateCommand.KEYWORD_COUNTRY)
                || keyword.equalsIgnoreCase(GenerateCommand.KEYWORD_RATING);
    }
}
