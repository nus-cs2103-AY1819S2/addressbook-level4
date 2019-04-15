package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TaskSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser to create a task sort command.
 */
public class TaskSortCommandParser implements Parser<TaskSortCommand> {

    @Override
    public TaskSortCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] inputs = userInput.trim().split(" ");
        String sortFiled;
        boolean isAscending;

        if (inputs.length == 1) {
            isAscending = true;
            sortFiled = captureSortField(inputs[0]);
        } else if (inputs.length == 2) {
            isAscending = captureSortOrder(inputs[1]);
            sortFiled = captureSortField(inputs[0]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskSortCommand.MESSAGE_USAGE));
        }

        return new TaskSortCommand(sortFiled, isAscending);
    }

    /**
     * Future ready function to process raw input from user
     * @param sortField raw input from user
     * @return processed input
     */
    private String captureSortField(String sortField) {
        return sortField.trim().toLowerCase();
    }

    /**
     * Enable user to type part of ascending or descending
     * @param sortOrder raw input from user
     * @return true for acending order
     * @throws ParseException
     */
    private boolean captureSortOrder(String sortOrder) throws ParseException {
        String regularInput = sortOrder.trim().toLowerCase();
        /*if ("descending".indexOf(regularInput) == 0) {
            return false;
        } else if ("ascending".indexOf(regularInput) == 0) {
            return true;
        }*/
        if (regularInput.equals("asce")) {
            return true;
        } else if (regularInput.equals("desc")) {
            return false;
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskSortCommand.MESSAGE_USAGE));
    }
}
