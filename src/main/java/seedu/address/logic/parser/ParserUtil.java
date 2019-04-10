package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gpa;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.SkillsTag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String tag} into a {@code SkillsTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static SkillsTag parseTag(String tag, String type) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!SkillsTag.isValidTagName(trimmedTag)) {
            throw new ParseException(SkillsTag.MESSAGE_CONSTRAINTS);
        }
        return new SkillsTag(trimmedTag, type);
    }

    /**
     * Parses the Gpa
     * @param gpa
     * @return
     * @throws ParseException
     */
    public static Gpa parseGpa(String gpa) throws ParseException {
        requireNonNull(gpa);
        String trimmedGpa = gpa.trim();
        if (!Gpa.isValidGpa(trimmedGpa)) {
            throw new ParseException(Gpa.MESSAGE_CONSTRAINTS);
        }
        return new Gpa(trimmedGpa);
    }

    /**
     * Parses the education field
     * @param education
     * @return
     * @throws ParseException
     */
    public static Education parseEducation(String education) throws ParseException {
        requireNonNull(education);
        String trimmedEducation = education.trim();
        if (!Education.isValidEducation(trimmedEducation)) {
            throw new ParseException(Education.MESSAGE_CONSTRAINTS);
        }
        return new Education(trimmedEducation);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<SkillsTag>}.
     */
    public static Set<SkillsTag> parseTags(Collection<String> tags, String type) throws ParseException {
        requireNonNull(tags);
        final Set<SkillsTag> tagSet = new HashSet<>();
        //final String color;
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName, type));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String sortWord} into a {@code SortWord}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static SortWord parseSortWord(String sortWord) throws ParseException {
        requireNonNull(sortWord);
        String trimmedSortWord = sortWord.trim();
        return new SortWord(trimmedSortWord);
    }
}
