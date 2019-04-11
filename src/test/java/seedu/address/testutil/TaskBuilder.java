package seedu.address.testutil;

import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Title;

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

}
