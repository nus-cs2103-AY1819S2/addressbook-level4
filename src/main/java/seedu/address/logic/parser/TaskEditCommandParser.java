package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDPATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskEditCommand;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.LinkedPatient;
import seedu.address.ui.MainWindow;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_STARTDATE, PREFIX_ENDDATE,
                        PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_PRIORITY, PREFIX_LINKEDPATIENT);

        Index index;
        Index patientIndex;

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
            editTaskDescriptor.setStartDate(ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_STARTDATE)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_ENDDATE).isPresent()) {
            editTaskDescriptor.setEndDate(ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_ENDDATE)
                    .get()));
        }

        if (argMultimap.getValue(PREFIX_STARTTIME).isPresent()) {
            editTaskDescriptor.setStartTime(ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME)
                    .get()));
        }

        if (argMultimap.getValue(PREFIX_ENDTIME).isPresent()) {
            editTaskDescriptor.setEndTime(ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_ENDTIME)
                    .get()));
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)
                    .get()));
        }

        if (argMultimap.getValue(PREFIX_LINKEDPATIENT).isPresent()) {
            if (MainWindow.isGoToMode()) {
                throw new ParseException(LinkedPatient.MESSAGE_ADDITIONAL_CONSTRAINT
                        + LinkedPatient.MESSAGE_CONSTRAINTS);
            }
            editTaskDescriptor.setPatientIndex(ParserUtil.parseLinkedPatientIndex(argMultimap
                    .getValue(PREFIX_LINKEDPATIENT).get()));
        } else {
            editTaskDescriptor.setPatientIndex(null);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TaskEditCommand.MESSAGE_NOT_EDITED);
        }


        return new TaskEditCommand(index, editTaskDescriptor);
    }
}
