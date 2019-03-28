package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /*
    Testing name find.
     */
    @Test
    public void execute_zeroKeywords_noPersonFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "name", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        execute_parameterPredicate_test(3, "Kurz Elle Kunz", "name", true, false, Arrays.asList(CARL, ELLE, FIONA));
    }

    /*
    Testing phone find
     */
    @Test
    public void execute_zeroKeywords_noPhoneFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "phone", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePhonesFound() throws ParseException {
        execute_parameterPredicate_test(3, "94351253 9482427 9482442", "phone",
            true, false, Arrays.asList(ALICE, FIONA, GEORGE));
    }

    /*
    Testing address find
     */
    @Test
    public void execute_zeroKeywords_noAddressFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "addr", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleAddressFound() throws ParseException {
        execute_parameterPredicate_test(3, "street", "addr", true, false, Arrays.asList(CARL, DANIEL, GEORGE));
    }

    /*
    Testing email find
     */
    @Test
    public void execute_zeroKeywords_noEmailFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "email", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmailsFound() throws ParseException {
        execute_parameterPredicate_test(3,
            "alice@example.com cornelia@example.com lydia@example.com", "email",
            true, false, Arrays.asList(ALICE, DANIEL, FIONA));
    }

    /*
    Testing Date of Birth find
     */
    @Test
    public void execute_zeroKeywords_noDateOfBirthFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "dob", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleDateOfBirthsFound() throws ParseException {
        execute_parameterPredicate_test(5, "December", "dob",
            true, false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, FIONA));
    }

    /*
    Testing Nric find
     */
    @Test
    public void execute_zeroKeywords_noNricFound() throws ParseException {
        execute_parameterPredicate_test(0, " ", "nric", true, false, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleNricFound() throws ParseException {
        execute_parameterPredicate_test(2, "S2334569A S5234569A", "nric",
            true, false, Arrays.asList(ELLE, GEORGE));
    }

    /**
     * Wrapper function to test FindCommand through each attribute
     * @param expectedNum expected number of returned Persons after predicate
     * @param userInput predicate to test
     * @param parameter attribute to test
     * @param expectedList expected list after predicate has been applied
     */
    private void execute_parameterPredicate_test(int expectedNum, String userInput, String parameter,
                                                 boolean isIgnoreCase, boolean isAnd,
                                                 List<Person> expectedList) throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedNum);
        ContainsKeywordsPredicate predicate = prepareNamePredicate(userInput, parameter, isIgnoreCase, isAnd);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate prepareNamePredicate(String userInput, String parameter, boolean isIgnoreCase,
                                                           boolean isAnd) throws ParseException {
        switch(parameter) {
        case "name":
            return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "phone":
            return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "addr":
            return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "email":
            return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "nric":
            return new NricContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "dob":
            return new DateOfBirthContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        default:
            throw new ParseException("Invalid Sort Attribute.");
        }
    }
}
