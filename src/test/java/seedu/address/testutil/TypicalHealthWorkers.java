package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.HealthWorkerBook;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

/**
 * A utility class containing a list of {@code HealthWorker} objects to be used in tests.
 */
public class TypicalHealthWorkers {

    public static final HealthWorker ANDY = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Andy Tan")
            .withPhone("94358253")
            .withNric("S8312942G"))
            .withOrganization("NUH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation.PHYSIOTHERAPY,
                    Specialisation.GENERAL_PRACTICE)))).build();
    public static final HealthWorker BETTY = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Betty Meier")
            .withPhone("98761232")
            .withNric("S8176952L"))
            .withOrganization("CGH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation
                    .GENERAL_PRACTICE, Specialisation.ORTHOPAEDIC)))).build();
    public static final HealthWorker CARLIE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Carlie Kurz")
            .withPhone("95358463")
            .withNric("S9312942G"))
            .withOrganization("NUH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation.GYNAECOLOGY,
                    Specialisation.GENERAL_PRACTICE)))).build();
    public static final HealthWorker PANIEL = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Paniel Meier")
            .withPhone("87652133")
            .withNric("S9701568T"))
            .withOrganization("SGH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation.PHYSIOTHERAPY,
                    Specialisation.HAEMATOLOGY)))).build();
    public static final HealthWorker ELLA = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Ella Meyer")
            .withPhone("94824524")
            .withNric("S9112942G"))
            .withOrganization("SGH")
            .withSkills(new Skills(new HashSet<>(Arrays.asList(Specialisation.ANAESTHESIOLOGY,
                    Specialisation.PHYSIOTHERAPY)))).build();
    public static final HealthWorker FIONE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Fione Kunz")
            .withPhone("94822373")
            .withNric("S7812942G"))
            .withOrganization("TTSH").build();
    public static final HealthWorker GEORGE = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("George Best")
            .withPhone("94824424")
            .withNric("S8812942G"))
            .withOrganization("TTSH").build();

    // Manually added
    public static final HealthWorker HOOK = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Hoon Meier")
            .withPhone("84824245")
            .withNric("S1234567A"))
            .withOrganization("NUH").build();

    public static final HealthWorker IVAN = ((HealthWorkerBuilder) new HealthWorkerBuilder()
            .withName("Ida Mueller")
            .withPhone("84821316")
            .withNric("S1234567B"))
            .withOrganization("NUH").build();

    private TypicalHealthWorkers() { } // prevents instantiation

    /**
     * Returns an {@code HealthWorkerBook} with all the typical persons.
     */
    public static HealthWorkerBook getTypicalHealthWorkerBook() {
        HealthWorkerBook healthWorkerBook = new HealthWorkerBook();
        for (HealthWorker healthWorker : getTypicalHealthStaff()) {
            healthWorkerBook.addHealthWorker(healthWorker);
        }
        return healthWorkerBook;
    }

    public static List<HealthWorker> getTypicalHealthStaff() {
        return new ArrayList<>(Arrays.asList(ANDY, BETTY, CARLIE, PANIEL, ELLA, FIONE, GEORGE));
    }
}

