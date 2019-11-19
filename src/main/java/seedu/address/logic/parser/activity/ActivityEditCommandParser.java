package seedu.address.logic.parser.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ActivityEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ActivityEditCommand object
 */
public class ActivityEditCommandParser implements Parser<ActivityEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ActivityEditCommand
     * and returns an ActivityEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ActivityEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACTIVITYNAME, PREFIX_LOCATION, PREFIX_DATETIME,
                        PREFIX_ADESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ActivityEditCommand.MESSAGE_USAGE), pe);
        }

        ActivityEditCommand.EditActivityDescriptor editActivityDescriptor =
                new ActivityEditCommand.EditActivityDescriptor();
        if (argMultimap.getValue(PREFIX_ACTIVITYNAME).isPresent()) {
            editActivityDescriptor.setActivityName(ParserUtil.parseActivityName(
                    argMultimap.getValue(PREFIX_ACTIVITYNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editActivityDescriptor.setActivityLocation(ParserUtil.parseActivityLocation(
                    argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editActivityDescriptor.setActivityDateTime(ParserUtil.parseActivityDateTime(
                    argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADESCRIPTION).isPresent()) {
            editActivityDescriptor.setActivityDescription(ParserUtil.parseActivityDescription(
                    argMultimap.getValue(PREFIX_ADESCRIPTION).get()));
        }

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ActivityEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ActivityEditCommand(index, editActivityDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
