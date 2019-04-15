package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditMedicineDescriptor;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Medicine.
 */
public class MedicineUtil {

    /**
     * Returns an add command string for adding the {@code medicine}.
     */
    public static String getAddCommand(Medicine medicine) {
        return AddCommand.COMMAND_WORD + " " + getMedicineDetails(medicine);
    }

    /**
     * Returns the part of command string for the given {@code medicine}'s details.
     */
    public static String getMedicineDetails(Medicine medicine) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + medicine.getName().fullName + " ");
        sb.append(PREFIX_COMPANY + medicine.getCompany().companyName + " ");
        medicine.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMedicineDescriptor}'s details.
     */
    public static String getEditMedicineDescriptorDetails(EditMedicineDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.companyName).append(" "));
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
