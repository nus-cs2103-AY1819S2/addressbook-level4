package seedu.address.testutil;

import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "Review Patients' records";
    public static final String DEFAULT_STARTDATE = "01-01-2020";
    public static final String DEFAULT_ENDDATE = "01-01-2020";
    public static final String DEFAULT_STARTTIME = "1100";
    public static final String DEFAULT_ENDTIME = "1200";
    public static final String DEFAULT_PRIORITY = "LOW";
    public static final String DEFAULT_LINKEDPATIENT = null;

    private Title title;
    private DateCustom startDate;
    private DateCustom endDate;
    private TimeCustom startTime;
    private TimeCustom endTime;
    private Priority priority;
    private LinkedPatient linkedPatient;

    public TaskBuilder() {
        title = new Title(DEFAULT_TITLE);
        startDate = new DateCustom(DEFAULT_STARTDATE);
        endDate = new DateCustom(DEFAULT_ENDDATE);
        startTime = new TimeCustom(DEFAULT_STARTTIME);
        endTime = new TimeCustom(DEFAULT_ENDTIME);
        priority = Priority.returnPriority(DEFAULT_PRIORITY);
        linkedPatient = null;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        title = taskToCopy.getTitle();
        startDate = taskToCopy.getStartDate();
        endDate = taskToCopy.getEndDate();
        startTime = taskToCopy.getStartTime();
        endTime = taskToCopy.getEndTime();
        priority = taskToCopy.getPriority();
        linkedPatient = taskToCopy.getLinkedPatient();
    }
}
