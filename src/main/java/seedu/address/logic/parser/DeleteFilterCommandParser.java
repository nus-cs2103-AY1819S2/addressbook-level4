package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteFilterCommandParser implements Parser<DeleteFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFilterCommand parse(String args) throws ParseException {
        JobListName listName;
        String trimedString = args.trim();
        boolean hasListName = trimedString.split("\\s+").length==2;
        String listNameString;
        String commandName;
        if (!hasListName){
            listNameString = "";
            commandName = trimedString.split("\\s+")[0].trim();
        }else {
            listNameString = trimedString.split("\\s+")[0].trim();
            commandName = trimedString.split("\\s+")[1].trim();
        }
        try {
            listName = ParserUtil.parseJobListName(listNameString);
            return new DeleteFilterCommand(listName, commandName);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFilterCommand.MESSAGE_USAGE), pe);
        }
    }

}
