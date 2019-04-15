package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;

import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY, PREFIX_DIRECTION);

        if (!argMultimap.getPreamble().isEmpty() || !argMultimap.getValue(PREFIX_PROPERTY).isPresent()
                || !argMultimap.getValue(PREFIX_DIRECTION).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        SortProperty sortProperty = ParserUtil.parseSortProperty(argMultimap.getValue(PREFIX_PROPERTY).get());

        SortDirection sortDirection = ParserUtil.parseSortDirection(argMultimap.getValue(PREFIX_DIRECTION).get());

        return new SortCommand(new InformationPanelSettings(sortProperty, sortDirection));
    }
}

