package seedu.equipment.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EquipmentCardHandle;
import guitests.guihandles.EquipmentListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.equipment.model.equipment.Equipment;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EquipmentCardHandle expectedCard, EquipmentCardHandle actualCard) {
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
    public static void assertCardDisplaysPerson(Equipment expectedEquipment, EquipmentCardHandle actualCard) {
        assertEquals(expectedEquipment.getName().name, actualCard.getName());
        assertEquals(expectedEquipment.getPhone().value, actualCard.getPhone());
        assertEquals(expectedEquipment.getDate().value, actualCard.getEmail());
        assertEquals(expectedEquipment.getAddress().value, actualCard.getAddress());
        assertTagsEqual(expectedEquipment, actualCard);
    }
    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedEquipment} with the correct
     * color.
     */
    private static void assertTagsEqual(Equipment expectedEquipment, EquipmentCardHandle actualCard) {
        List<String> expectedTags = expectedEquipment.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code equipmentListPanelHandle} displays the details of {@code equipment} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EquipmentListPanelHandle equipmentListPanelHandle, Equipment... equipment) {
        for (int i = 0; i < equipment.length; i++) {
            equipmentListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(equipment[i], equipmentListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code equipmentListPanelHandle} displays the details of {@code equipment} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EquipmentListPanelHandle equipmentListPanelHandle,
                                          List<Equipment> equipment) {
        assertListMatching(equipmentListPanelHandle, equipment.toArray(new Equipment[0]));
    }

    /**
     * Asserts the size of the list in {@code equipmentListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EquipmentListPanelHandle equipmentListPanelHandle, int size) {
        int numberOfPeople = equipmentListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
