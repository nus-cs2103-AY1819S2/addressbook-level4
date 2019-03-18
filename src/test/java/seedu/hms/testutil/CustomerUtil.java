package seedu.hms.testutil;

import static seedu.hms.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.hms.logic.commands.AddCustomerCommand;
import seedu.hms.logic.commands.EditCustomerCommand;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.tag.Tag;

/**
 * A utility class for Customer.
 */
public class CustomerUtil {

    /**
     * Returns an add command string for adding the {@code customer}.
     */
    public static String getAddCommand(Customer customer) {
        return AddCustomerCommand.COMMAND_WORD + " " + getCustomerDetails(customer);
    }

    /**
     * Returns the part of command string for the given {@code customer}'s details.
     */
    public static String getCustomerDetails(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + customer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + customer.getPhone().value + " ");
        sb.append(PREFIX_DATE_OF_BIRTH + customer.getDateOfBirth().value + " ");
        sb.append(PREFIX_EMAIL + customer.getEmail().value + " ");
        sb.append(PREFIX_IDENTIFICATION_NUMBER + customer.getIdNum().value + " ");
        sb.append(PREFIX_ADDRESS + customer.getAddress().value + " ");
        customer.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCustomerDescriptor}'s details.
     */
    public static String getEditCustomerDescriptorDetails(EditCustomerCommand.EditCustomerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getDateOfBirth().ifPresent(dob -> sb.append(PREFIX_DATE_OF_BIRTH).append(dob.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getIdNum().ifPresent(identificationNo -> sb.append(PREFIX_IDENTIFICATION_NUMBER)
            .append(identificationNo.value).append(" "));
        descriptor.getAddress().ifPresent(hms -> sb.append(PREFIX_ADDRESS).append(hms.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
