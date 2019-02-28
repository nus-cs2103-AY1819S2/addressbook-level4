package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.place.Address;
import seedu.address.model.place.Description;
import seedu.address.model.place.Name;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Place[] getSamplePlaces() {
        return new Place[] {
            new Place(new Name("Alex Yeoh"), new Rating("1"), new Description("No description"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Place(new Name("Bernice Yu"), new Rating("5"), new Description("I love this place"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Place(new Name("Charlotte Oliveiro"), new Rating("3"), new Description("I love the coffee here"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Place(new Name("David Li"), new Rating("4"), new Description("I love the ambience here"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Place(new Name("Irfan Ibrahim"), new Rating("4"), new Description("I love the night life here"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Place(new Name("Roy Balakrishnan"), new Rating("3"),
                    new Description("This place exudes luxury"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Place samplePlace : getSamplePlaces()) {
            sampleAb.addPlace(samplePlace);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
