package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.EditCommand;
import seedu.finance.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.category.Category;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditRecordDescriptor editRecordDescriptor = new EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecordDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editRecordDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editRecordDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editRecordDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editRecordDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> categorySet = categories.size() == 1
                && categories.contains("") ? Collections.emptySet() : categories;
        return Optional.of(ParserUtil.parseCategories(categorySet));
    }

}
