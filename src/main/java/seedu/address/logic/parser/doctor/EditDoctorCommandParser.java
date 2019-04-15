package seedu.address.logic.parser.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
                        args, PREFIX_NAME, PREFIX_GENDER, PREFIX_YEAR, PREFIX_PHONE, PREFIX_SPECIALISATION);

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
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editDoctorDescriptor.setYear(ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editDoctorDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_SPECIALISATION).isPresent()) {
            editDoctorDescriptor.setSpecs(ParserUtil
                    .parseSpecialisations(argMultimap.getAllValues(PREFIX_SPECIALISATION)));
        }

        if (!editDoctorDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDoctorCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDoctorCommand(index, editDoctorDescriptor);
    }

}
