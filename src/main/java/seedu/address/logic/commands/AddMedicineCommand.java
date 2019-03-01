package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * An command to add Medicine to the path specified
 */
public class AddMedicineCommand extends Command {

    public static final String COMMAND_WORD = "addMed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a medicine to the medicine storage. "
            + "Parameters: "
            + "Directory path separated by \\ "
            + "Name of Medicine "
            + "(Optional)Amount of Medicine\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM\\herbs "
            + "Healroot "
            + "50";

    public static final String MESSAGE_SUCCESS = "New Medicine added: %1$s";

    private final String name;
    private final int quantity;
    private final Medicine toAdd;
    private final String[] path;

    public AddMedicineCommand(String[] path, String medicineName) {
        this(path, medicineName, 0);
    }

    public AddMedicineCommand(String[] path, String medicineName, int quantity) {
        this.name = medicineName;
        this.quantity = quantity;
        this.toAdd = new Medicine(medicineName, quantity);
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        //model.addMedicine(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddMedicineCommand
                && name.equals(((AddMedicineCommand) other).name)
                && quantity == ((AddMedicineCommand) other).quantity
                && Arrays.equals(path, ((AddMedicineCommand) other).path));
    }
}
