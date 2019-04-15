package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";
    public static final String MESSAGE_EMPLOYEES_LISTED_OVERVIEW = "%1$d employees listed!";
    public static final String MESSAGE_PROJECTS_LISTED_OVERVIEW = "%1$d projects listed!";
    public static final String MESSAGE_INVALID_PROJECT_NAME = "The project name provided is invalid";
    public static final String MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX = "The milestone index provided is invalid";
    public static final String MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX = "The user story index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECTTASK_DISPLAYED_INDEX =
        "The project task index provided is invalid";

    public static final String MESSAGE_DUPLICATE_PROJ_EMPLOYEE = "This employee already exists in the selected"
        + " target project.";
    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "This employee already exists in the pocket project";
    public static final String MESSAGE_DUPLICATE_MILESTONE = "This milestone already exists in the PocketProject.";
    public static final String INVALID_MILESTONE_DATE = "Invalid milestone date. The date of a  milestone should be "
        + "after the start date of a project and before the deadline of a project.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the pocket project";
    public static final String MESSAGE_DUPLICATE_PROJECT_TASK = "This project task already exists in this milestone.";
    public static final String MESSAGE_INVALID_EDITED_PROJECT_DEADLINE = "The proposed deadline of the project has to "
        + "be later than the project start date and the latest milestone date in the project.";
}
