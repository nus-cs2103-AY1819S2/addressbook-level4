package quickdocs.logic.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import quickdocs.logic.commands.AddPatientCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

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
     * Parse the arguments entered by users to create an AddPatientCommand for the
     * logic manager to execute
     *
     * @param args the arguments to create the patient record: name, nric and so on
     * @return an AddPatientCommand with the patient record to be added
     * @throws ParseException when insufficient arguments are entered or any of the arguments are invalid
     */
    @Override
    public AddPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_DOB, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_CONTACT, PREFIX_GENDER, PREFIX_TAG);

        boolean arePrefixesPresent = arePrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_NRIC, PREFIX_DOB, PREFIX_ADDRESS, PREFIX_EMAIL,
                PREFIX_CONTACT, PREFIX_GENDER);
        boolean isPreambleMissing = argMultimap.getPreamble().isEmpty();

        if (!arePrefixesPresent || !isPreambleMissing) {
            throw new ParseException(INVALID_ADD_ARGUMENTS);
        }

        Patient patient;

        try {

            Name name = new Name(argMultimap.getValue(PREFIX_NAME).get().trim());
            Nric nric = new Nric(argMultimap.getValue(PREFIX_NRIC).get().trim());
            Email email = new Email(argMultimap.getValue(PREFIX_EMAIL).get().trim());
            Address address = new Address(argMultimap.getValue(PREFIX_ADDRESS).get().trim());
            Contact contact = new Contact(argMultimap.getValue(PREFIX_CONTACT).get().trim());
            Gender gender = new Gender(argMultimap.getValue(PREFIX_GENDER).get().trim());
            Dob dob = new Dob(argMultimap.getValue(PREFIX_DOB).get().trim());
            ArrayList<Tag> tagList = parseTags(argMultimap.getAllValues(PREFIX_TAG));
            patient = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        } catch (IllegalArgumentException Iae) {
            throw new ParseException(Iae.getMessage());
        }

        return new AddPatientCommand(patient);
    }

    /**
     * Returns true if the necessary arguments to create the patient records
     * indicated by their prefixes, are supplied
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap arguments that are parsed in from the user input
     * @param prefixes         the prefixes used to separate the different arguments of a patient record
     * @return true when all the required arguments are present
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
