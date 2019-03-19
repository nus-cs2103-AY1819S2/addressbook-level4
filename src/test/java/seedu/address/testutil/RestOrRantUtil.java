package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.BillCommand;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
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
    public static String getAddToOrderCommand(OrderItem orderItem) {
        return AddToOrderCommand.COMMAND_WORD + " " + getOrderItemDetails(orderItem);
    }

    /**
     * Returns an add command string for adding the {@code MenuItem}.
     */
    public static String getAddMenuCommand(MenuItem menuItem) {
        return AddToMenuCommand.COMMAND_WORD + " " + getMenuItemDetails(menuItem);
    }

    //TODO: Check if this method is relevant. Delete if it is not
    //TODO: Can I leave this here until I execute my totalRevenue command?
    /**
     * Returns an add command string for adding the {@code Bill}.
     */
    public static String getBillCommand(Bill bill) {
        return BillCommand.COMMAND_WORD + " " + getBillDetails(bill);
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

    //TODO: Check if this method is relevant. Delete if it is not
    /**
     * Return the part of command string for the given {@code dailyRevenue}'s details.
     */
    public static String getBillDetails(Bill bill) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DAY + bill.getDay().toString() + " ");
        sb.append(PREFIX_MONTH + bill.getMonth().toString() + " ");
        sb.append(PREFIX_YEAR + bill.getYear().toString() + " ");
        sb.append("Total Bill: $" + bill.getTotalBill() + "\n");
        sb.append(bill.getReceipt());
        return sb.toString();
    }

    //TODO: Check if this method is relevant. Delete if it is not
    //Can I leave this here until I execute my totalRevenue command?
    /**
     * Return the part of command string for the given {@code dailyRevenue}'s details.
     */
    public static String getDailyRevenueDetails(DailyRevenue dailyRevenue) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DAY + dailyRevenue.getDay().toString() + " ");
        sb.append(PREFIX_MONTH + dailyRevenue.getMonth().toString() + " ");
        sb.append(PREFIX_YEAR + dailyRevenue.getYear().toString() + " ");
        sb.append("Total Revenue: $" + dailyRevenue.getTotalDailyRevenue());
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
