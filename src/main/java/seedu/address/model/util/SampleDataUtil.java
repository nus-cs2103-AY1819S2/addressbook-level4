package seedu.address.model.util;

import static seedu.address.logic.parser.PatientAddCommandParser.NONE_EMAIL;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.dentist.Dentist;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKin;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.DrugAllergy;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.task.Priority;
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
                null, new Nric("S1234567B"), new DateOfBirth("01-05-1800"), new Sex("M"), new DrugAllergy("Panadol"),
                new NextOfKin(new Name("Jon Yeoh"), new Phone("96969696"), new Email(NONE_EMAIL),
                    new Address("Blk 30 Geylang Street 29, #06-40"), null, new NextOfKinRelation("Father")),
                new Description("Needs to floss more")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                null, new Nric("S1234567C"), new DateOfBirth("02-06-1800"), new Sex("F"), new DrugAllergy("NIL"),
                new NextOfKin(new Name("Janice Yu"), new Phone("69696969"), new Email(NONE_EMAIL),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), null, new NextOfKinRelation("Mother")),
                new Description("Pleasant to be with.")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    null, new Nric("S1234567D"), new DateOfBirth("11-12-1800"), new Sex("F"), new DrugAllergy("NIL"),
                new NextOfKin(new Name("Charlie Oliveiro"), new Phone("91548629"), new Email(NONE_EMAIL),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), null, new NextOfKinRelation("Brother")),
                new Description("Might need braces soon")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    null, new Nric("S1234567E"), new DateOfBirth("30-05-1800"), new Sex("M"), new DrugAllergy("NIL"),
                new NextOfKin(new Name("Li Sheng Yao"), new Phone("84751234"), new Email(NONE_EMAIL),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), null,
                    new NextOfKinRelation("Stepmother")), new Description("Possible Vampire")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    null, new Nric("S1234567F"), new DateOfBirth("31-01-1800"), new Sex("M"), new DrugAllergy("NIL"),
                new NextOfKin(new Name("Muhammad bin Ishmal"), new Phone("86415377"), new Email(NONE_EMAIL),
                    new Address("53 Meyer Road #03-05"), null, new NextOfKinRelation("Grandfather")),
                new Description("DEFINITELY a vampire")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    null, new Nric("S1234567G"), new DateOfBirth("01-12-1800"), new Sex("M"), new DrugAllergy("NIL"),
                new NextOfKin(new Name("Chris Balakrishnan"), new Phone("85169742"), new Email(NONE_EMAIL),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), null,
                    new NextOfKinRelation("Father")), new Description("My ancestor."))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Title("Tooth Extraction for Alex"), new DateCustom("17-04-2019"), new DateCustom("17-04-2019"),
                    new TimeCustom("1130"), new TimeCustom("1230"), Priority.HIGH, null),
            new Task(new Title("Tooth Filling for Bernice"), new DateCustom("17-04-2019"), new DateCustom("17-04-2019"),
                    new TimeCustom("1330"), new TimeCustom("1430"), Priority.LOW, null),
            new Task(new Title("Check all patient records"), new DateCustom("19-04-2019"), new DateCustom("22-04-2019"),
                    new TimeCustom("1130"), new TimeCustom("1230"), Priority.MED, null),
            new Task(new Title("Removal of John's braces"), new DateCustom("15-05-2019"), new DateCustom("15-05-2019"),
                    new TimeCustom("1000"), new TimeCustom("1100"), Priority.MED, null),
            new Task(new Title("Public Holiday , Fishing!"), new DateCustom("19-04-2019"), new DateCustom("19-04-2019"),
                    new TimeCustom("0900"), new TimeCustom("1200"), Priority.HIGH, null)
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
