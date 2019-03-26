package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
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
    private static final String CATEGORY_FIELD_ID = "#categories";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label amountLabel;
    private final Label dateLabel;
    private final Label descriptionLabel;
    private final List<Label> categoryLabels;

    public RecordCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        amountLabel = getChildNode(AMOUNT_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);

        Region categoriesContainer = getChildNode(CATEGORY_FIELD_ID);
        categoryLabels = categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
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

    public List<String> getCategories() {
        return categoryLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    //@@author geezlouisee-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits/1ac2e7c5597cf328cc9c28d5d8e18db8dc1fc5a0
    //with minor modifications
    public List<String> getCategoryStyleClasses(String category) {
        return categoryLabels
                .stream()
                .filter(label -> label.getText().equals(category))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new
                        IllegalArgumentException("No such category."));
    }
    //@@author

    /**
     * Returns true if this handle contains {@code record}.
     * @param record
     */
    public boolean equals(Record record) {
        return getName().equals(record.getName().fullName)
                && getAmount().equals("$" + record.getAmount().value)
                && getDate().equals(record.getDate().toString())
                && getDate().equals(record.getDate().toString())
                && ImmutableMultiset.copyOf(getCategories())
                        .equals(ImmutableMultiset.copyOf(record.getCategories().stream()
                        .map(category -> category.categoryName)
                        .collect(Collectors.toList())));
    }
}
