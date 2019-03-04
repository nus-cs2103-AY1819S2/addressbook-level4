package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.HealthWorker;
<<<<<<< HEAD
=======
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

>>>>>>> 87e54c4f504c62ce07a7be3761ed0b2e041dbbe7

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalHealthWorkers {

    public static final HealthWorker ANDY = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Andy Tan")
            .withAddress("125, Jurong West Ave 6, #08-111")
            .withEmail("andye@example.com")
            .withPhone("94358253")
            .withNric("S8312942G"))
            .withOrganization("NUH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation
                            .GENERAL_PRACTICE, Specialisation.PHYSIOTHERAPY)))).build();
    public static final HealthWorker BETTY = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Betty Meier")
            .withAddress("312, Clementi Ave 2, #02-25")
            .withEmail("betty@example.com").withPhone("98761232")
            .withNric("S8312942G")
            .withTags("Eldercare", "Stroke"))
            .withOrganization("NUH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation
                    .GENERAL_PRACTICE, Specialisation.ORTHOPAEDIC)))).build();
    public static final HealthWorker CARLIE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Carlie Kurz")
            .withPhone("95358463").withNric("S9312942G")
            .withEmail("Kurz@example.com")
            .withAddress("wall street")
            .withTags("Palliative")).withOrganization("NUH").build();
    public static final HealthWorker PANIEL = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Paniel Meier")
            .withPhone("87652133")
            .withEmail("panda@example.com")
            .withNric("S8412942G")
            .withAddress("10th street")
            .withTags("Doctor")).withOrganization("NUH").build();
    public static final HealthWorker ELLA = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Ella Meyer")
            .withPhone("94824524")
            .withNric("S9112942G")
            .withEmail("meyer@example.com")
            .withAddress("michegan ave")
            .withTags("Nurse", "Dementia")).withOrganization("NUH").build();
    public static final HealthWorker FIONE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Fione Kunz")
            .withPhone("9482237")
            .withNric("S7812942G")
            .withEmail("fione@example.com")
            .withAddress("little tokyo")
            .withTags("Nurse", "Alzheimers")).withOrganization("NUH").build();
    public static final HealthWorker GEORGE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withNric("S8812942G")
            .withEmail("george@example.com")
            .withAddress("4th street")
            .withTags("Cancer")).withOrganization("NUH").build();

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

