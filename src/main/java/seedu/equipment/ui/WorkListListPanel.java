package seedu.equipment.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.model.WorkList;

/**
 * Panel containing the list of WorkLists.
 */
public class WorkListListPanel extends UiPart<Region> {
    private static final String FXML = "WorkListListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WorkListListPanel.class);

    @FXML
    private ListView<WorkList> workListListView;

    public WorkListListPanel(ObservableList<WorkList> workListList, ObservableValue<WorkList> selectedWorkList,
                              Consumer<WorkList> onselectedWorkListChange) {
        super(FXML);
        workListListView.setItems(workListList);
        workListListView.setCellFactory(listView -> new WorkListListViewCell());
        workListListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in WorkList list panel changed to : '" + newValue + "'");
            onselectedWorkListChange.accept(newValue);
        });
        selectedWorkList.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected WorkList changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected WorkLists,
            // otherwise we would have an infinite loop.
            if (Objects.equals(workListListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                workListListView.getSelectionModel().clearSelection();
            } else {
                int index = workListListView.getItems().indexOf(newValue);
                workListListView.scrollTo(index);
                workListListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code WorkList} using a {@code WorkListCard}.
     */
    class WorkListListViewCell extends ListCell<WorkList> {
        @Override
        protected void updateItem(WorkList workList, boolean empty) {
            super.updateItem(workList, empty);

            if (empty || workList == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WorkListCard(workList, getIndex() + 1).getRoot());
            }
        }
    }

}
