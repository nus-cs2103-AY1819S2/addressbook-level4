package guitests.guihandles;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.finance.model.record.Record;

/**
 * Provides a handle to a record card in the record list panel.
 */
public class RecordCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String DATE_FIELD_ID = "#date";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String CATEGORY_FIELD_ID = "#category";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label amountLabel;
    private final Label dateLabel;
    private final Label descriptionLabel;
    private final Label categoryLabel;

    public RecordCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        amountLabel = getChildNode(AMOUNT_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        categoryLabel = getChildNode(CATEGORY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

    //@@author geezlouisee-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits/1ac2e7c5597cf328cc9c28d5d8e18db8dc1fc5a0
    //with minor modifications
    public List<String> getCategoryStyleClasses(String category) {
        if (categoryLabel.getText().equals(category)) {
            return categoryLabel.getStyleClass();
        } else {
            throw new IllegalArgumentException("No such category.");
        }
    }
    //@@author

    /**
     * Returns true if this handle contains {@code record}.
     * @param record
     */
    public boolean equals(Record record) {
        return getName().equals(record.getName().fullName)
                && getAmount().equals("$" + record.getAmount().toString())
                && getDate().equals(record.getDate().toString())
                && getCategory().equals(record.getCategory().toString());
    }
}
