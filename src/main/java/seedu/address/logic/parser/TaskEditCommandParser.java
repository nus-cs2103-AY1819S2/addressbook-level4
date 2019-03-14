package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskEditCommand;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TaskEditCommand object
 */
public class TaskEditCommandParser implements Parser<TaskEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskEditCommand
     * and returns an TaskEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskEditCommand parse(String args) throws ParseException {
        System.out.println("Edit command parser parse is run.");

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_STARTDATE, PREFIX_ENDDATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskEditCommand.MESSAGE_USAGE), pe);
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editTaskDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
            editTaskDescriptor.setStartDate(ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_STARTDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ENDDATE).isPresent()) {
            editTaskDescriptor.setEndDate(ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_ENDDATE)
                    .get(), argMultimap.getValue(PREFIX_STARTDATE).get()));
        }


        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TaskEditCommand.MESSAGE_NOT_EDITED);
        }

        return new TaskEditCommand(index, editTaskDescriptor);
    }


}
