package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;

import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Changes the property and direction that the batch table in information panel is sorted by.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the batch table according to the entered "
            + "property and direction. "
            + "Parameters: "
            + PREFIX_PROPERTY + "PROPERTY "
            + PREFIX_DIRECTION + "DIRECTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY + "batchnumber "
            + PREFIX_DIRECTION + "descending\n"
            + "Possible properties: batchnumber, quantity, expiry\n"
            + "Possible directions: ascending, descending";

    public static final String MESSAGE_SUCCESS = "Batch table sorted with %s.";

    private final InformationPanelSettings informationPanelSettings;

    /**
     * Creates a SortCommand to change the {@code informationPanelSettings}
     */
    public SortCommand(InformationPanelSettings informationPanelSettings) {
        requireNonNull(informationPanelSettings);

        this.informationPanelSettings = informationPanelSettings;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.setInformationPanelSettings(informationPanelSettings);
        return new CommandResult(String.format(MESSAGE_SUCCESS, informationPanelSettings));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && informationPanelSettings.equals(((SortCommand) other).informationPanelSettings));
    }
}

