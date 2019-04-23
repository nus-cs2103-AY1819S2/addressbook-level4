package seedu.address.model;

/**
 * Class that represents different types of books that were modified after a command
 */
public enum CommandType {

    HEALTHWORKER_COMMAND,
    REQUEST_COMMAND,
    //The following type of command affects both books
    HEALTHWORKER_AND_REQUEST_COMMAND
}
