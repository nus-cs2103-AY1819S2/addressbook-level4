package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditHealthWorkerCommand;
import seedu.address.logic.commands.EditHealthWorkerCommand.EditHealthWorkerDescriptor;
import seedu.address.logic.commands.request.EditRequestCommand;
import seedu.address.logic.commands.request.EditRequestCommand.EditRequestDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String INVALID_COMMAND_USAGE = EditCommand.MESSAGE_USAGE + "\n"
            + EditHealthWorkerCommand.MESSAGE_USAGE + EditRequestCommand.MESSAGE_USAGE;

    /**
     * Parses the given {@code String} of arguments in the context of the EditPersonCommand
     * and returns an EditPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        CommandMode commandMode = ArgumentTokenizer.checkMode(args);
        if (commandMode == CommandMode.HEALTH_WORKER) {
            return parseEditHealthWorker(ArgumentTokenizer.trimMode(args));
        } else if (commandMode == CommandMode.REQUEST) {
            return parseEditRequest(ArgumentTokenizer.trimMode(args));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_COMMAND_USAGE));
    }

    /**
     * @author Lookaz
     * Parses the arguments into respective fields for edit in EditHealthWorkerCommand.
     * @throws ParseException if the user input does not conform the expected format
     */
    private EditHealthWorkerCommand parseEditHealthWorker(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC,
                PREFIX_ORGANIZATION, PREFIX_SKILLS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditHealthWorkerCommand.MESSAGE_USAGE), pe);
        }
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            descriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ORGANIZATION).isPresent()) {
            descriptor.setOrganization(ParserUtil.parseOrganization(argMultimap.getValue(PREFIX_ORGANIZATION).get()));
        }
        if (argMultimap.getValue(PREFIX_SKILLS).isPresent()) {
            descriptor.setSkills(ParserUtil.parseSpecialisations(argMultimap.getAllValues(PREFIX_SKILLS)));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditHealthWorkerCommand.MESSAGE_NOT_EDITED);
        }

        return new EditHealthWorkerCommand(index, descriptor);
    }

    /**
     * @author Lookaz
     * Method for parsing the arguments into respective fields for edit in EditRequestCommand.
     * TODO: David - please review this method.
     * @throws ParseException if user does not confirm to command format
     */
    private EditRequestCommand parseEditRequest(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_NRIC, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_DATE, PREFIX_CONDITION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRequestCommand.MESSAGE_USAGE), pe);
        }
        EditRequestDescriptor descriptor = new EditRequestDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            descriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            descriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            descriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            descriptor.setDate(ParserUtil.parseRequestDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_CONDITION).isPresent()) {
            descriptor.setConditions(ParserUtil.parseConditions(argMultimap.getAllValues(PREFIX_CONDITION)));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRequestCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRequestCommand(index, descriptor);
    }

}
