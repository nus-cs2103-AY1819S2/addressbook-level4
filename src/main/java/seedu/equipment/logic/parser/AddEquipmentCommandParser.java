package seedu.equipment.logic.parser;

import java.util.Set;
import java.util.stream.Stream;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.AddEquipmentCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddEquipmentCommand object
 */
public class AddEquipmentCommandParser implements Parser<AddEquipmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEquipmentCommand
     * and returns an AddEquipmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEquipmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL,
                        CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_SERIALNUMBER, CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_SERIALNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddEquipmentCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        SerialNumber serialNumber = ParserUtil.parseSerialNumber(argMultimap.getValue(
                CliSyntax.PREFIX_SERIALNUMBER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));

        Equipment equipment = new Equipment(name, phone, email, address, serialNumber, tagList);

        return new AddEquipmentCommand(equipment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
