package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Bill;

/**
 * The Browser Panel for the bill.
 */

public class BillBrowserPanel extends UiPart<Region> {

    private static final String FXML = "BillBrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label tableNumber;

    @FXML
    private Label receipt;

    public BillBrowserPanel(Bill bill) {
        super(FXML);

        requireNonNull(bill);
        tableNumber.setText("TABLE NUMBER " + bill.getTableNumber().tableNumber);
        receipt.setWrapText(true);
        receipt.setText(bill.getReceipt());
    }
}
