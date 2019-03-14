package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.DateOfBirth;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.IdentificationNo;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Customer[] getSampleCustomers() {
        return new Customer[] {
            new Customer(new Name("Al()ex Yeoh"), new Phone("87438807"), new DateOfBirth ("28/05/1999"),
                new Email("alexyeoh@example.com"),
                new IdentificationNo("987653"), new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Customer(new Name("Bernice Yu"), new Phone("99272758"), new DateOfBirth ("28/01/1999"),
                new Email("berniceyu@example.com"),
                new IdentificationNo("98764353"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Customer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new DateOfBirth ("28/10/1999"),
                new Email("charlotte@example.com"),
                new IdentificationNo("981653"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Customer(new Name("David Li"), new Phone("91031282"), new DateOfBirth ("29/05/1999"),
                new Email("lidavid@example.com"),
                new IdentificationNo("9877853"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Customer(new Name("Irfan Ibrahim"), new Phone("92492021"), new DateOfBirth ("02/05/1989"),
                new Email("irfan@example.com"),
                new IdentificationNo("987973"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Customer(new Name("Roy Balakrishnan"), new Phone("92624417"), new DateOfBirth ("09/12/2002"),
                new Email("royb@example.com"),
                new IdentificationNo("9876113"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleAb.addCustomer(sampleCustomer);
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
