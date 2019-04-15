package seedu.address.logic.parser.doctor;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;


/**
 * Parses the given {@code String} of arguments in the context of the AddDoctorCommand
 * and returns an AddDoctorCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddDoctorCommandParser implements Parser<AddDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDoctorCommand
     * and returns an AddDoctorCommand object for execution.
     * @param args
     * @return
     * @throws ParseException
     */
    public AddDoctorCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_YEAR, PREFIX_PHONE,
                        PREFIX_SPECIALISATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_YEAR, PREFIX_PHONE,
                PREFIX_SPECIALISATION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDoctorCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Specialisation> specList = ParserUtil.parseSpecialisations(argMultimap.getAllValues(PREFIX_SPECIALISATION));

        Doctor doctor = new Doctor(name, phone, gender, year, specList);

        return new AddDoctorCommand(doctor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
