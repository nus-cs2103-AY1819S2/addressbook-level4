package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.equipment.Equipment;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

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
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEquipment}.
     */
    public static void assertCardDisplaysPerson(Equipment expectedEquipment, PersonCardHandle actualCard) {
        assertEquals(expectedEquipment.getName().fullName, actualCard.getName());
        assertEquals(expectedEquipment.getPhone().value, actualCard.getPhone());
        assertEquals(expectedEquipment.getEmail().value, actualCard.getEmail());
        assertEquals(expectedEquipment.getAddress().value, actualCard.getAddress());
        assertTagsEqual(expectedEquipment, actualCard);
    }
    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedEquipment} with the correct
     * color.
     */
    private static void assertTagsEqual(Equipment expectedEquipment, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedEquipment.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code equipment} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Equipment... equipment) {
        for (int i = 0; i < equipment.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(equipment[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code equipment} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Equipment> equipment) {
        assertListMatching(personListPanelHandle, equipment.toArray(new Equipment[0]));
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
