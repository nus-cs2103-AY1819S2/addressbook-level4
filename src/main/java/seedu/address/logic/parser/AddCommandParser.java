package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final String NONE_PHONE = "No phone number specified";
    public static final String NONE_ADDRESS = "No address specified";
    public static final String NONE_EMAIL = "No email specified";
    public static final String NONE_NOKN = "No next of kin name specified";
    public static final String NONE_NOKR = "No next of kin relationship specified";
    public static final String NONE_NOKP = "No next of kin phone specified";
    public static final String NONE_NOKA = "No next of kin address specified";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_NRIC, PREFIX_YEAR, PREFIX_SEX, PREFIX_NOKN, PREFIX_NOKR, PREFIX_NOKP, PREFIX_NOKA);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX,
                PREFIX_YEAR) || !argMultimap.getPreamble().isEmpty()
            || (arePrefixesPresent(argMultimap, PREFIX_NOKN) && !arePrefixesPresent(argMultimap, PREFIX_NOKR))
            || ((arePrefixesPresent(argMultimap, PREFIX_NOKR) || arePrefixesPresent(argMultimap, PREFIX_NOKA)
                || arePrefixesPresent(argMultimap, PREFIX_NOKP)) && !arePrefixesPresent(argMultimap, PREFIX_NOKN))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Sex sex = ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDob(argMultimap.getValue(PREFIX_YEAR).get());

        // Optional Fields
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(NONE_PHONE));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(NONE_EMAIL));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(NONE_ADDRESS));

        //Next Of Kin Fields
        Name kinName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NOKN).orElse(NONE_NOKN));
        NextOfKinRelation kinRelation = ParserUtil.parseRelation(argMultimap.getValue(PREFIX_NOKR).orElse(NONE_NOKR));
        Phone kinPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_NOKP).orElse(NONE_NOKP));
        Address kinAddr = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_NOKA).orElse(NONE_NOKA));

        Patient patient = new Patient(name, phone, email, address, null, nric, dateOfBirth, sex);

        return new AddCommand(patient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
