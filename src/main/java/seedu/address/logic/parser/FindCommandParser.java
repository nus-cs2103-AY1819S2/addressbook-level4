package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINISHED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindModulePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODCODE, PREFIX_SEMESTER, PREFIX_GRADE, PREFIX_FINISHED);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        FindModuleDescriptor findModuleDescriptor = new FindModuleDescriptor();
        if (argMultimap.getValue(PREFIX_MODCODE).isPresent()) {
            findModuleDescriptor.setSubCode(argMultimap.getValue(PREFIX_MODCODE).get());
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            findModuleDescriptor.setSemester(ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            findModuleDescriptor.setGrade(ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_FINISHED).isPresent()) {
            findModuleDescriptor.setFinished(ParserUtil.parseFinishedStatus(argMultimap.getValue(PREFIX_FINISHED).get()));
        }

        return new FindCommand(new FindModulePredicate(findModuleDescriptor));
    }
}
