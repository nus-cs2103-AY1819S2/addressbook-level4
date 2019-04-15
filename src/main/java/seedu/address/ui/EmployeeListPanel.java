package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;

/**
 * Panel containing the list of employees.
 */
public class EmployeeListPanel extends UiPart<Region> {
    private static final String FXML = "EmployeeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmployeeListPanel.class);

    @FXML
    private ListView<Employee> employeeListView;

    public EmployeeListPanel(ObservableList<Employee> employeeList, ObservableValue<Employee> selectedEmployee,
                             Consumer<Employee> onSelectedEmployeeChange) {
        super(FXML);
        employeeListView.setItems(employeeList);
        employeeListView.setCellFactory(listView -> new EmployeeListViewCell());
        employeeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("Selection in employee list panel changed to : '" + newValue + "'");
            onSelectedEmployeeChange.accept(newValue);
        });
        selectedEmployee.addListener((observable, oldValue, newValue) -> {
            logger.info("Selected employee changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected employee,
            // otherwise we would have an infinite loop.
            if (Objects.equals(employeeListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                employeeListView.getSelectionModel().clearSelection();
            } else {
                int index = employeeListView.getItems().indexOf(newValue);
                employeeListView.scrollTo(index);
                employeeListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class EmployeeListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);

            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(employee, getIndex() + 1).getRoot());
            }
        }
    }

}
