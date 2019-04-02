package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
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

    @FXML
    private TableColumn<Batch, BatchNumber> numberColumn;

    @FXML
    private TableColumn<Batch, Quantity> quantityColumn;

    @FXML
    private TableColumn<Batch, Expiry> expiryColumn;

    public BatchTable(Medicine selectedMedicine, SortProperty sortProperty, SortDirection sortDirection) {
        super(FXML);

        setDescriptionTexts(selectedMedicine);
        populateTable(selectedMedicine);
        sortTable(sortProperty, sortDirection);
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

    /**
     * Sorts the table depending on the input sortProperty and sortDirection.
     */
    public void sortTable(SortProperty sortProperty, SortDirection sortDirection) {
        table.getSortOrder().clear();
        TableColumn<Batch, ?> column;

        if (sortProperty.equals(SortProperty.BATCHNUMBER)) {
            column = numberColumn;
        } else if (sortProperty.equals(SortProperty.QUANTITY)) {
            column = quantityColumn;
        } else if (sortProperty.equals(SortProperty.EXPIRY)) {
            column = expiryColumn;
        } else {
            return;
        }

        if (sortDirection.equals(SortDirection.ASCENDING)) {
            column.setSortType(TableColumn.SortType.ASCENDING);
        } else if (sortDirection.equals(SortDirection.DESCENDING)) {
            column.setSortType(TableColumn.SortType.DESCENDING);
        }

        table.getSortOrder().add(column);
    }

    public String getNameLabelText() {
        return name.getText();
    }

    public String getCompanyLabelText() {
        return company.getText();
    }

    public String getQuantityLabelText() {
        return quantity.getText();
    }

    public String getExpiryLabelTexts() {
        return expiry.getText();
    }

    public List<Batch> getTableData() {
        List<Batch> data = new ArrayList<>();
        for (Batch batch : table.getItems()) {
            data.add(batch);
        }
        return data;
    }
}
