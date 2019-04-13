package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityDescription;
import seedu.address.model.activity.ActivityLocation;
import seedu.address.model.activity.ActivityName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Major;
import seedu.address.model.person.MatricNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.YearOfStudy;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new MatricNumber("A0111111M"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Gender("Male"), new YearOfStudy("1"), new Major("Chemistry"),
                    getTagSet("Swimming")),
            new Person(new Name("Bernice Yu"), new MatricNumber("A0222222B"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Gender("Female"), new YearOfStudy("2"), new Major("Computer Science"),
                    getTagSet("Running", "Swimming")),
            new Person(new Name("Charlotte Oliveiro"), new MatricNumber("A0333333N"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Gender("Female"), new YearOfStudy("3"), new Major("Mathematics"),
                    getTagSet("Running")),
            new Person(new Name("David Li"), new MatricNumber("A0444444A"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Gender("Male"), new YearOfStudy("3"), new Major("Bioengineering"),
                    getTagSet("Soccer")),
            new Person(new Name("Irfan Ibrahim"), new MatricNumber("A0555555R"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Gender("Male"), new YearOfStudy("2"), new Major("Physics"),
                    getTagSet("Basketball")),
            new Person(new Name("Roy Balakrishnan"), new MatricNumber("A0777777U"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 4 Bedok Street 12, #12-31"), new Gender("Male"),
                    new YearOfStudy("2"), new Major("Physics"), getTagSet("Soccer")),
            new Person(new Name("Jonathan Ng"), new MatricNumber("A0888888H"), new Phone("89225657"),
                    new Email("jon@example.com"), new Address("Blk 15 Sengkang Drive 17, #02-31"), new Gender("Male"),
                    new YearOfStudy("6"), new Major("Medicine"), getTagSet("Hockey")),
            new Person(new Name("Amy Ang"), new MatricNumber("A0181234M"), new Phone("92621117"),
                    new Email("amya@example.com"), new Address("Blk 2 Clementi Street 23, #09-12"),
                    new Gender("Female"), new YearOfStudy("3"), new Major("Physics"), getTagSet("Swimming")),
            new Person(new Name("Jane Ong"), new MatricNumber("A0171453J"), new Phone("80113651"),
                        new Email("janeo@example.com"), new Address("Blk 5 Marine Parade Street 12, #11-31"),
                    new Gender("Female"), new YearOfStudy("4"), new Major("Economics"), getTagSet("Soccer"))
        };
    }

    public static Activity[] getSampleActivities() {
        return new Activity[] {
            new Activity(new ActivityName("CS2103 Help Session"), new ActivityDateTime("10/02/2019 1400"),
                new ActivityLocation("Com1 02-06"), new ActivityDescription("Bring Laptop"),
                new ArrayList<>(Arrays.asList(new MatricNumber("A0111111M"), new MatricNumber("A0555555R")))),
            new Activity(new ActivityName("Club Annual Meeting"), new ActivityDateTime("11/02/2019 1600"),
                new ActivityLocation("Computer Club Room"), new ActivityDescription("Summary of past year activities"),
                new ArrayList<>(Arrays.asList(new MatricNumber("A0222222B"), new MatricNumber("A0333333N"),
                        new MatricNumber("A0555555R"), new MatricNumber("A0444444A"), new MatricNumber("A0777777U"),
                        new MatricNumber("A0888888H")))),
            new Activity(new ActivityName("Avenger Movie Viewing"), new ActivityDateTime("10/05/2019 2000"),
                new ActivityLocation("Cathay cinema"), new ActivityDescription("12 dollar per pax"),
                new ArrayList<>(Arrays.asList(new MatricNumber("A0111111M"), new MatricNumber("A0222222B"),
                        new MatricNumber("A0555555R"), new MatricNumber("A0444444A"), new MatricNumber("A0777777U")))),
            new Activity(new ActivityName("Summer Hackerthon"), new ActivityDateTime("06/06/2019 0900"),
                new ActivityLocation("Icube Auditorium"), new ActivityDescription(),
                new ArrayList<>(Arrays.asList(new MatricNumber("A0222222B"), new MatricNumber("A0181234M")))),
            new Activity(new ActivityName("CS2103 Final Exam"), new ActivityDateTime("30/04/2019 1300"),
                new ActivityLocation("MPSH5"), new ActivityDescription(),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0171453J"), new MatricNumber("A0555555R")))),
            new Activity(new ActivityName("Lightroom Workshop"), new ActivityDateTime("03/03/2019 1500"),
                    new ActivityLocation("Central Library"),
                    new ActivityDescription("Learn new photo editing techniques"),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0171453J"), new MatricNumber("A0181234M")))),
            new Activity(new ActivityName("Quadrantid Meteor Shower"), new ActivityDateTime("03/01/2019 2300"),
                    new ActivityLocation("Multi-purpose filed"), new ActivityDescription("Telescope will be provided"),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0777777U"), new MatricNumber("A0333333N"),
                            new MatricNumber("A0555555R"), new MatricNumber("A0444444A")))),
            new Activity(new ActivityName("Video Department Meeting"), new ActivityDateTime("31/03/2019 1930"),
                    new ActivityLocation("Club Room"), new ActivityDescription("Attendance required."),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0333333N"), new MatricNumber("A0777777U"),
                            new MatricNumber("A0444444A"), new MatricNumber("A0555555R")))),
            new Activity(new ActivityName("Valentine Day Sales"), new ActivityDateTime("14/02/2019 1500"),
                    new ActivityLocation("Utown Green Plaza"), new ActivityDescription("Selling brownies."),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0111111M"), new MatricNumber("A0333333N"),
                            new MatricNumber("A0555555R"), new MatricNumber("A0444444A"), new MatricNumber("A0777777U"),
                            new MatricNumber("A0888888H")))),
            new Activity(new ActivityName("Computing Open Day"), new ActivityDateTime("17/03/2019 0900"),
                    new ActivityLocation("Com1 Level 2"), new ActivityDescription("Students and families are welcome"),
                    new ArrayList<>(Arrays.asList(new MatricNumber("A0181234M"), new MatricNumber("A0777777U"))))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Activity sampleActivity : getSampleActivities()) {
            sampleAb.addActivity(sampleActivity);
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
