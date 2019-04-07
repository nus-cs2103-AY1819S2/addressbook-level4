package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMedicines.LEVOTHYROXINE;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InformationPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;

public class InformationPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private SimpleObjectProperty<InformationPanelSettings> informationPanelSettings = new SimpleObjectProperty<>();
    private InformationPanel informationPanel;
    private InformationPanelHandle informationPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> informationPanel = new InformationPanel(selectedMedicine, informationPanelSettings));
        uiPartRule.setUiPart(informationPanel);

        informationPanelHandle = new InformationPanelHandle(informationPanel.getRoot());
    }

    @Test
    public void display() {
        // default
        assertFalse(informationPanelHandle.isBatchTableLoaded());

        // load table of selected medicine
        guiRobot.interact(() -> selectedMedicine.set(PARACETAMOL));
        assertTrue(informationPanelHandle.isBatchTableLoaded());
        checkTable(informationPanel.getBatchTable(), InformationPanelSettings.DEFAULT_SORT_PROPERTY,
                InformationPanelSettings.DEFAULT_SORT_DIRECTION);

        // Select another medicine and change settings
        guiRobot.interact(() -> selectedMedicine.set(LEVOTHYROXINE));
        assertTrue(informationPanelHandle.isBatchTableLoaded());
        guiRobot.interact(() -> informationPanelSettings.set(new InformationPanelSettings(SortProperty.EXPIRY,
                SortDirection.DESCENDING)));
        checkTable(informationPanel.getBatchTable(), SortProperty.EXPIRY, SortDirection.DESCENDING);

        // deselect medicine
        guiRobot.interact(() -> selectedMedicine.set(null));
        assertFalse(informationPanelHandle.isBatchTableLoaded());
    }

    /**
     * Test that loaded batch table contains all the relevant information.
     */
    private void checkTable(BatchTable batchTable, SortProperty sortProperty, SortDirection sortDirection) {
        assertTrue(batchTable.getNameLabelText().equals(selectedMedicine.getValue().getName().toString()));
        assertTrue(batchTable.getCompanyLabelText().equals(selectedMedicine.getValue().getCompany().toString()));
        assertTrue(batchTable.getQuantityLabelText().equals(BatchTable.BATCHTABLE_FOOTER_QUANTITY
                + selectedMedicine.getValue().getTotalQuantity().toString()));
        assertTrue(batchTable.getExpiryLabelTexts().equals(BatchTable.BATCHTABLE_FOOTER_EXPIRY
                + selectedMedicine.getValue().getNextExpiry().toString()));

        List<Batch> data = batchTable.getTableData();
        assertSorted(data, sortProperty, sortDirection);
        for (Batch batch : selectedMedicine.getValue().getBatches().values()) {
            assertTrue(data.contains(batch));
        }
    }

    private void assertSorted(List<Batch> data, SortProperty sortProperty, SortDirection sortDirection) {
        if (sortDirection.equals(SortDirection.DESCENDING)) {
            Collections.reverse(data);
        }
        List<Batch> sortedData = new ArrayList<>(data);

        switch (sortProperty) {
        case BATCHNUMBER:
            sortedData.sort(Comparator.comparing(b -> b.getBatchNumber().toString()));
            break;
        case EXPIRY:
            sortedData.sort(Comparator.comparing(b -> b.getExpiry()));
            break;
        case QUANTITY:
            sortedData.sort(Comparator.comparing(b -> b.getQuantity().getNumericValue()));
        }
    }
}
