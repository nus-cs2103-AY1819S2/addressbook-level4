package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.EMPTY;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;

import seedu.address.commons.core.Messages;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the filter identified by the filter name used in the displayed person list.\n"
            + "Parameters: FilterList NameFilterName \n"
            + "Example: " + COMMAND_WORD + " Applicant Chinese\n"
            + "The alias \"d\" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS + "Applicant Chinese";

    public static final String MESSAGE_DELETE_FILTER_SUCCESS = "Deleted Filter: %1$s";
    public static final String MESSAGE_DELETE_FILTER_FAIL = "Deleted Filter Fail";

    private final String targetName;
    private final JobListName filterListName;

    public DeleteFilterCommand(JobListName filterListName, String targetName) {
        this.filterListName = filterListName;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        UniqueFilterList predicateList;
        boolean isAllJobScreen = model.getIsAllJobScreen();
        boolean hasListName = filterListName != EMPTY;
        if (!isAllJobScreen && !hasListName) {
            throw new CommandException(Messages.MESSAGE_LACK_LISTNAME);
        }
        requireNonNull(model);
        switch (filterListName) {
        case APPLICANT:
            try {
                model.removePredicateJobAllApplicants(targetName);
            } catch (FilterNotFoundException ex) {
                throw new CommandException(Messages.MESSAGE_CANOT_FOUND_TARGET_FILTER);
            }
            model.updateJobAllApplicantsFilteredPersonList();
            predicateList = model.getPredicateLists(APPLICANT);
            break;
        case KIV:
            try {
                model.removePredicateJobKiv(targetName);
            } catch (FilterNotFoundException ex) {
                throw new CommandException(Messages.MESSAGE_CANOT_FOUND_TARGET_FILTER);
            }
            model.updateJobKivFilteredPersonList();
            predicateList = model.getPredicateLists(KIV);
            break;
        case INTERVIEW:
            try {
                model.removePredicateJobInterview(targetName);
            } catch (FilterNotFoundException ex) {
                throw new CommandException(Messages.MESSAGE_CANOT_FOUND_TARGET_FILTER);
            }
            model.updateJobInterviewFilteredPersonList();
            predicateList = model.getPredicateLists(INTERVIEW);
            break;
        case SHORTLIST:
            try {
                model.removePredicateJobShortlist(targetName);
            } catch (FilterNotFoundException ex) {
                throw new CommandException(Messages.MESSAGE_CANOT_FOUND_TARGET_FILTER);
            }
            model.updateJobShortlistFilteredPersonList();
            predicateList = model.getPredicateLists(SHORTLIST);
            break;
        default:
            try {
                model.removePredicateAllPersons(targetName);
            } catch (FilterNotFoundException ex) {
                throw new CommandException(Messages.MESSAGE_CANOT_FOUND_TARGET_FILTER);
            }
            model.updateFilteredPersonList();
            predicateList = model.getPredicateLists(EMPTY);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_FILTER_SUCCESS, targetName), filterListName,
                predicateList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFilterCommand // instanceof handles nulls
                && (filterListName.equals(((DeleteFilterCommand) other).filterListName))
                && (targetName.equals(((DeleteFilterCommand) other).targetName))); // state check
    }
}
