package seedu.address.logic.parser;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;

/**
 * Parses the given {@code String} of arguments in the context of the AddDoctorCommand
 * and returns an AddDoctorCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddDoctorCommandParser implements Parser<AddDoctorCommand> {

    public AddDoctorCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_AGE, PREFIX_PHONE, PREFIX_SPECIALISATION);

        return new AddDoctorCommand();
    }

}
