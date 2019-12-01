package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentChronology;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.patient.Address;
import seedu.address.model.person.patient.Age;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String personId} into a {@code personId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code personId} is invalid.
     */
    public static PersonId parsePersonId(String personId) throws ParseException {
        requireNonNull(personId);
        String trimmedPersonId = personId.trim();
        if (!PersonId.isValidPersonId(trimmedPersonId)) {
            throw new ParseException(PersonId.MESSAGE_CONSTRAINTS);
        }
        return new PersonId(trimmedPersonId);
    }

    /**
     * Parses a {@code String validDate} into a {@code validDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code validDate} is invalid.
     */
    public static ValidDate parseValidDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedValidDate = date.trim();
        if (!ValidDate.isValidDate(trimmedValidDate)) {
            throw new ParseException(ValidDate.MESSAGE_CONSTRAINTS);
        }
        return new ValidDate(trimmedValidDate);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
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
     * Parses a {@code String description} into a {@code description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
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
     * Parses a {@code String specialisation} into a {@code Specialisation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code spec} is invalid.
     */
    public static Specialisation parseSpecialisation(String spec) throws ParseException {
        requireNonNull(spec);
        String trimmedSpec = spec.trim();
        if (!Specialisation.isValidSpecialisation(trimmedSpec)) {
            throw new ParseException(Specialisation.MESSAGE_CONSTRAINTS);
        }
        return new Specialisation(trimmedSpec);
    }

    /**
     * Parses {@code Collection<String> specialisations} into a {@code Set<Specialisation>}.
     */
    public static Set<Specialisation> parseSpecialisations(Collection<String> specs) throws ParseException {
        requireNonNull(specs);
        final Set<Specialisation> specSet = new HashSet<>();
        for (String spec : specs) {
            specSet.add(parseSpecialisation(spec));
        }
        return specSet;
    }

    /**
     * Parses a {@code String writeUp} into a {@code WriteUp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code writeUp} is invalid.
     */
    public static WriteUp parseWriteUp(String writeUp) throws ParseException {
        requireNonNull(writeUp);
        String trimmedWriteUp = writeUp.trim();
        if (!WriteUp.isValidWriteUp(trimmedWriteUp)) {
            throw new ParseException(WriteUp.MESSAGE_CONSTRAINTS);
        }
        return new WriteUp(trimmedWriteUp);
    }

    // Start of appointment related parsers

    /**
     * Parses a {@code String patientId} into a {@code AppointmentPatientId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code patientId} is invalid.
     */
    public static AppointmentPatientId parseAppointmentPatientId(String patientId) throws ParseException {
        requireNonNull(patientId);
        String trimmedPatientId = patientId.trim();
        if (!AppointmentPatientId.isValidAppointmentPatientId(trimmedPatientId)) {
            throw new ParseException(AppointmentPatientId.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentPatientId(trimmedPatientId);
    }

    /**
     * Parses a {@code String doctorId} into a {@code AppointmentDoctorId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code doctorId} is invalid.
     */
    public static AppointmentDoctorId parseAppointmentDoctorId(String doctorId) throws ParseException {
        requireNonNull(doctorId);
        String trimmedDoctorId = doctorId.trim();
        if (!AppointmentDoctorId.isValidAppointmentDoctorId(trimmedDoctorId)) {
            throw new ParseException(AppointmentDoctorId.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentDoctorId(trimmedDoctorId);
    }

    /**
     * Parses a {@code String date} into a {@code AppointmentDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static AppointmentDate parseAppointmentDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!AppointmentDate.isValidAppointmentDate(trimmedDate)) {
            throw new ParseException(AppointmentDate.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentDate(trimmedDate);
    }

    /**
     * Parses a {@code String time} into a {@code AppointmentTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static AppointmentTime parseAppointmentTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!AppointmentTime.isValidAppointmentTime(trimmedTime)) {
            throw new ParseException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }
        return new AppointmentTime(trimmedTime);
    }

    /**
     * Parses a {@code String status} into a {@code AppointmentStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static AppointmentStatus parseAppointmentStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim().toUpperCase();
        if (!AppointmentStatus.isValidAppointmentStatus(trimmedStatus)) {
            throw new ParseException(AppointmentStatus.MESSAGE_CONSTRAINTS);
        }
        return AppointmentStatus.valueOf(trimmedStatus);
    }

    /**
     * Parses a {@code String chronology} into a {@code AppointmentChronology}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static AppointmentChronology parseAppointmentChronology(String chronology) throws ParseException {
        requireNonNull(chronology);
        String trimmedChronology = chronology.trim().toUpperCase();
        if (!AppointmentChronology.isValidAppointmentChronology(trimmedChronology)) {
            throw new ParseException(AppointmentChronology.MESSAGE_CONSTRAINTS);
        }
        return AppointmentChronology.valueOf(trimmedChronology);
    }

    // End of appointment related parsers

    /**
     * Parses a {@code String name} into a {@code Medicine}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Medicine parseMedicineName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Medicine.isValidName(trimmedName)) {
            throw new ParseException(Medicine.MESSAGE_CONSTRAINTS);
        }
        return new Medicine(trimmedName);
    }
}
