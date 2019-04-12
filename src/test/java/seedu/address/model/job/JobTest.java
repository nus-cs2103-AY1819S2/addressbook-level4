package seedu.address.model.job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;
import static seedu.address.testutil.TypicalObjects.ALICE;
import static seedu.address.testutil.TypicalObjects.ENGINEER;
import static seedu.address.testutil.TypicalObjects.PASTOR;
import static seedu.address.testutil.TypicalObjects.PROFESSOR;
import static seedu.address.testutil.TypicalObjects.TEACHER;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.PersonBuilder;

public class JobTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testToString() {
        Job Teacher = new JobBuilder(TEACHER).build();
        assertEquals(VALID_JOB_NAME_TEACHER, Teacher.toString());
    }

    @Test
    public void testAddPerson() {
        Person alice = new PersonBuilder(ALICE).build();
        Job teacher = new JobBuilder(TEACHER).withName(VALID_JOB_NAME_TEACHER).build();

        teacher.add(alice, 0);
        UniquePersonList list = new UniquePersonList();
        list.add(alice);
        HashSet<Person> aList = new HashSet<>();
        aList.add(alice);
        Set<Nric> nrics = teacher.getPersonsNric(0);

        assertEquals(list, teacher.getPeople(0));
        assertTrue(aList.contains(alice));
        assertTrue(teacher.contains(alice));
        assertEquals(1, nrics.size());
    }

    @Test
    public void testRemovePerson() {
        Person alice = new PersonBuilder(ALICE).build();
        Job teacher = new JobBuilder(TEACHER).withName(VALID_JOB_NAME_TEACHER).build();

        teacher.add(alice, 0);
        teacher.remove(alice);
        Set<Nric> nrics = teacher.getPersonsNric(0);

        assertFalse(teacher.contains(alice));
        assertEquals(0, nrics.size());
    }

    @Test
    public void isSameJob() {
        // same object -> returns true
        assertTrue(ENGINEER.isSameJob(ENGINEER));

        // diff object same name -> returns true
        Job editedProfessor = new JobBuilder(PROFESSOR).withName(VALID_JOB_NAME_TEACHER).build();
        assertTrue(TEACHER.isSameJob(editedProfessor));

        // different name -> returns false
        Job editedEngineer = new JobBuilder(ENGINEER).withName(VALID_JOB_NAME_TEACHER).build();
        assertFalse(ENGINEER.isSameJob(editedEngineer));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Job engineerCopy = new JobBuilder(ENGINEER).build();
        assertTrue(ENGINEER.equals(engineerCopy));

        // same object -> returns true
        assertTrue(ENGINEER.equals(ENGINEER));

        // null -> returns false
        assertFalse(ENGINEER.equals(null));

        // different type -> returns false
        assertFalse(ENGINEER.equals(5));

        // different person -> returns false
        assertFalse(ENGINEER.equals(PASTOR));

        // different name -> returns false
        Job editedEngineer = new JobBuilder(ENGINEER).withName(VALID_JOB_NAME_TEACHER).build();
        assertFalse(ENGINEER.equals(editedEngineer));
    }
}
