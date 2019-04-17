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
import seedu.address.model.apparel.Apparel;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Apparel> personListView;

    public PersonListPanel(ObservableList<Apparel> apparelList, ObservableValue<Apparel> selectedPerson,
                           Consumer<Apparel> onSelectedPersonChange) {
        super(FXML);
        personListView.setItems(apparelList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in apparel list panel changed to : '" + newValue + "'");
            onSelectedPersonChange.accept(newValue);
        });
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected apparel changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected apparel,
            // otherwise we would have an infinite loop.
            if (Objects.equals(personListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                personListView.getSelectionModel().clearSelection();
            } else {
                int index = personListView.getItems().indexOf(newValue);
                personListView.scrollTo(index);
                personListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Apparel} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Apparel> {
        @Override
        protected void updateItem(Apparel apparel, boolean empty) {
            super.updateItem(apparel, empty);

            if (empty || apparel == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(apparel, getIndex() + 1).getRoot());
            }
        }
    }

}
