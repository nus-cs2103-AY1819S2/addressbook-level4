package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.logic.commands.AddItemToMenuCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.AddTableCommand;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.Table;

/**
 * A utility class for Person.
 */
public class RestOrRantUtil {

//    /**
//     * Returns an add command string for adding the {@code person}.
//     */
//    public static String getAddCommand(Person person) {
//        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
//    }

    /**
     * Returns an add command string for adding the (@code table).
     */
    public static String getAddTableCommand(Table table) {
        return AddTableCommand.COMMAND_WORD + " " + getTableDetails(table);
    }

    /**
     * Returns an add command string for adding the {@code OrderItem}.
     */
    public static String getAddOrderCommand(OrderItem orderItem) {
        return AddOrderCommand.COMMAND_WORD + " " + getOrderItemDetails(orderItem);
    }

    /**
     * Returns an add command string for adding the {@code MenuItem}.
     */
    public static String getAddMenuCommand(MenuItem menuItem) {
        return AddItemToMenuCommand.COMMAND_WORD + " " + getMenuItemDetails(menuItem);
    }

//    /**
//     * Returns an add command string for adding the {@code person}.
//     */
//    public static String getAddAlias(Person person) {
//        return AddCommand.COMMAND_ALIAS + " " + getPersonDetails(person);
//    }

//    /**
//     * Returns the part of command string for the given {@code person}'s details.
//     */
//    public static String getPersonDetails(Person person) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + person.getName().fullName + " ");
//        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
//        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
//        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
//        person.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }

    /**
     * Return the part of command string for the given {@code table}'s details.
     */
    public static String getTableDetails(Table table) {
        StringBuilder sb = new StringBuilder();
        sb.append(table.getTableStatus().numberOfSeats);
        return sb.toString();
    }

    /**
     * Return the part of command string for the given {@code orderItem}'s details.
     */
    public static String getOrderItemDetails(OrderItem orderItem) {
        StringBuilder sb = new StringBuilder();
        sb.append(orderItem.getMenuItemCode() + " ");
        sb.append(orderItem.getQuantity());
        return sb.toString();
    }

    /**
     * Return the part of command string for the given {@code menuItem}'s details.
     */
    public static String getMenuItemDetails(MenuItem menuItem) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + menuItem.getName().toString() + " ");
        sb.append(PREFIX_CODE + menuItem.getCode().toString() + " ");
        sb.append(PREFIX_PRICE + menuItem.getPrice().toString() + " ");
        return sb.toString();
    }

    // TODO: figure out if we need this.
//    /**
//     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
//     */
//    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
}
