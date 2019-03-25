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
import seedu.address.model.request.Request;

/**
 * A panel containing the list of requests on the UI.
 * @author Hui Chun
 */
public class RequestListPanel extends UiPart<Region> {
    private static final String FXML = "RequestListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RequestListPanel.class);

    @FXML
    private ListView<Request> requestListView;

    public RequestListPanel(ObservableList<Request> requestList, ObservableValue<Request> selectedRequest,
                Consumer<Request> onSelectedRequestChange) {
        super(FXML);
        requestListView.setItems(requestList);
        requestListView.setCellFactory(listView -> new RequestListViewCell());
        requestListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in request list panel changed to : '" + newValue + "'");
            onSelectedRequestChange.accept(newValue);
        });
        selectedRequest.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected request changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected request,
            // otherwise we would have an infinite loop.
            if (Objects.equals(requestListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                requestListView.getSelectionModel().clearSelection();
            } else {
                int index = requestListView.getItems().indexOf(newValue);
                requestListView.scrollTo(index);
                requestListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class RequestListViewCell extends ListCell<Request> {
        @Override
        protected void updateItem(Request request, boolean empty) {
            super.updateItem(request, empty);

            if (empty || request == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RequestCard(request, getIndex() + 1).getRoot());
            }
        }
    }

}
