package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.options.ListOption;
import seedu.address.logic.options.SortOption;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.ClothingTypeValue;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.ColorValue;
import seedu.address.model.apparel.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_SWAP_INVALID_EMPTY_OPTION = "Please supply 2 indexes";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parse {@code optionString} into an enum type and return it.
     * @throws ParseException if the specified optionString is invalid (not listed as valid options).
     */
    public static SortOption parseSortValue(String optionString) throws ParseException {
        String optionSupplied = optionString.trim();
        if (!SortOption.isValid(optionSupplied)) {
            throw new ParseException("Invalid option given");
        }

        return SortOption.create(optionSupplied);
    }

    /**
     * Parse {@code firstIndex} and {@code secondIndex} into an array of Index and return it.
     * @throws ParseException if the specified indexes are invalid (out of bound).
     */
    public static Index[] parseSwapValue(String indexes) throws ParseException {
        if (indexes.trim().equals("")) {
            throw new ParseException(MESSAGE_SWAP_INVALID_EMPTY_OPTION);
        }

        Index[] oneBasedIndexes = new Index[2];
        String[] indexArrStr = indexes.trim().split(" ");
        if (indexArrStr.length > 2) {
            throw new ParseException("Please supply only 2 indexes.");
        }

        for (int i = 0; i < indexArrStr.length; i++) {
            oneBasedIndexes[i] = Index.fromOneBased(Integer.parseInt(indexArrStr[i]));
        }

        return oneBasedIndexes;
    }

    /**
     * Parse {@code optionString} into an enum type and return it.
     * @throws ParseException if the specified optionString is invalid (not listed as valid options).
     */
    public static ListOption parseListValue(String optionString) throws ParseException {
        String optionSupplied = optionString.trim();
        if (!ListOption.isValid(optionSupplied)) {
            throw new ParseException("Invalid option given");
        }

        return ListOption.create(optionSupplied);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String color} into a {@code Color}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code color} is invalid.
     */
    // @@author PhilipPhil
    public static Color parseColor(String color) throws ParseException {
        requireNonNull(color);
        String trimmedColor = color.trim();
        if (!ColorValue.isValidColor(trimmedColor)) {
            throw new ParseException(Color.MESSAGE_CONSTRAINTS);
        }
        return new Color(trimmedColor);
    }


    /**
     * Parses a {@code String clothingType} into an {@code ClothingType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clothingType} is invalid.
     */
    // @@author PhilipPhil
    public static ClothingType parseClothingType(String clothingType) throws ParseException {
        requireNonNull(clothingType);
        String trimmedType = clothingType.trim();
        if (!ClothingTypeValue.isValidClothingType(trimmedType)) {
            throw new ParseException(ClothingType.MESSAGE_CONSTRAINTS);
        }
        return new ClothingType(trimmedType);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }


}
