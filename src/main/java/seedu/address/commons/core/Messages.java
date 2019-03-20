package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PATH = "Invalid path given. \n%1$s";
    public static final String MESSAGE_INVALID_TYPE = "File is not a valid type.";
    public static final String MESSAGE_DUPLICATE_FILE = "Duplicate file name exists in assets folder.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist. Use listfiles to view existing files.\n%1$s";
    public static final String MESSAGE_ROTATE_SUCCESS = "Rotated successfully";
    public static final String MESSAGE_CONTRAST_SUCCESS = "Contrast filter applied successfully";
    public static final String MESSAGE_BRIGHTNESS_SUCCESS = "Brightness filter applied successfully";
    public static final String MESSAGE_ROTATE_DEGREE_ERROR = "Argument given is an invalid integer or rotating degree. "
            + "Only 90, 180 or 270 is allowed.";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist.";
    public static final String MESSAGE_FILE_NAME_UNSPECIFIED = "File name to be specified after keying in degree. "
            + "e.g. rotate 90 sample.png";
    public static final String MESSAGE_CROP_INT_ERROR = "Values keyed in are not integer. E.g: crop 0 0 200 200 "
            + "sample.png";
    public static final String MESSAGE_CROP_INSUFFICIENT_INPUTS = "Input file name. E.g: crop 0 0 200 200 "
            + "sample.png";
    public static final String MESSAGE_CROP_SUCCESS = "Cropped successfully.";



}
