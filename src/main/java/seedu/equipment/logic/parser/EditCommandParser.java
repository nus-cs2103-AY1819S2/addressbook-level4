package seedu.equipment.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_PM;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_SERIALNUMBER;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.EditCommand;
import seedu.equipment.logic.commands.EditCommand.EditEquipmentDescriptor;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_PM, PREFIX_ADDRESS,
                        PREFIX_SERIALNUMBER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE),
                    pe);
        }

        EditCommand.EditEquipmentDescriptor editEquipmentDescriptor = new EditEquipmentDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEquipmentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editEquipmentDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_PM).isPresent()) {
            editEquipmentDescriptor.setDate(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PM).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editEquipmentDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_SERIALNUMBER).isPresent()) {
            editEquipmentDescriptor.setSerialNumber(ParserUtil.parseSerialNumber(
                    argMultimap.getValue(PREFIX_SERIALNUMBER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEquipmentDescriptor::setTags);

        if (!editEquipmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editEquipmentDescriptor);
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
