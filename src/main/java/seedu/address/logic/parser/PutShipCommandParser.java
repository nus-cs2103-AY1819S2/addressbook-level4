package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIENTATION;

import java.util.stream.Stream;

import seedu.address.logic.commands.PutShipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Fleet;

/**
 * Parses input arguments and creates a new PutShipCommand object
 */
public class PutShipCommandParser implements Parser<PutShipCommand> {

    private static Fleet fleet = new Fleet();

    /**
     * Parses the given {@code String} of arguments in the context of the PutShipCommand
     * and returns an PutShipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PutShipCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COORDINATES, PREFIX_ORIENTATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_COORDINATES, PREFIX_ORIENTATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PutShipCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Coordinates coordinates = ParserUtil.parseCoordinates(argMultimap.getValue(PREFIX_COORDINATES).get());
        Orientation orientation = ParserUtil.parseOrientation(argMultimap.getValue(PREFIX_ORIENTATION).get());

        // Default 1 by 1 battleship
        Battleship battleship = ParserUtil.parseBattleship(name);

        try {
            fleet.deployBattleship(battleship);
            return new PutShipCommand(coordinates, battleship, orientation);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
