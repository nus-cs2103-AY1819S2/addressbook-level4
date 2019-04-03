package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.HealthWorkerBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.Condition;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;


/**
 * Contains utility methods for populating {@code RequestBook} and {@code HealthWorkerBook}with sample data.
 */
public class SampleDataUtil {


    public static HealthWorker[] getSampleHealthWorkers() {
        return new HealthWorker[] {
            new HealthWorker(new Name("Alex Tan"), new Nric("T1234567J"), new Phone("98765432"),
                        new Organization("SGH"), getSkillsFromString("PATHOLOGY")),
            new HealthWorker(new Name("Betty Chua"), new Nric("T5857367J"), new Phone("98875432"),
                        new Organization("SGH"), getSkillsFromString("GYNAECOLOGY")),
            new HealthWorker(new Name("Charles Anderson"), new Nric("S5856787J"), new Phone("91234432"),
                        new Organization("NUH"), getSkillsFromString("GENERAL_PRACTICE")),
            new HealthWorker(new Name("John Peterson"), new Nric("T2334567J"), new Phone("90987432"),
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
            new Request(new Name("Angie Lim"), new Nric("S9875432L"), new Phone("91665831"),
                        new Address("39 Upper Boon Keng Road, #07-45"), new RequestDate("10-04-2019 10:00:00"),
                        getConditionSet("Palliative"), new RequestStatus("PENDING")),
            new Request(new Name("Benson Tan"), new Nric("S9878932L"), new Phone("84561267"),
                        new Address("14, Bishan Ave 2, #11-90"), new RequestDate("05-04-2019 15:00:00"),
                        getConditionSet("Palliative"), new RequestStatus("COMPLETED")),
            new Request(new Name("Rajuratnam"), new Nric("S8016757C"), new Phone("81654488"),
                    new Address("108, McNair Road, #09-65"), new RequestDate("01-03-2019 08:00:00"),
                    getConditionSet("Diabetic", "Kidney Dialysis"), new RequestStatus("COMPLETED"))
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
