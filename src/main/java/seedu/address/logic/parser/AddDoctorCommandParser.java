package seedu.address.logic.parser;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the AddDoctorCommand
 * and returns an AddDoctorCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddDoctorCommandParser implements Parser<AddDoctorCommand> {

    public AddDoctorCommand parse(String arguments) throws ParseException {
        return new AddDoctorCommand();
    }

}
