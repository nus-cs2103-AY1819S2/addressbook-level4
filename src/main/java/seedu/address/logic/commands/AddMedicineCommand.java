package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;

/**
 * An command to add Medicine to the path specified. If no Medicine with same name yet exists, new medicine is added.
 * If medicine with same name exists and quantity is not specified, add the existing medicine to desire directory.
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

    public static final String MESSAGE_SUCCESS_NEW_MED = "New Medicine added: %1$s with quantity at %2$d\n";
    public static final String MESSAGE_SUCCESS_EXISTING_MED = "Existing %1$s, added to %2$s\n";

    private final String name;
    private final int quantity;
    private final String[] path;
    private boolean possibleExisting;

    public AddMedicineCommand(String[] path, String medicineName) {
        this(path, medicineName, 0, true);
    }

    public AddMedicineCommand(String[] path, String medicineName, int quantity) {
        this(path, medicineName, quantity, false);
    }

    public AddMedicineCommand(String[] path, String medicineName, int quantity, boolean possibleExisting) {
        this.name = medicineName;
        this.quantity = quantity;
        this.path = path;
        this.possibleExisting = possibleExisting;
    }

    /**
     * If the command suggests there is a possiblity that medicine with same name already exists,
     * check whether it is true.
     * If medicine with same name already exists, add the medicine to desired directory
     * Else, create new medicine to be place under the desired directory
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return A commandresult showing what is done
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            Optional<Directory> findDirectory = model.findDirectory(path);
            if (!findDirectory.isPresent()) {
                throw new CommandException("No Directory found at given path");
            }
            Optional<Medicine> findMedicine = model.findMedicine(name);
            if (possibleExisting) {
                if (findMedicine.isPresent()) {
                    findDirectory.get().addMedicine(findMedicine.get());
                    model.commitAddressBook();
                    return new CommandResult(String.format(MESSAGE_SUCCESS_EXISTING_MED,
                            findMedicine.toString(), fromPathToString(path)));
                }
            }
            model.addMedicine(name, quantity, path);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS_NEW_MED, name, quantity));
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    /**
     * Convert path to a String to display to User
     * @param path the path field
     * @return A string representation of the path
     */
    private String fromPathToString(String[] path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.length - 1; i++) {
            sb.append(path[i] + "\\");
        }
        sb.append(path[path.length - 1]);
        return sb.toString();
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
