package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collection;

import seedu.address.logic.commands.EditPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.PatientEditedFields;
import seedu.address.model.tag.Tag;

/**
 * parse arguments to create EditPatientCommand for logic to execute
 */
public class EditPatientParser implements Parser<EditPatientCommand> {

    // use preamble as the original nric to edit
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final String INVALID_EDIT_ARGUMENTS = "Invalid input parameters entered.\n"
            + EditPatientCommand.MESSAGE_USAGE;
    public static final String NO_EDIT_PARAMETERS = "Nothing to edit\n";

    @Override
    public EditPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_DOB, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_CONTACT, PREFIX_GENDER, PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(INVALID_EDIT_ARGUMENTS);
        }

        //if (!argMultimap.getPreamble().trim().matches("\\d+")) {
        //    throw new ParseException("Index should be numeric");
        //}

        Nric nric = new Nric(argMultimap.getPreamble());

        //int index = Integer.valueOf(argMultimap.getPreamble());
        PatientEditedFields editedFields = createEditedFields(argMultimap);

        if (editedFields.checkEmpty()) {
            throw new ParseException(NO_EDIT_PARAMETERS);
        }

        return new EditPatientCommand(nric, editedFields);
    }

    /**
     * Create a PatientEditedFields object with arguments supplied in by user
     *
     * @return patientEditedFields object consisting of the valid changes user intend to make
     */
    public static PatientEditedFields createEditedFields(ArgumentMultimap argMultimap) {

        PatientEditedFields editedFields = new PatientEditedFields();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editedFields.setName(new Name(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editedFields.setNric(new Nric(argMultimap.getValue(PREFIX_NRIC).get()));
        }

        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            editedFields.setDob(new Dob(argMultimap.getValue(PREFIX_DOB).get()));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editedFields.setAddress(new Address(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editedFields.setEmail(new Email(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_CONTACT).isPresent()) {
            editedFields.setContact(new Contact(argMultimap.getValue(PREFIX_CONTACT).get()));
        }

        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editedFields.setGender(new Gender(argMultimap.getValue(PREFIX_GENDER).get()));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {

            ArrayList<Tag> tagList = parseTags(argMultimap.getAllValues(PREFIX_TAG));
            editedFields.setTagList(tagList);
        }

        return editedFields;
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
