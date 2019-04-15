package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.InformationPanelSettings;
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

    public static final String FONT_SIZE_VERY_SMALL = "-fx-font-size: 12pt";
    public static final String FONT_SIZE_SMALL = "-fx-font-size: 15pt";
    public static final String FONT_SIZE_MEDIUM = "-fx-font-size: 20pt";
    public static final int NAME_LENGTH_VERY_LONG = 30;
    public static final int NAME_LENGTH_LONG = 25;
    public static final int NAME_LENGTH_MEDIUM = 20;

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

    public BatchTable(Medicine selectedMedicine, InformationPanelSettings informationPanelSettings) {
        super(FXML);

        setDescriptionTexts(selectedMedicine);
        setColumnWidth();
        populateTable(selectedMedicine);
        sortTable(informationPanelSettings);
        disableSortingByClicks(); // To prevent sorting without change to InformationPanelSettings
    }

    private void setDescriptionTexts(Medicine selectedMedicine) {
        setNameText(selectedMedicine.getName().toString());
        company.setText(selectedMedicine.getCompany().toString());
        quantity.setText(BATCHTABLE_FOOTER_QUANTITY + selectedMedicine.getTotalQuantity().toString());
        expiry.setText(BATCHTABLE_FOOTER_EXPIRY + selectedMedicine.getNextExpiry().toString());
    }

    private void setNameText(String medicineName) {
        if (medicineName.length() > NAME_LENGTH_VERY_LONG) {
            name.setStyle(FONT_SIZE_VERY_SMALL);
        } else if (medicineName.length() > NAME_LENGTH_LONG) {
            name.setStyle(FONT_SIZE_SMALL);
        } else if (medicineName.length() > NAME_LENGTH_MEDIUM) {
            name.setStyle(FONT_SIZE_MEDIUM);
        }
        name.setText(medicineName);
    }

    private void setColumnWidth() {
        numberColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
        quantityColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.24));
        expiryColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.24));
    }

    /**
     * Gets batch details from {@code selectedMedicine} and add them to the table.
     */
    private void populateTable(Medicine selectedMedicine) {
        ObservableList<Batch> batches = FXCollections.observableArrayList(selectedMedicine.getBatches().values());
        table.setItems(batches);
    }

    /**
     * Sorts the table depending on the {@code informationPanelSettings}.
     */
    private void sortTable(InformationPanelSettings informationPanelSettings) {
        SortProperty sortProperty = informationPanelSettings.getSortProperty();
        SortDirection sortDirection = informationPanelSettings.getSortDirection();

        TableColumn<Batch, ?> column;
        switch (sortProperty) {
        case BATCHNUMBER:
            column = numberColumn;
            break;
        case QUANTITY:
            column = quantityColumn;
            break;
        case EXPIRY:
            column = expiryColumn;
            break;
        default:
            throw new IllegalArgumentException("Unknown sort property.");
        }

        switch (sortDirection) {
        case ASCENDING:
            column.setSortType(TableColumn.SortType.ASCENDING);
            break;
        case DESCENDING:
            column.setSortType(TableColumn.SortType.DESCENDING);
            break;
        default:
            throw new IllegalArgumentException("Unknown sort direction.");
        }

        table.getSortOrder().add(column);
    }

    private void disableSortingByClicks() {
        numberColumn.setSortable(false);
        quantityColumn.setSortable(false);
        expiryColumn.setSortable(false);
    }
}
