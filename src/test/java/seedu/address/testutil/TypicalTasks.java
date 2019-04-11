package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINKEDPATIENTNAME_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINKEDPATIENTNRIC_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_NOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_WITHPATIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

public class TypicalTasks {

    //Adds some persons in addition to task to test linking of patients to task
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withNric("S1234567A")
            .withDob("11-12-1990").withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withSex("F").withDrugAllergy("NIL").withDescription("Just another patient.")
            .withNextOfKin(new PersonBuilder.NextOfKinBuilder().withKinName("Joshua Pauline").withKinRelation("Father")
                    .withKinAddress("123, Jurong West Ave 6, #08-111").withKinPhone("96969696").build())
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withNric("S1234568A")
            .withDob("12-12-1990").withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withPhone("98765432").withSex("M").withDrugAllergy("Panadol").withDescription("Just yet another patient.")
            .withNextOfKin(new PersonBuilder.NextOfKinBuilder().withKinName("Jane Meier").withKinRelation("Sister")
                    .withKinAddress("311, Clementi Ave 2, #02-25").withKinPhone("98712345").build())
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withNric("S1234569A")
            .withDob("11-12-1991").withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withSex("F").withDrugAllergy("asprin").withDescription("Needs special anesthetics")
            .withNextOfKin(new PersonBuilder.NextOfKinBuilder().withKinName("Charlie Kurz").withKinRelation("Brother")
                    .withKinAddress("floor square").withKinPhone("85214369").build())
            .build();

    public static final Task CLEANING = new TaskBuilder().withTitle("Equipment Cleaning").withStartDate("15-01-2020")
            .withEndDate("15-01-2020").withStartTime("0900").withEndTime("1000").withPriority("high")
            .withNoLinkedPatient().build();

    public static final Task EXTRACT = new TaskBuilder().withTitle("Tooth extraction for Alice")
            .withStartDate("16-01-2020").withEndDate("20-01-2020").withStartTime("0900").withEndTime("0900")
            .withPriority("med").withLinkedPatient(ALICE.getName().fullName, ((Patient) ALICE).getNric().getNric())
            .build();

    public static final Task REORG = new TaskBuilder().withTitle("Reorganize Documents").withStartDate("12-03-2020")
            .withEndDate("12-03-2020").withStartTime("1500").withEndTime("1830").withPriority("low")
            .withNoLinkedPatient().build();


    // Manually added - Task's details found in {@code CommandTestUtil}
    // edited to specify task attributes - for testing purposes
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY).withDob(VALID_DOB_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSex("F").build();

    public static final Task REVIEW = new TaskBuilder().withTitle(VALID_TITLE_NOPATIENT)
            .withStartDate(VALID_STARTDATE_NOPATIENT).withEndDate(VALID_ENDDATE_NOPATIENT)
            .withStartTime(VALID_STARTTIME_NOPATIENT).withEndTime(VALID_ENDTIME_NOPATIENT)
            .withPriority(VALID_PRIORITY_NOPATIENT).withNoLinkedPatient().build();

    public static final Task TEETHCLEANING = new TaskBuilder().withTitle(VALID_TITLE_WITHPATIENT)
            .withStartDate(VALID_STARTDATE_WITHPATIENT).withEndDate(VALID_ENDDATE_WITHPATIENT)
            .withStartTime(VALID_STARTTIME_WITHPATIENT).withEndTime(VALID_ENDTIME_WITHPATIENT)
            .withPriority(VALID_PRIORITY_WITHPATIENT)
            .withLinkedPatient(VALID_LINKEDPATIENTNAME_WITHPATIENT, VALID_LINKEDPATIENTNRIC_WITHPATIENT).build();

    private TypicalTasks() {}; // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks and persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CLEANING, EXTRACT, REORG));
    }
}
