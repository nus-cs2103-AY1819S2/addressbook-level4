package seedu.address.logic.parser;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.ImportResumesCommand;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

public class ImportResumesCommandParser implements Parser<ImportResumesCommand> {

    public static final String FILE_NOT_FOUND_MESSAGE = "The specified file cannot be found.";
    public static final String READER_ERROR_MESSAGE = "There is a problem reading your file";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportResumesCommand
     * and returns an ImportResumesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportResumesCommand parse(String args) throws ParseException {
        Person person = null;
        String filename = args.trim();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // TODO: Parse the file and continue the command call
            // You'll need reader.readLine() to extract lines from the file, line by line
            // Refer to AddCommandParser for fields, and how to handle them

            // Example:
            // Original: Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            // After you change: Name name = ParserUtil.parseName( reader.readLine() );
            Name name = ParserUtil.parseName(reader.readLine());
            Phone phone = ParserUtil.parsePhone(reader.readLine());
            Email email = ParserUtil.parseEmail(reader.readLine());
            Nric nric = ParserUtil.parseNric(reader.readLine());
            Gender gender = ParserUtil.parseGender(reader.readLine());
            Race race = ParserUtil.parseRace(reader.readLine());
            Address address = ParserUtil.parseAddress(reader.readLine());
            School school = ParserUtil.parseSchool(reader.readLine());
            Major major = ParserUtil.parseMajor(reader.readLine());
            Grade grade = ParserUtil.parseGrade(reader.readLine());

            // Todo: This may break if lines are left blank
            String langString = reader.readLine();
            String[] langArray = langString.split(",");
            Collection<String> langsStringList = Arrays.asList(langArray);
            Set<KnownProgLang> knownProgLangsList = ParserUtil.parseKnownProgLangs(langsStringList);

            String pastJobsString = reader.readLine();
            String[] jobsArray = pastJobsString.split(",");
            Collection<String> jobsStringList = Arrays.asList(jobsArray);
            Set<PastJob> pastjobList = ParserUtil.parsePastJobs(jobsStringList);

            String jobsApplyString = reader.readLine();
            String[] jobsApplyArray = jobsApplyString.split(",");
            Collection<String> jobsApplyStringList = Arrays.asList(jobsApplyArray);
            Set<JobsApply> jobsApplyList = ParserUtil.parseJobsApply(jobsApplyStringList);

            InterviewScores interviewScores = ParserUtil.parseInterviewScores(reader.readLine());

            String tagsString = reader.readLine();
            String[] tagsArray = tagsString.split(",");
            Collection<String> tagsStringList = Arrays.asList(tagsArray);
            Set<Tag> tagList = ParserUtil.parseTags(tagsStringList);

            person = new Person(name, phone, email, nric, gender, race, address, school, major, grade,
                    knownProgLangsList, pastjobList, jobsApplyList, interviewScores, tagList);

            // TODO: Test and make sure this doesn't explode

        } catch (FileNotFoundException e) {
            throw new ParseException(FILE_NOT_FOUND_MESSAGE);
        } catch (IOException e) {
            throw new ParseException(READER_ERROR_MESSAGE);
        }
        return new ImportResumesCommand(person);
    }
}
