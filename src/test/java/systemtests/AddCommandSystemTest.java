package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEWSCORES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEWSCORES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTERVIEWSCORES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOBSAPPLY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHOOL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOBSAPPLY_DESC_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.JOBSAPPLY_DESC_TRADER;
import static seedu.address.logic.commands.CommandTestUtil.KNOWNPROGLANG_DESC_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PASTJOB_DESC_PROFESSOR;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RACE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RACE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWSCORES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBSAPPLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.InterviewScores;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + NRIC_DESC_AMY + "   " + GENDER_DESC_AMY + "   " + RACE_DESC_AMY + "   "
                + ADDRESS_DESC_AMY + "   " + SCHOOL_DESC_AMY + "  " + MAJOR_DESC_AMY + "   " + GRADE_DESC_AMY + "   "
                + TAG_DESC_FRIEND + " " + JOBSAPPLY_DESC_TRADER + "   " + PASTJOB_DESC_PROFESSOR + " "
                + KNOWNPROGLANG_DESC_PYTHON + " " + INTERVIEWSCORES_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except nric -> added */
        toAdd = new PersonBuilder(AMY).withNric(VALID_NRIC_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + TAG_DESC_FRIEND + GENDER_DESC_AMY
                + NRIC_DESC_BOB + JOBSAPPLY_DESC_TRADER + GRADE_DESC_AMY + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + INTERVIEWSCORES_DESC_AMY;
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags and past jobs, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB
            + NAME_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + RACE_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
            + PASTJOB_DESC_PROFESSOR + KNOWNPROGLANG_DESC_PYTHON + GENDER_DESC_BOB + GRADE_DESC_BOB
            + NRIC_DESC_BOB + JOBSAPPLY_DESC_ENGINEER + INTERVIEWSCORES_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different phone -> rejected */
        toAdd = new PersonBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different email -> rejected */
        toAdd = new PersonBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different address -> rejected */
        toAdd = new PersonBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different school -> rejected */
        toAdd = new PersonBuilder(HOON).withSchool(VALID_SCHOOL_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different major -> rejected */
        toAdd = new PersonBuilder(HOON).withMajor(VALID_MAJOR_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different race -> rejected */
        toAdd = new PersonBuilder(HOON).withRace(VALID_RACE_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different gender -> rejected */
        toAdd = new PersonBuilder(HOON).withGender(VALID_GENDER_AMY).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different grade -> rejected */
        toAdd = new PersonBuilder(HOON).withGrade(VALID_GRADE_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different interview scores -> rejected */
        toAdd = new PersonBuilder(HOON).withInterviewScores(VALID_INTERVIEWSCORES_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different jobs apply -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_JOBSAPPLY.getPrefix() + "Marketing Intern";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY + ADDRESS_DESC_AMY
            + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY + ADDRESS_DESC_AMY
            + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + RACE_DESC_AMY + ADDRESS_DESC_AMY
            + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing school -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing race -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing gender -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
                + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing grade -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + NRIC_DESC_AMY
                + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing nric -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
                + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing jobs apply -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
                + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid race -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_RACE_DESC
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Race.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + INVALID_ADDRESS_DESC + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid school -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + INVALID_SCHOOL_DESC + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, School.MESSAGE_CONSTRAINTS);

        /* Case: invalid major -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + INVALID_MAJOR_DESC + GENDER_DESC_AMY + GRADE_DESC_AMY + NRIC_DESC_AMY
            + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Major.MESSAGE_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + INVALID_GENDER_DESC + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Gender.MESSAGE_CONSTRAINTS);

        /* Case: invalid grade -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + INVALID_GRADE_DESC
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Grade.MESSAGE_CONSTRAINTS);

        /* Case: invalid nric -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + INVALID_NRIC_DESC + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Nric.MESSAGE_CONSTRAINTS);

        /* Case: invalid interview scores -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INVALID_INTERVIEWSCORES_DESC;
        assertCommandFailure(command, InterviewScores.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */

        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + INVALID_TAG_DESC + GENDER_DESC_AMY + GRADE_DESC_AMY
            + NRIC_DESC_AMY + JOBSAPPLY_DESC_TRADER + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);

        /* Case: invalid jobs apply -> rejected */

        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
            + ADDRESS_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + TAG_DESC_FRIEND + GENDER_DESC_AMY
            + GRADE_DESC_AMY + NRIC_DESC_AMY + INVALID_JOBSAPPLY_DESC + INTERVIEWSCORES_DESC_AMY;
        assertCommandFailure(command, JobsApply.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
