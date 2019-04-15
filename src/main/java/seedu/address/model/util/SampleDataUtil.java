package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Degree;
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
                new Education("NUS"), new Gpa("3.2"), new Degree("Bachelors"),
                new Address("Blk 30 Geylang Street 29, #06-40, Singapore"), getTagSet(Arrays.asList("PHP", "Java",
                "Debugging", "C#", "Internet Protocols"), Arrays.asList("UI Developer",
                "Advanced Graphics Designer"), Arrays.asList("Steve Jobs", "Mark Cuban"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Education("NUS"), new Gpa("2.5"), new Degree("Associates"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18, Singapore"),
                getTagSet(Arrays.asList("HTML", "JavaScript", "C++", "Perl", "Linux", "Task Management"),
                Arrays.asList("Front End Specialist", "Software Engineer"), Arrays.asList("Mark Zuckerberg"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Education("NUS"), new Gpa("3.0"), new Degree("Bachelors"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04, Singapore"),
                getTagSet(Arrays.asList("C++", "Java", "Swift", "Certificates", "Networking"),
                Arrays.asList("Cyber Crime "), Arrays.asList("Barack Obama"))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Education("NUS"), new Gpa("3"), new Degree("Bachelors"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43, Singapore"),
                getTagSet(Arrays.asList("Python", "SQL", "Database Administration", "Project Management",
                "Software Testing"), Arrays.asList("Databases", "Systems " + "Analyst"),
                Arrays.asList("Bill Gates", "Frederick Brooks"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Education("NUS"), new Gpa("2.1"), new Degree("Masters"),
                new Address("Blk 47 Tampines Street 20, #17-35, Singapore"),
                getTagSet(Arrays.asList("Java", "JavaScript", "Excel", "Hadoop", "Market Analysis"),
                Arrays.asList("Software ", "Engineer", "Financial Analyst"),
                Arrays.asList("Jeff Bezos", "Warren Buffett"))),
            new Person(new Name("Susan McDonald"), new Phone("35810495"), new Email("susanM@gmail.com"),
                new Education("Yale"), new Gpa("4.0"), new Degree("Bachelors"),
                new Address("32 Independence Way, New York NY, US"),
                getTagSet(Arrays.asList("Java", "HTML", "CSS", "JavaScript", "Swift"), Arrays.asList("Front "
                + "End Specialist", "UI Developer", "Project Manager"), Arrays.asList("Jeff Bezos"))),
            new Person(new Name("Tony Stark"), new Phone("92874639"), new Email("tonys@avengers.com"),
                new Education("MIT"), new Gpa("4.0"), new Degree("PHD"),
                new Address("911 Avengers Way, New York NY, US"), getTagSet(Arrays.asList("Java",
                "Electrical Engineering", "MATLab", "Software Development", "Leadership",
                "Presentation Skills"), Arrays.asList("Software Engineer", "Project Manager"),
                Arrays.asList("Stan Lee"))),
            new Person(new Name("Bruce Wayne"), new Phone("92875639"), new Email("bruce@wayne.com"),
                new Education("Stanford"), new Gpa("3.6"), new Degree("Bachelors"),
                new Address("1 Wayne Manor, New York NY, US"),
                getTagSet(Arrays.asList("Hardware Testing", "Business Analysis", "Finance", "Cyber Crime"),
                Arrays.asList("Project Consultant", "Crime Analyst"), Arrays.asList("Thomas Wayne"))),
            new Person(new Name("Peter Parker"), new Phone("92874339"), new Email("peterp@gmail.com"),
                new Education("NUS"), new Gpa("2.0"), new Degree("Bachelors"),
                new Address("99 Sunflower Street, Singapore"),
                getTagSet(Arrays.asList("Electrical Engineering", "Java", "Python", "Design",
                "Photography"), Arrays.asList("Advanced Graphics Designer", "Software " + "Testing"),
                Arrays.asList("Stan Lee", "Ben Parker"))),
            new Person(new Name("Claire Smith"), new Phone("98765432"), new Email("claireS@gmail.com"),
                new Education("Oxford"), new Gpa("2.7"), new Degree("PHD"),
                new Address("12 Biscuits Way, Oxford, UK"), getTagSet(Arrays.asList("C#", "PHP", "Linux", "C++",
                "Finance"), Arrays.asList("Systems " + "Analyst"), Arrays.asList("Elon Musk"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Education("NUS"), new Gpa("2.1"), new Degree("Masters"),
                new Address("Blk 45 Aljunied Street 85, #11-31, Singapore"), getTagSet(Arrays.asList("Swift", "Java",
                "Linux", "Operating Systems", "Graphic Design", "Hardware"),
                Arrays.asList("Project Consultant"), Arrays.asList("Steve Jobs"))),
            new Person(new Name("Susan Cornfield"), new Phone("12224448"), new Email("susan@gmail.com"),
                 new Education("NUS"), new Gpa("2.3"), new Degree("PHD"),
                 new Address("99 Sunflower Street, Singapore"),
                 getTagSet(Arrays.asList("C++", "Python", "Design", "Scala"),
                 Arrays.asList("Advanced Graphics Designer", "Software Developer"),
                 Arrays.asList("John Smith", "Ben Affleck"))),
            new Person(new Name("Black Widow"), new Phone("11115555"), new Email("blackw@gmail.com"),
                 new Education("NUS"), new Gpa("2.9"), new Degree("Masters"),
                 new Address("UTown, Singapore"),
                 getTagSet(Arrays.asList("Art History", "Karate", "Python", "Design", "Photography"),
                 Arrays.asList("Artist", "Fighting Instructor"),
                 Arrays.asList("Captain America", "Hulk"))),
            new Person(new Name("Jhonny Bravo"), new Phone("11115666"), new Email("bravo@yahoo.com"),
                new Education("NUS"), new Gpa("1.4"), new Degree("Masters"),
                new Address("PGPR, Singapore"),
                getTagSet(Arrays.asList("C++", "Scheme"),
                Arrays.asList("Developer", "Tester"),
                Arrays.asList("Ben Benson"))),
            new Person(new Name("Yoo Jae Suk"), new Phone("11115666"), new Email("yoo@yahoo.com"),
                new Education("NUS"), new Gpa("2.8"), new Degree("PHD"),
                new Address("Marina Bay Road, Singapore"),
                getTagSet(Arrays.asList("Acting", "Speaking"),
                Arrays.asList("Comedian", "Actor"),
                Arrays.asList("Ji Hyo", "Jong Kook"))),
            new Person(new Name("Ji Suk Jin"), new Phone("11115776"), new Email("jisukjin@yahoo.com"),
                new Education("NUS"), new Gpa("2.3"), new Degree("Bachelors"),
                new Address("Orchard Road, Singapore"),
                getTagSet(Arrays.asList("Python", "Java", "Public Speaking"),
                Arrays.asList("Software Engineer"),
                Arrays.asList("Lee Kwang Soo"))),
            new Person(new Name("Baris Batuhan Topal"), new Phone("12398746"), new Email("bbt@gmail.com"),
                new Education("NUS"), new Gpa("2.9"), new Degree("Bachelors"),
                new Address("UTown Residence, Singapore"),
                getTagSet(Arrays.asList("Python", "Java", "C++"),
                Arrays.asList("Software Engineer", "Testing", "Developer"),
                Arrays.asList("Daniel Greenhouse", "Abby Williams", "Charlotte Nixon"))),
            new Person(new Name("Ha Ha"), new Phone("77777777"), new Email("haha@gmail.com"),
                new Education("NUS"), new Gpa("2.6"), new Degree("Masters"),
                new Address("Raffles Hall, Singapore"),
                getTagSet(Arrays.asList("Acting", "Singing", "Python"),
                Arrays.asList("Software Tester", "Singer"),
                Arrays.asList("Kang Gary", "Jeon Soo Min"))),
            new Person(new Name("Chris Evans"), new Phone("66666666"), new Email("captainamerica@gmail.com"),
                new Education("Stanford"), new Gpa("4.0"), new Degree("PHD"),
                new Address("Times Square, NY, US"),
                getTagSet(Arrays.asList("Acting", "Fighting", "Singing"),
                Arrays.asList("Actor", "Superhero"),
                Arrays.asList("Scarlett Johansson", "Chris Hemsworth"))),
            new Person(new Name("Edsger Dijkstra"), new Phone("12349876"), new Email("dijkstralgorithm@legend.com"),
                new Education("University of Amsterdam"), new Gpa("3.7"), new Degree("PHD"),
                new Address("Amsterdam, Netherlands"),
                getTagSet(Arrays.asList("C++", "Algorithms", "Graph"),
                Arrays.asList("Mathematician", "Professor", "Programmer", "Software Engineer"),
                Arrays.asList("John Reif", "Belman Ford"))),
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
