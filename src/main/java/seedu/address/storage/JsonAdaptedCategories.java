package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;

/**
 * Jackson-friendly version of {@link Categories}.
 */
public class JsonAdaptedCategories {

    private final String cuisine;
    private final String occasion;
    private final String priceRange;

    /**
     * Constructs a {@code JsonAdaptedCategories} with the given categories details.
     */
    @JsonCreator
    public JsonAdaptedCategories(@JsonProperty("cuisine") String cuisine, @JsonProperty("occasion") String occasion,
                                 @JsonProperty("priceRange") String priceRange) {
        this.cuisine = cuisine;
        this.occasion = occasion;
        this.priceRange = priceRange;
    }

    /**
     * Converts a given {@code Categories} into this class for Jackson use.
     */
    public JsonAdaptedCategories(Categories categories) {
        this.cuisine = categories.getCuisine().map(cuisine -> cuisine.value).orElse(null);
        this.occasion = categories.getOccasion().map(occasion -> occasion.value).orElse(null);
        this.priceRange = categories.getPriceRange().map(priceRange -> priceRange.value).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted categories object into the model's {@code Categories} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted categories.
     */
    public Categories toModelType() throws IllegalValueException {
        if (cuisine != null && !Cuisine.isValidCuisine(cuisine)) {
            throw new IllegalValueException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        if (occasion != null && !Occasion.isValidOccasion(occasion)) {
            throw new IllegalValueException(Occasion.MESSAGE_CONSTRAINTS);
        }
        if (priceRange != null && !PriceRange.isValidPriceRange(priceRange)) {
            throw new IllegalValueException(PriceRange.MESSAGE_CONSTRAINTS);
        }
        final Optional<Cuisine> modelCuisine = Optional.ofNullable(cuisine).map(Cuisine::new);
        final Optional<Occasion> modelOccasion = Optional.ofNullable(occasion).map(Occasion::new);
        final Optional<PriceRange> modelPriceRange = Optional.ofNullable(priceRange).map(PriceRange::new);
        return new Categories(modelCuisine, modelOccasion, modelPriceRange);
    }
}
