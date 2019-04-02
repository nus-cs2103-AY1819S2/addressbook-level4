package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gpa;
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
                    new Education("NUS"), new Gpa("3"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet(Arrays.asList("PHP", "Java", "Debugging", "C#", "Internet Protocols"), Arrays.asList(
                            "UI Developer",
                            "Advanced Graphics Designer"), Arrays.asList("Steve Jobs", "Mark Cuban"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Education("NUS"), new Gpa("3"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet(Arrays.asList("HTML", "JavaScript", "C++", "Perl", "Linux", "Task Management"),
                            Arrays.asList("Front End Specialist",
                            "Software Engineer"), Arrays.asList("Mark Zuckerberg"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Education("NUS"), new Gpa("3"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet(Arrays.asList("C++", "Java", "Swift", "Certificates", "Networking"), Arrays.asList(
                            "Cyber Crime "),
                            Arrays.asList("Barack Obama"))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Education("NUS"), new Gpa("3"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet(Arrays.asList("Python", "SQL", "Database Administration", "Project Management",
                            "Software Testing"),
                            Arrays.asList("Databases", "Systems " + "Analyst"),
                            Arrays.asList("Bill Gates", "Frederick Brooks"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Education("NUS"), new Gpa("3"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet(Arrays.asList("Java", "JavaScript", "Excel", "Hadoop", "Market Analysis"), Arrays.asList(
                            "Software ", "Engineer", "Financial Analyst"),
                            Arrays.asList("Jeff Bezos", "Warren Buffett"))),
            new Person(new Name("Susan McDonald"), new Phone("35810495"), new Email("susanM@gmail.com"),
                    new Education("Yale"), new Gpa("4"), new Address("32 Independence Way, New York NY"),
                    getTagSet(Arrays.asList("Java", "HTML", "CSS", "JavaScript", "Swift"), Arrays.asList("Front "
                            + "End Specialist", "UI Developer", "Project Manager"), Arrays.asList("Jeff Bezos"))),
            new Person(new Name("Tony Stark"), new Phone("92874639"), new Email("tonys@avengers.com"),
                    new Education("MIT"), new Gpa("4"), new Address("911 Avengers Way, New York NY"),
                    getTagSet(Arrays.asList("Java", "Electrical Engineering", "MATLab", "Software Development",
                            "Leadership", "Presentation Skills"), Arrays.asList("Software Engineer", "Project Manager"),
                            Arrays.asList("Stan Lee"))),
            new Person(new Name("Bruce Wayne"), new Phone("92875639"), new Email("bruce@wayne.com"),
                    new Education("Stanford"), new Gpa("4"), new Address("1 Wayne Manor, New York NY"),
                    getTagSet(Arrays.asList("Hardware Testing", "Business Analysis", "Finance", "Cyber Crime"),
                            Arrays.asList("Project Consultant", "Crime Analyst"), Arrays.asList("Thomas Wayne"))),
            new Person(new Name("Peter Parker"), new Phone("92874339"), new Email("peterp@gmail.com"),
                    new Education("Brooklyn High School"), new Gpa("4"), new Address("99 Sunflower Dr, Brooklyn "
                    + "NY"), getTagSet(Arrays.asList("Electrical Engineering", "Java", "Python",
                            "Design", "Photography"), Arrays.asList("Advanced Graphics Designer", "Software "
                            + "Testing"), Arrays.asList("Stan Lee"))),
            new Person(new Name("Claire Smith"), new Phone("98765432"), new Email("claireS@gmail.com"),
                    new Education("Oxford"), new Gpa("3"), new Address("12 Biscuits Way, Oxford"),
                    getTagSet(Arrays.asList("C#", "PHP", "Linux", "C++", "Finance"), Arrays.asList("Systems "
                                    + "Analyst"), Arrays.asList("Elon Musk"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Education("NUS"), new Gpa("3"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet(Arrays.asList("Swift", "Java", "Linux", "Operating Systems", "Graphic Design",
                            "Hardware"), Arrays.asList("Project Consultant"), Arrays.asList("Steve Jobs")))
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
    public static Set<SkillsTag> getTagSet(List<String> skills, List<String> positions, List<String> endorsements) {
        final Set<SkillsTag> tagSet = new HashSet<>();


        if (skills != null) {
            for (String skill : skills) {
                tagSet.add(new SkillsTag(skill, "skill"));
            }
        }

        if (positions != null) {
            for (String pos : positions) {
                tagSet.add(new SkillsTag(pos, "pos"));

            }
        }

        if (endorsements != null) {
            for (String end : endorsements) {
                tagSet.add(new SkillsTag(end, "endorse"));
            }
        }
        return tagSet;
    }
}
