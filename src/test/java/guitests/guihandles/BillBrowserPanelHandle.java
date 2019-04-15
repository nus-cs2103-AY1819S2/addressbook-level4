package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import seedu.address.model.statistics.Bill;

/**
 * Provides a handle for {@code BillBrowserPanel}
 */
public class BillBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BILL_BROWSER_ID = "#billBrowser";

    private static final String TITLE_FEILD_ID = "#title";
    private static final String TABLE_NUMBER_FIELD_ID = "#tableNumber";
    private static final String RECEIPT_FIELD_ID = "#receipt";

    private final Label titleLabel;
    private final Label tableNumberLabel;
    private final TextArea receiptLabel;

    public BillBrowserPanelHandle(Node node) {
        super(node);

        titleLabel = getChildNode(TITLE_FEILD_ID);
        tableNumberLabel = getChildNode(TABLE_NUMBER_FIELD_ID);
        receiptLabel = getChildNode(RECEIPT_FIELD_ID);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getTableNumber() {
        return tableNumberLabel.getText();
    }

    public String getReceipt() {
        return receiptLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code bill}.
     */
    public boolean equals(Bill bill) {
        return getTableNumber().equals(bill.getTableNumber().tableNumber) && getReceipt().equals(bill.getReceipt());
    }
}
