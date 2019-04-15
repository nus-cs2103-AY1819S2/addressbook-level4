package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetCurrentSemesterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduletaken.Semester;

/**
 * Parses input arguments and creates a new SetLimitCommand object
 */
public class SetCurrentSemesterCommandParser implements Parser<SetCurrentSemesterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetLimitCommand
     * and returns an SetLimitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCurrentSemesterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Semester semester;
        String rawSemester = argMultimap.getPreamble();
        if (!Semester.isValidSemester(rawSemester)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetCurrentSemesterCommand.MESSAGE_USAGE));
        }
        semester = Semester.valueOf(rawSemester);

        return new SetCurrentSemesterCommand(semester);
    }
}
