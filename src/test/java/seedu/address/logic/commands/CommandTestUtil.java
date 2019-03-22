package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.request.EditRequestCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.tag.Specialisation;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditRequestDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "94672740";
    public static final String VALID_PHONE_BOB = "81812288";
    public static final String VALID_DATE_AMY = "01-10-2018 10:00:00";
    public static final String VALID_DATE_BOB = "02-10-2018 10:00:00";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_NRIC_BOB = "S9876543Z";
    public static final String VALID_NRIC_AMY = "S1234567A";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    // Default strings for Health Worker objects
    public static final String VALID_NAME_ANDY = "Andy Tan";
    public static final String VALID_NAME_BETTY = "Health Worker B";
    public static final String VALID_NRIC_ANDY = "S8312942G";
    public static final String VALID_NRIC_BETTY = "S9898222A";
    public static final String VALID_PHONE_ANDY = "94358253";
    public static final String VALID_PHONE_BETTY = "99991111";
    public static final String VALID_EMAIL_ANDY = "andye@example.com";
    public static final String VALID_EMAIL_BETTY = "healthworkerb@example.com";
    public static final String VALID_ADDRESS_ANDY = "125, Jurong West Ave 6, #08-111";
    public static final String VALID_ADDRESS_BETTY = "Block 456, NUS";
    public static final String VALID_ORGANIZATION_ANDY = "NUH";
    public static final String VALID_ORGANIZATION_BETTY = "Clinic B";
    // Default strings for Patient objects
    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_BENSON = "Benson Meier";
    public static final String VALID_NRIC_ALICE = "S9670515H";
    public static final String VALID_NRIC_BENSON = "S9274100D";
    public static final String VALID_PHONE_ALICE = "94351253";
    public static final String VALID_PHONE_BENSON = "98765432";
    public static final String VALID_CONDITION_PHYSIO = "Physiotherapy";
    public static final String VALID_CONDITION_DIALYSIS = "Dialysis";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    // Default descriptions for Health Worker objects
    public static final String MODE_HEALTHWORKER = " " + PREFIX_ADD_HEALTHWORKER;
    public static final String NAME_DESC_ANDY = " " + PREFIX_NAME + VALID_NAME_ANDY;
    public static final String NAME_DESC_BETTY = " " + PREFIX_NAME + VALID_NAME_BETTY;
    public static final String NRIC_DESC_ANDY = " " + PREFIX_NRIC + VALID_NRIC_ANDY;
    public static final String NRIC_DESC_BETTY = " " + PREFIX_NRIC + VALID_NRIC_BETTY;
    public static final String PHONE_DESC_ANDY = " " + PREFIX_PHONE + VALID_PHONE_ANDY;
    public static final String PHONE_DESC_BETTY = " " + PREFIX_PHONE + VALID_PHONE_BETTY;
    public static final String EMAIL_DESC_ANDY = " " + PREFIX_EMAIL + VALID_EMAIL_ANDY;
    public static final String EMAIL_DESC_BETTY = " " + PREFIX_EMAIL + VALID_EMAIL_BETTY;
    public static final String ADDRESS_DESC_ANDY = " " + PREFIX_ADDRESS + VALID_ADDRESS_ANDY;
    public static final String ADDRESS_DESC_BETTY = " " + PREFIX_ADDRESS + VALID_ADDRESS_BETTY;
    public static final String ORGANIZATION_DESC_ANDY = " " + PREFIX_ORGANIZATION + VALID_ORGANIZATION_ANDY;
    public static final String ORGANIZATION_DESC_BETTY = " " + PREFIX_ORGANIZATION + VALID_ORGANIZATION_BETTY;
    public static final String SKILLS_DESC_ANDY = " " + PREFIX_SKILLS + Specialisation.GENERAL_PRACTICE.name()
            + " " + PREFIX_SKILLS + Specialisation.PHYSIOTHERAPY.name();

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ORGANIZATION_DESC = " " + PREFIX_ORGANIZATION + "ABC!";
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "A12345678";
    public static final String INVALID_SKILLS_DESC = " " + PREFIX_SKILLS + "general_practice";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;
    public static final EditHealthWorkerCommand.EditHealthWorkerDescriptor DESC_ANDY;
    public static final EditHealthWorkerCommand.EditHealthWorkerDescriptor DESC_BETTY;
    public static final EditRequestCommand.EditRequestDescriptor REQ_DESC_ALICE;
    public static final EditRequestCommand.EditRequestDescriptor REQ_DESC_BOB;

    static {
        REQ_DESC_ALICE =
            new EditRequestDescriptorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY).withConditions(VALID_CONDITION_PHYSIO)
                .build();
        REQ_DESC_BOB =
            new EditRequestDescriptorBuilder().withAddress(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withConditions(VALID_CONDITION_PHYSIO).build();
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withNric(VALID_NRIC_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withNric(VALID_NRIC_BOB)
                .build();
        DESC_ANDY = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_ANDY)
                .withPhone(VALID_PHONE_ANDY)
                .withNric(VALID_NRIC_ANDY)
                .withOrganization(VALID_ORGANIZATION_ANDY)
                .withSkills(Specialisation.GENERAL_PRACTICE.name(), Specialisation.PHYSIOTHERAPY.name()).build();
        DESC_BETTY = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_BETTY)
                .withPhone(VALID_PHONE_BETTY)
                .withNric(VALID_NRIC_BETTY)
                .withOrganization(VALID_ORGANIZATION_BETTY)
                .withSkills(Specialisation.GENERAL_PRACTICE.name(), Specialisation.ORTHOPAEDIC.name()).build();
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
     * Updates {@code model}'s filtered list to show only the HealthWorker at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showHealthWorkerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredHealthWorkerList().size());

        HealthWorker healthWorker = model.getFilteredHealthWorkerList().get(targetIndex.getZeroBased());
        final String[] splitName = healthWorker.getName().fullName.split("\\s+");
        model.updateFilteredHealthWorkerList(p -> Arrays.asList(splitName[0]).stream().anyMatch(
            keyword -> StringUtil.containsWordIgnoreCase(p.getName().fullName, keyword)
        ));

        assertEquals(1, model.getFilteredHealthWorkerList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
