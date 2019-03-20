package seedu.equipment.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.EquipmentCardHandle;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.testutil.EquipmentBuilder;
import seedu.equipment.ui.EquipmentCard;

public class EquipmentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Equipment equipmentWithNoTags = new EquipmentBuilder().withTags().build();
        EquipmentCard equipmentCard = new EquipmentCard(equipmentWithNoTags, 1);
        uiPartRule.setUiPart(equipmentCard);
        assertCardDisplay(equipmentCard, equipmentWithNoTags, 1);

        // with tags
        Equipment equipmentWithTags = new EquipmentBuilder().build();
        equipmentCard = new EquipmentCard(equipmentWithTags, 2);
        uiPartRule.setUiPart(equipmentCard);
        assertCardDisplay(equipmentCard, equipmentWithTags, 2);
    }

    @Test
    public void equals() {
        Equipment equipment = new EquipmentBuilder().build();
        EquipmentCard equipmentCard = new EquipmentCard(equipment, 0);

        // same equipment, same index -> returns true
        EquipmentCard copy = new EquipmentCard(equipment, 0);
        assertTrue(equipmentCard.equals(copy));

        // same object -> returns true
        assertTrue(equipmentCard.equals(equipmentCard));

        // null -> returns false
        assertFalse(equipmentCard.equals(null));

        // different types -> returns false
        assertFalse(equipmentCard.equals(0));

        // different equipment, same index -> returns false
        Equipment differentEquipment = new EquipmentBuilder().withName("differentName").build();
        assertFalse(equipmentCard.equals(new EquipmentCard(differentEquipment, 0)));

        // same equipment, different index -> returns false
        assertFalse(equipmentCard.equals(new EquipmentCard(equipment, 1)));
    }

    /**
     * Asserts that {@code equipmentCard} displays the details of {@code expectedEquipment} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EquipmentCard equipmentCard, Equipment expectedEquipment, int expectedId) {
        guiRobot.pauseForHuman();

        EquipmentCardHandle equipmentCardHandle = new EquipmentCardHandle(equipmentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", equipmentCardHandle.getId());

        // verify equipment details are displayed correctly
        assertCardDisplaysPerson(expectedEquipment, equipmentCardHandle);
    }
}
