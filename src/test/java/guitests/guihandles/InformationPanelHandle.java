package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code InformationPanel} of the UI.
 */
public class InformationPanelHandle extends NodeHandle<Node> {

    public static final String INFORMATION_PANEL_ID = "#informationPanel";

    private static final String BATCHTABLE_BORDER_PANE_ID = "#batchTable";
    private static final String MEDICINE_NAME_LABEL = "#name";
    private static final String MEDICINE_COMPANY_LABEL = "#company";
    private static final String MEDICINE_QUANTITY_LABEL = "#quantity";
    private static final String MEDICINE_EXPIRY_LABEL = "#expiry";

    private List<String> lastRememberedTableDetails;

    public InformationPanelHandle(Node informationPanelNode) {
        super(informationPanelNode);
    }

    /**
     * Returns true if the BatchTable has been loaded, else return false.
     */
    public boolean isBatchTableLoaded() {
        try {
            getChildNode(BATCHTABLE_BORDER_PANE_ID);
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
        List<String> details = new ArrayList<>();
        if (isBatchTableLoaded()) {
            details.add(getMedicineName());
            details.add(getMedicineCompany());
            details.add(getMedicineQuantity());
            details.add(getMedicineExpiry());
            return details;
        }
        return null;
    }

    public String getMedicineName() {
        return getDetail(MEDICINE_NAME_LABEL);
    }

    public String getMedicineCompany() {
        return getDetail(MEDICINE_COMPANY_LABEL);
    }

    public String getMedicineQuantity() {
        return getDetail(MEDICINE_QUANTITY_LABEL);
    }

    public String getMedicineExpiry() {
        return getDetail(MEDICINE_EXPIRY_LABEL);
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

}
