package seedu.address.logic.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

/**
 * Parses input arguments and creates a new ImportResumesCommand object
 */
public class ImportResumesCommandParser implements Parser<ImportResumesCommand> {

    public static final String FILE_NOT_FOUND_MESSAGE = "The specified file cannot be found.";
    public static final String READER_ERROR_MESSAGE = "There is a problem reading your file";
    public static final String INVALID_DIRECTORY_MESSAGE = "The given path does not specify a folder.";
    public static final String EMPTY_DIRECTORY_MESSAGE = "The given path does not contain any files.";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportResumesCommand
     * and returns an ImportResumesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportResumesCommand parse(String args) throws ParseException {
        File folder = new File(args.trim());
        File[] filesList = folder.listFiles();

        if (filesList == null) {
            throw new ParseException(INVALID_DIRECTORY_MESSAGE);
        } else if (filesList.length == 0) {
            throw new ParseException(EMPTY_DIRECTORY_MESSAGE);
        }

        Set<Person> peopleToAdd = new HashSet<>();
        for (int i = 0; i < filesList.length; i++) {
            Person currentPerson = parseOnePerson(filesList[i]);
            peopleToAdd.add(currentPerson);
        }

        return new ImportResumesCommand(peopleToAdd);
    }

    /**
     * Opens the given (@code File), and parses its contents in the context of the
     * ImportResumesCommand. It returns a Person object.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private Person parseOnePerson(File resumeFile) throws ParseException {
        Person person = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(resumeFile));

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

        } catch (FileNotFoundException e) {
            throw new ParseException(FILE_NOT_FOUND_MESSAGE);
        } catch (IOException e) {
            throw new ParseException(READER_ERROR_MESSAGE);
        }

        return person;
    }
}
