package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MAX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MIN_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_INFO_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Hour;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_INFO_CODE, PREFIX_SEMESTER,
                        PREFIX_EXPECTED_MIN_GRADE, PREFIX_EXPECTED_MAX_GRADE, PREFIX_LECTURE_HOUR, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_INFO_CODE,
                PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ModuleInfoCode name = ParserUtil.moduleInfoCode(argMultimap.getValue(PREFIX_MODULE_INFO_CODE).get());
        Semester semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Grade expectedMinGrade = Grade.valueOf("F");
        if (arePrefixesPresent(argMultimap, PREFIX_EXPECTED_MIN_GRADE)) {
            expectedMinGrade = ParserUtil
                    .parseGrade(argMultimap.getValue(PREFIX_EXPECTED_MIN_GRADE).get());
        }

        Grade expectedMaxGrade = Grade.valueOf("A");
        if (arePrefixesPresent(argMultimap, PREFIX_EXPECTED_MAX_GRADE)) {
            expectedMaxGrade = ParserUtil
                    .parseGrade(argMultimap.getValue(PREFIX_EXPECTED_MAX_GRADE).get());
        }

        Hour lectureHour = new Hour("0"); //to be replaced based on ModuleInfo default
        if (arePrefixesPresent(argMultimap, PREFIX_LECTURE_HOUR)) {
            lectureHour = ParserUtil
                    .parseHour(argMultimap.getValue(PREFIX_LECTURE_HOUR).get());
        }

        ModuleTaken moduleTaken = new ModuleTaken(name, semester, expectedMinGrade,
                expectedMaxGrade, lectureHour, tagList);

        return new AddCommand(moduleTaken);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
