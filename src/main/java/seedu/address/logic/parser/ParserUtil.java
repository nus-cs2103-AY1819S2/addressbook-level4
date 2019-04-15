package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.Client;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.ProjectDescription;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.CalendarDate;
import seedu.address.model.util.Description;
import seedu.address.model.util.PocketProjectDate;

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
     * Parses a {@code String event} into a {@code Milestone}.
     * Leading and trailing whitespaces will be trimmed and event split into
     * description and date to check for validity
     *
     * @throws ParseException if the given {@code event} is invalid.
     */
    public static MilestoneDescription parseMilestoneDescription(String event) throws ParseException {
        requireNonNull(event);
        String trimmedMilestoneDesc = event.trim();

        if (!Description.isValidDescription(trimmedMilestoneDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new MilestoneDescription(trimmedMilestoneDesc);
    }


    /**
     * Parses a {@code String name} into a {@code EmployeeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EmployeeName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EmployeeName.isValidName(trimmedName)) {
            throw new ParseException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        return new EmployeeName(trimmedName);
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
     * Parses a {@code String account} into an {@code GitHubAccount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code account} is invalid.
     */
    public static GitHubAccount parseAccount(String account) throws ParseException {
        requireNonNull(account);
        String trimmedAccount = account.trim();
        if (!GitHubAccount.isValidAccount(trimmedAccount)) {
            throw new ParseException(GitHubAccount.MESSAGE_CONSTRAINTS);
        }
        return new GitHubAccount(trimmedAccount);
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
     * Parses a {@code String skill} into a {@code Skill}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code skill} is invalid.
     */
    public static Skill parseSkill(String skill) throws ParseException {
        requireNonNull(skill);
        String trimmedSkill = skill.trim();
        if (!Skill.isValidSkillName(trimmedSkill)) {
            throw new ParseException(Skill.MESSAGE_CONSTRAINTS);
        }
        return new Skill(trimmedSkill);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>}.
     */
    public static Set<Skill> parseSkills(Collection<String> skills) throws ParseException {
        requireNonNull(skills);
        final Set<Skill> skillSet = new HashSet<>();
        for (String skillName : skills) {
            skillSet.add(parseSkill(skillName));
        }
        return skillSet;
    }

    /**
     * Parses a {@code String name} into a {@code Client}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code client} is invalid.
     */
    public static Client parseClient(String client) throws ParseException {
        requireNonNull(client);
        String trimmedClient = client.trim();
        if (!Client.isValidName(trimmedClient)) {
            throw new ParseException(Client.MESSAGE_CONSTRAINTS);
        }
        return new Client(trimmedClient);
    }

    /**
     * Parses a {@code String date} into a {@code PocketProjectDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static PocketProjectDate parseDate(String date) throws ParseException {

        requireNonNull(date);
        String trimmedDate = date.trim();
        String formattedDate;

        if (!PocketProjectDateParser.isFlexibleInput(trimmedDate)) {
            formattedDate = trimmedDate;
            if (!PocketProjectDate.isValidDate(formattedDate)) {
                throw new ParseException(PocketProjectDate.MESSAGE_CONSTRAINTS);
            }
        } else {
            formattedDate = PocketProjectDateParser.parsePocketProjectDate(trimmedDate).trim();
        }

        if (!CalendarDate.isValidDayInMonth(formattedDate)) {
            throw new ParseException(CalendarDate.DAY_MONTH_CONSTRAINTS);
        }

        return new PocketProjectDate(formattedDate);

    }

    /**
     * Parses a {@code String description} into a {@code Description}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static ProjectDescription parseDescription(String description) throws ParseException {

        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new ProjectDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String name} into a {@code Project ProjectName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code project name} is invalid.
     */
    public static ProjectName parseProjectName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ProjectName.isValidName(trimmedName)) {
            throw new ParseException(ProjectName.MESSAGE_CONSTRAINTS);
        }
        return new ProjectName(trimmedName);
    }

    /**
     * Parses a {@code String user} into a {@code UserStoryUser user}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code user} is invalid.
     */
    public static UserStoryUser parseStoryUser(String user) throws ParseException {
        requireNonNull(user);
        String trimmedUser = user.trim();
        if (!UserStoryUser.isValidName(trimmedUser)) {
            throw new ParseException(UserStoryUser.MESSAGE_CONSTRAINTS);
        }
        return new UserStoryUser(trimmedUser);
    }

    /**
     * Parses a {@code String function} into a {@code UserStoryFunction function}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static UserStoryFunction parseStoryFunction(String function) {
        requireNonNull(function);
        String trimmedFunction = function.trim();
        return new UserStoryFunction(trimmedFunction);
    }

    /**
     * Parses a {@code String reason} into a {@code UserStoryReason reason}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static UserStoryReason parseStoryReason(String reason) {
        String trimmedReason = reason.trim();
        return new UserStoryReason(trimmedReason);
    }

    /**
     * Parses a {@code String importance} into a {@code UserStoryImportance importance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code input} is invalid.
     */
    public static UserStoryImportance parseStoryImportance(String input) throws ParseException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        if (!UserStoryImportance.isValidImportanceLevel(trimmedInput)) {
            throw new ParseException(UserStoryImportance.MESSAGE_CONSTRAINTS);
        }
        return new UserStoryImportance(trimmedInput);
    }

    /**
     * Parses a {@code String status} into a {@code Status status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String name} into a {@code ProjectTaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static ProjectTaskDescription parseProjectTaskDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedName = description.trim();
        if (!ProjectTaskDescription.isValidDescription(trimmedName)) {
            throw new ParseException(ProjectTaskDescription.MESSAGE_CONSTRAINTS);
        }
        return new ProjectTaskDescription(trimmedName);
    }
}
