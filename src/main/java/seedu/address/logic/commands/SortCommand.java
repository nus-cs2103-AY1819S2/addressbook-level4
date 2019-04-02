package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;

import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts the batch table according to the entered property and direction.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the batch table according to the entered "
            + "property and direction."
            + "Parameters: "
            + PREFIX_PROPERTY + "PROPERTY "
            + PREFIX_DIRECTION + "DIRECTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY + "Paracetamol "
            + PREFIX_DIRECTION + "Novartis ";

    public static final String MESSAGE_SUCCESS = "Batch table sorted with %s.";

    private final SortProperty sortProperty;
    private final SortDirection sortDirection;

    /**
     * Creates an AddCommand to add the specified {@code Medicine}
     */
    public SortCommand(SortProperty sortProperty, SortDirection sortDirection) {
        requireAllNonNull(sortProperty, sortDirection);

        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        InformationPanelSettings informationPanelSettings = new InformationPanelSettings(sortProperty, sortDirection);
        model.setInformationPanelSettings(informationPanelSettings);
        return new CommandResult(String.format(MESSAGE_SUCCESS, informationPanelSettings));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortProperty.equals(((SortCommand) other).sortProperty)
                && sortDirection.equals(((SortCommand) other).sortDirection));
    }
}

