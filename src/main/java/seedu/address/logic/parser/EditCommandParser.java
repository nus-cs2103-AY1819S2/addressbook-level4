package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Config.IMAGE_DIRECTORY;
import static seedu.address.commons.core.Messages.MESSAGE_IMAGE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.ImagePath;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_FRONT_FACE, PREFIX_BACK_FACE, PREFIX_IMAGE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditFlashcardDescriptor editFlashcardDescriptor = new EditFlashcardDescriptor();
        if (argMultimap.getValue(PREFIX_FRONT_FACE).isPresent()) {
            editFlashcardDescriptor.setFrontFace(ParserUtil.parseFace(argMultimap.getValue(PREFIX_FRONT_FACE).get()));
        }
        if (argMultimap.getValue(PREFIX_BACK_FACE).isPresent()) {
            editFlashcardDescriptor.setBackFace(ParserUtil.parseFace(argMultimap.getValue(PREFIX_BACK_FACE).get()));
        }
        if (argMultimap.getValue(PREFIX_IMAGE).isPresent()) {
            String imageFileName = argMultimap.getValue(PREFIX_IMAGE).get();
            if (imageFileName.equals("")) {
                editFlashcardDescriptor.setImagePath(new ImagePath(Optional.empty()));
            } else {
                Path imageDirectoryPath = Paths.get(IMAGE_DIRECTORY);
                Path imageFileNamePath = Paths.get(imageFileName);
                if (!Files.exists(imageDirectoryPath.resolve(imageFileNamePath))) {
                    throw new ParseException(String.format(MESSAGE_IMAGE_NOT_FOUND, EditCommand.MESSAGE_USAGE));
                }
                ImagePath imagePath = new ImagePath(Optional.of((imageDirectoryPath.resolve(imageFileNamePath))
                    .toString()));
                if (!imagePath.imageExistsAtPath()) {
                    throw new ParseException(String.format(MESSAGE_IMAGE_NOT_FOUND, EditCommand.MESSAGE_USAGE));
                }
                editFlashcardDescriptor.setImagePath(imagePath);
            }
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editFlashcardDescriptor::setTags);

        if (!editFlashcardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFlashcardDescriptor);
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
