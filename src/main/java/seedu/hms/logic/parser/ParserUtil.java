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
import seedu.hms.model.BookingModel;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.reservation.roomType.RoomType;
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
        if (!(DateOfBirth.isValidDob(trimmedDob).getKey())) {
            throw new ParseException(DateOfBirth.isValidDob(trimmedDob).getValue());
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
    public static RoomType parseRoom(String roomName, ReservationModel reservationModel) throws ParseException {
        requireNonNull(roomName);
        String trimmedRoomName = roomName.trim();
        RoomType parsedRoomType = reservationModel.getRoomType(trimmedRoomName);
        if (parsedRoomType == null) {
            throw new ParseException("Room type does not exist!");
        } else {
            return parsedRoomType;
        }
    }

    /**
     * Parses a {@code String serviceName} into a {@code ServiceType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code serviceName} is invalid.
     */
    public static ServiceType parseService(String serviceName, BookingModel bookingModel) throws ParseException {
        requireNonNull(serviceName);
        String trimmedServiceName = serviceName.trim();
        ServiceType parsedServiceType = bookingModel.getServiceType(trimmedServiceName);
        if (parsedServiceType == null) {
            throw new ParseException("Service does not exist!");
        } else {
            return parsedServiceType;
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
        int startHour;
        int endHour;
        if (hours.length != 2) {
            throw new ParseException("You have entered an invalid timing");
        } else {
            try {
                startHour = Integer.parseInt(hours[0].trim());
                endHour = Integer.parseInt(hours[1].trim());
            } catch (NumberFormatException e) {
                throw new ParseException("You have entered an invalid timing");
            }
            if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23) {
                throw new ParseException("Hour does not lie in the valid range of 0 - 23");
            }
            if (startHour >= endHour) {
                throw new ParseException("End hour must be after start hour");
            }
        }
        return new TimeRange(startHour, endHour);
    }

    /**
     * Parses a {@code String dates} into a {@code DateRange}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateRange parseDates(String dates) throws ParseException {
        requireNonNull(dates);
        String trimmedDates = dates.trim();
        String[] dateRange = trimmedDates.split("-");
        if (dateRange.length != 2) {
            throw new ParseException("You have entered an invalid date");
        }
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
            return customers.get(index - 1);
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
            Customer customer = parseOtherCustomer(customerIndex, customers);
            result.add(customer);
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

    /**
     * Parses a {@code String customerIndex} into a {@code Customer} using the {@code customers}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Customer parseOtherCustomer(String customerIndex, List<Customer> customers) throws ParseException {
        requireNonNull(customerIndex);
        try {
            int index = Integer.parseInt(customerIndex);
            return customers.get(index - 1);
        } catch (Exception e) {
            throw new ParseException(String.format("Invalid customer index for other users: %s", customerIndex));
        }
    }

    /**
     * Parses {@code String s} into a {@code capacity}.
     */
    public static int parseCapacity(String s) throws ParseException {
        int c = Integer.parseInt(s.trim());
        if (c <= 0) {
            throw new ParseException("Capacity must be positive");
        }
        return c;
    }

    /**
     * Parses {@code String s} into a {@code rate}.
     */
    public static double parseRate(String s) throws ParseException {
        Double r = Double.parseDouble(s.trim());
        if (r <= 0) {
            throw new ParseException("Rate must be positive");
        }
        return r;
    }

    /**
     * Parses a {@code String type} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static String parseType(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();

        if ((name.length() > 20) || !Name.isValidName(trimmedName)) {
            throw new ParseException("Names should only contain alphanumeric characters and spaces, and it "
                + "should not be blank. The length should be less than 15.");
        }
        return name;
    }
}
