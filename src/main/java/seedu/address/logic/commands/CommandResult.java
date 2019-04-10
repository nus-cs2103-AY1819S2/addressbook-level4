package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.analytics.Analytics;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
import seedu.address.model.person.predicate.UniqueFilterList;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Analytics information should be shown to user
     */

    private Analytics analytics;

    private JobName job;

    private String interviews;

    private boolean search = false;

    private UniqueFilterList filterList;

    private JobListName listName;


    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public CommandResult(String feedbackToUser, Analytics results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulAnalytics()) {
            analytics = results;
        }

    }

    public CommandResult(String feedbackToUser, JobListName name, UniqueFilterList list) {
        this(feedbackToUser, false, false);
        search = true;
        listName = name;
        filterList = list;
    }

    public CommandResult(String feedbackToUser, JobName results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulDisplayJob()) {
            job = results;
        }

    }

    public CommandResult(String feedbackToUser, String results) {
        this(feedbackToUser, false, false);
        if (isSuccessfulInterviews()) {
            interviews = results;
        }
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSuccessfulAnalytics() {
        return feedbackToUser.equals(GenerateAnalyticsCommand.MESSAGE_SUCCESS);
    }

    public boolean isSuccessfulInterviews() {
        return feedbackToUser.equals(ShowInterviewsCommand.COMMAND_SUCCESS);
    }

    public boolean isSuccessfulDisplayJob() {
        return feedbackToUser.equals(DisplayJobCommand.MESSAGE_SUCCESS);
    }

    public boolean isSuccessfulSearch() {
        return search;
    }

    public boolean isList() {
        return feedbackToUser.equals(ListCommand.MESSAGE_SUCCESS);
    }

    //remember to handle null later
    public Analytics getAnalytics() {
        return analytics;
    }

    public JobListName getJobListName() {
        return listName;
    }

    public UniqueFilterList getFilterList() {
        return filterList;
    }

    public JobName getJob() {
        return job;
    }

    public String getInterviews() {
        return interviews;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
