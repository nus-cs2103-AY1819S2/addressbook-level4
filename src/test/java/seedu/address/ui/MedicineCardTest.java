package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;

import org.junit.Test;

import guitests.guihandles.MedicineCardHandle;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.MedicineBuilder;

public class MedicineCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Medicine medicineWithNoTags = new MedicineBuilder().withTags(new String[0]).build();
        MedicineCard medicineCard = new MedicineCard(medicineWithNoTags, 1);
        uiPartRule.setUiPart(medicineCard);
        assertCardDisplay(medicineCard, medicineWithNoTags, 1);

        // with tags
        Medicine medicineWithTags = new MedicineBuilder().build();
        medicineCard = new MedicineCard(medicineWithTags, 2);
        uiPartRule.setUiPart(medicineCard);
        assertCardDisplay(medicineCard, medicineWithTags, 2);
    }

    @Test
    public void equals() {
        Medicine medicine = new MedicineBuilder().build();
        MedicineCard medicineCard = new MedicineCard(medicine, 0);

        // same medicine, same index -> returns true
        MedicineCard copy = new MedicineCard(medicine, 0);
        assertTrue(medicineCard.equals(copy));

        // same object -> returns true
        assertTrue(medicineCard.equals(medicineCard));

        // null -> returns false
        assertFalse(medicineCard.equals(null));

        // different types -> returns false
        assertFalse(medicineCard.equals(0));

        // different medicine, same index -> returns false
        Medicine differentMedicine = new MedicineBuilder().withName("differentName").build();
        assertFalse(medicineCard.equals(new MedicineCard(differentMedicine, 0)));

        // same medicine, different index -> returns false
        assertFalse(medicineCard.equals(new MedicineCard(medicine, 1)));
    }

    /**
     * Asserts that {@code medicineCard} displays the details of {@code expectedMedicine} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(MedicineCard medicineCard, Medicine expectedMedicine, int expectedId) {
        guiRobot.pauseForHuman();

        MedicineCardHandle medicineCardHandle = new MedicineCardHandle(medicineCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", medicineCardHandle.getId());

        // verify medicine details are displayed correctly
        assertCardDisplaysMedicine(expectedMedicine, medicineCardHandle);
    }
}
