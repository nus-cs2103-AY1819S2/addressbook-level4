package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTALPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLINGPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Buyer.CUSTOMER_TYPE_BUYER;
import static seedu.address.model.person.Landlord.CUSTOMER_TYPE_LANDLORD;
import static seedu.address.model.person.Seller.CUSTOMER_TYPE_SELLER;
import static seedu.address.model.person.Tenant.CUSTOMER_TYPE_TENANT;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_REMARK, PREFIX_ADDRESS, PREFIX_SELLINGPRICE, PREFIX_RENTALPRICE, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CUSTOMER, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_REMARK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        String customer = argMultimap.getValue(PREFIX_CUSTOMER).get();
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());

        switch (customer) {
        case CUSTOMER_TYPE_BUYER:
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                    || argMultimap.getValue(PREFIX_RENTALPRICE).isPresent()
                    || argMultimap.getValue(PREFIX_SELLINGPRICE).isPresent()
                    || argMultimap.getValue(PREFIX_TAG).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_BUYER));
            }
            Buyer buyer = new Buyer(name, phone, email, remark);
            return new AddCommand(buyer);
        case CUSTOMER_TYPE_SELLER:
            if (argMultimap.getValue(PREFIX_SELLINGPRICE).isPresent()
                    && argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                    && !argMultimap.getValue(PREFIX_RENTALPRICE).isPresent()) {
                Price sellingPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_SELLINGPRICE).get());
                Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
                Seller seller = new Seller(name, phone, email, remark, new Property(PropertyType.SELLING,
                        address, sellingPrice, tagList));
                return new AddCommand(seller);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_SELLER));
            }
        case CUSTOMER_TYPE_LANDLORD:
            if (argMultimap.getValue(PREFIX_RENTALPRICE).isPresent()
                    && argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                    && !argMultimap.getValue(PREFIX_SELLINGPRICE).isPresent()) {
                Price rentalPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_RENTALPRICE).get());
                Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
                Landlord landlord = new Landlord(name, phone, email, remark,
                        new Property(PropertyType.RENTAL, address, rentalPrice, tagList));
                return new AddCommand(landlord);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_LANDLORD));
            }
        case CUSTOMER_TYPE_TENANT:
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                    || argMultimap.getValue(PREFIX_RENTALPRICE).isPresent()
                    || argMultimap.getValue(PREFIX_SELLINGPRICE).isPresent()
                    || argMultimap.getValue(PREFIX_TAG).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_TENANT));
            }
            Tenant tenant = new Tenant(name, phone, email, remark);
            return new AddCommand(tenant);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
