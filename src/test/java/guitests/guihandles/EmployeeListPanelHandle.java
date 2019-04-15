package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.employee.Employee;

/**
 * Provides a handle for {@code EmployeeListPanel} containing the list of {@code EmployeeCard}.
 */
public class EmployeeListPanelHandle extends NodeHandle<ListView<Employee>> {
    public static final String EMPLOYEE_LIST_VIEW_ID = "#employeeListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Employee> lastRememberedSelectedEmployeeCard;

    public EmployeeListPanelHandle(ListView<Employee> employeeListPanelNode) {
        super(employeeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EmployeeCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EmployeeCardHandle getHandleToSelectedCard() {
        List<Employee> selectedEmployeeList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedEmployeeList.size() != 1) {
            throw new AssertionError("Employee list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(EmployeeCardHandle::new)
                .filter(handle -> handle.equals(selectedEmployeeList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Employee> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code employee}.
     */
    public void navigateToCard(Employee employee) {
        if (!getRootNode().getItems().contains(employee)) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(employee);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code EmployeeCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the employee card handle of a employee associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public EmployeeCardHandle getEmployeeCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(EmployeeCardHandle::new)
                .filter(handle -> handle.equals(getEmployee(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Employee getEmployee(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code EmployeeCard} in the list.
     */
    public void rememberSelectedEmployeeCard() {
        List<Employee> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEmployeeCard = Optional.empty();
        } else {
            lastRememberedSelectedEmployeeCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code EmployeeCard} is different from the value remembered by the most recent
     * {@code rememberSelectedEmployeeCard()} call.
     */
    public boolean isSelectedEmployeeCardChanged() {
        List<Employee> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedEmployeeCard.isPresent();
        } else {
            return !lastRememberedSelectedEmployeeCard.isPresent()
                    || !lastRememberedSelectedEmployeeCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
