package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

/**
 * Adds an to the club manager.
 */
public class ActivityAddCommand extends Command {

    public static final String COMMAND_WORD = "activityAdd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the address book. "
            + "Parameters: "
            + PREFIX_ACTIVITYNAME + "ACTIVITY_NAME "
            + PREFIX_DATETIME + "ACTIVITY_DATETIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACTIVITYNAME + "CSS Workshop "
            + PREFIX_DATETIME + "ACTIVITY_DATETIME"; // to be updated

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This person already exists in Club Manager";


    private final Activity toAdd;

    /**
     * Creates an ActivityAddCommand to add the specified {@code Person}
     */
    public ActivityAddCommand(Activity activity) {
        requireNonNull(activity);
        toAdd = activity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.addActivity(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityAddCommand // instanceof handles nulls
                && toAdd.equals(((ActivityAddCommand) other).toAdd));
    }

}
