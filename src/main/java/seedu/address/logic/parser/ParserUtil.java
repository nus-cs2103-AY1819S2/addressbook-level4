package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.model.threshold.Threshold;

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
     * Parses a {@code String fileName} into a {@code FileName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param fileName The input file name.
     * @param isEmptyFileNameAllowed If empty input filename is allowed. If it is set to true, then
     *                               if input file name is empty it will be given the default file name in
     *                               the format (current date)_(current time).
     * @return Returns a FileName object based on the input file name if the input file name is valid.
     * @throws ParseException if the given {@code fileName} is invalid.
     */
    public static FileName parseFileName(String fileName, boolean isEmptyFileNameAllowed) throws ParseException {
        requireNonNull(fileName);
        String trimmedFileName = fileName.trim();
        if (isEmptyFileNameAllowed && fileName.isEmpty()) {
            SimpleDateFormat currentDateAndTimeFormat = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
            trimmedFileName = currentDateAndTimeFormat.format(new Date());
        }
        if (!FileName.isValidFileName(trimmedFileName)) {
            throw new ParseException(FileName.MESSAGE_CONSTRAINTS);
        }
        return new FileName(trimmedFileName);
    }

    /**
     * Parses a {@code String quantity} into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code String expiry} into an {@code Expiry}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expiry} is invalid.
     */
    public static Expiry parseExpiry(String expiry) throws ParseException {
        requireNonNull(expiry);
        String trimmedExpiry = expiry.trim();
        if (!Expiry.isValidDate(trimmedExpiry)) {
            throw new ParseException(Expiry.MESSAGE_CONSTRAINTS);
        }
        return new Expiry(trimmedExpiry);
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
     * Parses a {@code String threshold} into a {@code Threshold}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code threshold} is invalid.
     */
    public static Threshold parseThreshold(String threshold) throws ParseException {
        requireNonNull(threshold);
        String trimmedThreshold = threshold.trim();
        if (!Threshold.isValidQuantity(trimmedThreshold)) {
            throw new ParseException(Threshold.MESSAGE_CONSTRAINTS);
        }
        return new Threshold(trimmedThreshold);
    }

    /**
     * Parses a {@code String batchNumber} into a {@code BatchNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code batchNumber} is invalid.
     */
    public static BatchNumber parseBatchNumber(String batchNumber) throws ParseException {
        requireNonNull(batchNumber);
        String trimmedBatchNumber = batchNumber.trim();
        if (!BatchNumber.isValidBatchNumber(trimmedBatchNumber)) {
            throw new ParseException(BatchNumber.MESSAGE_CONSTRAINTS);
        }
        return new BatchNumber(trimmedBatchNumber);
    }
}
