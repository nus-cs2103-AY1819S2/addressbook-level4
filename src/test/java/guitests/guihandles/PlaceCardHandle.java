package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.travel.model.place.Place;

/**
 * Provides a handle to a place card in the place list panel.
 */
public class PlaceCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String COUNTRY_CODE_FIELD_ID = "#countryCode";
    private static final String ADDRESS_FIELD_ID = "#travel";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label countryCodeLabel;
    private final Label addressLabel;
    private final Label ratingLabel;
    private final Label descriptionLabel;
    private final List<Label> tagLabels;

    public PlaceCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        countryCodeLabel = getChildNode(COUNTRY_CODE_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        ratingLabel = getChildNode(RATING_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
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

    public String getCountryCode() {
        return countryCodeLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
            .stream()
            .filter(label -> label.getText().equals(tag))
            .map(Label::getStyleClass)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code place}.
     */
    public boolean equals(Place place) {
        return getName().equals(place.getName().fullName)
                && getAddress().equals(place.getAddress().value)
                && getCountryCode().equals(place.getCountryCode().code)
                && getRating().equals(place.getRating().value)
                && getDescription().equals(place.getDescription().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(place.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
