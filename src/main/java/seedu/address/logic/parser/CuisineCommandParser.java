package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.SetCuisineCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CuisineCommandParser implements Parser<SetCuisineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCuisineCommand
     * and returns a SetCuisineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCuisineCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        return null;
    }
}
