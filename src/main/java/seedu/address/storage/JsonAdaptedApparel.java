package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.ClothingTypeValue;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.ColorValue;
import seedu.address.model.apparel.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Apparel}.
 */
class JsonAdaptedApparel {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Apparel's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final Color color;
    private final ClothingType clothingType;
    private final boolean status;
    private final int usageCount;

    /**
     * Constructs a {@code JsonAdaptedApparel} with the given apparel details.
     */
    @JsonCreator
    public JsonAdaptedApparel(@JsonProperty("name") String name, @JsonProperty("color") Color color,
                              @JsonProperty("clothingType") ClothingType clothingType,
                              @JsonProperty("status") boolean status,
                              @JsonProperty("usageCount") int usageCount) {
        this.name = name;
        this.color = color;
        this.clothingType = clothingType;
        this.status = status;
        this.usageCount = usageCount;

    }

    /**
     * Converts a given {@code Apparel} into this class for Jackson use.
     */
    public JsonAdaptedApparel(Apparel source) {
        name = source.getName().fullName;
        color = source.getColor();
        clothingType = source.getClothingType();
        status = source.isAvailable();
        usageCount = source.getUsageCount();
    }
    /**
     * Converts this Jackson-friendly adapted apparel object into the model's {@code Apparel} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted apparel.
     */
    public Apparel toModelType() throws IllegalValueException {
        final List<Tag> apparelTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            apparelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (color == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Color.class.getSimpleName()));
        }
        if (!ColorValue.isValidColor(color.toString())) {
            throw new IllegalValueException(Color.MESSAGE_CONSTRAINTS);
        }
        final Color modelColor = new Color(color.toString());

        if (clothingType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClothingType.class.getSimpleName()));
        }
        if (!ClothingTypeValue.isValidClothingType(clothingType.toString())) {
            throw new IllegalValueException(ClothingType.MESSAGE_CONSTRAINTS);
        }
        final ClothingType modelClothingType = new ClothingType(clothingType.toString());

        final boolean modelStatus = status;

        final int modelUsageCount = usageCount;


        final Set<Tag> modelTags = new HashSet<>(apparelTags);
        return new Apparel(modelName, modelColor, modelClothingType, modelStatus, modelUsageCount);
    }


}
