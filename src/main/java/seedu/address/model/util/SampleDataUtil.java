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
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Buyer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Remark("I am a buyer")),
            new Seller(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Remark("I am a seller"),
                new Property("selling", new Address("Blk 131 Geylang East Ave 1, #06-40"),
                new Price("750000"), getTagSet("MRT", "4Room"))),
            new Landlord(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Remark("I am a Landlord"),
                new Property("rental", new Address("Blk 11 Marsiling Dr, #11-04"),
                new Price("2500"), getTagSet("3Room"))),
            new Tenant(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Remark("I am a tenant")),
            new Buyer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Remark("I am also a buyer")),
            new Seller(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Remark("I am also a seller"),
                new Property("selling", new Address("Blk 444 Pasir Ris Dr 6, #01-43"),
                new Price("500000"), getTagSet("4Room")))
        };
    }

    public static Person[] getSampleArchivedPersons() {
        return new Person[] {
            new Buyer(new Name("James Lee"), new Phone("98765432"), new Email("jameslee@example.com"),
                    new Remark("I am an archived buyer")),
            new Seller(new Name("Tan Ah Beng"), new Phone("87654321"), new Email("tab@example.com"),
                    new Remark("I am an archived seller"), new Property("selling",
                    new Address("Blk 150 Tampines Street 12, #17-35"), new Price("500000"),
                    getTagSet("3Room", "new"))),
            new Landlord(new Name("Ricky Young"), new Phone("91827364"), new Email("rickyoung@example.com"),
                    new Remark("I am an archived landlord"), new Property("rental",
                    new Address("10 Ocean Drive"), new Price("10000"),
                    getTagSet("seaView", "bungalow"))),
        };
    }

    public static Person[] getSamplePinnedPersons() {
        return new Person[] {
            new Buyer(new Name("Philip Fu"), new Phone("83070005"), new Email("philipfu@example.com"),
                new Remark("I am a pinned buyer")),
            new Seller(new Name("Irwin King"), new Phone("83070006"), new Email("irwinking@example.com"),
                new Remark("I am a pinned seller"), new Property("selling",
                new Address("Blk 345 Clementi Ave 5, #04-04"), new Price("600000"),
                getTagSet("MRT", "4Room"))),
            new Landlord(new Name("Jimmy Lee"), new Phone("83070007"), new Email("jimmylee@example.com"),
                new Remark("I am a pinned landlord"), new Property("rental",
                new Address("Blk 346 Clementi Ave 5, #05-05"), new Price("2500"),
                getTagSet("MRT", "4Room")))
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
