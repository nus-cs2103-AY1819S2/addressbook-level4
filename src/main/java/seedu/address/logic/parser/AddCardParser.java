package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Card;

/**
 * Parses input arguments and creates a new {@link AddCardCommand} object.
 */
public class AddCardParser implements Parser<AddCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddCardCommand}
     * and returns an {@link AddCardCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CORE, PREFIX_OPTIONAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_CORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCardCommand.MESSAGE_USAGE));
        }

        ArrayList<String> coreHeaders = new ArrayList<>(argMultimap.getAllValues(PREFIX_CORE));
        ArrayList<String> optHeaders = new ArrayList<>(argMultimap.getAllValues(PREFIX_OPTIONAL));

        Card card = new Card(coreHeaders, optHeaders);
        return new AddCardCommand(card);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the mapping of prefixes to their respective arguments
     * @param prefixes the prefixes to check if present
     * @return true if prefixes are present in {@see argumentMultimap}; false otherwise
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
