package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.LogicManager;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.Model;

/**
 * Switch between tabs according to the index.
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_ALIAS = "st";
    public static final String COMMAND_WORD = "switch-tab";
    public static final String MESSAGE_SUCCESS = "Switch success!";
    public static final String MESSAGE_FAILURE = "Switch failed!";
    public static final String MESSAGE_INVALID_TAB_NUMBER = "Invalid tab number.\n " + MESSAGE_FAILURE;
    public static final String MESSAGE_INVALID_PANEL_NUMBER = "Invalid panel number.\n " + MESSAGE_FAILURE;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch to the type according to index\n"
        + "Parameters: PANEL_INDEX TAB_INDEX\n"
        + "Example: " + COMMAND_WORD + " 1 1";

    private final Integer panelIndex;
    private final Integer tabIndex;

    public SwitchTabCommand(Integer panelIndex, Integer tabIndex) {
        this.panelIndex = panelIndex;
        this.tabIndex = tabIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        switch (panelIndex) {
        case 1:
            LogicManager.setSelectedPanelOneTabIndex(tabIndex);
            break;

        case 2:
            LogicManager.setSelectedPanelTwoTabIndex(tabIndex);
            break;

        default:
            throw new CommandException("Invalid panel index");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SwitchTabCommand // instanceof handles nulls
            && panelIndex.equals(((SwitchTabCommand) other).panelIndex)
            && tabIndex.equals(((SwitchTabCommand) other).tabIndex)); // state check
    }
}
