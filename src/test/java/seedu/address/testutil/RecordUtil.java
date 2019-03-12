package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.Set;

import seedu.address.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.address.logic.commands.SpendCommand;
import seedu.address.model.record.Record;
import seedu.address.model.category.Category;



/**
 * A utility class for Record.
 */
public class RecordUtil {

    /**
     * Returns an add command string for adding the {@code record}.
     * @param record
     */
    public static String getAddCommand(Record record) {
        return SpendCommand.COMMAND_WORD + " " + getRecordDetails(record);
    }

    /**
     * Returns the part of command string for the given {@code record}'s details.
     * @param record
     */
    public static String getRecordDetails(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + record.getName().fullName + " ");
        sb.append(PREFIX_AMOUNT + record.getAmount().value + " ");
        sb.append(PREFIX_DATE + record.getDate().value + " ");
        record.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.categoryName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecordDescriptor}'s details.
     */
    public static String getEditRecordDescriptorDetails(EditRecordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> tags = descriptor.getCategories().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                tags.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.categoryName).append(" "));
            }
        }
        return sb.toString();
    }
}
