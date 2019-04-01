package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;

/**
 * A ui for the BatchTable that is displayed in the information panel when a medicine is selected.
 */
public class BatchTable extends UiPart<Region> {

    public static final String BATCHTABLE_FOOTER_QUANTITY = "Total Quantity: ";
    public static final String BATCHTABLE_FOOTER_EXPIRY = "Next Expiry Date: ";

    private static final String FXML = "BatchTable.fxml";

    @FXML
    private Label name;

    @FXML
    private Label company;

    @FXML
    private Label quantity;

    @FXML
    private Label expiry;

    @FXML
    private TableView<Batch> table;

    public BatchTable(Medicine selectedMedicine) {
        super(FXML);

        setDescriptionTexts(selectedMedicine);
        populateTable(selectedMedicine);
    }

    private void setDescriptionTexts(Medicine selectedMedicine) {
        name.setText(selectedMedicine.getName().toString());
        company.setText(selectedMedicine.getCompany().toString());
        quantity.setText(BATCHTABLE_FOOTER_QUANTITY + selectedMedicine.getTotalQuantity().toString());
        expiry.setText(BATCHTABLE_FOOTER_EXPIRY + selectedMedicine.getNextExpiry().toString());
    }

    /**
     * Gets batch details from selectedMedicine and add them to the table.
     */
    private void populateTable(Medicine selectedMedicine) {
        ObservableList<Batch> batches = FXCollections.observableArrayList(selectedMedicine.getBatches().values());
        table.setItems(batches);
    }

}
