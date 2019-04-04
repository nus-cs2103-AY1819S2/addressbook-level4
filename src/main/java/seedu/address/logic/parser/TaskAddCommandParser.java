package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINKEDPATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.ui.MainWindow;

/**
 * Parses input arguments and creates a new PatientAddCommand object
 */
public class TaskAddCommandParser implements Parser<TaskAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PatientAddCommand
     * and returns an PatientAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_STARTDATE, PREFIX_ENDDATE,
                        PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_PRIORITY, PREFIX_LINKEDPATIENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_STARTDATE, PREFIX_STARTTIME,
                PREFIX_ENDTIME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        DateCustom startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_STARTDATE).get());
        DateCustom endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_ENDDATE)
                .orElse(argMultimap.getValue(PREFIX_STARTDATE).get()));
        TimeCustom startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME).get());
        TimeCustom endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_ENDTIME).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).orElse("low"));
        Index patientIndex;
        if (argMultimap.getValue(PREFIX_LINKEDPATIENT).isPresent()) {
            if (MainWindow.isGoToMode()) {
                throw new ParseException(LinkedPatient.MESSAGE_ADDITIONAL_CONSTRAINT
                        + LinkedPatient.MESSAGE_CONSTRAINTS);
            }
            patientIndex = ParserUtil.parseLinkedPatientIndex(argMultimap.getValue(PREFIX_LINKEDPATIENT).get());
        } else {
            patientIndex = null;
        }

        Task task = new Task(title, startDate, endDate, startTime, endTime, priority, null);
        return new TaskAddCommand(task, patientIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
