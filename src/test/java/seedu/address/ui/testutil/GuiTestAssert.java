package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Person;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getEducation(), actualCard.getEducation());
        assertEquals(expectedCard.getGpa(), actualCard.getGpa());
        assertEquals(expectedCard.getDegree(), actualCard.getDegree());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals("Phone - " + expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals("Email - " + expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals("Address - " + expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals("School - " + expectedPerson.getEducation().toString(), actualCard.getEducation());
        assertEquals("GPA - " + expectedPerson.getGpa().value, actualCard.getGpa());
        assertEquals("Degree - " + expectedPerson.getDegree().value, actualCard.getDegree());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPersonSystemTest(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals("Phone - " + expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals("Email - " + expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals("Address - " + expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals("School - " + expectedPerson.getEducation().toString(), actualCard.getEducation());
        assertEquals("GPA - " + expectedPerson.getGpa().value, actualCard.getGpa());
        assertEquals("Degree - " + expectedPerson.getDegree().value, actualCard.getDegree());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.unlabelledTagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatchingSystemTest(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatchingSystemTest(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatchingSystemTest(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPersonSystemTest(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
