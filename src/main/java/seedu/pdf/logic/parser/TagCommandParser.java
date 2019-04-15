package seedu.pdf.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;
import static seedu.pdf.logic.parser.CliSyntax.getAllPrefixes;

import java.util.Optional;
import java.util.Set;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.TagCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.tag.Tag;


/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG_NAME, PREFIX_TAG_ADD, PREFIX_TAG_REMOVE);
        ArgumentMultimap fullArgMultimap =
                ArgumentTokenizer.tokenize(args, getAllPrefixes());

        if (argMultimap.getNumValues() != fullArgMultimap.getNumValues()) {
            System.out.println(argMultimap);
            System.out.println(fullArgMultimap);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Index index;
        Set<Tag> tags;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_TAG_ADD).isPresent() && argMultimap.getValue(PREFIX_TAG_REMOVE).isPresent()) {
            throw new ParseException("Invalid Prefix: -a -r");
        } else if (argMultimap.getValue(PREFIX_TAG_ADD).isPresent()
                && argMultimap.getValue(PREFIX_TAG_ADD).equals(Optional.of(""))
                && argMultimap.getValue(PREFIX_TAG_NAME).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_NAME));
            return new TagCommand(index, tags, true);
        } else if (argMultimap.getValue(PREFIX_TAG_REMOVE).isPresent()
                && argMultimap.getValue(PREFIX_TAG_REMOVE).equals(Optional.of(""))
                && argMultimap.getValue(PREFIX_TAG_NAME).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_NAME));
            return new TagCommand(index, tags, false);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
    }
}
