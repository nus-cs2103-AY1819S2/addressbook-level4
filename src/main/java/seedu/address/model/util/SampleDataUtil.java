package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.SkillsTag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet(Arrays.asList("PHP", "Java"), Arrays.asList("UIDeveloper", "Graphics"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet(Arrays.asList("HTML", "JavaScript"), Arrays.asList("FrontEnd", "WebDev"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet(Arrays.asList("Cplusplus", "Java"), Arrays.asList("Security"))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet(Arrays.asList("Python", "SQL"), Arrays.asList("Databases", "SystemsAnalyst"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet(Arrays.asList("Java", "JavaScript"), Arrays.asList("SoftwareEngineer"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet(Arrays.asList("Swift", "Java", "Linux"), Arrays.asList("OSDeveloper")))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<SkillsTag> getTagSet(List<String> skills, List<String> positions) {
        final Set<SkillsTag> tagSet = new HashSet<>();
        for (String skill : skills) {
            tagSet.add(new SkillsTag(skill, "yellow"));
        }
        for (String pos : positions) {
            tagSet.add(new SkillsTag(pos, "pink"));
        }

        return tagSet;
    }

}
