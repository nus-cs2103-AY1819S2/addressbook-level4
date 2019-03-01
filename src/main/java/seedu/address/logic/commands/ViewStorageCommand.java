package seedu.address.logic.commands;

//import java.util.Optional;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//import seedu.address.model.medicine.Directory;
//import seedu.address.model.medicine.Medicine;

/**
 * A Command to view the detail of directory/medicine at the given path
 */
public class ViewStorageCommand extends Command {

    public static final String COMMAND_WORD = "viewMed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display the information of the directory/medicine "
            + "given by the path "
            + "Parameters: "
            + "Directory/Medicine separated by \\\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM";

    public static final String MESSAGE_SUCCESS_DIRECTORY = "Directory found at %1$s";

    public static final String MESSAGE_SUCCESS_MEDICINE = "Medicine found at %1$s";

    private String[] path;

    public ViewStorageCommand(String[] path) {
        this.path = path;
    }

    /**
     * Execute the command and return a CommandResult showing the detail
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory the history of commands to record on
     * @return The CommandResult including the details of directory/medicine
     */
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        //Optional<Directory> directory = model.findDirectory(path);
        //if (!directory.isPresent()) {
        //    Optional<Medicine> medicine = model.findMedicine(path);
        //    if (!medicine.isPresent()) {
        //        return new CommandResult("No directory/medicine found at the given path\n");
        //}
        //    return new CommandResult(String.format(MESSAGE_SUCCESS_MEDICINE,medicine.get().viewDetail() + "\n"));
        //}
        //return new CommandResult(String.format(MESSAGE_SUCCESS_DIRECTORY, directory.get().viewDetail() + "\n"));
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewStorageCommand
                && Arrays.equals(this.path, ((ViewStorageCommand) other).path));
    }
}
