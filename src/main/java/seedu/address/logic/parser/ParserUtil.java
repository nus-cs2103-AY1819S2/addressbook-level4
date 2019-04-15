package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.NoInternetException;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.WebUtil;
import seedu.address.logic.commands.SortCommand.Limit;
import seedu.address.logic.commands.SortCommand.Order;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Postal;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }


    /**
     * Parses a {@code String posta} into an {@code Postal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postal} is invalid.
     */
    public static Postal parsePostal(String postal) throws ParseException {
        requireNonNull(postal);
        String trimmedPostal = postal.trim();
        if (!Postal.isValidPostal(postal)) {
            throw new ParseException(Postal.MESSAGE_CONSTRAINTS);
        }
        return new Postal(trimmedPostal);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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

    /**
     * Parses a {@code String entry} into a {@code Entry}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Entry parseEntry(String entry) throws ParseException {
        requireNonNull(entry);
        String trimmedEntry = entry.trim();
        if (!Entry.isValidEntry(trimmedEntry)) {
            throw new ParseException(Entry.MESSAGE_CONSTRAINTS);
        }
        return new Entry(trimmedEntry);
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
    }
    /**
     * Parses a {@code String cuisine} into a {@code Cuisine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cuisine} is invalid.
     */
    public static Cuisine parseCuisine(String cuisine) throws ParseException {
        requireNonNull(cuisine);
        String trimmedCuisine = cuisine.trim();
        if (!Cuisine.isValidCuisine(trimmedCuisine)) {
            throw new ParseException(Cuisine.MESSAGE_CONSTRAINTS);
        }
        return new Cuisine(trimmedCuisine);
    }

    /**
     * Parses a {@code String occasion} into a {@code Occasion}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occasion} is invalid.
     */
    public static Occasion parseOccasion(String occasion) throws ParseException {
        requireNonNull(occasion);
        String trimmedOccasion = occasion.trim();
        if (!Occasion.isValidOccasion(trimmedOccasion)) {
            throw new ParseException(Occasion.MESSAGE_CONSTRAINTS);
        }
        return new Occasion(trimmedOccasion);
    }

    /**
     * Parses a {@code String priceRange} into a {@code PriceRange}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code } is invalid.
     */
    public static PriceRange parsePriceRange(String priceRange) throws ParseException {
        requireNonNull(priceRange);
        String trimmedPriceRange = priceRange.trim();
        if (!PriceRange.isValidPriceRange(trimmedPriceRange)) {
            throw new ParseException(PriceRange.MESSAGE_CONSTRAINTS);
        }
        return new PriceRange(trimmedPriceRange);
    }

    /**
     * Parses a {@code String weblink} into an {@code Weblink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weblink} is invalid.
     */
    public static Weblink parseWeblink(String weblink) throws ParseException, NoInternetException {
        requireNonNull(weblink);
        String trimmedWeblink = weblink.trim();
        if (weblink.equals(Weblink.NO_WEBLINK_STRING)) {
            return new Weblink(trimmedWeblink);
        }
        if (!Weblink.isValidWeblinkString(trimmedWeblink)) {
            throw new ParseException(Weblink.MESSAGE_CONSTRAINTS);
        }

        // Check if weblink is valid, throw NoInternetException if no internet connection is found
        trimmedWeblink = WebUtil.validateAndAppend(trimmedWeblink);

        return new Weblink(trimmedWeblink);
    }

    /**
     * Parses a {@code String openingHours} into an {@code OpeningHours}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code OpeningHours} is invalid.
     */
    public static OpeningHours parseOpeningHours(String openingHours) throws ParseException {
        requireNonNull(openingHours);
        String trimmedOpeningHours = openingHours.trim();
        if (!OpeningHours.isValidOpeningHours(trimmedOpeningHours)) {
            throw new ParseException(OpeningHours.MESSAGE_CONSTRAINTS);
        }
        return new OpeningHours(trimmedOpeningHours);
    }

    /**
     * Parses a {@code String order} into an {@code Order} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Order} is invalid.
     */
    public static Order parseOrder(String order) throws ParseException {
        requireNonNull(order);
        String trimmedOrder = order.trim();
        if (!Order.isValidOrder(trimmedOrder)) {
            throw new ParseException(Order.MESSAGE_CONSTRAINTS);
        }
        return new Order(trimmedOrder);
    }

    /**
     * Parses a {@code String limit} into an {@code Limit} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Order} is invalid.
     */
    public static Limit parseLimit(String limit) throws ParseException {
        requireNonNull(limit);
        String trimmedLimit = limit.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedLimit)) {
            throw new ParseException(Limit.MESSAGE_CONSTRAINTS);
        }
        return new Limit(trimmedLimit);
    }

}
