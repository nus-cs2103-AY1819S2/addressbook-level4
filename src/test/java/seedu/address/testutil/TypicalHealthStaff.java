package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalHealthStaff {

    public static final Person ANDY = new PersonBuilder().withName("Andy Tan")
            .withAddress("125, Jurong West Ave 6, #08-111").withEmail("andye@example.com")
            .withPhone("94358253")
            .withTags("Physiotherapy", "Dialysis").build();
    public static final Person BETTY = new PersonBuilder().withName("Betty Meier")
            .withAddress("312, Clementi Ave 2, #02-25")
            .withEmail("betty@example.com").withPhone("98761232")
            .withTags("Eldercare", "Stroke").build();
    public static final Person CARLIE = new PersonBuilder().withName("Carlie Kurz").withPhone("95358463")
            .withEmail("Kurz@example.com").withAddress("wall street").withTags("Palliative").build();
    public static final Person PANIEL = new PersonBuilder().withName("Paniel Meier").withPhone("87652133")
            .withEmail("panda@example.com").withAddress("10th street").withTags("Doctor").build();
    public static final Person ELLA = new PersonBuilder().withName("Ella Meyer").withPhone("94824524")
            .withEmail("meyer@example.com").withAddress("michegan ave").withTags("Nurse", "Dementia").build();
    public static final Person FIONE = new PersonBuilder().withName("Fione Kunz").withPhone("9482237")
            .withEmail("fione@example.com").withAddress("little tokyo").withTags("Nurse", "Alzheimers").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("george@example.com").withAddress("4th street").withTags("Cancer").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalHealthStaff() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalHealthStaffBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalHealthStaff()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalHealthStaff() {
        return new ArrayList<>(Arrays.asList(ANDY, BETTY, CARLIE, PANIEL, ELLA, FIONE, GEORGE));
    }
}

