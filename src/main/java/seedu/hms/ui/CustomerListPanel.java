package seedu.hms.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.customer.Customer;

/**
 * Panel containing the list of customers.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);

    @FXML
    private ListView<Customer> customerListView;

    public CustomerListPanel(ObservableList<Customer> customerList, ObservableValue<Customer> selectedCustomer,
                             Consumer<Customer> onSelectedCustomerChange) {
        super(FXML);
        customerListView.setItems(customerList);
        customerListView.setCellFactory(listView -> new CustomerListViewCell());
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in customer list panel changed to : '" + newValue + "'");
            onSelectedCustomerChange.accept(newValue);
        });
        selectedCustomer.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected customer changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected customer,
            // otherwise we would have an infinite loop.
            if (Objects.equals(customerListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                customerListView.getSelectionModel().clearSelection();
            } else {
                int index = customerListView.getItems().indexOf(newValue);
                customerListView.scrollTo(index);
                customerListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class CustomerListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);

            Platform.runLater(() -> {
                if (empty || customer == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new CustomerCard(customer, getIndex() + 1).getRoot());
                }
            });
        }
    }

}
