package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.apparel.Apparel;

/**
 * An UI component that displays information of a {@code Apparel}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FashionMatch level 4</a>
     */

    public final Apparel apparel;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label color;
    @FXML
    private Label clothingType;
    @FXML
    private Label usageCount;
    @FXML
    private Label status; //availability
    @FXML
    private FlowPane tags;

    public PersonCard(Apparel apparel, int displayedIndex) {
        super(FXML);
        this.apparel = apparel;
        id.setText(displayedIndex + ". ");
        name.setText(apparel.getName().fullName);
        color.setText(StringUtil.capitaliseFirstLetter(apparel.getColor().toString()));
        clothingType.setText(StringUtil.capitaliseFirstLetter(apparel.getClothingType().toString()));
        status.setText(apparel.getWornStatus());
        usageCount.setText("Times worn: " + Integer.toString(apparel.getUsageCount()));
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
                && apparel.equals(card.apparel);
    }
}
