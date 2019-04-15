package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;
import static seedu.address.model.job.JobListName.STUB;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.job.JobListName;

/**
 * Generates analytics report based on selected list of persons
 */
public class GenerateAnalyticsCommand extends Command {

    public static final String COMMAND_WORD = "analytics";
    public static final String MESSAGE_SUCCESS = "Analytics generated!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates analytics of desired list. "
        + "Parameters: list name (If no list name provided, analytics of all applicants will be shown)"
        + "\n" + "Possible lists are: applicant, kiv, interview, shortlist"
        + "\n" + "Example: " + COMMAND_WORD + " " + "kiv";

    private final JobListName listName;

    public GenerateAnalyticsCommand(JobListName listName) {
        requireNonNull(listName);
        this.listName = listName;
    }

    public GenerateAnalyticsCommand() {
        this.listName = STUB;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Analytics analytics;
        switch (listName) {

        case APPLICANT:
            analytics = model.generateAnalytics(APPLICANT);
            break;
        case KIV:
            analytics = model.generateAnalytics(KIV);
            break;
        case INTERVIEW:
            analytics = model.generateAnalytics(INTERVIEW);
            break;
        case SHORTLIST:
            analytics = model.generateAnalytics(SHORTLIST);
            break;
        case STUB:
            analytics = model.generateAnalytics();
            break;
        default:
            analytics = model.generateAnalytics();
        }
        return new CommandResult(MESSAGE_SUCCESS, analytics);
    }

}
