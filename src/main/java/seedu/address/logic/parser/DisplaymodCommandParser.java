package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.DisplaymodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduleinfo.CodeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DisplaymodCommand object
 */
public class DisplaymodCommandParser implements Parser<DisplaymodCommand> {

    private static final String MODULECODE_REGEX = ".*?[a-zA-Z]{2,3}\\d{4}[a-zA-Z]{0,3}.*?";
    private static final String MODULETITLE_REGX = ".*?[a-zA-Z].*?";

    /**
     * Parses the given {@code String} of arguments in the context of the DisplaymodCommand
     * and returns an DisplaymodCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplaymodCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODCODE, PREFIX_MODNAME);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_MODCODE, PREFIX_MODNAME)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplaymodCommand.MESSAGE_USAGE));
        }

        ArrayList<String> keywordsList = new ArrayList<>();

        if (argumentMultimap.getValue(PREFIX_MODCODE).isPresent()) {
            String codes = argumentMultimap.getValue(PREFIX_MODCODE).get().trim();
            String[] tester = codes.split(" ");
            if (tester.length > 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DisplaymodCommand.MESSAGE_USAGE));
            }
            if (!codes.matches(MODULECODE_REGEX)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DisplaymodCommand.MESSAGE_USAGE));
            }
            String[] codesKeyword = codes.split(",");

            Stream.of(codesKeyword).forEach(code -> keywordsList.add(code));
        }

        if (argumentMultimap.getValue(PREFIX_MODNAME).isPresent()) {
            String names = argumentMultimap.getValue(PREFIX_MODNAME).get().trim();
            String [] tester = names.split(" ");
            if (tester.length > 1 || !names.matches(MODULETITLE_REGX)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DisplaymodCommand.MESSAGE_USAGE));
            } else {
                keywordsList.add(names);
            }
        }

        String[] keywords = keywordsList.toArray(new String[keywordsList.size()]);

        return new DisplaymodCommand(new CodeContainsKeywordsPredicate(Arrays.asList(keywords)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
