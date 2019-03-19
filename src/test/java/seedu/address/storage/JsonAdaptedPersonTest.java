package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.InterviewScores;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.testutil.Assert;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_RACE = "Ang Moh";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_NRIC = "2131X";
    private static final String INVALID_GENDER = "444";
    private static final String INVALID_GRADE = "123";
    private static final String INVALID_JOBSAPPLY = " ";
    private static final String INVALID_INTERVIEWSCORES = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SCHOOL = " ";
    private static final String INVALID_MAJOR = "1234";
    private static final String INVALID_KNOWNPROGLANG = " ";
    private static final String INVALID_PASTJOB = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_GRADE = BENSON.getGrade().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_INTERVIEWSCORES = BENSON.getInterviewScores().toString();
    private static final String VALID_RACE = BENSON.getRace().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SCHOOL = BENSON.getSchool().toString();
    private static final String VALID_MAJOR = BENSON.getMajor().toString();
    private static final List<JsonAdaptedKnownProgLang> VALID_KNOWNPROGLANG = BENSON.getKnownProgLangs().stream()
            .map(JsonAdaptedKnownProgLang::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedPastJob> VALID_PASTJOBS = BENSON.getPastJobs().stream()
            .map(JsonAdaptedPastJob::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedJobsApply> VALID_JOBSAPPLY = BENSON.getJobsApply().stream()
            .map(JsonAdaptedJobsApply::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        INVALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        null, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSchool_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, INVALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = School.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSchool_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC, VALID_GENDER, VALID_RACE,
                        VALID_ADDRESS, null, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG, VALID_PASTJOBS,
                        VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_invalidMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, INVALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Major.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, null, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRace_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, INVALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Race.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRace_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, null, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Race.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                INVALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                null, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, INVALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, null, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidInterviewScores_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, INVALID_INTERVIEWSCORES, VALID_TAGS);
        String expectedMessage = InterviewScores.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullInterviewScores_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE, VALID_KNOWNPROGLANG,
                VALID_PASTJOBS, VALID_JOBSAPPLY, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewScores.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidKnownProgLang_throwsIllegalValueException() {
        List<JsonAdaptedKnownProgLang> invalidKnownProgLang = new ArrayList<>(VALID_KNOWNPROGLANG);
        invalidKnownProgLang.add(new JsonAdaptedKnownProgLang(INVALID_KNOWNPROGLANG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                        VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE,
                        invalidKnownProgLang, VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
    @Test
    public void toModelType_invalidPastJobs_throwsIllegalValueException() {
        List<JsonAdaptedPastJob> invalidPastJobs = new ArrayList<>(VALID_PASTJOBS);
        invalidPastJobs.add(new JsonAdaptedPastJob(INVALID_PASTJOB));
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                    VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE,
                    VALID_KNOWNPROGLANG, invalidPastJobs, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
    @Test
    public void toModelType_invalidJobsApply_throwsIllegalValueException() {
        List<JsonAdaptedJobsApply> invalidJobsApply = new ArrayList<>(VALID_JOBSAPPLY);
        invalidJobsApply.add(new JsonAdaptedJobsApply(INVALID_JOBSAPPLY));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                        VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE,
                        VALID_KNOWNPROGLANG, VALID_PASTJOBS, invalidJobsApply, VALID_INTERVIEWSCORES, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_NRIC,
                        VALID_GENDER, VALID_RACE, VALID_ADDRESS, VALID_SCHOOL, VALID_MAJOR, VALID_GRADE,
                        VALID_KNOWNPROGLANG, VALID_PASTJOBS, VALID_JOBSAPPLY, VALID_INTERVIEWSCORES, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
