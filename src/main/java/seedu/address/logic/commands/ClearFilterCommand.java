package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.EMPTY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.predicate.UniqueFilterList;

/**
 * Clear filter list
 */
public class ClearFilterCommand extends Command {

    public static final String COMMAND_WORD = "clearFilter";
    public static final String COMMAND_ALIAS = "cf";

    public static final String MESSAGE_USAGE_ALLJOB_SCREEN = COMMAND_WORD
        + ": Clears the filter identified by the filter name used in the All Job Showing Screen.\n"
        + "Example: " + COMMAND_WORD + " \n"
        + "The alias \"d\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS + " \n";

    public static final String MESSAGE_USAGE_DETAIL_SCREEN = COMMAND_WORD
        + ": Clears the filter identified by the filter name used in the All Job Showing Screen.\n"
        + "Example: " + COMMAND_WORD + " Applicant \n"
        + "The alias \"d\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS + " Applicant \n";

    public static final String MESSAGE_CLEAR_FILTER_SUCCESS = "Cleard Filter Success";
    public static final String MESSAGE_LACK_LISTNAME =
        "Clear Filter Command in Display Job page need indicate job list\n%1$s";
    public static final String MESSAGE_REDUNDANT_LISTNAME =
        "Clear Filter Command in All Jobs page no need indicate job list\n%1$s";
    private final JobListName filterListName;

    public ClearFilterCommand(JobListName filterListName) {
        this.filterListName = filterListName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        UniqueFilterList predicateList;
        boolean isAllJobScreen = model.getIsAllJobScreen();
        boolean hasListName = filterListName != EMPTY;
        checkException(isAllJobScreen, hasListName);
        model.clearJobFilteredLists(filterListName);
        model.updateFilteredPersonLists(filterListName);
        predicateList = model.getPredicateLists(filterListName);
        return new CommandResult(MESSAGE_CLEAR_FILTER_SUCCESS, filterListName, predicateList);
    }

    /**
     * @param isAllJobScreen Indicate the current screen, true if screen on all jobs screen
     * @param hasListName    Indicate whether command parser parse the List name
     * @throws CommandException throw exception and catch by function excute()
     */
    private void checkException(boolean isAllJobScreen, boolean hasListName)
        throws CommandException {
        String showMessage = isAllJobScreen ? MESSAGE_USAGE_ALLJOB_SCREEN : MESSAGE_USAGE_DETAIL_SCREEN;
        if (!isAllJobScreen && !hasListName) {
            throw new CommandException(String.format(MESSAGE_LACK_LISTNAME, showMessage));
        } else if (isAllJobScreen && hasListName) {
            throw new CommandException(String.format(MESSAGE_REDUNDANT_LISTNAME, showMessage));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClearFilterCommand // instanceof handles nulls
            && (filterListName.equals(((ClearFilterCommand) other).filterListName))); // state check
    }
}
