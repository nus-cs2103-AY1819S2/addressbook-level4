package seedu.address.model.util;

import java.util.Arrays;
import java.util.Date;
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
 * Contains utility methods for populating {@code RequestBook} and {@code HealthWorkerBooknew  sample data.
 */
public class SampleDataUtil {

    private static final String VALID_NRIC = "S8312942G";

    public static final HealthWorker ANDY = new HealthWorker(
            new Name("Andy Tan"),
            new Nric("S8312942G"),
            new Phone("94358253"),
            new Organization("NUH"),
            new Skills(new HashSet<>(Arrays.asList(Specialisation.PHYSIOTHERAPY,
                    Specialisation.GENERAL_PRACTICE))));

    public static final HealthWorker BETTY = new HealthWorker(
            new Name("Betty Meier"),
            new Nric("S8176952L"),
            new Phone("98761232"),
            new Organization("CGH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation
                    .GENERAL_PRACTICE, Specialisation.ORTHOPAEDIC)))));

    public static final HealthWorker CARLIE = new HealthWorker(
            new Name("Carlie Kurz"),
            new Nric("S9312942G"),
            new Phone("95358463"),
            new Organization("NUH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation.GYNAECOLOGY,
                    Specialisation.GENERAL_PRACTICE)))));

    public static final HealthWorker PANIEL = new HealthWorker(
            new Name("Paniel Meier"),
            new Nric("S9701568T"),
            new Phone("87652133"),
            new Organization("SGH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation.PHYSIOTHERAPY,
                    Specialisation.HAEMATOLOGY)))));

    public static final HealthWorker ELLA = new HealthWorker(
            new Name("Ella Meyer"),
            new Nric("S9112942G"),
            new Phone("94824524"),
            new Organization("SGH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation.ANAESTHESIOLOGY,
                    Specialisation.PHYSIOTHERAPY)))));

    public static final HealthWorker FIONE = new HealthWorker(
            new Name("Fione Kunz"),
            new Nric("S7812942G"),
            new Phone("94822373"),
            new Organization("TTSH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation.CARDIOLOGY,
                    Specialisation.OCCUPATIONAL_THERAPY)))));

    public static final HealthWorker GEORGE = new HealthWorker(
            new Name("George Best"),
            new Nric("S8812942G"),
            new Phone("94824424"),
            new Organization("TTSH"),
            new Skills(new Skills(new HashSet<>(Arrays.asList(Specialisation.UROLOGY,
                    Specialisation.PAEDIATRIC)))));

 public static final Request ALICE_REQUEST = new Request(
        new Name("Alice Pauline"),
         new Nric("S9670515H"),
         new Phone("94351253"),
         new Address("123, Jurong West Ave 6, #08-111"),
         new RequestDate("01-01-2019 10:00:00"),
         getConditionSet("Physiotherapy"),
         new RequestStatus("ONGOING"),
            ANDY.getNric().toString()
        );

    public static final Request BENSON_REQUEST = new Request(
            new Name("Benson Meier"),
            new Nric("S9274100D"),
            new Phone("98765432"),
        new Address("311, Clementi Ave 2, #02-25"),
            new RequestDate("02-01-2019 08:00:00"),
        getConditionSet("Palliative"),
            new RequestStatus("ONGOING"),
        BETTY.getNric().toString());

    public static final Request CARL_REQUEST = new Request(
            new Name("Carl Kurz"),
            new Nric("S9328723A"),
            new Phone("87652533"),
            new Address("wall street"),
        new RequestDate("04-01-2019 14:00:00"),
            getConditionSet("Palliative"),
            new RequestStatus("ONGOING"),
        CARLIE.getNric().toString());

    public static final Request DANIEL_REQUEST = new Request(
         new Name("Daniel Meier"),
            new Nric("S2652663Z"),
            new Phone("82015737"),
            new Address("10th street"),
            new RequestDate("02-01-2019 18:00:00"),
            getConditionSet("Palliative"),
        new RequestStatus("COMPLETED"),
        PANIEL.getNric().toString());

    public static final Request EMMANUEL_REQUEST = new Request(
        new Name("Emmanuel Meier"),
            new Nric("S2862663Z"),
            new Phone("82205737"),
            new Address("10th street"),
            new RequestDate("02-01-2019 18:00:00"),
            getConditionSet("Palliative"),
        new RequestStatus("PENDING"));

    public static final Request FRANCIS_REQUEST = new Request(
        new Name("Francis Meier"),
            new Nric("S2122663Z"),
            new Phone("82655737"),
            new Address("10th street"),
            new RequestDate("02-01-2019 18:00:00"),
            getConditionSet("Palliative"),
        new RequestStatus("PENDING"));

    public static final Request GLADYS_REQUEST = new Request(
            new Name("Gladys Meier"),
            new Nric("S2176663Z"),
            new Phone("82659337"),
            new Address("10th street"),
            new RequestDate("02-01-2019 19:00:00"),
        getConditionSet("Palliative"),
        new RequestStatus("PENDING"));

    public static final Request HEPZHI_REQUEST = new Request(
            new Name("Hepzhi Meier"),
            new Nric("S2176698Z"),
            new Phone("82658937"),
            new Address("10th street"),
            new RequestDate("02-01-2019 16:00:00"),
        getConditionSet("Palliative"),
        new RequestStatus("PENDING"));

    public static final Request INDIANA_REQUEST = new Request(
            new Name("Indiana Meier"),
            new Nric("S2184698Z"),
            new Phone("82692937"),
            new Address("10th street"),
            new RequestDate("01-01-2019 08:00:00"),
        getConditionSet("Palliative"),
        new RequestStatus("PENDING"));

    public static final Request JANE_REQUEST = new Request(
            new Name("Jane Romero"),
            new Nric("S2652663Z"),
            new Phone("82845737"),
            new Address("10th street"),
            new RequestDate("02-01-2019 15:00:00"),
            getConditionSet("Palliative"),
            new RequestStatus("COMPLETED"),
            ELLA.getNric().toString();
    );

    public static final Request NEA_REQUEST = new Request(
            new Name("Nea Karlsson"),
            new Nric("S8875613T"),
            new Phone("88179352"),
            new Address("480 Lorong 6 Toa Payoh"),
            new RequestDate("25-03-2019 10:00:00"),
            getConditionSet("Physiotherapy"),
            new RequestStatus("ONGOING"),
            ELLA.getNric().toString();
    );

    public static HealthWorker[] getSampleHealthWorkers() {
        return new HealthWorker[] {
           ANDY,BETTY,CARLIE,PANIEL,ELLA,FIONE,GEORGE
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
