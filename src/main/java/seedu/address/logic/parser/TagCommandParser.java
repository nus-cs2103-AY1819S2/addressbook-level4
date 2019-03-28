package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;

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
                ArgumentTokenizer.tokenize(args, PREFIX_TAG_NEW, PREFIX_TAG_REMOVE);

        Index index;
        Tag tag;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_TAG_NEW).isPresent()) {
            tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG_NEW).get());
            return new TagCommand(index, tag, true);
        } else if (argMultimap.getValue(PREFIX_TAG_REMOVE).isPresent()) {
            tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG_REMOVE).get());
            return new TagCommand(index, tag, false);
        } else {
            throw new ParseException("Missing Prefix(s)");
        }
    }
}
