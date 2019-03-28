package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


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
                ArgumentTokenizer.tokenize(args, PREFIX_TAG_NAME, PREFIX_TAG_NEW, PREFIX_TAG_REMOVE);

        Index index;
        Set<Tag> tags;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_TAG_NEW).isPresent() && argMultimap.getValue(PREFIX_TAG_REMOVE).isPresent()) {
            throw new ParseException("Invalid Prefix: -a -r");
        } else if (argMultimap.getValue(PREFIX_TAG_NEW).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_NAME));
            return new TagCommand(index, tags, true);
        } else if (argMultimap.getValue(PREFIX_TAG_REMOVE).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_NAME));
            return new TagCommand(index, tags, false);
        } else {
            throw new ParseException("Missing Prefix(s)");
        }
    }
}
