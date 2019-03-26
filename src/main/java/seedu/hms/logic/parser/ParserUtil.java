package seedu.hms.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.hms.commons.core.index.Index;
import seedu.hms.commons.util.StringUtil;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.booking.ServiceType;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.reservation.RoomType;
import seedu.hms.model.tag.Tag;
import seedu.hms.model.util.DateRange;
import seedu.hms.model.util.TimeRange;


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
     * Parses a {@code String dob} into a {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dob} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dob) throws ParseException {
        requireNonNull(dob);
        String trimmedDob = dob.trim();
        if (!DateOfBirth.isValidDob(trimmedDob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(trimmedDob);
    }

    /**
     * Parses a {@code String idnum} into a {@code IdentificationNo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code idnum} is invalid.
     */
    public static IdentificationNo parseIdNum(String idnum) throws ParseException {
        requireNonNull(idnum);
        String trimmedIdNum = idnum.trim();
        if (!IdentificationNo.isValidIdNum(trimmedIdNum)) {
            throw new ParseException(IdentificationNo.MESSAGE_CONSTRAINTS);
        }
        return new IdentificationNo(trimmedIdNum);
    }

    /**
     * Parses a {@code String hms} into an {@code hms}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hms} is invalid.
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
     * Parses a {@code String roomName} into a {@code RoomType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code roomName} is invalid.
     */
    public static RoomType parseRoom(String roomName) throws ParseException {
        requireNonNull(roomName);
        String trimmedRoomName = roomName.trim();
        switch (trimmedRoomName) {
        case "SINGLE ROOM":
            return RoomType.SINGLE;
        case "DOUBLE ROOM":
            return RoomType.DOUBLE;
        case "DELUXE ROOM":
            return RoomType.DELUXE;
        case "FAMILY SUITE":
            return RoomType.SUITE;
        default:
            throw new ParseException(String.format("Room Type %s doesn't exist!", trimmedRoomName));
        }
    }

    /**
     * Parses a {@code String serviceName} into a {@code ServiceType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code serviceName} is invalid.
     */
    public static ServiceType parseService(String serviceName) throws ParseException {
        requireNonNull(serviceName);
        String trimmedServiceName = serviceName.trim();
        switch (trimmedServiceName) {
        case "GYM":
            return ServiceType.GYM;
        case "SWIMMING POOL":
            return ServiceType.POOL;
        case "SPA":
            return ServiceType.SPA;
        case "GAMES ROOM":
            return ServiceType.GAMES;
        default:
            throw new ParseException(String.format("Service Type %s doesn't exist!", trimmedServiceName));
        }
    }

    /**
     * Parses a {@code String timing} into a {@code TimeRange}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TimeRange parseTiming(String timing) throws ParseException {
        requireNonNull(timing);
        String trimmedTiming = timing.trim();
        String[] hours = trimmedTiming.split("-");
        return new TimeRange(Integer.parseInt(hours[0].trim()), Integer.parseInt(hours[1].trim()));
    }

    /**
     * Parses a {@code String dates} into a {@code DateRange}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateRange parseDates(String dates) throws ParseException {
        requireNonNull(dates);
        String trimmedDates = dates.trim();
        String[] dateRange = trimmedDates.split("-");
        return new DateRange(dateRange[0].trim(), dateRange[1].trim());
    }

    /**
     * Parses a {@code String customerIndex} into a {@code Customer} using the {@code customers}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Customer parseCustomer(String customerIndex, List<Customer> customers) throws ParseException {
        requireNonNull(customerIndex);
        try {
            int index = Integer.parseInt(customerIndex);
            return customers.get(index);
        } catch (Exception e) {
            throw new ParseException(String.format("Invalid customer index - %s", customerIndex));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Optional<List<Customer>> parseCustomers(Collection<String> customerIndices, List<Customer> customers)
        throws ParseException {
        requireNonNull(customerIndices);
        final List<Customer> result = new ArrayList<>();
        for (String customerIndex : customerIndices) {
            result.add(customers.get(Integer.parseInt(customerIndex)));
        }
        return Optional.of(result);
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
