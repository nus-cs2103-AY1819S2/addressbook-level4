package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Option;
import seedu.address.model.hint.Hint;

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
                ArgumentTokenizer.tokenize(
                        args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_OPTION, PREFIX_HINT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditCardDescriptor editCardDescriptor = new EditCommand.EditCardDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editCardDescriptor.setQuestion(ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get()));
        }
        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editCardDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }
        parseOptionsForEdit(argMultimap.getAllValues(PREFIX_OPTION)).ifPresent(editCardDescriptor::setOptions);
        parseHintsForEdit(argMultimap.getAllValues(PREFIX_HINT)).ifPresent(editCardDescriptor::setHints);

        if (!editCardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editCardDescriptor);
    }

    /**
     * Returns emptySet if string collection only contains an empty string, or null is collection is empty.
     */
    private Collection<String> parseStringCollection(Collection<String> strings) {
        assert strings != null;
        if (strings.isEmpty()) {
            return null;
        }
        return strings.size() == 1 && strings.contains("") ? Collections.emptySet() : strings;
    }

    /**
     * Parses {@code Collection<String> options} into a {@code Set<Option>} if {@code options} is non-empty.
     * If {@code options} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Option>} containing zero options.
     */
    private Optional<Set<Option>> parseOptionsForEdit(Collection<String> options) throws ParseException {
        Collection<String> optionSet = parseStringCollection(options);
        return optionSet != null ? Optional.of(ParserUtil.parseOptions(optionSet)) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> hints} into a {@code Set<Hint>} if {@code hints} is non-empty.
     * If {@code hints} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Hint>} containing zero hints.
     */
    private Optional<Set<Hint>> parseHintsForEdit(Collection<String> hints) throws ParseException {
        Collection<String> hintSet = parseStringCollection(hints);
        return hintSet != null ? Optional.of(ParserUtil.parseHints(hintSet)) : Optional.empty();
    }

}
