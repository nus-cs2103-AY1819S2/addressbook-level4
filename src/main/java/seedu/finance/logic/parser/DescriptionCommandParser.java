package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.finance.commons.core.index.Index;
import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.logic.commands.DescriptionCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.Description;

/**
 * Parse input arguments and creates a new {@code DescriptionCommand} object.
 */
public class DescriptionCommandParser implements Parser<DescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of {@code DescriptionCommand}
     * and returns a {@code DescriptionCommand} object for execution.
     * @param args User String input
     * @return {@code DescriptionCommand} object for execution.
     * @throws ParseException if user input does not follow expected format
     */
    public DescriptionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DescriptionCommand.MESSAGE_USAGE, ive));
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse("");

        return new DescriptionCommand(index, new Description(description));
    }
}
