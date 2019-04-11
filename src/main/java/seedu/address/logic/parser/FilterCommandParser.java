package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORESQ1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORESQ2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORESQ3;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORESQ4;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORESQ5;
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
import java.util.HashSet;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.PredicatePersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_RACE, PREFIX_ADDRESS,
                PREFIX_SCHOOL, PREFIX_MAJOR, PREFIX_PASTJOB, PREFIX_TAG, PREFIX_GENDER, PREFIX_GRADE,
                PREFIX_NRIC, PREFIX_JOBSAPPLY, PREFIX_KNOWNPROGLANG, PREFIX_INTERVIEWSCORESQ1, PREFIX_INTERVIEWSCORESQ2,
                PREFIX_INTERVIEWSCORESQ3, PREFIX_INTERVIEWSCORESQ4, PREFIX_INTERVIEWSCORESQ5, PREFIX_FILTERNAME);
        JobListName listName;
        String commandName;
        String preambleString = argMultimap.getPreamble();
        String listNameString = preambleString.trim();
        try {
            listName = ParserUtil.parseJobListName(listNameString);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE), pe);
        }
        PredicatePersonDescriptor predicatePersonDescriptor = new PredicatePersonDescriptor();
        //predicatePersonDescriptor.setPredicateName(commandName);
        if (argMultimap.getValue(PREFIX_FILTERNAME).isPresent()) {
            commandName = argMultimap.getValue(PREFIX_FILTERNAME).get();
        } else {
            commandName = "";
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicatePersonDescriptor.setName(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            predicatePersonDescriptor.setPhone(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_PHONE).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicatePersonDescriptor.setEmail(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_RACE).isPresent()) {
            predicatePersonDescriptor.setRace(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_RACE).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicatePersonDescriptor.setAddress(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            predicatePersonDescriptor.setSchool(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_SCHOOL).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_MAJOR).isPresent()) {
            predicatePersonDescriptor.setMajor(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_MAJOR).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            predicatePersonDescriptor.setGender(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_GENDER).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            predicatePersonDescriptor.setGrade(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_GRADE).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORESQ1).isPresent()) {
            predicatePersonDescriptor.setInterviewScoreQ1(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_INTERVIEWSCORESQ1).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORESQ2).isPresent()) {
            predicatePersonDescriptor.setInterviewScoreQ2(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_INTERVIEWSCORESQ2).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORESQ3).isPresent()) {
            predicatePersonDescriptor.setInterviewScoreQ3(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_INTERVIEWSCORESQ3).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORESQ4).isPresent()) {
            predicatePersonDescriptor.setInterviewScoreQ4(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_INTERVIEWSCORESQ4).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORESQ5).isPresent()) {
            predicatePersonDescriptor.setInterviewScoreQ5(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_INTERVIEWSCORESQ5).get().split(";")))));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            predicatePersonDescriptor.setNric(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_NRIC).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_PASTJOB).isPresent()) {
            predicatePersonDescriptor.setPastJobs(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_PASTJOB).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_JOBSAPPLY).isPresent()) {
            predicatePersonDescriptor.setJobsApply(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_JOBSAPPLY).get().split("\\s+")))));
        }
        if (argMultimap.getValue(PREFIX_KNOWNPROGLANG).isPresent()) {
            predicatePersonDescriptor.setKnownProgLangs(new HashSet<>((
                Arrays.asList(argMultimap.getValue(PREFIX_KNOWNPROGLANG).get().split("\\s+")))));
        }

        return new FilterCommand(commandName, listName, predicatePersonDescriptor);
    }


}
