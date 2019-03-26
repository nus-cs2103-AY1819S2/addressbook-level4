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
import seedu.address.model.person.Person;

/**
 * Panel containing the list of pinned persons.
 */
public class PinListPanel extends UiPart<Region> {
    private static final String FXML = "PinListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PinListPanel.class);

    @FXML
    private ListView<Person> pinListView;

    public PinListPanel(ObservableList<Person> pinList, ObservableValue<Person> pinnedPerson,
            Consumer<Person> onPinnedPersonChange) {
        super(FXML);
        pinListView.setItems(pinList);
        pinListView.setCellFactory(listView -> new PinListViewCell());
        pinListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in pin list panel changed to : '" + newValue + "'");
            onPinnedPersonChange.accept(newValue);
        });
        pinnedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected person changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(pinListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                pinListView.getSelectionModel().clearSelection();
            } else {
                int index = pinListView.getItems().indexOf(newValue);
                pinListView.scrollTo(index);
                pinListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PinListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
