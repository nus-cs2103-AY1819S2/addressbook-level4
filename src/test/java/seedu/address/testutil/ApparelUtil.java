package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOTHING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.apparel.Apparel;

/**
 * A utility class for Apparel.
 */
public class ApparelUtil {

    /**
     * Returns an add command string for adding the {@code apparel}.
     */
    public static String getAddCommand(Apparel apparel) {
        return AddCommand.COMMAND_WORD + " " + getApparelDetails(apparel);
    }

    /**
     * Returns the part of command string for the given {@code apparel}'s details.
     */
    public static String getApparelDetails(Apparel apparel) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + apparel.getName().fullName + " ");
        sb.append(PREFIX_COLOR + apparel.getColor().toString() + " ");
        sb.append(PREFIX_CLOTHING_TYPE + apparel.getClothingType().toString() + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code AvailablePersonDescriptor}'s details.
     */
    public static String getEditApparelDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getColor().ifPresent(color -> sb.append(PREFIX_COLOR).append(color.toString()).append(" "));
        descriptor.getClothingType().ifPresent(clothingType -> sb.append(PREFIX_CLOTHING_TYPE).append(
                clothingType.toString()).append(" "));
        return sb.toString();
    }
}
