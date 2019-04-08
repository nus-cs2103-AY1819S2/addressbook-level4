package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskCopyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse user input
 */
public class TaskCopyCommandParser implements Parser<TaskCopyCommand> {

    /**
     *
     * @param args user's input for index
     * @return corresponding copy command
     * @throws ParseException if user input is in a wrong format
     */
    public TaskCopyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        int numOfCopies;

        try {
            Pair<Index, Integer> parsedPair = ParserUtil.parseCopy(args);
            index = parsedPair.getKey();
            numOfCopies = parsedPair.getValue();

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        TaskCopyCommand.MESSAGE_USAGE), pe);
        }
        return new TaskCopyCommand(index, numOfCopies);
    }
}
