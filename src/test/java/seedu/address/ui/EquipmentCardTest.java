package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.equipment.Equipment;
import seedu.address.testutil.EquipmentBuilder;

public class EquipmentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Equipment equipmentWithNoTags = new EquipmentBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(equipmentWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, equipmentWithNoTags, 1);

        // with tags
        Equipment equipmentWithTags = new EquipmentBuilder().build();
        personCard = new PersonCard(equipmentWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, equipmentWithTags, 2);
    }

    @Test
    public void equals() {
        Equipment equipment = new EquipmentBuilder().build();
        PersonCard personCard = new PersonCard(equipment, 0);

        // same equipment, same index -> returns true
        PersonCard copy = new PersonCard(equipment, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different equipment, same index -> returns false
        Equipment differentEquipment = new EquipmentBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentEquipment, 0)));

        // same equipment, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(equipment, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedEquipment} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Equipment expectedEquipment, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify equipment details are displayed correctly
        assertCardDisplaysPerson(expectedEquipment, personCardHandle);
    }
}
