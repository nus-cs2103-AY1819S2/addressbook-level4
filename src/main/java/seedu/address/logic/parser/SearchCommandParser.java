package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBSAPPLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KNOWNPROGLANG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASTJOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.GenderContainsKeywordsPredicate;
import seedu.address.model.person.predicate.JobsApplyContainsKeywordsPredicate;
import seedu.address.model.person.predicate.KnownProgLangContainsKeywordsPredicate;
import seedu.address.model.person.predicate.MajorContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PastJobContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PredicateManager;
import seedu.address.model.person.predicate.RaceContainsKeywordsPredicate;
import seedu.address.model.person.predicate.SchoolContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_RACE, PREFIX_ADDRESS,
                PREFIX_SCHOOL, PREFIX_MAJOR, PREFIX_KNOWNPROGLANG, PREFIX_PASTJOB, PREFIX_TAG, PREFIX_GENDER,
                PREFIX_NRIC, PREFIX_JOBSAPPLY, PREFIX_KNOWNPROGLANG);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicator = new PredicateManager();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicator = predicator.and(new NameContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_NAME).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            predicator = predicator.and(new PhoneContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_PHONE).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicator = predicator.and(new EmailContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_RACE).isPresent()) {
            predicator = predicator.and(new RaceContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_RACE).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicator = predicator.and(new AddressContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            predicator = predicator.and(new SchoolContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_SCHOOL).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_MAJOR).isPresent()) {
            predicator = predicator.and(new MajorContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_MAJOR).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            predicator = predicator.and(new GenderContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_GENDER).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            predicator = predicator.and(new NricContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_NRIC).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_PASTJOB).isPresent()) {
            predicator = predicator.and(new PastJobContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_PASTJOB).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_JOBSAPPLY).isPresent()) {
            predicator = predicator.and(new JobsApplyContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_JOBSAPPLY).get().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_KNOWNPROGLANG).isPresent()) {
            predicator = predicator.and(new KnownProgLangContainsKeywordsPredicate(Arrays.asList(
                argMultimap.getValue(PREFIX_KNOWNPROGLANG).get().split("\\s+"))));
        }

        return new SearchCommand(predicator);
    }


}
