package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.dentist.Dentist;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    /**
     * Note: method is modified such that it creates sample patients rather than persons.
     * If Dentist is not set, creates a default one. Removes after persons are created.
     * @return an array of sample patient instances.
     */
    public static Person[] getSamplePersons() {
        if (!Dentist.dentistExists()) {
            Dentist.setSampleDentistName();
            Person[] persons = getSamplePersonsImpl();
            Dentist.removeDentistName();
            return persons;
        } else {
            return getSamplePersonsImpl();
        }
    }

    /**
     * Note: method is modified such that it creates sample patients rather than persons.
     * @return an array of sample patient instances.
     */
    public static Person[] getSamplePersonsImpl() {
        return new Person[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                null, new Nric("S1234567B"), new DateOfBirth("01-05-1800"), new Sex("M")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                null, new Nric("S1234567C"), new DateOfBirth("02-06-1800"), new Sex("F")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    null, new Nric("S1234567D"), new DateOfBirth("11-12-1800"), new Sex("F")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    null, new Nric("S1234567E"), new DateOfBirth("30-05-1800"), new Sex("M")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    null, new Nric("S1234567F"), new DateOfBirth("31-01-1800"), new Sex("M")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    null, new Nric("S1234567G"), new DateOfBirth("01-12-1800"), new Sex("M"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Title("Tooth Extraction for Alex"), new DateCustom("30-03-2019"),
                    new DateCustom("30-03-2019")),
            new Task(new Title("Tooth Filling for Bernice"), new DateCustom("22-06-2019"),
                    new DateCustom("22-06-2019")),
            new Task(new Title("Review all patient's records"), new DateCustom("30-03-2019"),
                    new DateCustom("22-06-2019")),
            new Task(new Title("Removal of John's braces"), new DateCustom("30-05-2019"),
                    new DateCustom("30-05-2019")),
            new Task(new Title("Knock off early"), new DateCustom("22-03-2019"),
                    new DateCustom("22-03-2019"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Task sampleTask: getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

}
