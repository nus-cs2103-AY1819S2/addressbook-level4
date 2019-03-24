package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Logic will execute this command to display either
 * a specific patient's record or a list of patients
 * with the similar names, nric or tags
 */
public class ListPatientCommand extends Command {

    public static final String COMMAND_WORD = "plist";

    // to indicate which constructor was used to create this command
    private int constructedBy;
    private int index = -1;
    private String name;
    private String nric;
    private Tag tag;

    public ListPatientCommand(int index) {
        // for user entry, index is always 1 indexed.
        // since patientmanager uses 0 indexing, 1 indexing is handled here
        this.index = index - 1;
        constructedBy = 1;
    }

    public ListPatientCommand(String search, boolean byName) {
        if (byName == true) {
            name = search;
            constructedBy = 2;
        } else {
            nric = search;
            constructedBy = 3;
        }
    }

    public ListPatientCommand(Tag tag) {
        this.tag = tag;
        constructedBy = 4;
    }

    public ListPatientCommand() {
        constructedBy = 5;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.isPatientListEmpty()) {
            throw new CommandException("No medical records to list");
        }

        if (constructedBy == 1) {

            if (!model.checkValidIndex(index)) {
                throw new CommandException("Invalid index to find patient records");
            }

            Patient patient = model.getPatientAtIndex(index);
            return constructResult(patient.toString());
        }

        if (constructedBy == 2) {
            String result = model.findPatientsByName(name);
            if (result.equals("No patient record found")) {
                throw new CommandException("Invalid index to find patient records");
            }
            return constructResult(result);
        }

        if (constructedBy == 3) {
            String result = model.findPatientsByNric(nric);
            if (result.equals("No patient record found")) {
                throw new CommandException("Invalid index to find patient records");
            }
            return constructResult(result);
        }

        if (constructedBy == 4) {
            String result = model.findPatientsByTag(tag);
            return constructResult(result);
        }

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
