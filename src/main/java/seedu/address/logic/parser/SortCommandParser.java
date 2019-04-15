package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.SORTWORD_DEGREE;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_ENDORSEMENTS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_ENDORSEMENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_GPA;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_NAME;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_POSITION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_DEGREE;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_ENDORSEMENTS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_ENDORSEMENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_GPA;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_NAME;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_POSITION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_SKILLS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_SKILL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_REVERSE_SURNAME;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_SKILLS;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_SKILL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.SORTWORD_SURNAME;

import java.util.Arrays;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String word = args.substring(1);
        String[] possibleSortWords = {SORTWORD_DEGREE.toString(), SORTWORD_REVERSE_DEGREE.toString(),
                SORTWORD_EDUCATION.toString(), SORTWORD_REVERSE_EDUCATION.toString(),
                SORTWORD_ENDORSEMENTS.toString(), SORTWORD_REVERSE_ENDORSEMENTS.toString(),
                SORTWORD_ENDORSEMENT_NUMBER.toString(), SORTWORD_REVERSE_ENDORSEMENT_NUMBER.toString(),
                SORTWORD_GPA.toString(), SORTWORD_REVERSE_GPA.toString(),
                SORTWORD_NAME.toString(), SORTWORD_REVERSE_NAME.toString(),
                SORTWORD_POSITION_NUMBER.toString(), SORTWORD_REVERSE_POSITION_NUMBER.toString(),
                SORTWORD_POSITIONS.toString(), SORTWORD_REVERSE_POSITIONS.toString(),
                SORTWORD_SKILL_NUMBER.toString(), SORTWORD_REVERSE_SKILL_NUMBER.toString(),
                SORTWORD_SKILLS.toString(), SORTWORD_REVERSE_SKILLS.toString(),
                SORTWORD_SURNAME.toString(), SORTWORD_REVERSE_SURNAME.toString()};
        if (!areSortWordsPresent(word, possibleSortWords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(ParserUtil.parseSortWord(args));
    }

    /**
     * Returns true if args is a sortWord.
     */
    private static boolean areSortWordsPresent(String inputWord, String[] sortWords) {
        return Arrays.stream(sortWords).parallel().anyMatch(inputWord::equals);
    }
}
