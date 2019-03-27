package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;

/**
 * A ui for the BatchTable that is displayed in the information panel when a medicine is selected.
 */
public class BatchTable extends UiPart<Region> {


    public static final String BATCHTABLE_FOOTER_QUANTITY = "Total Quantity: ";
    public static final String BATCHTABLE_FOOTER_EXPIRY = "Next Expiry Date: ";
    private static final String FXML = "BatchTable.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label name;

    @FXML
    private Label company;

    @FXML
    private Label quantity;

    @FXML
    private Label expiry;

    @FXML
    private TableView<Batch> tablePane;

    @FXML
    private TableColumn<Batch, BatchNumber> numberColumn;

    @FXML
    private TableColumn<Batch, Quantity> quantityColumn;

    @FXML
    private TableColumn<Batch, Expiry> expiryColumn;

    public BatchTable(Medicine selectedMedicine) {
        super(FXML);

        name.setText(selectedMedicine.getName().toString());
        company.setText(selectedMedicine.getCompany().toString());
        quantity.setText(BATCHTABLE_FOOTER_QUANTITY + selectedMedicine.getTotalQuantity().toString());
        expiry.setText(BATCHTABLE_FOOTER_EXPIRY + selectedMedicine.getNextExpiry().toString());
        addBatchDetails(selectedMedicine);
    }

    /**
     * Gets batch details from medicine and add them to the table.
     */
    private void addBatchDetails(Medicine medicine) {
        ObservableList<Batch> batches = FXCollections.observableArrayList(medicine.getBatches().values());
        tablePane.setItems(batches);
    }

}
