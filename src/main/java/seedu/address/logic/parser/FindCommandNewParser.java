package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.FindCommandNew;
import seedu.address.logic.commands.FindCommandNew.FindModuleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindModulePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandNewParser implements Parser<FindCommandNew> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommandNew parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) { // list all
            return new FindCommandNew(PREDICATE_SHOW_ALL_PERSONS);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODCODE, PREFIX_MODNAME, PREFIX_SEMESTER, PREFIX_GRADE);

        FindModuleDescriptor findModuleDescriptor = new FindModuleDescriptor();
        if (argMultimap.getValue(PREFIX_MODCODE).isPresent()) {
            findModuleDescriptor.setCode(argMultimap.getValue(PREFIX_MODCODE).get());
        }
        if (argMultimap.getValue(PREFIX_MODNAME).isPresent()) {
            findModuleDescriptor.setTitle(argMultimap.getValue(PREFIX_MODNAME).get());
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            findModuleDescriptor.setSemester(ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            findModuleDescriptor.setGrade(ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));
        }

        return new FindCommandNew(new FindModulePredicate(findModuleDescriptor));
    }
}
