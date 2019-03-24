package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * parse arguments to create AddPatientCommand for logic to execute
 */
public class AddPatientParser implements Parser<AddPatientCommand> {

    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final String INVALID_ADD_ARGUMENTS = "Invalid or insufficient input parameters entered.\n"
            + AddPatientCommand.MESSAGE_USAGE;

    /**
     * Parse arguments to create an AddPatientCommand to be executed
     *
     * @throws ParseException if input is not in the right format
     */
    @Override
    public AddPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_DOB, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_CONTACT, PREFIX_GENDER, PREFIX_TAG);

        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_NRIC, PREFIX_DOB, PREFIX_ADDRESS, PREFIX_EMAIL,
                PREFIX_CONTACT, PREFIX_GENDER);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();

        if (!prefixesPresent || !preamblePresent) {
            throw new ParseException(INVALID_ADD_ARGUMENTS);
        }

        Name name = new Name(argMultimap.getValue(PREFIX_NAME).get().trim());
        Nric nric = new Nric(argMultimap.getValue(PREFIX_NRIC).get().trim());
        Email email = new Email(argMultimap.getValue(PREFIX_EMAIL).get().trim());
        Address address = new Address(argMultimap.getValue(PREFIX_ADDRESS).get().trim());
        Contact contact = new Contact(argMultimap.getValue(PREFIX_CONTACT).get().trim());
        Gender gender = new Gender(argMultimap.getValue(PREFIX_GENDER).get().trim());
        Dob dob = new Dob(argMultimap.getValue(PREFIX_DOB).get().trim());
        ArrayList<Tag> tagList = parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Patient patient;
        try {
            patient = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        } catch (IllegalArgumentException Iae) {
            throw new ParseException(Iae.getMessage());
        }

        return new AddPatientCommand(patient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * parse the list of tags entered into a list of tags to create the patient record with
     */
    public static ArrayList<Tag> parseTags(Collection<String> tags) {
        final ArrayList<Tag> tagList = new ArrayList<>();
        for (String tagName : tags) {
            tagList.add(parseTag(tagName));
        }
        return tagList;
    }

    /**
     * returns a tag created from the command argument
     */
    public static Tag parseTag(String tag) {
        String trimmedTag = tag.trim();
        return new Tag(trimmedTag);
    }
}
