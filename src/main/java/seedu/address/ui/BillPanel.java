package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Bill;

/**
 * The Browser Panel for the bill.
 */

public class BillPanel extends UiPart<Region> {

    private static final String FXML = "BillPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label tableNumber;

    @FXML
    private Label receipt;

    public BillPanel(Bill bill) {
        super(FXML);

        requireNonNull(bill);
        tableNumber.setText("TABLE NUMBER " + bill.getTableNumber().tableNumber);
        receipt.setWrapText(true);
        receipt.setText(bill.getReceiptWFormattedDate());
    }
}
