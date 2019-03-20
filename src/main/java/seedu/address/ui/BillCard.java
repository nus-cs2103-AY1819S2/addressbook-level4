package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.Bill;

/**
 * The Receipt to be added in the BillBrowserPanel.
 */
public class BillCard extends UiPart<Region> {
    private static final String FXML = "BillPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Bill bill;

    @FXML
    private Label tableNumber;

    @FXML
    private TextArea receipt;

    public BillCard(Bill bill) {
        super(FXML);
        requireNonNull(bill);
        this.bill = bill;
        tableNumber.setText("TABLE NUMBER " + bill.getTableNumber().tableNumber);
        //TODO: Find out why there will be nullpointer exception
        receipt.setText(bill.getReceipt());
        System.out.println("Hello");
    }
}

//    // TODO: constructors for different modes
//    public BillBrowserPanel(ObservableValue<Bill> selectedBill) {
//        super(FXML);
//        // Load person page when selected person changes.
//        selectedBill.addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) {
//                loadDefaultText();
//                return;
//            }
//            loadBillReceipt(newValue);
//        });
//    }
//
//    public BillBrowserPanel() {
//        super(FXML);
//        loadDefaultText();
//    }
//
//
//    /**
//     * Loads the actual receipt.
//     */
//    private void loadBillReceipt(Bill bill) {
//        requireNonNull(bill);
//        text = new Text(bill.getReceipt());
//        receipt.getChildren().addAll(text);
//        tableNumber.setText(bill.getTableNumber().tableNumber);
//    }
//
//    /**
//     * Loads a default receipt.
//     */
//    private void loadDefaultText() {
//        text = new Text("No Bill Found");
//        receipt.getChildren().addAll(text);
//    }

