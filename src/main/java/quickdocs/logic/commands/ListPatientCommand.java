package quickdocs.logic.commands;

import java.util.logging.Logger;

import quickdocs.commons.core.LogsCenter;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

/**
 * Returns either a specific patient's record or a list
 * of patients with similar names, nric or tags
 */
public class ListPatientCommand extends Command {

    public static final String COMMAND_WORD = "listpat";
    public static final String COMMAND_ALIAS = "lp";
    public static final String NO_PATIENTS = "No medical records to list.\n";
    public static final String NO_PATIENT_FOUND_NAME = "No patient by the name: %s found";
    public static final String NO_PATIENT_FOUND_NRIC = "No patient by NRIC: %s found";
    public static final String NO_PATIENT_FOUND_TAG = "No patient with tag: %s found";

    public static final String INVALID_INDEX = "Invalid index to find patient records.\n";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": List patient details using a particular index, name or nric.\n"
                    + " A list of patients with the same tags can also be displayed by entering the tag.\n"
                    + " If no parameters are entered, QuickDocs will try to list at least 50 patients\n"
                    + "Parameters: "
                    + "INDEX OR"
                    + "n/NAME OR"
                    + "r/NRIC OR"
                    + "t/TAG\n"
                    + "Examples: " + COMMAND_WORD + "\n"
                    + COMMAND_WORD + "10" + "\n"
                    + COMMAND_WORD + "r/S9214538C" + "\n"
                    + COMMAND_WORD + "n/Tan Ah Kow" + "\n"
                    + COMMAND_WORD + "t/diabetes" + "\n";

    private static final Logger logger = LogsCenter.getLogger(ListPatientCommand.class);

    // to indicate which constructor was used to create this command
    private int constructedBy;
    private int index = -1;
    private String name;
    private String nric;
    private Tag tag;

    /**
     * Indicates that the search is conducted by an index during the
     * command's execution
     *
     * @param index 1-based index to select the patient record in the PatientManager's
     *              patientList
     */
    public ListPatientCommand(int index) {
        // for user entry, index is always 1 indexed.
        // since patientmanager uses 0 indexing, index are adjusted here
        logger.info("Listing patient by index");
        this.index = index - 1;
        constructedBy = 1;
    }

    public ListPatientCommand(String search, boolean byName) {
        if (byName == true) {
            logger.info("ListPatientCommand: Listing patient by name");
            name = search;
            constructedBy = 2;
        } else {
            logger.info("ListPatientCommand: Listing patient by nric");
            nric = search;
            constructedBy = 3;
        }
    }

    public ListPatientCommand(Tag tag) {
        logger.info("ListPatientCommand: Listing patient by tag");
        this.tag = tag;
        constructedBy = 4;
    }

    public ListPatientCommand() {
        logger.info("ListPatientCommand: Listing first 50 patients");
        constructedBy = 5;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.isPatientListEmpty()) {
            throw new CommandException(NO_PATIENTS);
        }

        // searches patient records via indexing
        if (constructedBy == 1) {

            if (!model.checkValidIndex(index)) {
                throw new CommandException(INVALID_INDEX);
            }

            Patient patient = model.getPatientAtIndex(index);
            return constructResult(patient.toString());
        }

        // handles patient record search by name sequences
        if (constructedBy == 2) {
            String result = model.findPatientsByName(name);
            if (result.equals("No patient record found")) {
                throw new CommandException(String.format(NO_PATIENT_FOUND_NAME, name));
            }
            return constructResult(result);
        }

        // handles patient record search by NRIC sequences
        if (constructedBy == 3) {
            String result = model.findPatientsByNric(nric);
            if (result.equals("No patient record found")) {
                throw new CommandException(String.format(NO_PATIENT_FOUND_NRIC, nric));
            }
            return constructResult(result);
        }

        // find patient records with the given tag
        if (constructedBy == 4) {
            String result = model.findPatientsByTag(tag);
            if (result.equals("No patient record found")) {
                throw new CommandException(String.format(NO_PATIENT_FOUND_TAG, tag));
            }

            return constructResult(result);
        }

        // list as many patient records if no parameters are provided
        String result = model.listFiftyPatients();
        return constructResult(result);
    }

    /**
     * format the list of patients or single patient record to a commandresult for display
     */
    public CommandResult constructResult(String result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Listing patients:\n");
        sb.append("==============================\n");
        sb.append(result);
        return new CommandResult(sb.toString(), false, false);
    }

    public int getConstructedBy() {
        return constructedBy;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListPatientCommand
                && checkAttributes((ListPatientCommand) other));
    }

    /**
     * Check the attributes (name, nric, tag search sequences)
     * of two ListPatientCommand object and see if they are the same
     */
    public boolean checkAttributes(ListPatientCommand other) {
        switch (other.getConstructedBy()) {
        case 1:
            return index == other.getIndex();
        case 2:
            return name.equals(other.getName());
        case 3:
            return nric.equals(other.getNric());
        case 4:
            return tag.equals(other.getTag());
        default:
            return true;
        }
    }
}
