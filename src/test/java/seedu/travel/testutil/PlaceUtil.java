package seedu.travel.testutil;

import static seedu.travel.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_COUNTRY_CODE;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DATE_VISITED;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.travel.logic.commands.AddCommand;
import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.model.place.Place;
import seedu.travel.model.tag.Tag;

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
        sb.append(PREFIX_COUNTRY_CODE + place.getCountryCode().code + " ");
        sb.append(PREFIX_DATE_VISITED + place.getDateVisited().date + " ");
        sb.append(PREFIX_RATING + place.getRating().value + " ");
        sb.append(PREFIX_DESCRIPTION + place.getDescription().value + " ");
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
        descriptor.getCountryCode().ifPresent(countryCode -> sb.append(PREFIX_COUNTRY_CODE)
            .append(countryCode.code).append(" "));
        descriptor.getDateVisited().ifPresent(dateVisited -> sb.append(PREFIX_DATE_VISITED)
            .append(dateVisited.date).append(" "));
        descriptor.getRating().ifPresent(rating -> sb.append(PREFIX_RATING).append(rating.value).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION).append(description.value)
                .append(" "));
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
