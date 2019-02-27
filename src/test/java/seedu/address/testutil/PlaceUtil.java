package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.address.model.place.Place;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Place.
 */
public class PlaceUtil {

    /**
     * Returns an add command string for adding the {@code place}.
     */
    public static String getAddCommand(Place place) {
        return AddCommand.COMMAND_WORD + " " + getPlaceDetails(place);
    }

    /**
     * Returns the part of command string for the given {@code place}'s details.
     */
    public static String getPlaceDetails(Place place) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + place.getName().fullName + " ");
        sb.append(PREFIX_PHONE + place.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + place.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + place.getAddress().value + " ");
        place.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPlaceDescriptor}'s details.
     */
    public static String getEditPlaceDescriptorDetails(EditPlaceDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
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
