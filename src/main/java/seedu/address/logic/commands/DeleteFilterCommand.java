package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobListName;
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

        requireNonNull(model);
        switch (filterListName) {
        case APPLICANT:
            model.removePredicateJobAllApplicants(targetName);
            model.updateJobAllApplicantsFilteredPersonList();
            predicateList = model.getPredicateLists(APPLICANT);
            break;
        case KIV:
            model.removePredicateJobKiv(targetName);
            model.updateJobKivFilteredPersonList();
            predicateList = model.getPredicateLists(KIV);
            break;
        case INTERVIEW:
            model.removePredicateJobInterview(targetName);
            model.updateJobInterviewFilteredPersonList();
            predicateList = model.getPredicateLists(INTERVIEW);
            break;
        case SHORTLIST:
            model.removePredicateJobShortlist(targetName);
            model.updateJobShortlistFilteredPersonList();
            predicateList = model.getPredicateLists(SHORTLIST);
            break;
        default:
            throw new CommandException(MESSAGE_DELETE_FILTER_FAIL);
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
