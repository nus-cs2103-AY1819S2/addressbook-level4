package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;

import org.junit.Test;

import guitests.guihandles.WarningCardHandle;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.MedicineBuilder;


public class WarningCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Medicine medicine = new MedicineBuilder().withBatches("batch number", "1", "12/12/2019").build();

        // thresholds set high enough to view all medicines and batches
        WarningPanelPredicateAccessor predicateAccessor = new WarningPanelPredicateAccessor();
        predicateAccessor.setMaxThresholds();

        // expiry list
        WarningCard warningCard = new WarningCard(medicine, 1, WarningPanelPredicateType.EXPIRY, predicateAccessor);
        uiPartRule.setUiPart(warningCard);
        assertCardDisplay(warningCard, medicine, 1, WarningPanelPredicateType.EXPIRY);

        // low stock list
        warningCard = new WarningCard(medicine, 2, WarningPanelPredicateType.LOW_STOCK, predicateAccessor);
        uiPartRule.setUiPart(warningCard);
        assertCardDisplay(warningCard, medicine, 2, WarningPanelPredicateType.LOW_STOCK);
    }

    @Test
    public void equals() {
        Medicine medicine = new MedicineBuilder().withBatches("batch number", "1", "12/12/2019").build();
        WarningPanelPredicateAccessor predicateAccessor = new WarningPanelPredicateAccessor();
        predicateAccessor.setMaxThresholds();

        WarningCard expiryWarningCard = new WarningCard(medicine, 0,
                WarningPanelPredicateType.EXPIRY, predicateAccessor);
        WarningCard lowStockWarningCard = new WarningCard(medicine, 0,
                WarningPanelPredicateType.LOW_STOCK, predicateAccessor);

        // same medicine, same index -> returns true
        WarningCard copy = new WarningCard(medicine, 0, WarningPanelPredicateType.EXPIRY, predicateAccessor);
        assertTrue(expiryWarningCard.equals(copy));

        copy = new WarningCard(medicine, 0, WarningPanelPredicateType.LOW_STOCK, predicateAccessor);
        assertTrue(lowStockWarningCard.equals(copy));

        // same object -> returns true
        assertTrue(expiryWarningCard.equals(expiryWarningCard));
        assertTrue(lowStockWarningCard.equals(lowStockWarningCard));

        // null -> returns false
        assertFalse(expiryWarningCard.equals(null));
        assertFalse(lowStockWarningCard.equals(null));

        // different types -> returns false
        assertFalse(expiryWarningCard.equals(0));
        assertFalse(lowStockWarningCard.equals(0));

        // different medicine, same index -> returns false
        Medicine differentMedicine = new MedicineBuilder().withName("differentName").build();
        assertFalse(expiryWarningCard.equals(new WarningCard(differentMedicine, 0,
                WarningPanelPredicateType.EXPIRY, predicateAccessor)));
        assertFalse(lowStockWarningCard.equals(new WarningCard(differentMedicine, 0,
                WarningPanelPredicateType.LOW_STOCK, predicateAccessor)));

        // same medicine, different index -> returns false
        assertFalse(expiryWarningCard.equals(new WarningCard(medicine, 1,
                WarningPanelPredicateType.EXPIRY, predicateAccessor)));
        assertFalse(lowStockWarningCard.equals(new WarningCard(medicine, 1,
                WarningPanelPredicateType.LOW_STOCK, predicateAccessor)));
    }

    /**
     * Asserts that {@code warningCard} displays the details of {@code expectedMedicine} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WarningCard warningCard, Medicine expectedMedicine,
            int expectedId, WarningPanelPredicateType listType) {

        guiRobot.pauseForHuman();

        WarningCardHandle warningCardHandle = new WarningCardHandle(warningCard.getRoot(), listType);

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", warningCardHandle.getId());

        // verify medicine details are displayed correctly
        assertCardDisplaysMedicine(expectedMedicine, warningCardHandle);
    }
}
