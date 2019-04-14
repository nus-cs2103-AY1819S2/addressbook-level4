package seedu.finance.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.finance.model.record.Record;

/**
 * An UI component that displays information of a {@code Record}.
 */
public class RecordCard extends UiPart<Region> {

    private static final String FXML = "RecordListCard.fxml";
    private static final String[] CATEGORY_COLOR_STYLES =
        { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "gray", "purple",
            "coral", "tan", "turquoise", "lightGray", "lightSkyBlue", "hotPink", "peachPuff", "cadetBlue" };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Record record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label category;

    public RecordCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        name.setText(record.getName().fullName);
        amount.setText("$" + record.getAmount().toString());
        date.setText(record.getDate().toString());
        description.setText(record.getDescription().value);
        category.setText(record.getCategory().toString());
        category.getStyleClass().add(getCategoryColorStyleFor(record.getCategory().toString()));
    }

    //@@author geezlouisee-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits/1ac2e7c5597cf328cc9c28d5d8e18db8dc1fc5a0
    // with minor modifications
    /**
     * Returns the color style for {@code categoryName}'s label.
     */
    private String getCategoryColorStyleFor(String categoryName) {
        //Using the hash code of the category name to generate a random color, so color remains consistent
        //between different runs of the program while still making it random enough between Categories
        return CATEGORY_COLOR_STYLES[Math.abs(categoryName.hashCode() % 23) % CATEGORY_COLOR_STYLES.length];
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCard)) {
            return false;
        }

        // state check
        RecordCard card = (RecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
