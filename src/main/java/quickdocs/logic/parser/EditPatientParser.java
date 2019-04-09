package quickdocs.logic.parser;

import java.util.ArrayList;
import java.util.Collection;

import quickdocs.logic.commands.EditPatientCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.PatientEditedFields;
import quickdocs.model.tag.Tag;

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

        Nric nric = new Nric(argMultimap.getPreamble());

        PatientEditedFields editedFields = createEditedFields(argMultimap);

        if (editedFields.checkEmpty()) {
            throw new ParseException(NO_EDIT_PARAMETERS);
        }

        return new EditPatientCommand(nric, editedFields);
    }


    /**
     * Create a PatientEditedFields object, using the arguments supplied in by the user.
     * The PatientEditedFields will only contain the values that are to be changed on the
     * original patient object
     *
     * @param argMultimap all the arguments supplied in by the user during the edit patient parsing
     * @return A PatientEditedFields of the details to be edited on the current patient object
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
