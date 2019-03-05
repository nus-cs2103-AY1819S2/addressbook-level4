package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Prescription;

/**
 * This command allows the patient to administer the medicine and its quantity to the patient
 * during the consultation session
 */
public class PrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "prescribe";

    private ArrayList<String> medicineList;
    private ArrayList<Integer> quantityList;

    public PrescriptionCommand(ArrayList<String> medicineList, ArrayList<Integer> quantityList) {
        this.medicineList = medicineList;
        this.quantityList = quantityList;
    }

    public ArrayList<String> getMedicineList() {
        return medicineList;
    }

    public ArrayList<Integer> getQuantityList() {
        return quantityList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        // check if there is a current consultation session
        if (model.checkConsultation() == false) {
            throw new CommandException("There is no ongoing consultation to prescribe medicine to");
        }

        ArrayList<Prescription> prescriptions = new ArrayList<>();

        for (int i = 0; i < medicineList.size(); i++) {
            prescriptions.add(new Prescription(medicineList.get(i), quantityList.get(i)));
        }

        model.prescribeMedicine(prescriptions);

        // model will call the addPrescription method in consultationmanager
        StringBuilder sb = new StringBuilder();
        sb.append("prescription:\n");
        sb.append("==============================\n");
        for (Prescription prescription : prescriptions) {
            sb.append(prescription.toString());
        }
        return new CommandResult(sb.toString());
    }

    // for testing
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof PrescriptionCommand // instanceof handles nulls
            && checkAttributes((PrescriptionCommand) other));
    }

    /**
     * Check whether the content of medicine list and quantity list is the same for two
     * prescription commands
     */
    public boolean checkAttributes(PrescriptionCommand other) {
        return Arrays.equals(getMedicineList().toArray(), other.getMedicineList().toArray())
                && getQuantityList().equals(other.getQuantityList());
    }
}
