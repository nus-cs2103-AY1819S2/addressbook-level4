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
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.InterviewScores;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
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
     * Parses a {@code String gender} into an {@code Gender}.
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
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }

    /**
     * Parses a {@code String nric} into an {@code Nric}.
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
     * Parses a {@code String interviewScores} into an {@code InterviewScores}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code interviewScores} is invalid.
     */
    public static InterviewScores parseInterviewScores(String interviewScores) throws ParseException {
        requireNonNull(interviewScores);
        String trimmedInterviewScores = interviewScores.trim();
        if (!InterviewScores.isValidInterviewScores(trimmedInterviewScores)) {
            throw new ParseException(InterviewScores.MESSAGE_CONSTRAINTS);
        }
        return new InterviewScores(trimmedInterviewScores);
    }

    /**
     * Parses a {@code String major} into an {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
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
     * Parses a {@code String race} into an {@code Race}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code race} is invalid.
     */
    public static Race parseRace(String race) throws ParseException {
        requireNonNull(race);
        String trimmedRace = race.trim();
        if (!Race.isValidRace(trimmedRace)) {
            throw new ParseException(Race.MESSAGE_CONSTRAINTS);
        }
        return new Race(trimmedRace);
    }

    /**
     * Parses a {@code String school} into an {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code school} is invalid.
     */

    public static School parseSchool(String school) throws ParseException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new ParseException(School.MESSAGE_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }

    /**
     * Parses a {@code String knownproglang} into a {@code KnownProgLang}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code knownproglang} is invalid.
     */
    public static KnownProgLang parseKnownProgLang(String knownProgLang) throws ParseException {
        requireNonNull(knownProgLang);
        String trimmedKnownProgLang = knownProgLang.trim();
        if (!KnownProgLang.isValidKnownProgLang(trimmedKnownProgLang)) {
            throw new ParseException(KnownProgLang.MESSAGE_CONSTRAINTS);
        }
        return new KnownProgLang(trimmedKnownProgLang);
    }

    /**
     * Parses {@code Collection<String> knownproglangs} into a {@code Set<KnownProgLang>}.
     */
    public static Set<KnownProgLang> parseKnownProgLangs(Collection<String> knownProgLangs) throws ParseException {
        requireNonNull(knownProgLangs);
        final Set<KnownProgLang> knownProgLangSet = new HashSet<>();
        for (String knownProgLangName : knownProgLangs) {
            knownProgLangSet.add(parseKnownProgLang(knownProgLangName));
        }
        return knownProgLangSet;
    }

    /**
     * Parses a {@code String jobsApply} into a {@code JobsApply}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code jobsApply} is invalid.
     */
    public static JobsApply parseJobsApply(String jobsApply) throws ParseException {
        requireNonNull(jobsApply);
        String trimmedJobsApply = jobsApply.trim();
        if (!JobsApply.isValidJobsApply(trimmedJobsApply)) {
            throw new ParseException(JobsApply.MESSAGE_CONSTRAINTS);
        }
        return new JobsApply(trimmedJobsApply);
    }

    /**
     * Parses {@code Collection<String> jobsApply} into a {@code Set<JobsApply>}.
     */
    public static Set<JobsApply> parseJobsApply(Collection<String> jobsApply) throws ParseException {
        requireNonNull(jobsApply);
        final Set<JobsApply> jobsApplySet = new HashSet<>();
        for (String jobsApplyName : jobsApply) {
            jobsApplySet.add(parseJobsApply(jobsApplyName));
        }
        return jobsApplySet;
    }

    /**
     * Parses a {@code String pastjob} into a {@code PastJob}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pastjob} is invalid.
     */
    public static PastJob parsePastJob(String pastjob) throws ParseException {
        requireNonNull(pastjob);
        String trimmedPastJob = pastjob.trim();
        if (!PastJob.isValidPastJob(trimmedPastJob)) {
            throw new ParseException(PastJob.MESSAGE_CONSTRAINTS);
        }
        return new PastJob(trimmedPastJob);
    }

    /**
     * Parses {@code Collection<String> pastjobs} into a {@code Set<PastJob>}.
     */
    public static Set<PastJob> parsePastJobs(Collection<String> pastjobs) throws ParseException {
        requireNonNull(pastjobs);
        final Set<PastJob> pastjobSet = new HashSet<>();
        for (String pastjobName : pastjobs) {
            pastjobSet.add(parsePastJob(pastjobName));
        }
        return pastjobSet;
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
