package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;
import static seedu.address.testutil.TypicalMedicines.IBUPROFEN;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.WarningPanelSettings;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.model.medicine.predicates.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineLowStockThresholdPredicate;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.threshold.Threshold;
import seedu.address.testutil.InventoryBuilder;
import seedu.address.testutil.MedicineBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new WarningPanelSettings(), modelManager.getWarningPanelSettings());
        assertEquals(new Inventory(), new Inventory(modelManager.getInventory()));
        assertEquals(null, modelManager.getSelectedMedicine());
        assertEquals(new WarningPanelPredicateAccessor(), modelManager.getWarningPanelPredicateAccessor());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setInventoryFilePath(Paths.get("inventory/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setInventoryFilePath(Paths.get("new/inventory/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setWarningPanelSettings_nullWarningPanelSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setWarningPanelSettings(null);
    }

    @Test
    public void setWarningPanelSettings_validWarningPanelSettings_setsWarningPanelSettings() {
        WarningPanelSettings warningPanelSettings = new WarningPanelSettings(1, 2);
        modelManager.setWarningPanelSettings(warningPanelSettings);
        assertEquals(warningPanelSettings, modelManager.getWarningPanelSettings());
    }

    @Test
    public void configureWarningPanelLists_expiryThresholdExceedsMax() {
        WarningPanelSettings actualSetting = new WarningPanelSettings(Threshold.MAX_EXPIRY_THRESHOLD + 1, 2);
        WarningPanelSettings expectedSetting = new WarningPanelSettings(Threshold.MAX_EXPIRY_THRESHOLD, 2);
        modelManager.setWarningPanelSettings(actualSetting);
        modelManager.configureWarningPanelLists();
        assertEquals(expectedSetting, modelManager.getWarningPanelSettings());
    }

    @Test
    public void setInventoryFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setInventoryFilePath(null);
    }

    @Test
    public void setInventoryFilePath_validPath_setsInventoryFilePath() {
        Path path = Paths.get("inventory/file/path");
        modelManager.setInventoryFilePath(path);
        assertEquals(path, modelManager.getInventoryFilePath());
    }

    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasMedicine(null);
    }

    @Test
    public void hasMedicine_medicineNotInInventory_returnsFalse() {
        assertFalse(modelManager.hasMedicine(PARACETAMOL));
    }

    @Test
    public void hasMedicine_medicineInInventory_returnsTrue() {
        modelManager.addMedicine(PARACETAMOL);
        assertTrue(modelManager.hasMedicine(PARACETAMOL));
    }

    @Test
    public void deleteMedicine_medicineIsSelectedAndFirstMedicineInFilteredMedicineList_selectionCleared() {
        modelManager.addMedicine(PARACETAMOL);
        modelManager.setSelectedMedicine(PARACETAMOL);
        modelManager.deleteMedicine(PARACETAMOL);
        assertEquals(null, modelManager.getSelectedMedicine());
    }

    @Test
    public void deleteMedicine_medicineIsSelectedAndSecondMedicineInFilteredMedicineList_firstMedicineSelected() {
        modelManager.addMedicine(PARACETAMOL);
        modelManager.addMedicine(GABAPENTIN);
        assertEquals(Arrays.asList(GABAPENTIN, PARACETAMOL), modelManager.getFilteredMedicineList());
        modelManager.setSelectedMedicine(PARACETAMOL);
        modelManager.deleteMedicine(PARACETAMOL);
        assertEquals(GABAPENTIN, modelManager.getSelectedMedicine());
    }

    @Test
    public void setMedicine_medicineIsSelected_selectedMedicineUpdated() {
        modelManager.addMedicine(PARACETAMOL);
        modelManager.setSelectedMedicine(PARACETAMOL);
        Medicine updatedParacetamol = new MedicineBuilder(PARACETAMOL).withExpiry(VALID_EXPIRY_GABAPENTIN).build();
        modelManager.setMedicine(PARACETAMOL, updatedParacetamol);
        assertEquals(updatedParacetamol, modelManager.getSelectedMedicine());
    }

    @Test
    public void getFilteredMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredMedicineList().remove(0);
    }

    @Test
    public void getExpiringMedicinesList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getExpiringMedicinesList().remove(0);
    }

    @Test
    public void getLowStockMedicinesList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getLowStockMedicinesList().remove(0);
    }

    @Test
    public void updateFilteredMedicineList_nullPredicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.updateFilteredMedicineList(null);
    }

    @Test
    public void updateFilteredExpiringMedicineList_nullPredicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.updateFilteredExpiringMedicineList(null);
    }

    @Test
    public void updateFilteredLowStockMedicineList_nullPredicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.updateFilteredLowStockMedicineList(null);
    }

    @Test
    public void setSelectedMedicine_medicineNotInFilteredMedicineList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        modelManager.setSelectedMedicine(PARACETAMOL);
    }

    @Test
    public void setSelectedMedicine_medicineInFilteredMedicineList_setsSelectedMedicine() {
        modelManager.addMedicine(PARACETAMOL);
        assertEquals(Collections.singletonList(PARACETAMOL), modelManager.getFilteredMedicineList());
        modelManager.setSelectedMedicine(PARACETAMOL);
        assertEquals(PARACETAMOL, modelManager.getSelectedMedicine());
    }

    @Test
    public void equals() {
        Inventory inventory = new InventoryBuilder().withMedicine(PARACETAMOL).withMedicine(IBUPROFEN).build();
        Inventory differentInventory = new Inventory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(inventory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(inventory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different inventory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentInventory, userPrefs)));

        // different filteredMedicineList -> returns false
        String[] keywords = PARACETAMOL.getName().fullName.split("\\s+");
        modelManager.updateFilteredMedicineList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);

        // different filteredExpiringMedicineList -> returns false
        Threshold threshold =
                new Threshold(Integer.toString(Threshold.MAX_EXPIRY_THRESHOLD), WarningPanelPredicateType.EXPIRY);
        modelManager.updateFilteredExpiringMedicineList(new MedicineExpiryThresholdPredicate(threshold));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpiringMedicineList(
                new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD));

        // different filteredLowStockMedicineList -> returns false
        threshold =
                new Threshold(Integer.toString(Threshold.MAX_QUANTITY_THRESHOLD), WarningPanelPredicateType.LOW_STOCK);
        modelManager.updateFilteredLowStockMedicineList(new MedicineLowStockThresholdPredicate(threshold));
        assertFalse(modelManager.equals(new ModelManager(inventory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredLowStockMedicineList(
                new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInventoryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(inventory, differentUserPrefs)));
    }
}
