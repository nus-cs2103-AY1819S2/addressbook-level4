package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;

/**
 * An Command setting alarm level for directory/medicine given by the path
 */
public class AlarmCommand extends Command {

    public static final String COMMAND_WORD = "alarm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": set the alarm level for a specific medicine / every medicine under a directory. "
            + "Parameters: "
            + "Medicine / directory path separated by \\ "
            + "Alarm level\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM 30";

    public static final String MESSAGE_SUCCESS = "Alarm level is set to %1$d for:\n%2$s\n";

    private String[] path;
    private int alarmLevel;

    public AlarmCommand(String[] path, int alarmLevel) {
        this.path = path;
        this.alarmLevel = alarmLevel;
    }

    /**
     * Set the alarm level to the quantity specifies by the user
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return A CommandResult showing the operation done
     * @throws CommandException
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Optional<Directory> directory = model.findDirectory(path);
            if (!directory.isPresent()) {
                Optional<Medicine> medicine = model.findMedicine(path);
                if (!medicine.isPresent()) {
                    throw new CommandException("No medicine/directory found at the given path");
                }
                model.setThreshold(medicine.get(), alarmLevel);
                return new CommandResult(String.format(MESSAGE_SUCCESS, alarmLevel, medicine.get().toString()));
            } else {
                model.setThreshold(directory.get(), alarmLevel);
                return new CommandResult(String.format(MESSAGE_SUCCESS, alarmLevel, directory.get().toString()));
            }
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof AlarmCommand
                && Arrays.equals(this.path, ((AlarmCommand) other).path)
                && this.alarmLevel == ((AlarmCommand) other).alarmLevel);
    }
}
