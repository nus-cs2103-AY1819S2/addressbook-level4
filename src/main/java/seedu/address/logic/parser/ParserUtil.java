package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Nric;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Title;
import seedu.address.storage.ParsedInOut;

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
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String dob} into an {@code Dob}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateOfBirth parseDob(String dob) throws ParseException {
        requireNonNull(dob);
        String trimmedDob = dob.trim();
        if (!DateOfBirth.isValidDob(dob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(trimmedDob);
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
     * Parses a {@code String title} into an {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(title)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String date} into an {@code DateCustom}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateCustom parseStartDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DateCustom.isValidDate(date)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        if (DateCustom.isDateBeforeToday(date)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        return new DateCustom(trimmedDate);
    }

    /**
     * Parses a {@code String date} into an {@code DateCustom}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateCustom parseEndDate(String endDate, String startDate) throws ParseException {
        requireNonNull(startDate);
        requireNonNull(endDate);
        String trimmedStartDate = startDate.trim();
        String trimmedEndDate = endDate.trim();
        if (!DateCustom.isValidDate(trimmedEndDate)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        if (DateCustom.isEndDateBeforeStartDate(DateCustom.getFormat(), trimmedStartDate, trimmedEndDate)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS);
        }
        return new DateCustom(trimmedEndDate);
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
     * Parses a {@code String filePath} into a {@code ParsedIO}.
     * @throws ParseException if the given {@code file} is invalid.
     */
    public static ParsedInOut parseOpenSave(String filePath) throws ParseException {
        requireNonNull(filePath);
        filePath = filePath.trim();
        String newPath = "data\\";
        File file = new File(newPath.concat(filePath));

        final String jsonRegex = "^([\\w-\\\\\\s.\\(\\)]+)+\\.(json)$";
        final String pdfRegex = "^([\\w-\\\\\\s.\\(\\)]+)+\\.(pdf)$";

        if (filePath.matches(jsonRegex)) {
            return new ParsedInOut(file, "json");
        } else {
            if (filePath.matches(pdfRegex)) {
                return new ParsedInOut(file, "pdf");
            } else {
                throw new ParseException("Input file type is not a .json or .pdf.");
            }
        }
    }

    /**
     * Parses a {@code String input} into a {@code ParsedIO}.
     * @throws ParseException if the given {@code file} is invalid.
     */
    public static ParsedInOut parseImportExport(String input) throws ParseException {
        requireNonNull(input);
        input = input.trim();
        String newPath = "data\\";
        String filepath = "";
        String fileType = "";

        final String validationRegex = "^([\\w-\\\\\\s.\\(\\)]+)+\\.(json|pdf)+\\s?([0-9,-]*)?$";
        final String allRegex = "^([\\w-\\\\\\s.\\(\\)]+)+\\.(json|pdf)+\\s(all)$";

        if (!input.matches(validationRegex)) {
            if (input.matches(allRegex)) {
                final Pattern splitRegex = Pattern.compile("^([\\w-\\\\\\s.\\(\\)]+)+\\.(json|pdf)+\\s(all)$");
                Matcher splitMatcher = splitRegex.matcher(input);

                if (splitMatcher.find()) {
                    filepath = splitMatcher.group(1).concat(".");
                    filepath = filepath.concat(splitMatcher.group(2));
                    filepath = newPath.concat(filepath);
                    fileType = splitMatcher.group(2);
                    return new ParsedInOut(new File(filepath), fileType);
                } else {
                    // This shouldn't be possible after validationRegex
                    throw new ParseException("Input file type is not a .json or .pdf.");
                }
            } else {
                throw new ParseException("Input file type is not a .json or .pdf.");
            }
        }

        final Pattern splitRegex = Pattern.compile("([\\w-\\\\\\s.\\(\\)]+)+\\.(json|pdf)+\\s?([0-9,-]*)?");
        Matcher splitMatcher = splitRegex.matcher(input);
        String indexRange = "";

        if (splitMatcher.find()) {
            filepath = splitMatcher.group(1).concat(".");
            filepath = filepath.concat(splitMatcher.group(2));
            filepath = newPath.concat(filepath);
            fileType = splitMatcher.group(2);
            indexRange = splitMatcher.group(3);
        } else {
            // This shouldn't be possible after validationRegex
            throw new ParseException("Input file type is not a .json or .pdf.");
        }

        HashSet<Integer> parsedIndex = new HashSet<>();

        String[] splitInput = indexRange.trim().split(",");
        String[] splitRange;
        final String singleNumberRegex = "^\\d+$";
        final String rangeNumberRegex = "^(\\d+)-(\\d+)$";

        for (String string : splitInput) {
            if (string.matches(singleNumberRegex)) {
                // -1 because indexes displayed to user starts with 1, not 0
                parsedIndex.add(Integer.parseInt(string) - 1);
            } else if (string.matches(rangeNumberRegex)) {
                splitRange = string.split("-");
                for (int i = Integer.parseInt(splitRange[0]); i < Integer.parseInt(splitRange[1]) + 1; i++) {
                    // -1 because indexes displayed to user starts with 1, not 0
                    parsedIndex.add(i - 1);
                }
            } else {
                throw new ParseException("Invalid index range!");
            }
        }

        return new ParsedInOut(new File(filepath), fileType, parsedIndex);
    }
}
