package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons not in activity.
 */
public class PersonNotAttendingListPanel extends UiPart<Region> {
    private static final String FXML = "PersonNotAttendingListPanel.fxml";

    @FXML
    private ListView<Person> personNotAttending;

    public PersonNotAttendingListPanel(ObservableList<Person> personList) {
        super(FXML);

        getRoot().setOnKeyPressed(Event::consume);

        personNotAttending.setItems(personList);
        personNotAttending.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
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
