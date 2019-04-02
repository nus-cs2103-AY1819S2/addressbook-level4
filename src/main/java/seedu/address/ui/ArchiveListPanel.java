package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * Panel containing the list of persons.
 */
public class ArchiveListPanel extends UiPart<Region> {
    private static final String FXML = "ArchiveListPanel.fxml";

    @FXML
    private ListView<Person> archiveListView;

    public ArchiveListPanel(ObservableList<Person> personList) {
        super(FXML);
        archiveListView.setItems(personList);
        archiveListView.setCellFactory(listView -> new PersonListViewCell());
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
                if (person instanceof Buyer) {
                    setGraphic(new PersonCard((Buyer) person, getIndex() + 1).getRoot());
                } else if (person instanceof Seller) {
                    setGraphic(new PersonCard((Seller) person, getIndex() + 1).getRoot());
                } else if (person instanceof Landlord) {
                    setGraphic(new PersonCard((Landlord) person, getIndex() + 1).getRoot());
                } else if (person instanceof Tenant) {
                    setGraphic(new PersonCard((Tenant) person, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
