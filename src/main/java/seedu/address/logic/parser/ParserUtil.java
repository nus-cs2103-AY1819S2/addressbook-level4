package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.request.RequestDate;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;
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
     * Parses a {@code String id} and returns it's respective index in the addressbook patient list.
     * @return
     */
    public static int parsePatientIndex(String id) {
        requireNonNull(id);
        String trimmedId = id.trim();
        return Integer.parseInt(trimmedId);
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

    // ===== Methods for parsing Organisation, Nric and Specialisations =====
    // @author Lookaz
    /**
     * Parses a {@code String organization} into a {@code Organization}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code organization} is invalid.
     */
    public static Organization parseOrganization(String organization) throws ParseException {
        requireNonNull(organization);
        String trimmedOrganization = organization.trim();
        if (!Organization.isValidOrgName(trimmedOrganization)) {
            throw new ParseException(Organization.MESSAGE_CONSTRAINTS);
        }

        return new Organization(trimmedOrganization);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
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
     * Parses a {@code String specialisation} into a {@code Specialisation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code specialisation} is invalid.
     */
    public static Specialisation parseSpecialisation(String specialisation)
            throws ParseException {
        requireNonNull(specialisation);
        String trimmedSpecialisation = specialisation.trim();
        if (!Specialisation.isValidSpecialisation(trimmedSpecialisation)) {
            throw new ParseException(Specialisation.MESSAGE_CONSTRAINTS);
        }

        return Specialisation.parseString(trimmedSpecialisation);
    }

    /**
     * Parses {@code Collection<String> specialisations} into a {@code
     * Skills}.
     */
    public static Skills parseSpecialisations(Collection<String> specialisations)
            throws ParseException {
        requireNonNull(specialisations);
        final Skills skills = new Skills();
        for (String specialisation : specialisations) {
            skills.addSpecialisation(parseSpecialisation(specialisation));
        }
        return skills;
    }

    /**
     * Parses a {@code String conditionTag} into a {@code ConditionTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code conditionTag} is invalid.
     */
    public static ConditionTag parseConditionTag(String conditionTag)
        throws ParseException {
        requireNonNull(conditionTag);
        String trimmedConditionTag = conditionTag.trim();
        if (!ConditionTag.isValidConditionTagName(trimmedConditionTag)) {
            throw new ParseException(ConditionTag.MESSAGE_CONSTRAINTS);
        }

        return ConditionTag.parseString(trimmedConditionTag);
    }

    /**
     * Parses {@code Collection<String> conditionTag} into a {@code
     * Conditions}.
     */
    public static Conditions parseConditions(Collection<String> conditionTags)
        throws ParseException {
        requireNonNull(conditionTags);
        final Conditions conditions = new Conditions();
        for (String conditionTag : conditionTags) {
            conditions.addConditionTag(parseConditionTag(conditionTag));
        }
        return conditions;
    }

    /**
     * Parses {@code String date} into a {@code RequestDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static RequestDate parseRequestDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!RequestDate.isValidDate(trimmedDate)) {
            throw new ParseException(RequestDate.MESSAGE_DATE_CONSTRAINTS);
        }

        return new RequestDate(trimmedDate);
    }
}
