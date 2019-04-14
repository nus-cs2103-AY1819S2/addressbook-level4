package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_IMAGE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.ImagePath;
import seedu.address.model.flashcard.Proficiency;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FRONT_FACE, PREFIX_BACK_FACE, PREFIX_IMAGE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT_FACE, PREFIX_BACK_FACE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Face frontFace = ParserUtil.parseFace(argMultimap.getValue(PREFIX_FRONT_FACE).get());
        Face backFace = ParserUtil.parseFace(argMultimap.getValue(PREFIX_BACK_FACE).get());
        ImagePath imagePath = new ImagePath(argMultimap.getValue(PREFIX_IMAGE));
        if (!imagePath.imageExistsAtPath()) {
            throw new ParseException(MESSAGE_IMAGE_NOT_FOUND);
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Statistics statistics = new Statistics(0, 0);
        Proficiency proficiency = new Proficiency(Calendar.getInstance(), 0);
        Flashcard flashcard = new Flashcard(frontFace, backFace, imagePath, statistics, proficiency, tagList);

        return new AddCommand(flashcard);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
