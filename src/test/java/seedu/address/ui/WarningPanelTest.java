package seedu.address.ui;

import guitests.guihandles.MedicineCardHandle;
import guitests.guihandles.MedicineListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import seedu.address.model.medicine.*;

import java.util.Collections;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalMedicines;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

public class WarningPanelTest extends GuiUnitTest {
    private static final ObservableList<String> DUMMY_LIST =
            FXCollections.observableArrayList("dummy1", "dummy2", "dummy3");

    private final SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private MedicineListPanelHandle medicineListPanelHandle;

    @Test
    public void display() {
        initUi(DUMMY_LIST);

        for (int i = 0; i < DUMMY_LIST.size(); i++) {
            String expectedDummy = DUMMY_LIST.get(i);
            MedicineCardHandle actualCard = medicineListPanelHandle.getMedicineCardHandle(i);

            assertCardDisplaysMedicine(expectedDummy, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Initializes {@code medicineListPanelHandle} with a {@code MedicineListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code MedicineListPanel}.
     */
    private void initUi(ObservableList<String> backingList) {
        MedicineListPanel medicineListPanel =
                new MedicineListPanel(backingList, selectedMedicine, selectedMedicine::set);
        uiPartRule.setUiPart(medicineListPanel);

        medicineListPanelHandle = new MedicineListPanelHandle(getChildNode(medicineListPanel.getRoot(),
                MedicineListPanelHandle.MEDICINE_LIST_VIEW_ID));
    }
}
