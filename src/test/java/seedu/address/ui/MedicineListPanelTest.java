package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalMedicines;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.MedicineCardHandle;
import guitests.guihandles.MedicineListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;

public class MedicineListPanelTest extends GuiUnitTest {
    private static final ObservableList<Medicine> TYPICAL_MEDICINES =
            FXCollections.observableList(getTypicalMedicines());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private MedicineListPanelHandle medicineListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MEDICINES);

        for (int i = 0; i < TYPICAL_MEDICINES.size(); i++) {
            medicineListPanelHandle.navigateToCard(TYPICAL_MEDICINES.get(i));
            Medicine expectedMedicine = TYPICAL_MEDICINES.get(i);
            MedicineCardHandle actualCard = medicineListPanelHandle.getMedicineCardHandle(i);

            assertCardDisplaysMedicine(expectedMedicine, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedMedicineChanged_selectionChanges() {
        initUi(TYPICAL_MEDICINES);
        Medicine secondMedicine = TYPICAL_MEDICINES.get(INDEX_SECOND_MEDICINE.getZeroBased());
        guiRobot.interact(() -> selectedMedicine.set(secondMedicine));
        guiRobot.pauseForHuman();

        MedicineCardHandle expectedMedicine = medicineListPanelHandle.getMedicineCardHandle(
                INDEX_SECOND_MEDICINE.getZeroBased());
        MedicineCardHandle selectedMedicine = medicineListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedMedicine, selectedMedicine);
    }

    /**
     * Verifies that creating and deleting large number of medicines in {@code MedicineListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Medicine> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of medicine cards exceeded time limit");
    }

    /**
     * Returns a list of medicines containing {@code medicineCount} medicines that is used to populate the
     * {@code MedicineListPanel}.
     */
    private ObservableList<Medicine> createBackingList(int medicineCount) {
        ObservableList<Medicine> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < medicineCount; i++) {
            Name name = new Name(i + "a");
            Quantity quantity = new Quantity("0");
            Expiry expiry = new Expiry("-");
            Company company = new Company("a");
            Medicine medicine = new Medicine(name, company, quantity, expiry, Collections.emptySet(),
                    Collections.emptyMap());
            backingList.add(medicine);
        }
        return backingList;
    }

    /**
     * Initializes {@code medicineListPanelHandle} with a {@code MedicineListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code MedicineListPanel}.
     */
    private void initUi(ObservableList<Medicine> backingList) {
        MedicineListPanel medicineListPanel =
                new MedicineListPanel(backingList, selectedMedicine, selectedMedicine::set);
        uiPartRule.setUiPart(medicineListPanel);

        medicineListPanelHandle = new MedicineListPanelHandle(getChildNode(medicineListPanel.getRoot(),
                MedicineListPanelHandle.MEDICINE_LIST_VIEW_ID));
    }
}
