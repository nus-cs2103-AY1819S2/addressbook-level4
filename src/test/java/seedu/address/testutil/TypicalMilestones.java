package seedu.address.testutil;

import java.util.Arrays;

import seedu.address.model.project.Milestone;

/**
 * A utility class containing a list of {@code Milestones} objects to be used in tests.
 */
public class TypicalMilestones {

    public static final Milestone TYPICAL_MILESTONE_START = new MilestoneBuilder()
        .withDescription("The project starts")
        .withDeadline("11/11/2011")
        .withProjectTasks(Arrays.asList(TypicalProjectTasks.PROJECT_TASK_DO_SOMETHING,
                TypicalProjectTasks.PROJECT_TASK_COMPLETED))
        .build();

    public static final Milestone TYPICAL_MILESTONE_START_COMPLETED_UG = new MilestoneBuilder()
        .withDescription("The UG is completed")
        .withDeadline("10/11/2012")
        .withProjectTasks(Arrays.asList(TypicalProjectTasks.PROJECT_TASK_DO_SOMETHING,
            TypicalProjectTasks.PROJECT_TASK_COMPLETED))
        .build();

    public static final Milestone TYPICAL_MILESTONE_END = new MilestoneBuilder()
        .withDescription("The project completes")
        .withDeadline("12/12/2012")
        .withProjectTasks(Arrays.asList(TypicalProjectTasks.PROJECT_TASK_COMPLETED))
        .build();

    public static final Milestone TYPICAL_MILESTONE_COMPLETED_UG = new MilestoneBuilder()
        .withDescription("The UG is completed")
        .withDeadline("10/11/2012")
        .build();

    public static final Milestone TYPICAL_MILESTONE_GUI_V2 = new MilestoneBuilder()
        .withDescription("GUI is in version 2")
        .withDeadline("08/03/2012")
        .build();
}
