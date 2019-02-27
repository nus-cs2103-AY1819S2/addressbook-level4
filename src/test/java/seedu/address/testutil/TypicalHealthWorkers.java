package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.HealthWorker;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalHealthWorkers {

    public static final HealthWorker ANDY = new HealthWorkerBuilder().withName("Andy Tan")
            .withAddress("125, Jurong West Ave 6, #08-111").withEmail("andye@example.com")
            .withPhone("94358253").withOrganization("NUH").withNric("S8312942G")
            .withTags("Physiotherapy", "Dialysis").build();
    public static final HealthWorker BETTY = new HealthWorkerBuilder().withName("Betty Meier")
            .withAddress("312, Clementi Ave 2, #02-25")
            .withEmail("betty@example.com").withPhone("98761232")
            .withOrganization("NUH").withNric("S8312942G")
            .withTags("Eldercare", "Stroke").build();
    public static final HealthWorker CARLIE = new HealthWorkerBuilder().withName("Carlie Kurz")
            .withPhone("95358463").withOrganization("NUH").withNric("S9312942G")
            .withEmail("Kurz@example.com").withAddress("wall street").withTags("Palliative").build();
    public static final HealthWorker PANIEL = new HealthWorkerBuilder().withName("Paniel Meier").withPhone("87652133")
            .withEmail("panda@example.com").withOrganization("NUH").withNric("S8412942G")
            .withAddress("10th street").withTags("Doctor").build();
    public static final HealthWorker ELLA = new HealthWorkerBuilder().withName("Ella Meyer")
            .withPhone("94824524").withOrganization("NUH").withNric("S9112942G")
            .withEmail("meyer@example.com").withAddress("michegan ave").withTags("Nurse", "Dementia").build();
    public static final HealthWorker FIONE = new HealthWorkerBuilder().withName("Fione Kunz")
            .withPhone("9482237").withOrganization("NUH").withNric("S7812942G")
            .withEmail("fione@example.com").withAddress("little tokyo").withTags("Nurse", "Alzheimers").build();
    public static final HealthWorker GEORGE = new HealthWorkerBuilder().withName("George Best")
            .withPhone("9482442").withOrganization("NUH").withNric("S8812942G")
            .withEmail("george@example.com").withAddress("4th street").withTags("Cancer").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalHealthWorkers() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalHealthStaffBook() {
        AddressBook ab = new AddressBook();
        for (HealthWorker person : getTypicalHealthStaff()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<HealthWorker> getTypicalHealthStaff() {
        return new ArrayList<>(Arrays.asList(ANDY, BETTY, CARLIE, PANIEL, ELLA, FIONE, GEORGE));
    }
}

