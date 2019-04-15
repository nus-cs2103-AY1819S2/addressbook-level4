package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.TaskStartDateContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //Strings for Patient testing
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "S9876543A";
    public static final String VALID_NRIC_BOB = "S8765432B";
    public static final String VALID_DOB_AMY = "05-05-1992";
    public static final String VALID_DOB_BOB = "28-02-1985";
    public static final String VALID_SEX_AMY = "F";
    public static final String VALID_SEX_BOB = "M";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_YEAR + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_YEAR + VALID_DOB_BOB;
    public static final String SEX_DESC_AMY = " " + PREFIX_SEX + VALID_SEX_AMY;
    public static final String SEX_DESC_BOB = " " + PREFIX_SEX + VALID_SEX_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "S123A"; // numerical length should be 7
    public static final String INVALID_DOB_DESC = " " + PREFIX_YEAR + "09061995"; // not separated by '-'
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "G"; // only M or F accepted
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

    public static final PatientEditCommand.EditPersonDescriptor DESC_AMY;
    public static final PatientEditCommand.EditPersonDescriptor DESC_BOB;
    //Strings for Task testing
    public static final String VALID_TITLE_NOPATIENT = "Patient Review";
    public static final String VALID_STARTDATE_NOPATIENT = "02-01-2020";
    public static final String VALID_ENDDATE_NOPATIENT = "02-01-2020";
    public static final String VALID_STARTTIME_NOPATIENT = "1400";
    public static final String VALID_ENDTIME_NOPATIENT = "1500";
    public static final String VALID_PRIORITY_NOPATIENT = "low";

    public static final String VALID_TITLE_WITHPATIENT = "Teeth cleaning for Amy";
    public static final String VALID_STARTDATE_WITHPATIENT = "02-02-2020";
    public static final String VALID_ENDDATE_WITHPATIENT = "02-02-2020";
    public static final String VALID_STARTTIME_WITHPATIENT = "1100";
    public static final String VALID_ENDTIME_WITHPATIENT = "1200";
    public static final String VALID_PRIORITY_WITHPATIENT = "med";
    public static final String VALID_LINKEDPATIENTNAME_WITHPATIENT = VALID_NAME_AMY;
    public static final String VALID_LINKEDPATIENTNRIC_WITHPATIENT = VALID_NRIC_AMY;

    public static final String TITLE_DESC_NOPATIENT = " " + PREFIX_TITLE + VALID_TITLE_NOPATIENT;
    public static final String STARTDATE_DESC_NOPATIENT = " " + PREFIX_STARTDATE + VALID_STARTDATE_NOPATIENT;
    public static final String ENDDATE_DESC_NOPATIENT = " " + PREFIX_ENDDATE + VALID_ENDDATE_NOPATIENT;
    public static final String STARTTIME_DESC_NOPATIENT = " " + PREFIX_STARTTIME + VALID_STARTTIME_NOPATIENT;
    public static final String ENDTIME_DESC_NOPATIENT = " " + PREFIX_ENDTIME + VALID_ENDTIME_NOPATIENT;
    public static final String PRIORITY_DESC_NOPATIENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_NOPATIENT;

    public static final String TITLE_DESC_WITHPATIENT = " " + PREFIX_TITLE + VALID_TITLE_WITHPATIENT;
    public static final String STARTDATE_DESC_WITHPATIENT = " " + PREFIX_STARTDATE + VALID_STARTDATE_WITHPATIENT;
    public static final String ENDDATE_DESC_WITHPATIENT = " " + PREFIX_ENDDATE + VALID_ENDDATE_WITHPATIENT;
    public static final String STARTTIME_DESC_WITHPATIENT = " " + PREFIX_STARTTIME + VALID_STARTTIME_WITHPATIENT;
    public static final String ENDTIME_DESC_WITHPATIENT = " " + PREFIX_ENDTIME + VALID_ENDTIME_WITHPATIENT;
    public static final String PRIORITY_DESC_WITHPATIENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_WITHPATIENT;


    public static final String INVALID_TITLE_DESC = "Close*"; // '*' not allowed in title;
    public static final String INVALID_DATEVALUE_DESC = "33-02-2020"; // 33rd of February is not a real and valid date
    public static final String INVALID_DATEFORMAT_DESC = "22.04.2020"; // wrong seperator being used, should use '-'
    public static final String INVALID_TIME = "2900"; // '2900' is not a valid time;
    public static final String INVALID_PRIORITY = "never"; // must be either high, med or low
    public static final String INVALID_LINKEDPATIENTNAME = "Zemo"; //patient with this name does not exist
    public static final String INVALID_LINKEDPATIENTNRIC = "S99999999H"; // patient with this nric does not exist

    public static final TaskEditCommand.EditTaskDescriptor DESC_TASKWITHPATIENT;
    public static final TaskEditCommand.EditTaskDescriptor DESC_TASKWITHNOPATIENT;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
                .withDob(VALID_DOB_AMY).withSex(VALID_SEX_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
                .withDob(VALID_DOB_BOB).withSex(VALID_SEX_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();

        DESC_TASKWITHNOPATIENT = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_NOPATIENT)
                .withStartDate(VALID_STARTDATE_NOPATIENT).withEndDate(VALID_ENDDATE_NOPATIENT)
                .withStartTime(VALID_STARTTIME_NOPATIENT).withEndTime(VALID_ENDTIME_NOPATIENT)
                .withPriority(VALID_PRIORITY_NOPATIENT).withNoLinkedPatient().build();

        DESC_TASKWITHPATIENT = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT)
                .withStartDate(VALID_STARTDATE_WITHPATIENT).withEndDate(VALID_ENDDATE_WITHPATIENT)
                .withStartTime(VALID_STARTTIME_WITHPATIENT).withEndTime(VALID_ENDTIME_WITHPATIENT)
                .withPriority(VALID_PRIORITY_WITHPATIENT)
                .withLinkedPatient(VALID_LINKEDPATIENTNAME_WITHPATIENT, VALID_LINKEDPATIENTNRIC_WITHPATIENT).build();
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        Person expectedSelectedPerson = actualModel.getSelectedPerson();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedSelectedPerson, actualModel.getSelectedPerson());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] date = new String[1];
        date[0] = task.getStartDate().toString();
        model.updateFilteredTaskList(new TaskStartDateContainsKeywordsPredicate(Arrays.asList(date)));
        assertEquals(1, model.getFilteredTaskList().size());
    }

}
