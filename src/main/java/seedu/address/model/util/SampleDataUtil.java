package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
import seedu.address.model.property.PropertyType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Buyer(new Name("Alvin Quek"), new Phone("94563459"), new Email("alvinquek@example.com"),
                new Remark("I am a buyer")),
            new Seller(new Name("Bernard Chan"), new Phone("86495582"), new Email("bernardchan@example.com"),
                new Remark("I am a seller"),
                new Property(PropertyType.SELLING, new Address("Block 321 Clementi Ave 5 #12-08 "),
                new Price("750000"), getTagSet("MRT", "4Room"))),
            new Landlord(new Name("Calvin Pratt"), new Phone("94226854"), new Email("calvinpratt@example.com"),
                new Remark("I am a Landlord"),
                new Property(PropertyType.RENTAL, new Address("Block 323 Clementi Ave 5 #11-12 "),
                new Price("2500"), getTagSet("3Room"))),
            new Tenant(new Name("Damon Khoo"), new Phone("98256372"), new Email("damonkhoo@example.com"),
                new Remark("I am a tenant")),
            new Buyer(new Name("Francis Chan"), new Phone("82569732"), new Email("francischan@example.com"),
                new Remark("I am also a buyer")),
            new Seller(new Name("Gillian Cook"), new Phone("87564931"), new Email("gilliancook@example.com"),
                new Remark("I am also a seller"),
                new Property(PropertyType.SELLING, new Address("Blk 444 Pasir Ris Dr 6, #01-43"),
                new Price("500000"), getTagSet("4Room")))
        };
    }

    public static Person[] getSampleArchivedPersons() {
        return new Person[] {
            new Buyer(new Name("James Lee"), new Phone("92536734"), new Email("jameslee@example.com"),
                    new Remark("I am an archived buyer")),
            new Seller(new Name("Lee Yi Tian"), new Phone("86427462"), new Email("leeyitian@example.com"),
                    new Remark("I am an archived seller"), new Property(PropertyType.SELLING,
                    new Address("Blk 150 Tampines Street 12, #17-35"), new Price("500000"),
                    getTagSet("3Room", "new"))),
            new Landlord(new Name("Ricky Young"), new Phone("95426123"), new Email("rickyoung@example.com"),
                    new Remark("I am an archived landlord"), new Property(PropertyType.RENTAL,
                    new Address("10 Ocean Dr"), new Price("10000"),
                    getTagSet("seaView", "bungalow"))),
        };
    }

    public static Person[] getSamplePinnedPersons() {
        return new Person[] {
            new Buyer(new Name("Philip Fu"), new Phone("85427482"), new Email("philipfu@example.com"),
                new Remark("I am a pinned buyer")),
            new Seller(new Name("Irwin King"), new Phone("86258492"), new Email("irwinking@example.com"),
                new Remark("I am a pinned seller"), new Property(PropertyType.SELLING,
                new Address("Blk 345 Clementi Ave 5, #04-04"), new Price("600000"),
                getTagSet("MRT", "4Room"))),
            new Landlord(new Name("Jimmy Lee"), new Phone("95527572"), new Email("jimmylee@example.com"),
                new Remark("I am a pinned landlord"), new Property(PropertyType.RENTAL,
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

    public static ReadOnlyAddressBook getSampleArchiveBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person sampleArchivedPerson : getSampleArchivedPersons()) {
            sampleAb.addPerson(sampleArchivedPerson);
        }
        return sampleAb;
    }

    public static ReadOnlyAddressBook getSamplePinBook() {
        AddressBook sampleAb = new AddressBook();
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
