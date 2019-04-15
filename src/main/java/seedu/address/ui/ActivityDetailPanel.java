package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Panel containing the details of an activity.
 */
public class ActivityDetailPanel extends UiPart<Region> {
    private static final String FXML = "ActivityDetailPanel.fxml";
    //private final ObservableList<Person> attending;

    @FXML
    private VBox actDetParent;
    @FXML
    private Text actName;
    @FXML
    private Text actTime;
    @FXML
    private Text actLocation;
    @FXML
    private Text actDescription;
    @FXML
    private Text actStatus;
    @FXML
    private Text numAttending;
    @FXML
    private Text hasAttending;
    @FXML
    private ListView<Person> attendingList;

    public ActivityDetailPanel(ObservableValue<Activity> selectedActivity, ObservableList<Person> attending) {
        super(FXML);


        getRoot().setOnKeyPressed(Event::consume);

        selectedActivity.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                setUpDefaultView();
                return;
            }
            setActivityDetails(newValue);
        });

        setUpDefaultView();

        //attending.addListener((ListChangeListener.Change<? super Person> c) ->  );

        attendingList.setItems(attending);
        attendingList.setCellFactory(listView -> new PersonListViewCell());

    }

    private void setActivityDetails(Activity activity) {
        actName.setText(activity.getName().fullActivityName);
        actTime.setText("Date & Time: " + activity.getDateTime().fullDateTime);
        actLocation.setText("Location: " + activity.getLocation().value);
        actDescription.setText("Description: " + activity.getDescription().value);
        actDescription.setWrappingWidth(actDetParent.getWidth() - 2);
        actStatus.setText("Status: " + activity.getStatus().status.name());
        numAttending.setText("Number of people attending: " + activity.getNumberAttending());

        if (activity.getNumberAttending() > 0) {
            hasAttending.setText("\nHere's the list of people attending: \n");
        } else {
            hasAttending.setText("\nNo person in the attending list. \n");
        }
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

    private void setUpView(Activity activity, ObservableList<Person> attending) {
        this.setActivityDetails(activity);
        attendingList.setItems(attending);
        attendingList.setCellFactory(listView -> new PersonListViewCell());
    }

    private void setUpDefaultView() {
        actName.setText("");
        actTime.setText("");
        actLocation.setText("");
        actDescription.setText("");
        actStatus.setText("");
        numAttending.setText("");
        hasAttending.setText("");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Activity)) {
            return false;
        }

        // state check
        ActivityDetailPanel detail = (ActivityDetailPanel) other;
        return actName.getText().equals(detail.actName.getText())
                && actTime.getText().equals(actTime);
    }

}
