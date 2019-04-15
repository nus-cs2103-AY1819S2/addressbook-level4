package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalObjects.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.JobListName;

public class GenerateAnalyticsCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.generateAnalytics();

        assertCommandSuccess(new GenerateAnalyticsCommand(), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        expectedModel.generateAnalytics();

        assertCommandSuccess(new GenerateAnalyticsCommand(), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_applicantsList_success() {
        expectedModel.generateAnalytics(JobListName.APPLICANT);

        assertCommandSuccess(new GenerateAnalyticsCommand(JobListName.APPLICANT), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_kivList_success() {
        expectedModel.generateAnalytics(JobListName.KIV);

        assertCommandSuccess(new GenerateAnalyticsCommand(JobListName.KIV), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_interviewList_success() {
        expectedModel.generateAnalytics(JobListName.INTERVIEW);

        assertCommandSuccess(new GenerateAnalyticsCommand(JobListName.INTERVIEW), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_shortListList_success() {
        expectedModel.generateAnalytics(JobListName.SHORTLIST);

        assertCommandSuccess(new GenerateAnalyticsCommand(JobListName.SHORTLIST), model, commandHistory,
                GenerateAnalyticsCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
