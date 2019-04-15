package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.EMPTY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.exceptions.FilterNotFoundException;
import seedu.address.model.person.predicate.UniqueFilterList;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteFilterCommand extends Command {

    public static final String COMMAND_WORD = "deleteFilter";
    public static final String COMMAND_ALIAS = "df";

    public static final String MESSAGE_USAGE_ALLJOB_SCREEN = COMMAND_WORD
        + ": Deletes the filter identified by the filter name used in the All Job Showing Screen.\n"
        + "Parameters: NameFilterName \n"
        + "Example: " + COMMAND_WORD + " Chinese\n"
        + "The alias \"d\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS + " Chinese\n";

    public static final String MESSAGE_USAGE_DETAIL_SCREEN = COMMAND_WORD
        + ": Deletes the filter identified by the filter name used in the displayed person list.\n"
        + "Parameters: FilterList NameFilterName \n"
        + "Example: " + COMMAND_WORD + " Applicant Chinese\n"
        + "The alias \"d\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS + " Applicant Chinese\n";

    public static final String MESSAGE_LACK_FILTERNAME = "Delete Filter Command need a name\n%1$s";
    public static final String MESSAGE_DELETE_FILTER_SUCCESS = "Deleted Filter: %1$s";
    public static final String MESSAGE_LACK_LISTNAME =
        "Delete Filter Command in Display Job page need indicate job list\n%1$s";
    public static final String MESSAGE_REDUNDANT_LISTNAME =
        "Delete Filter Command in All Jobs page no need indicate job list\n%1$s";
    public static final String MESSAGE_CANOT_FOUND_TARGET_FILTER = "The filter you want to delete can not found\n%1$s";

    private final String targetName;
    private final JobListName filterListName;

    public DeleteFilterCommand(JobListName filterListName, String targetName) {
        this.filterListName = filterListName;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        UniqueFilterList predicateList;
        boolean isAllJobScreen = model.getIsAllJobScreen();
        boolean hasListName = filterListName != EMPTY;
        checkException(isAllJobScreen, hasListName);
        try {
            model.removePredicate(targetName, filterListName);
        } catch (FilterNotFoundException ex) {
            throw new CommandException(MESSAGE_CANOT_FOUND_TARGET_FILTER);
        }
        model.updateFilteredPersonLists(filterListName);
        predicateList = model.getPredicateLists(filterListName);
        return new CommandResult(String.format(MESSAGE_DELETE_FILTER_SUCCESS, targetName), filterListName,
            predicateList);
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
            || (other instanceof DeleteFilterCommand // instanceof handles nulls
            && (filterListName.equals(((DeleteFilterCommand) other).filterListName))
            && (targetName.equals(((DeleteFilterCommand) other).targetName))); // state check
    }
}
