package guitests.guihandles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;
import seedu.address.ui.BatchTable;

/**
 * A handler for the {@code InformationPanel} of the UI.
 */
public class InformationPanelHandle extends NodeHandle<Node> {
    public static final String INFORMATION_PANEL_ID = "#informationPanel";

    private static final String BATCHTABLE_TABLE_VIEW_ID = "#table";
    private static final String MEDICINE_NAME_LABEL_ID = "#name";
    private static final String MEDICINE_COMPANY_LABEL_ID = "#company";
    private static final String MEDICINE_QUANTITY_LABEL_ID = "#quantity";
    private static final String MEDICINE_EXPIRY_LABEL_ID = "#expiry";

    private List<String> lastRememberedTableDetails;

    public InformationPanelHandle(Node informationPanelNode) {
        super(informationPanelNode);
    }

    /**
     * Returns true if the BatchTable has been loaded, else return false.
     */
    public boolean isBatchTableLoaded() {
        try {
            getChildNode(BATCHTABLE_TABLE_VIEW_ID);
            return true;
        } catch (NodeNotFoundException e) {
            return false;
        }
    }

    /**
     * set lastRememberedTableDetails to current table's details.
     */
    public void rememberLoadedTableDetails() {
        lastRememberedTableDetails = getTableDetails();
    }

    /**
     * Returns true if the table details have changed, else return false.
     */
    public boolean isLoadedTableDetailsChanged() {
        if (lastRememberedTableDetails == null) {
            return getTableDetails() != null;
        }
        return !getTableDetails().equals(lastRememberedTableDetails);
    }

    /**
     * Returns a list of details from the loaded table, in the order of name, company, quantity, expiry.
     */
    public List<String> getTableDetails() {
        if (!isBatchTableLoaded()) {
            return null;
        }

        List<String> details = new ArrayList<>();
        details.add(getMedicineName());
        details.add(getMedicineCompany());
        details.add(getMedicineQuantity());
        details.add(getMedicineExpiry());
        return details;
    }

    /**
     * Returns a list of batches being displayed in the loaded table.
     */
    public List<Batch> getTableData() {
        if (!isBatchTableLoaded()) {
            return null;
        }

        Node batchTable = getChildNode(BATCHTABLE_TABLE_VIEW_ID);
        if (batchTable instanceof TableView) {
            return new ArrayList<>(((TableView<Batch>) batchTable).getItems());
        }
        throw new IllegalArgumentException("Unknown node found");
    }

    public String getMedicineName() {
        return getDetail(MEDICINE_NAME_LABEL_ID);
    }

    public String getMedicineCompany() {
        return getDetail(MEDICINE_COMPANY_LABEL_ID);
    }

    public String getMedicineQuantity() {
        return getDetail(MEDICINE_QUANTITY_LABEL_ID);
    }

    public String getMedicineExpiry() {
        return getDetail(MEDICINE_EXPIRY_LABEL_ID);
    }

    private String getDetail(String labelName) {
        if (isBatchTableLoaded()) {
            Node detailLabel = getChildNode(labelName);
            if (detailLabel instanceof Label) {
                return ((Label) detailLabel).getText();
            }
        }
        return null;
    }

    /**
     * Test that loaded batch table contains all the relevant information of {@code selectedMedicine}.
     */
    public void assertTableCorrect(Medicine selectedMedicine, InformationPanelSettings informationPanelSettings) {
        List<String> expectedDetails = new ArrayList<>();
        expectedDetails.add(selectedMedicine.getName().toString());
        expectedDetails.add(selectedMedicine.getCompany().toString());
        expectedDetails.add(BatchTable.BATCHTABLE_FOOTER_QUANTITY + selectedMedicine.getTotalQuantity().toString());
        expectedDetails.add(BatchTable.BATCHTABLE_FOOTER_EXPIRY + selectedMedicine.getNextExpiry().toString());
        assertEquals(expectedDetails, getTableDetails());

        List<Batch> data = getTableData();
        assertSorted(data, informationPanelSettings);
        for (Batch batch : selectedMedicine.getBatches().values()) {
            assertTrue(data.contains(batch));
        }
    }

    /**
     * Asserts that the data is sorted according to the sortProperty and sortDirection.
     */
    private void assertSorted(List<Batch> data, InformationPanelSettings informationPanelSettings) {

        if (informationPanelSettings.getSortDirection().equals(SortDirection.DESCENDING)) {
            Collections.reverse(data);
        }
        List<Batch> sortedData = new ArrayList<>(data);

        switch (informationPanelSettings.getSortProperty()) {
        case BATCHNUMBER:
            sortedData.sort(Comparator.comparing(b -> b.getBatchNumber().toString()));
            break;
        case EXPIRY:
            sortedData.sort(Comparator.comparing(Batch::getExpiry));
            break;
        case QUANTITY:
            sortedData.sort(Comparator.comparing(Batch::getQuantity));
            break;
        default:
            throw new IllegalArgumentException("Unknown Sort Property");
        }
        assertEquals(data, sortedData);
    }
}
