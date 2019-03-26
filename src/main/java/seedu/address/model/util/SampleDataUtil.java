package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ArchiveBook;
import seedu.address.model.PinBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveBook;
import seedu.address.model.ReadOnlyPinBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Person[] getSampleArchivedPersons() {
        return new Person[] {
            new Person(new Name("James Lee"), new Phone("98765432"), new Email("jameslee@example.com"),
                    new Address("Blk 123 Clementi Road, #02-02"),
                    getTagSet("friends")),
            new Person(new Name("Tan Ah Beng"), new Phone("87654321"), new Email("tab@example.com"),
                    new Address("Blk 456 Woodlands Ave 1, #10-10"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Ricky Young"), new Phone("91827364"), new Email("rickyoung@example.com"),
                    new Address("10 Ocean Drive"),
                    getTagSet("family")),
        };
    }

    public static Person[] getSamplePinnedPersons() {
        return new Person[] {
             new Person(new Name("Philip Fu"), new Phone("83070005"), new Email("philipfu@example.com"),
                 new Address("Blk 502 Clementi Road, #03-03"),
                 getTagSet("neighbours")),
             new Person(new Name("Irwin King"), new Phone("83070006"), new Email("irwinking@example.com"),
                 new Address("Blk 504 Clementi Road, #04-04"),
                 getTagSet("partners", "friends")),
             new Person(new Name("Jimmy Lee"), new Phone("83070007"), new Email("jimmylee@example.com"),
                 new Address("Blk 505 Clementi Road, #05-05"),
                 getTagSet("family")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyArchiveBook getSampleArchiveBook() {
        ArchiveBook sampleAb = new ArchiveBook();
        for (Person sampleArchivedPerson : getSampleArchivedPersons()) {
            sampleAb.addPerson(sampleArchivedPerson);
        }
        return sampleAb;
    }

    public static ReadOnlyPinBook getSamplePinBook() {
        PinBook sampleAb = new PinBook();
        for (Person samplePinnedPerson : getSamplePinnedPersons()) {
            sampleAb.addPerson(samplePinnedPerson);
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
