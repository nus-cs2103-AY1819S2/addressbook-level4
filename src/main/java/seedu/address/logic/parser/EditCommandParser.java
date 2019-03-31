package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                    PREFIX_NRIC, PREFIX_YEAR, PREFIX_SEX, PREFIX_DRUG_ALLERGY, PREFIX_NOKN, PREFIX_NOKR,
                    PREFIX_NOKP, PREFIX_NOKA, PREFIX_DESC);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SEX).isPresent()) {
            editPersonDescriptor.setSex(ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editPersonDescriptor.setDateOfBirth(ParserUtil.parseDob(argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DRUG_ALLERGY).isPresent()) {
            editPersonDescriptor.setDrugAllergy(ParserUtil
                .parseDrugAllergy(argMultimap.getValue(PREFIX_DRUG_ALLERGY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editPersonDescriptor.setDescription(ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC).get()));
        }

        //Next Of Kin checks
        if (argMultimap.getValue(PREFIX_NOKN).isPresent()) {
            editPersonDescriptor.setNextOfKinName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NOKN).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKR).isPresent()) {
            editPersonDescriptor.setNextOfKinRelation(ParserUtil.parseRelation(argMultimap
                .getValue(PREFIX_NOKR).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKP).isPresent()) {
            editPersonDescriptor.setNextOfKinPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_NOKP).get()));
        }
        if (argMultimap.getValue(PREFIX_NOKA).isPresent()) {
            String updatedValue = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_NOKA).get()).toString();
            if (!updatedValue.toLowerCase().equals("same")) {
                editPersonDescriptor.setNextOfKinAddress(ParserUtil.parseAddress(argMultimap
                    .getValue(PREFIX_NOKA).get()));
            } else {
                if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                    editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap
                        .getValue(PREFIX_ADDRESS).get()));
                } else {
                    editPersonDescriptor.setSameAddr();
                }
            }
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

}
