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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch to the type accorting to index\n"
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
            if (tabIndex >= 0 || tabIndex <= 2) {
                LogicManager.setSelectedPanelOneTabIndex(tabIndex);
            } else {
                throw new CommandException("Invalid tab index");
            }
            break;
        case 2:
            if (tabIndex >= 0 || tabIndex <= 1) {
                LogicManager.setSelectedPanelTwoTabIndex(tabIndex);
            } else {
                throw new CommandException("Invalid tab index");
            }
            break;
        default:
            throw new CommandException("Invalid panel index");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
