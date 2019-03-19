package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORES;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.PastJob;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_NRIC, PREFIX_GENDER,
                        PREFIX_RACE, PREFIX_ADDRESS, PREFIX_SCHOOL, PREFIX_MAJOR, PREFIX_GRADE, PREFIX_KNOWNPROGLANG,
                        PREFIX_PASTJOB, PREFIX_JOBSAPPLY, PREFIX_INTERVIEWSCORES, PREFIX_TAG);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            editPersonDescriptor.setNric(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            editPersonDescriptor.setGender(ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get()));
        }
        if (argMultimap.getValue(PREFIX_RACE).isPresent()) {
            editPersonDescriptor.setRace(ParserUtil.parseRace(argMultimap.getValue(PREFIX_RACE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_SCHOOL).isPresent()) {
            editPersonDescriptor.setSchool(ParserUtil.parseSchool(argMultimap.getValue(PREFIX_SCHOOL).get()));
        }
        if (argMultimap.getValue(PREFIX_MAJOR).isPresent()) {
            editPersonDescriptor.setMajor(ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR).get()));
        }
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            editPersonDescriptor.setGrade(ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_INTERVIEWSCORES).isPresent()) {
            editPersonDescriptor.setInterviewScores(ParserUtil.parseInterviewScores(argMultimap
                    .getValue(PREFIX_INTERVIEWSCORES).get()));
        }
        parseKnownProgLangsForEdit(argMultimap.getAllValues(PREFIX_KNOWNPROGLANG))
                .ifPresent(editPersonDescriptor::setKnownProgLangs);
        parsePastJobsForEdit(argMultimap.getAllValues(PREFIX_PASTJOB)).ifPresent(editPersonDescriptor::setPastJobs);
        parseJobsApplyForEdit(argMultimap.getAllValues(PREFIX_JOBSAPPLY)).ifPresent(editPersonDescriptor::setJobsApply);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> pastjobs} into a {@code Set<PastJob>} if {@code pastjobs} is non-empty.
     * If {@code pastjobs} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<PastJob>} containing zero past jobs.
     */
    private Optional<Set<KnownProgLang>> parseKnownProgLangsForEdit(Collection<String> knownProjLang)
            throws ParseException {
        assert knownProjLang != null;

        if (knownProjLang.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> knownProgLangSet = knownProjLang.size() == 1
                && knownProjLang.contains("") ? Collections.emptySet() : knownProjLang;
        return Optional.of(ParserUtil.parseKnownProgLangs(knownProgLangSet));
    }

    /**
     * Parses {@code Collection<String> pastjobs} into a {@code Set<PastJob>} if {@code pastjobs} is non-empty.
     * If {@code pastjobs} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<PastJob>} containing zero past jobs.
     */
    private Optional<Set<JobsApply>> parseJobsApplyForEdit(Collection<String> jobsApply) throws ParseException {
        assert jobsApply != null;

        if (jobsApply.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> jobsApplySet = jobsApply.size() == 1
                && jobsApply.contains("") ? Collections.emptySet() : jobsApply;
        return Optional.of(ParserUtil.parseJobsApply(jobsApplySet));
    }

    /**
     * Parses {@code Collection<String> jobsApply} into a {@code Set<JobsApply} if {@code jobsApply} is non-empty.
     * If {@code jobsApply} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<JobsApply>} containing zero jobsApply.
     */
    private Optional<Set<PastJob>> parsePastJobsForEdit(Collection<String> pastjobs) throws ParseException {
        assert pastjobs != null;

        if (pastjobs.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> pastjobSet = pastjobs.size() == 1
                && pastjobs.contains("") ? Collections.emptySet() : pastjobs;
        return Optional.of(ParserUtil.parsePastJobs(pastjobSet));
    }




    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
