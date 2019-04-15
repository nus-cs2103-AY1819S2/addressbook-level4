package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBLINK;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRestaurantDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_POSTAL,
                        PREFIX_TAG, PREFIX_WEBLINK, PREFIX_OPENING_HOURS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditRestaurantDescriptor editRestaurantDescriptor = new EditRestaurantDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRestaurantDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editRestaurantDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editRestaurantDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editRestaurantDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_POSTAL).isPresent()) {
            editRestaurantDescriptor.setPostal(ParserUtil.parsePostal(argMultimap.getValue(PREFIX_POSTAL).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editRestaurantDescriptor::setTags);

        if (argMultimap.getValue(PREFIX_OPENING_HOURS).isPresent()) {
            editRestaurantDescriptor.setOpeningHours(ParserUtil.parseOpeningHours(argMultimap
                    .getValue(PREFIX_OPENING_HOURS).get()));
        }

        if (argMultimap.getValue(PREFIX_WEBLINK).isPresent()) {
            try {
                editRestaurantDescriptor.setWeblink(ParserUtil.parseWeblink(argMultimap
                        .getValue(PREFIX_WEBLINK).get()));
            } catch (NoInternetException e) {
                return handleNoInternet(index, editRestaurantDescriptor);
            }
        }

        if (!editRestaurantDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editRestaurantDescriptor);
    }

    /**
     * Handles the no internet exception and return with the appropriate error message
     */
    private EditCommand handleNoInternet(Index index, EditRestaurantDescriptor editRestaurantDescriptor)
            throws ParseException {

        // If restaurant not edited, return just the no internet error message.
        if (!editRestaurantDescriptor.isAnyFieldEdited()) {
            throw new ParseException(Messages.MESSAGE_EDIT_NO_INTERNET);
        }

        return new EditCommand(index, editRestaurantDescriptor, Messages.MESSAGE_EDIT_NO_INTERNET);
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
