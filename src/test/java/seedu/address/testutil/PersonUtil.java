package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTALPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLINGPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        if (person instanceof Buyer) {
            return AddCommand.COMMAND_WORD + " c/buyer " + getPersonDetails(person);
        }
        if (person instanceof Seller) {
            return AddCommand.COMMAND_WORD + " c/seller " + getPersonDetails(person);
        }
        if (person instanceof Landlord) {
            return AddCommand.COMMAND_WORD + " c/landlord " + getPersonDetails(person);
        }
        if (person instanceof Tenant) {
            return AddCommand.COMMAND_WORD + " c/tenant " + getPersonDetails(person);
        }
        return AddCommand.COMMAND_WORD + " c/buyer " + getPersonDetails(person);
    }

    /**
     * Returns an add command string for adding the {@code buyer}.
     */
    public static String getAddCommand(Buyer buyer) {
        return AddCommand.COMMAND_WORD + " c/buyer " + getPersonDetails(buyer);
    }

    /**
     * Returns an add command string for adding the {@code seller}.
     */
    public static String getAddCommand(Seller seller) {
        return AddCommand.COMMAND_WORD + " c/seller " + getPersonDetails(seller);
    }

    /**
     * Returns an add command string for adding the {@code landlord}.
     */
    public static String getAddCommand(Landlord landlord) {
        return AddCommand.COMMAND_WORD + " c/landlord " + getPersonDetails(landlord);
    }

    /**
     * Returns an add command string for adding the {@code tenant}.
     */
    public static String getAddCommand(Tenant tenant) {
        return AddCommand.COMMAND_WORD + " c/tenant " + getPersonDetails(tenant);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_REMARK + person.getRemark().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code seller}'s details.
     */
    public static String getPersonDetails(Seller seller) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + seller.getName().fullName + " ");
        sb.append(PREFIX_PHONE + seller.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + seller.getEmail().value + " ");
        sb.append(PREFIX_REMARK + seller.getRemark().value + " ");
        sb.append(PREFIX_ADDRESS + seller.getAddress().value + " ");
        sb.append(PREFIX_SELLINGPRICE + seller.getSellingPrice().toString() + " ");
        seller.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code landlord}'s details.
     */
    public static String getPersonDetails(Landlord landlord) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + landlord.getName().fullName + " ");
        sb.append(PREFIX_PHONE + landlord.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + landlord.getEmail().value + " ");
        sb.append(PREFIX_REMARK + landlord.getRemark().value + " ");
        sb.append(PREFIX_ADDRESS + landlord.getAddress().value + " ");
        sb.append(PREFIX_RENTALPRICE + landlord.getRentalPrice().toString() + " ");
        landlord.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK).append(remark.value).append(" "));
        return sb.toString();
    }
}
