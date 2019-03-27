package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PATH = "Invalid path given.";
    public static final String MESSAGE_INVALID_TYPE = "File is not a valid type.";
    public static final String MESSAGE_DUPLICATE_FILE = "Duplicate file name exists in assets folder.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist. "
            + "Use listfiles to view existing files.";
    public static final String MESSAGE_ROTATE_SUCCESS = "Rotated successfully.";
    public static final String MESSAGE_CONTRAST_SUCCESS = "Contrast filter applied successfully.";
    public static final String MESSAGE_BRIGHTNESS_SUCCESS = "Brightness filter applied successfully.";
    public static final String MESSAGE_BLACKWHITE_SUCCESS = "Black/White filter applied successfully.";
    public static final String MESSAGE_CONTRAST_DOUBLE_ERROR = "Value keyed in is not double. E.g: contrast 1.3";
    public static final String MESSAGE_BRIGHTNESS_DOUBLE_ERROR = "Value keyed in is not double. E.g: brightness 1.3";
    public static final String MESSAGE_BLACKWHITE_INT_ERROR = "Value keyed in is not integer. E.g: bw 123";
    public static final String MESSAGE_NEGATIVE_ERROR = "Value keyed in is not positive.";
    public static final String MESSAGE_ROTATE_DEGREE_ERROR = "Value keyed in is not a valid integer or rotating degree."
            + " Only 90, 180 or 270 is allowed.";
    public static final String MESSAGE_CROP_INT_ERROR = "Values keyed in are not all integers. E.g: crop 0 0 200 200";
    public static final String MESSAGE_CROP_SUCCESS = "Cropped successfully.";
    public static final String MESSAGE_DID_NOT_OPEN = "Did not open any image to edit on.";


}
