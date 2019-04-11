package seedu.address.commons.core;

import java.util.Arrays;
import java.util.List;

/**
 * Container for all commonly used commands
 */
public class Commands {

    public static final String ADD = "add";
    public static final String ADD_HEALTH_WORKER = "add healthworker n/Judy Hopps i/S8974421C p/98765432 "
            + "o/SGH s/GENERAL_PRACTICE s/ORTHOPAEDIC";
    public static final String ADD_HEALTH_WORKER_MODE = "add 1 n/Judy Hopps i/S8974421C p/98765432 "
            + "o/SGH s/GENERAL_PRACTICE s/ORTHOPAEDIC";
    public static final String ADD_REQUEST = "add request n/John Doe i/S8974421C p/98765432 "
            + "a/123 Brick Road, #01-01 dt/01-01-2019 14:50:00 c/Diabetic c/Physiotherapy";
    public static final String ADD_REQUEST_MODE = "add 2 n/John Doe i/S8974421C p/98765432 "
            + "a/123 Brick Road, #01-01 dt/01-01-2019 14:50:00 c/Diabetic c/Physiotherapy";
    public static final String EDIT = "edit";
    public static final String EDIT_HEALTH_WORKER = "edit healthworker n/Judy Hopps";
    public static final String EDIT_HEALTH_WORKER_MODE = "edit 1 n/Judy Hopps";
    public static final String EDIT_REQUEST = "edit request n/John Doe";
    public static final String EDIT_REQUEST_MODE = "edit 2 n/John Doe";
    public static final String LIST = "list";
    public static final String LIST_ALL_HEALTHWORKER = "list healthworker";
    public static final String LIST_ALL_HEALTHWORKER_MODE = "list 1";
    public static final String LIST_ALL_REQUEST = "list request";
    public static final String LIST_ALL_REQUEST_MODE = "list 2";
    public static final String ASSIGN = "assign";
    public static final String ASSIGN_REQUEST = "assign hw/1 r/2";
    public static final String COMPLETE = "complete";
    public static final String FILTER = "filter";
    public static final String FIND_REQUEST = "frequest";
    public static final String SELECT = "select";
    public static final String DELETE = "delete";
    public static final String CLEAR = "clr";
    public static final String HISTORY = "history";
    public static final String STATISTICS = "statistics";
    public static final String SET_REMINDER = "setreminder";
    public static final String SET_REMINDER_FULL = "setreminder t/05:00:00 m/call patient John Doe for his appointment";
    public static final String UNDO = "undo";
    public static final String REDO = "redo";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    public static List<String> getAllCommands() {
        return Arrays.asList(ADD, ADD_HEALTH_WORKER, ADD_HEALTH_WORKER_MODE, ADD_REQUEST, ADD_REQUEST_MODE, EDIT,
                EDIT_HEALTH_WORKER, EDIT_HEALTH_WORKER_MODE, EDIT_REQUEST, EDIT_REQUEST_MODE, LIST,
                LIST_ALL_HEALTHWORKER, LIST_ALL_HEALTHWORKER_MODE, LIST_ALL_REQUEST, LIST_ALL_REQUEST_MODE,
                ASSIGN, ASSIGN_REQUEST, COMPLETE, FILTER, FIND_REQUEST, SELECT, DELETE, CLEAR, HISTORY, STATISTICS,
                SET_REMINDER, SET_REMINDER_FULL, UNDO, REDO, HELP, EXIT);
    }
}
