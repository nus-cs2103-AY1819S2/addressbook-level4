package quickdocs.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

import quickdocs.commons.core.LogsCenter;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.PrescriptionCommandParser;
import quickdocs.model.Model;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.medicine.Medicine;

/**
 * Creates or replaces the current prescription record of the current consultation session.
 * Prescription allows the user to administer the medicine and its quantity to the patient
 * during the consultation session.
 */
public class PrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "prescribe";
    public static final String COMMAND_ALIAS = "p";
    public static final String MEDICINE_NOT_FOUND = "Medicine: %s not found";
    public static final String INSUFFICIENT_MEDICINE = "Insufficient %s to prescribe";
    public static final String NO_ONGOING_CONSULTATION = "There is no ongoing consultation to prescribe medicine to\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds prescribed medicine and its quantities for current patient.\n"
            + "Parameters: "
            + PrescriptionCommandParser.PREFIX_MEDICINE + "MEDICINE 1 " + PrescriptionCommandParser.PREFIX_MEDICINE
            + "MEDICINE 2 " + " ... "
            + PrescriptionCommandParser.PREFIX_QUANTITY + "QUANTITY FOR MEDICINE 1 "
            + PrescriptionCommandParser.PREFIX_QUANTITY + "QUANTITY FOR MEDICINE 2 " + " ... \n"
            + "OR: " + PrescriptionCommandParser.PREFIX_MEDICINE + "MEDICINE 1 "
            + PrescriptionCommandParser.PREFIX_QUANTITY + "QUANTITY FOR MEDICINE 1" + " ... \n"
            + "Example: "
            + COMMAND_WORD + " m/Ibuprofen q/1 m/Afrin Spray q/2\n";

    private static final Logger logger = LogsCenter.getLogger(PrescriptionCommand.class);

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

        if (model.checkConsultation() == false) {
            throw new CommandException(NO_ONGOING_CONSULTATION);
        }

        ArrayList<Prescription> prescriptions = new ArrayList<>();

        /**
         * Check whether the medicine to be administered is currently present in the inventory and
         * if it is present, check whether there is sufficient medicine to be prescribed.
         *
         * Throws CommandException when medicine to be prescribed is insufficient or not
         * present.
         */
        for (int i = 0; i < medicineList.size(); i++) {

            Optional<Medicine> foundMedicine = model.findMedicine(medicineList.get(i));

            if (!foundMedicine.isPresent()) {
                throw new CommandException(String.format(MEDICINE_NOT_FOUND, medicineList.get(i)));
            }

            if (quantityList.get(i) > foundMedicine.get().getQuantity()) {
                throw new CommandException(String.format(INSUFFICIENT_MEDICINE, medicineList.get(i)));
            }

            prescriptions.add(new Prescription(foundMedicine.get(), quantityList.get(i)));
        }

        model.prescribeMedicine(prescriptions);

        logger.info("Prescription entered or replaced for current consultation");

        StringBuilder sb = new StringBuilder();
        sb.append("prescription:\n");
        sb.append("====================\n");
        for (Prescription prescription : prescriptions) {
            sb.append(prescription.toString());
        }
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof PrescriptionCommand // instanceof handles nulls
                && checkAttributes((PrescriptionCommand) other));
    }

    /**
     * Check whether the content of medicine list and quantity list is the same for two
     * prescription commands. This method is used in testing.
     *
     * @param other the other PrescriptionCommand object
     */
    public boolean checkAttributes(PrescriptionCommand other) {
        return Arrays.equals(getMedicineList().toArray(), other.getMedicineList().toArray())
                && getQuantityList().equals(other.getQuantityList());
    }
}
