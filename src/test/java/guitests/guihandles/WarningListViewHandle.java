package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Medicine;


/**
 * Provides a handle for {@code WarningListView} containing the list of {@code WarningCard}.
 */
public class WarningListViewHandle extends NodeHandle<ListView<Medicine>> {
    public static final String WARNING_LIST_VIEW_ID = "#warningListView";

    private static final String CARD_PANE_ID = "#warningCardPane";

    public WarningListViewHandle(ListView<Medicine> warningListViewNode) {
        super(warningListViewNode);
    }

    /**
     * Navigates the listview to display {@code medicine}.
     */
    public void navigateToCard(Medicine medicine) {
        if (!getRootNode().getItems().contains(medicine)) {
            throw new IllegalArgumentException("Medicine does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(medicine);
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
     * Returns the warning card handle of a medicine associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public WarningCardHandle getWarningCardHandle(int index, WarningPanelPredicateType type) {
        return getAllCardNodes().stream()
                .map(node -> new WarningCardHandle(node, type))
                .filter(handle -> handle.equals(getMedicine(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Medicine getMedicine(int index) {
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
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
