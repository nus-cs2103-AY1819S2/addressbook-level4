package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT_FACE, PREFIX_BACK_FACE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT_FACE) && !arePrefixesPresent(argMultimap, PREFIX_BACK_FACE)
                && !arePrefixesPresent(argMultimap, PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Set<Face> frontFaceKeywordSet = ParserUtil.parseFaces(argMultimap.getAllValues(PREFIX_FRONT_FACE));
        Set<Face> backFaceKeywordSet = ParserUtil.parseFaces(argMultimap.getAllValues(PREFIX_BACK_FACE));
        Set<Tag> tagKeywordSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ArrayList<String> frontFaceKeywords = new ArrayList<>();
        ArrayList<String> backFaceKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();

        for (Face frontFace : frontFaceKeywordSet) {
            String[] frontFaceTextSplit = frontFace.text.split("\\s+");
            for (String frontFaceKeyword : frontFaceTextSplit) {
                frontFaceKeywords.add(frontFaceKeyword);
            }
        }

        for (Face backFace : backFaceKeywordSet) {
            String[] backFaceTextSplit = backFace.text.split("\\s+");
            for (String backFaceKeyword : backFaceTextSplit) {
                backFaceKeywords.add(backFaceKeyword);
            }
        }

        for (Tag tag : tagKeywordSet) {
            tagKeywords.add(tag.tagName);
        }

        return new FindCommand(
                new FlashcardContainsKeywordsPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
