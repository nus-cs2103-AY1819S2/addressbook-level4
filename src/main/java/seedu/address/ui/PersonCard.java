package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES = {"darkestblue", "darkblue", "blue",
        "lightblue", "lightestblue", "white", "black"};
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label matricNumber;
    @FXML
    private Label yearOfStudy;
    @FXML
    private Label major;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        matricNumber.setText(person.getMatricNumber().value);
        yearOfStudy.setText("Year " + person.getYearOfStudy().value);
        major.setText(person.getMajor().value);
        initTags(person);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
