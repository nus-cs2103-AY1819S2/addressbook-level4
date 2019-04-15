package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.datetime.TimeCustom;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.DrugAllergy;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Procedure;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.LinkedPatient;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Title;
import seedu.address.storage.ParsedInOut;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NOT_JSON_OR_PDF = "Input file type is not a .json or .pdf.";
    public static final String MESSAGE_INVALID_INDEX_RANGE =
            "Invalid index range! Please input a positive unsigned index range.";

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
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseLinkedPatientIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isUnsignedInteger(trimmedIndex)) {
            throw new ParseException(LinkedPatient.MESSAGE_CONSTRAINTS);
        }
        return Index.fromZeroBased(Integer.parseInt(trimmedIndex));
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
     * Parses a {@code String sex} into a {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sex} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!Sex.isValidSex(trimmedSex)) {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
        return new Sex(trimmedSex);
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
     * Parses a {@code String dob} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateOfBirth parseDob(String dob) throws ParseException {
        requireNonNull(dob);
        String trimmedDob = dob.trim();
        if (!DateOfBirth.isValidDate(dob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        } else if (!DateOfBirth.isNotFutureDay(dob)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DAY);
        }
        return new DateOfBirth(trimmedDob);
    }

    /**
     * Parses a {@code String drug} into a {@code DrugAllergy}
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DrugAllergy parseDrugAllergy(String drug) throws ParseException {
        requireNonNull(drug);
        String trimmedDrug = drug.trim();
        if (!DrugAllergy.isValidDrugAllergy(trimmedDrug)) {
            throw new ParseException(DrugAllergy.MESSAGE_CONSTRAINTS);
        }
        return new DrugAllergy(trimmedDrug);
    }

    /**
     * Parses a {@code String relation} into an {@code NextOfKinRelation}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static NextOfKinRelation parseRelation(String relation) throws ParseException {
        requireNonNull(relation);
        String trimmedRelation = relation.trim();
        if (!NextOfKinRelation.isValidNextOfKinRelation(relation)) {
            throw new ParseException(NextOfKinRelation.MESSAGE_CONSTRAINTS);
        }
        return new NextOfKinRelation(trimmedRelation);
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
        if (trimmedDate.equals("today")) {
            return new DateCustom(DateCustom.getToday());
        }
        if (!DateCustom.isValidDate(date)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS_START_DATE);
        }
        return new DateCustom(trimmedDate);
    }

    /**
     * Parses a {@code String date} into an {@code DateCustom}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static DateCustom parseEndDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (trimmedDate.equals("today")) {
            return new DateCustom(DateCustom.getToday());
        }
        if (!DateCustom.isValidDate(date)) {
            throw new ParseException(DateCustom.MESSAGE_CONSTRAINTS_END_DATE);
        }
        return new DateCustom(trimmedDate);
    }

    /**
     * Parses a {@code String time} into an {@code TimeCustom}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TimeCustom parseStartTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!TimeCustom.isValidTime(time)) {
            throw new ParseException(TimeCustom.MESSAGE_CONSTRAINTS_START_TIME);
        }
        return new TimeCustom(trimmedTime);
    }

    /**
     * Parses a {@code String time} into an {@code TimeCustom}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TimeCustom parseEndTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!TimeCustom.isValidTime(time)) {
            throw new ParseException(TimeCustom.MESSAGE_CONSTRAINTS_END_TIME);
        }
        return new TimeCustom(trimmedTime);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim().toLowerCase();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return Priority.returnPriority(trimmedPriority);
    }

    /**
     * Parses a {@code String procedure} into an {@code Procedure}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Procedure parseProcedure(String procedure) throws ParseException {
        requireNonNull(procedure);
        String trimmedProcedure = procedure.trim();
        if (!Procedure.isValidProcedure(trimmedProcedure)) {
            throw new ParseException(Procedure.MESSAGE_CONSTRAINTS);
        }
        return new Procedure(trimmedProcedure);
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
     * Converts all slashes ( \ ) in the input {@code String filePath} to /.
     */
    private static String convertSlashes(String input) {
        char[] pathArr = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            // Convert example\example.json to example/example.json if the system prefers /
            if (pathArr[i] == '\\') {
                pathArr[i] = '/';
            }
        }
        return String.valueOf(pathArr);
    }

    /**
     * Parses a {@code String filePath} into a {@code ParsedIO}.
     * @throws ParseException if the given {@code file} is invalid.
     */
    static ParsedInOut parseOpenSave(String input) throws ParseException {
        requireNonNull(input);
        input = input.trim();
        String newPath = "data/";

        input = convertSlashes(input);

        File file = new File(newPath.concat(input));

        final String validationRegex = "^(.*)+\\.(\\w*)$";
        if (input.matches(validationRegex)) {
            final String jsonRegex = "^([\\w/\\s!@#$%^&()_+\\-={}\\[\\];',.]+)+\\.(json)$";
            final String emptyJson = "^\\.(json)$";
            if (input.matches(jsonRegex) | input.matches(emptyJson)) {
                return new ParsedInOut(file, "json");
            } else {
                final String pdfRegex = "^([\\w/\\s!@#$%^&()_+\\-={}\\[\\];',.]+)+\\.(pdf)$";
                final String emptyPdf = "^\\.(pdf)$";
                if (input.matches(pdfRegex) | input.matches(emptyPdf)) {
                    return new ParsedInOut(file, "pdf");
                } else {
                    final String specialRegex = "^(.*)+\\.(json|pdf)$";
                    if (input.matches(specialRegex)) {
                        throw new ParseException("Special characters such as\n> < : \" | ? *\nare not allowed.");
                    }
                    throw new ParseException(MESSAGE_NOT_JSON_OR_PDF);
                }
            }
        }
        throw new ParseException("Invalid command format!");
    }

    /**
     * Parses a {@code String input} into a {@code ParsedIO}.
     * @throws ParseException if the given {@code file} is invalid.
     */
    static ParsedInOut parseImportExport(String input) throws ParseException {
        requireNonNull(input);
        input = input.trim();
        String newPath = "data/";
        String filepath;
        String fileType;

        input = convertSlashes(input);

        final String fileRegex = "^(.*)+\\.(json|pdf)+(.*)$";
        if (!input.matches(fileRegex)) {
            throw new ParseException(MESSAGE_NOT_JSON_OR_PDF);
        }

        // Parse for "all" keyword
        final String allRegex = "^([\\w/\\s!@#$%^&()_+\\-={}\\[\\];',.]+)+\\.(json|pdf)+\\s(all)$";
        if (input.matches(allRegex)) {
            final Pattern splitRegex = Pattern.compile(allRegex);
            Matcher splitMatcher = splitRegex.matcher(input);

            if (splitMatcher.find()) {
                filepath = splitMatcher.group(1).concat(".");
                filepath = filepath.concat(splitMatcher.group(2));
                filepath = newPath.concat(filepath);
                fileType = splitMatcher.group(2);
                return new ParsedInOut(new File(filepath), fileType);
            } else {
                // This shouldn't be possible after allRegex
                throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
            }
        }
        final String emptyAllRegex = "^\\.(json|pdf)+\\s(all)$";
        if (input.matches(emptyAllRegex)) {
            final Pattern splitRegex = Pattern.compile(emptyAllRegex);
            Matcher splitMatcher = splitRegex.matcher(input);
            if (splitMatcher.find()) {
                filepath = ".";
                filepath = filepath.concat(splitMatcher.group(1));
                filepath = newPath.concat(filepath);
                fileType = splitMatcher.group(1);
                return new ParsedInOut(new File(filepath), fileType);
            } else {
                // This shouldn't be possible after emptyAllRegex
                throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
            }
        }

        // Parse for index range
        final String indexRegex = "^([\\w/\\s!@#$%^&()_+\\-={}\\[\\];',.]+)+\\.(json|pdf)+\\s([0-9,\\-]*)$";
        final String emptyIndexRegex = "^\\.(json|pdf)+\\s([0-9,\\-]*)$";
        String indexRange;
        if (input.matches(indexRegex) | input.matches(emptyIndexRegex)) {
            if (input.matches(indexRegex)) {
                final Pattern splitRegex = Pattern.compile(indexRegex);
                Matcher splitMatcher = splitRegex.matcher(input);

                if (splitMatcher.find()) {
                    filepath = splitMatcher.group(1).concat(".");
                    filepath = filepath.concat(splitMatcher.group(2));
                    filepath = newPath.concat(filepath);
                    fileType = splitMatcher.group(2);
                    indexRange = splitMatcher.group(3);
                } else {
                    // This shouldn't be possible after indexRegex
                    throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
                }
            } else if (input.matches(emptyIndexRegex)) {
                final Pattern splitRegex = Pattern.compile(emptyIndexRegex);
                Matcher splitMatcher = splitRegex.matcher(input);

                if (splitMatcher.find()) {
                    filepath = ".";
                    filepath = filepath.concat(splitMatcher.group(1));
                    filepath = newPath.concat(filepath);
                    fileType = splitMatcher.group(1);
                    indexRange = splitMatcher.group(2);
                } else {
                    // This shouldn't be possible after emptyIndexRegex
                    throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
                }
            } else {
                // This shouldn't be possible
                throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
            }

            HashSet<Integer> parsedIndex = parseIndexRange(indexRange);

            return new ParsedInOut(new File(filepath), fileType, parsedIndex);
        }

        final String specialRegex = "^(.*)+\\.(json|pdf)+\\s([0-9,\\-]*|all)$";
        if (input.matches(specialRegex)) {
            throw new ParseException("Special characters such as\n> < : \" | ? *\nare not allowed.");
        }

        throw new ParseException("Invalid command format!");
    }

    /**
     * Parses a {@code String indexRange} into a {@code HashSet}.
     * @throws ParseException if index range is invalid
     */
    private static HashSet<Integer> parseIndexRange(String indexRange) throws ParseException {
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
                throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
            }
        }
        return parsedIndex;
    }

    /**
     * Parse a {@code  String argument} from copy or taskcopy command to number of copies needed
     * @param input input from copy or taskcopy command
     * @return number of copies requested
     * @throws ParseException
     */
    public static Pair<Index, Integer> parseCopy(String input) throws ParseException {
        requireNonNull(input);
        input = input.trim();

        String[] parsedInput = input.split("\\s+");
        Index i;
        int numOfCopies;

        if (parsedInput.length == 1) {

            try {
                i = parseIndex(parsedInput[0]);
            } catch (NumberFormatException e) {
                throw new ParseException("Wrong input format!");
            }
            return new Pair(i, 1);
        } else if (parsedInput.length == 2) {

            try {
                i = parseIndex(parsedInput[0]);
                numOfCopies = Integer.parseInt(parsedInput[1]);
            } catch (NumberFormatException e) {
                throw new ParseException("Wrong input format!");
            }
            if (numOfCopies < 1) {
                throw new ParseException("Input number must be positive!");
            }

            return new Pair(i, numOfCopies);
        }

        throw new ParseException("Wrong number of arguments");
    }

    /**
     * Parse argument into parameters for RecordMc command
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String[] parseRecordMc(String arg) throws ParseException {
        String[] args = arg.trim().split("\\s+");
        String[] results = new String[] { "-1", "2" };

        if (args.length > 2) {
            throw new ParseException("Wrong input numbers!");
        }

        results[0] = parseIndex(args[0]).getOneBased() + "";

        if (args.length > 1) {
            try {
                int days = Integer.parseInt(args[1]);
                if (days < 1) { throw new ParseException("Rest for at least 1 day"); }
                results[1] = days + "";
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid days of rest");
            }
        }

        return results;
    }

    /**
     * Parses a {@code String desc} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code desc} is invalid.
     */
    public static Description parseDesc(String desc) throws ParseException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        } else {
            return new Description(trimmedDesc);
        }
    }
}
