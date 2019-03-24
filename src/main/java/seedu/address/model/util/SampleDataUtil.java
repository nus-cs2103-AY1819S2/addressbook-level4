package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.RequestBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.Condition;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // TODO: Fix implementation for HealthHub in MainApp for loading AddressBook class
    public static Person[] getSamplePersons() {
        return new Person[] {
                new Person(new Name("Alison Jacksonston"), new Nric("S9876542A"), new Phone("86633666")),
                new Person(new Name("Brandon Stark"), new Nric("S9879842A"), new Phone("81234466")),
                new Person(new Name("Charlie LivinStone"), new Nric("S9133154A"), new Phone("87895566")),
                new Person(new Name("Dick Grayson"), new Nric("S9076542A"), new Phone("88888888"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static HealthWorker[] getSampleHealthWorkers() {
        return new HealthWorker[] {
                new HealthWorker(new Name("Alex Raider"), new Nric("T1234567J"), new Phone("98765432"),
                        new Organization("SGH"), getSkillsFromString("PATHOLOGY")),
                new HealthWorker(new Name("Billy Batson"), new Nric("T5857367J"), new Phone("98875432"),
                        new Organization("SGH"), getSkillsFromString("GYNAECOLOGY")),
                new HealthWorker(new Name("Charles Anderson"), new Nric("S5856787J"), new Phone("91234432"),
                        new Organization("NUH"), getSkillsFromString("GYNAECOLOGY")),
                new HealthWorker(new Name("Dawgs Galore"), new Nric("T2334567J"), new Phone("90987432"),
                        new Organization("SGH"), getSkillsFromString("UROLOGY"))
        };
    }

    public static ReadOnlyHealthWorkerBook getSampleHealthWorkerBook() {
        HealthWorkerBook sampleHwb = new HealthWorkerBook();
        for (HealthWorker sampleHealthworker : getSampleHealthWorkers()) {
            sampleHwb.addHealthWorker(sampleHealthworker);
        }
        return sampleHwb;
    }
    public static Request[] getSampleRequests() {
        return new Request[] {
                new Request(new Name("Aggie Tan"), new Nric("S9875432L"), new Phone("81234567"),
                        new Address("311, Clementi Ave 2, #02-25"),new RequestDate("02-01-2919 08:00:00"),
                        getConditionSet("Palliative"), new RequestStatus("PENDING")),
                new Request(new Name("Benson Tan"), new Nric("S9878932L"), new Phone("84561267"),
                        new Address("14, Bishan Ave 2, #01-20"),new RequestDate("02-01-2009 08:00:00"),
                        getConditionSet("Palliative"), new RequestStatus("COMPLETED"))
        };
    }

    public static ReadOnlyRequestBook getSampleRequestBook() {
        RequestBook sampleRb = new RequestBook();
        for (Request sampleRequest : getSampleRequests()) {
            sampleRb.addRequest(sampleRequest);
        }
        return sampleRb;
    }
    /**
     * Returns a Condition set containing the list of strings given.
     * @param strings the conditions in String form.
     * @return A set of conditions made from the strings.
     */
    public static Set<Condition> getConditionSet(String... strings) {
        return Arrays.stream(strings)
                .map(Condition::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns Conditions from a set of conditions passed in.
     */
    public static Set<Condition> getConditionsFromConditionSet(Set<Condition> conditions) {
        HashSet<Condition> set = new HashSet<>();
        conditions.forEach(condition -> {
            set.add(condition);
        });
        return set;
    }

    /**
     * Returns a Specialisation from a set of strings
     * @param
     * @return
     */
    public static Skills getSkillsFromString(String... strings) {
        HashSet<Specialisation> specialisations = new HashSet<>();
        for (String string: strings) {
            specialisations.add(Specialisation.parseString(string));
        }
        return new Skills(specialisations);
    }
}
