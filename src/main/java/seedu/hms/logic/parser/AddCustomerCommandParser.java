package seedu.hms.logic.parser;

import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.hms.logic.commands.AddCustomerCommand;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class AddCustomerCommandParser implements Parser<AddCustomerCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCustomerCommand
     * and returns an AddCustomerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCustomerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE_OF_BIRTH, PREFIX_EMAIL,
                PREFIX_IDENTIFICATION_NUMBER,
                PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_IDENTIFICATION_NUMBER, PREFIX_EMAIL)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        DateOfBirth dob = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).orElse(""));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        IdentificationNo idnum = ParserUtil.parseIdNum(argMultimap.getValue(PREFIX_IDENTIFICATION_NUMBER).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Customer customer = new Customer(name, phone, dob, email, idnum, address, tagList);

        return new AddCustomerCommand(customer);
    }


}
