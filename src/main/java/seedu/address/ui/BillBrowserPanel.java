package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    private ListView<Bill> receiptView;

    @FXML
    private Label tableNumber;

    @FXML
    private TextArea receipt;

    public BillBrowserPanel(Bill bill, ObservableValue<Bill> selectedBill, Consumer<Bill> onSelectedBillChange) {
        super(FXML);

        requireNonNull(bill);
        tableNumber.setText("TABLE NUMBER " + bill.getTableNumber().tableNumber);
        //TODO: Find out why the text aren't printing
        receipt = new TextArea(" ");
        receipt.setWrapText(true);
        receipt.setText(bill.getReceipt());
        receipt.appendText(bill.getReceipt());

//        receiptView.setCellFactory(listView -> new BillBrowserPanel.BillCell());
//        receiptView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            logger.fine("Selection in list panel changed to : '" + newValue + "'");
//            onSelectedBillChange.accept(newValue);
//        });
//        selectedBill.addListener(((observable, oldValue, newValue) -> {
//            logger.fine("Selected item changed to: " + newValue);
//
//            // Don't modify selection if we are already selecting the selected item,
//            // otherwise we would have an infinite loop.
//            if (Objects.equals(receiptView.getSelectionModel().getSelectedItem(), newValue)) {
//                return;
//            }
//
//            if (newValue == null) {
//                receiptView.getSelectionModel().clearSelection();
//            } else {
//                int index = receiptView.getItems().indexOf(newValue);
//                receiptView.scrollTo(index);
//                receiptView.getSelectionModel().clearAndSelect(index);
//            }
        //        }));
    }

//    /**
//     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
//     */
//    class BillCell extends ListCell<Bill> {
//        @Override
//        protected void updateItem(Bill item, boolean empty) {
//            super.updateItem(item, empty);
//
//            if (empty || item == null) {
//                setGraphic(null);
//                setText(null);
//            } else {
//                setGraphic(new BillCard(bill));
//            }
//        }
//    }

}
