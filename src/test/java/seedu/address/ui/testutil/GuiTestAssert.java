package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.MedicineCardHandle;
import guitests.guihandles.MedicineListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.WarningCardHandle;
import seedu.address.model.medicine.Medicine;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(MedicineCardHandle expectedCard, MedicineCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCompany(), actualCard.getCompany());
        assertEquals(expectedCard.getExpiry(), actualCard.getExpiry());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMedicine}.
     */
    public static void assertCardDisplaysMedicine(Medicine expectedMedicine, MedicineCardHandle actualCard) {
        assertEquals(expectedMedicine.getName().fullName, actualCard.getName());
        assertEquals(expectedMedicine.getTotalQuantity().toString(), actualCard.getQuantity());
        assertEquals(expectedMedicine.getNextExpiry().toString(), actualCard.getExpiry());
        assertEquals(expectedMedicine.getCompany().companyName, actualCard.getCompany());
        assertEquals(expectedMedicine.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMedicine}.
     */
    public static void assertCardDisplaysMedicine(Medicine expectedMedicine, WarningCardHandle actualCard) {
        assertEquals(expectedMedicine.getName().fullName, actualCard.getName());
        assertTrue(actualCard.equals(expectedMedicine));
    }

    /**
     * Asserts that the list in {@code medicineListPanelHandle} displays the details of {@code medicines} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MedicineListPanelHandle medicineListPanelHandle, Medicine... medicines) {
        for (int i = 0; i < medicines.length; i++) {
            medicineListPanelHandle.navigateToCard(i);
            assertCardDisplaysMedicine(medicines[i], medicineListPanelHandle.getMedicineCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code medicineListPanelHandle} displays the details of {@code medicines} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MedicineListPanelHandle medicineListPanelHandle, List<Medicine> medicines) {
        assertListMatching(medicineListPanelHandle, medicines.toArray(new Medicine[0]));
    }

    /**
     * Asserts the size of the list in {@code medicineListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(MedicineListPanelHandle medicineListPanelHandle, int size) {
        int numberOfPeople = medicineListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
