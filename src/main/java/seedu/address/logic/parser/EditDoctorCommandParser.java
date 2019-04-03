package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDoctorCommand;
import seedu.address.logic.commands.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Specialisation;

/**
 * Parses input arguments and creates a new EditDoctorCommand object
 */
public class EditDoctorCommandParser implements Parser<EditDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDoctorCommand
     * and returns an EditDoctorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDoctorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_GENDER, PREFIX_AGE, PREFIX_PHONE, PREFIX_SPECIALISATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE), pe);
        }

        EditDoctorDescriptor editDoctorDescriptor = new EditDoctorDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDoctorDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editDoctorDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editDoctorDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editDoctorDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        parseSpecsForEdit(argMultimap.getAllValues(PREFIX_SPECIALISATION)).ifPresent(editDoctorDescriptor::setSpecs);

        if (!editDoctorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDoctorCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDoctorCommand(index, editDoctorDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Specialisation>} if {@code specs} is non-empty.
     * If {@code specs} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Specialisation>} containing zero specs.
     */
    private Optional<Set<Specialisation>> parseSpecsForEdit(Collection<String> specs) throws ParseException {
        assert specs != null;

        if (specs.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> specSet = specs.size() == 1 && specs.contains("") ? Collections.emptySet() : specs;
        return Optional.of(ParserUtil.parseSpecialisations(specSet));
    }

}
