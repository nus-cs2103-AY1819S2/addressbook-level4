package seedu.finance.testutil;

import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.finance.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.model.record.Record;

/**
 * A utility class for Record.
 */
public class RecordUtil {

    /**
     * Returns an add command string for adding the {@code record}.
     * @param record
     */
    public static String getSpendCommand(Record record) {
        return SpendCommand.COMMAND_WORD + " " + getRecordDetails(record);
    }

    /**
     * Returns the part of command string for the given {@code record}'s details.
     * @param record
     */
    public static String getRecordDetails(Record record) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + record.getName().fullName + " ");
        sb.append(PREFIX_AMOUNT + record.getAmount().toString() + " ");
        sb.append(PREFIX_DATE + record.getDate().toString() + " ");
        sb.append(PREFIX_CATEGORY + record.getCategory().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + record.getDescription().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecordDescriptor}'s details.
     */
    public static String getEditRecordDescriptorDetails(EditRecordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY)
                .append(category.toString()).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.toString()).append(" "));
        return sb.toString();
    }
}
