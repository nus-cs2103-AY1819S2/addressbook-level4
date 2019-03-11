package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Race("Chinese"), new Address("Blk 30 Geylang Street 29, #06-40"), new School("NUS"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Professor", "SDE"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Race("Chinese"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new School("NTU"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Lawyer"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Race("Others"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new School("SMU"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Doctor"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Race("Chinese"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new School("SUSS"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Data Scientist"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Race("Malay"), new Address("Blk 47 Tampines Street 20, #17-35"), new School("SIT"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Software Engineer"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Race("Indian"), new Address("Blk 45 Aljunied Street 85, #11-31"), new School("SUTD"),
                    new Major("CS"), getKnownProgLangSet("python"), getPastJobSet("Professor"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a past job set containing the list of strings given.
     */
    public static Set<KnownProgLang> getKnownProgLangSet(String... strings) {
        return Arrays.stream(strings)
            .map(KnownProgLang::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a past job set containing the list of strings given.
     */
    public static Set<PastJob> getPastJobSet(String... strings) {
        return Arrays.stream(strings)
            .map(PastJob::new)
            .collect(Collectors.toSet());
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
