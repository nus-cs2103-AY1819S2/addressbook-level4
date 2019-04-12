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

import java.util.ArrayList;
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
import seedu.address.model.util.predicate.DescriptionContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DrugAllergyContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinAddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinNameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinPhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinRelationContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.SexContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code PatientFindCommand}.
 */
public class PatientFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        PatientFindCommand findFirstCommand = new PatientFindCommand(firstPredicate);
        PatientFindCommand findSecondCommand = new PatientFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        PatientFindCommand findFirstCommandCopy = new PatientFindCommand(firstPredicate);
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
    public void execute_nameParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "name", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "ElLe", "name", true, false, Arrays.asList(ELLE));
        //Single keyword, case sensitive, person found.
        execute_parameterPredicate_test(0, "ElLe", "name", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(3, "Kurz Elle Kunz", "name", true, false, Arrays.asList(CARL, ELLE, FIONA));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "kurz Elle kunz", "name", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, multiple people found
        execute_parameterPredicate_test(2, "kurz Elle Kunz", "name", false, false, Arrays.asList(ELLE, FIONA));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "Kurz Elle kunz", "name", false, true, Collections.emptyList());
    }

    /*
    Testing phone find
     */
    @Test
    public void execute_phoneParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "phone", true, false, Collections.emptyList());
        //Multiple keywords, or condition, multiple people found
        execute_parameterPredicate_test(3, "94351253 9482427 9482442", "phone",
            true, false, Arrays.asList(ALICE, FIONA, GEORGE));
        //Multiple keywords, and condition, multiple people found
        execute_parameterPredicate_test(0, "94351253 9482427 9482442", "phone",
            true, true, Collections.emptyList());
    }

    /*
    Testing address find
     */
    @Test
    public void execute_addressParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "addr", true, false, Collections.emptyList());
        //Single keyword, ignore case, people found.
        execute_parameterPredicate_test(3, "ave", "addr", true, false, Arrays.asList(ALICE, BENSON, ELLE));
        //Single keyword, case sensitive, people found.
        execute_parameterPredicate_test(2, "Ave", "addr", false, false, Arrays.asList(ALICE, BENSON));
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "jurong Clementi", "addr", true, false, Arrays.asList(ALICE, BENSON));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "jurong Clementi", "addr", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, multiple people found
        execute_parameterPredicate_test(1, "jurong Clementi", "addr", false, false, Arrays.asList(BENSON));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "jurong Clementi", "addr", false, true, Collections.emptyList());
    }

    /*
    Testing email find
     */

    @Test
    public void execute_emailParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "email", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "ALICE", "email", true, false, Arrays.asList(ALICE));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "ALICE", "email", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "ALiCe anna", "email", true, false, Arrays.asList(ALICE, GEORGE));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "ALiCe anna", "email", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, one person found
        execute_parameterPredicate_test(1, "ALiCe anna", "email", false, false, Arrays.asList(GEORGE));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "ALiCe anna", "email", false, true, Collections.emptyList());
    }

    /*
    Testing Date of Birth find
     */

    @Test
    public void execute_dateOfBirthParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "dob", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(5, "december", "dob",
            true, false, Arrays.asList(ALICE, BENSON, CARL, DANIEL, FIONA));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "december", "dob", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(4, "june 11", "dob",
            true, false, Arrays.asList(ALICE, CARL, ELLE, GEORGE));
        //Multiple keywords, ignore case, and condition, one person found
        execute_parameterPredicate_test(1, "june 11", "dob",
            true, true, Arrays.asList(ELLE));
        //Multiple keywords, case sensitive, or condition, one person found
        execute_parameterPredicate_test(4, "june 11", "dob",
            false, false, Arrays.asList(ALICE, CARL, ELLE, GEORGE));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "june 11", "dob",
            false, true, Collections.emptyList());
    }

    /*
    Testing Nric find
     */
    @Test
    public void execute_nricParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "nric", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "s1234567A", "nric", true, false, Arrays.asList(ALICE));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "s1234567A", "nric", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "S1234567a S5234569A", "nric", true, false, Arrays.asList(ALICE, GEORGE));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "S1234567a S5234569A", "nric", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, one person found
        execute_parameterPredicate_test(1, "S1234567a S5234569A", "nric", false, false, Arrays.asList(GEORGE));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "S1234567a S5234569A", "nric", false, true, Collections.emptyList());
    }

    @Test
    public void execute_sexParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "sex", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(4, "f", "sex", true, false, Arrays.asList(ALICE, CARL, ELLE, FIONA));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "f", "sex", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(7, "m F", "sex", true, false,
            Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "m F", "sex", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(4, "m F", "sex", false, false, Arrays.asList(ALICE, CARL, ELLE, FIONA));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "m F", "sex", false, true, Collections.emptyList());
    }

    @Test
    public void execute_drugAllergyParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "drug", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(3, "nil", "drug", true, false, Arrays.asList(ALICE, ELLE, FIONA));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "nil", "drug", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "panadol penicilin", "drug", true, false,
            Arrays.asList(BENSON, DANIEL));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "panadol penicilin", "drug", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(1, "panadol Penicilin", "drug", false, false, Arrays.asList(DANIEL));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "panadol penicilin", "drug", false, true, Collections.emptyList());
    }

    @Test
    public void execute_descParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "desc", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "surgery", "desc", true, false, Arrays.asList(FIONA));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "SuRGery", "desc", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "different surgery", "desc", true, false,
            Arrays.asList(DANIEL, FIONA));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(1, "tEEth surgery", "desc", true, true, Arrays.asList(FIONA));
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(1, "tEEth surgery", "desc", false, false, Arrays.asList(FIONA));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "tEEth surgery", "desc", false, true, Collections.emptyList());
    }

    @Test
    public void execute_kinNameParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "kinN", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "karly", "kinN", true, false, Arrays.asList(FIONA));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "karly", "kinN", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(3, "Meier charLie", "kinN", true, false,
            Arrays.asList(BENSON, CARL, DANIEL));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "Meier charLie", "kinN", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(2, "Meier charLie", "kinN", false, false, Arrays.asList(BENSON, DANIEL));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "Meier charLie", "kinN", false, true, Collections.emptyList());
    }

    @Test
    public void execute_kinRelationParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "kinR", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(1, "MOTHER", "kinR", true, false, Arrays.asList(DANIEL));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "MOTHER", "kinR", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "Grandfather sister", "kinR", true, false,
            Arrays.asList(BENSON, ELLE));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "Grandfather sister", "kinR", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(1, "Grandfather sister", "kinR", false, false, Arrays.asList(ELLE));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "Grandfather sister", "kinR", false, true, Collections.emptyList());
    }

    @Test
    public void execute_kinPhoneParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "kinP", true, false, Collections.emptyList());
        //Multiple keywords, or condition, multiple people found
        execute_parameterPredicate_test(2, "98712345 84456622", "kinP",
            true, false, Arrays.asList(BENSON, DANIEL));
        //Multiple keywords, and condition, multiple people found
        execute_parameterPredicate_test(0, "98712345 84456622", "kinP",
            true, true, Collections.emptyList());
    }

    @Test
    public void execute_kinAddressParameter() throws ParseException {
        //No user input
        execute_parameterPredicate_test(0, " ", "kinA", true, false, Collections.emptyList());
        //Single keyword, ignore case, person found.
        execute_parameterPredicate_test(2, "ave", "kinA", true, false, Arrays.asList(ALICE, BENSON));
        //Single keyword, case sensitive, no one found.
        execute_parameterPredicate_test(0, "ave", "kinA", false, false, Collections.emptyList());
        //Multiple keywords, ignore case, or condition, multiple people found
        execute_parameterPredicate_test(2, "SQUare Ohio", "kinA", true, false,
            Arrays.asList(CARL, ELLE));
        //Multiple keywords, ignore case, and condition, no one found
        execute_parameterPredicate_test(0, "SQUare Ohio", "kinA", true, true, Collections.emptyList());
        //Multiple keywords, case sensitive, or condition, people found
        execute_parameterPredicate_test(1, "SQUare Ohio", "kinA", false, false, Arrays.asList(ELLE));
        //Multiple keywords, case sensitive, and condition, no one found
        execute_parameterPredicate_test(0, "SQUare Ohio", "kinA", false, true, Collections.emptyList());
    }

    @Test
    public void execute_multiParameter_namePhone() throws ParseException {
        //different parameters, two expected people
        execute_multipleParameterPredicate_test(2, "alice 95352563", "name phone", true, false,
            Arrays.asList(ALICE, CARL));
        //different paramters from same person, ignore case, and operation
        execute_multipleParameterPredicate_test(1, "alice 94351253", "name phone", true, true,
            Arrays.asList(ALICE));
        //different paramters from same person, case sensitive, and operation
        execute_multipleParameterPredicate_test(0, "alice 94351253", "name phone", false, true,
            Collections.emptyList());
    }

    /**
     * Wrapper function to test PatientFindCommand through multiple attributes
     * @param isIgnoreCase flag for case sensitivity
     * @param isAnd flag for and operation
     * @param userInput inputs to test
     * @param parameter parameters to test
     * @param expectedList expected list after predicate has been applied
     */
    public void execute_multipleParameterPredicate_test(int expectedNum, String userInput, String parameter,
                                          boolean isIgnoreCase, boolean isAnd,
                                          List<Person> expectedList) throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedNum);
        ContainsKeywordsPredicate predicate = prepareMultiPredicate(isIgnoreCase, isAnd, userInput, parameter);
        PatientFindCommand command = new PatientFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    /**
     * Prepares a {@Code MultipleContainsKeywordsPredicate} for testing multi-parameter search
     * @param isIgnoreCase flag for case sensitivity
     * @param isAnd flag for and operation
     * @param userInput inputs to test
     * @param parameters parameters to test
     */
    private MultipleContainsKeywordsPredicate prepareMultiPredicate (boolean isIgnoreCase, boolean isAnd,
                                                                     String userInput, String parameters)
        throws ParseException {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            isIgnoreCase, isAnd);
        String[] inputs = userInput.split("\\s+");
        String[] paras = parameters.split("\\s+");
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();

        for (int i = 0; i < inputs.length; i++) {
            predicateList.add(preparePatientPredicate(inputs[i], paras[i], isIgnoreCase, isAnd));
        }
        tempPred.setPredicateList(predicateList);
        return tempPred;
    }

    /**
     * Wrapper function to test PatientFindCommand through each attribute
     * @param expectedNum expected number of returned Persons after predicate
     * @param userInput predicate to test
     * @param parameter attribute to test
     * @param isIgnoreCase flag for case sensitivity
     * @param isAnd flag for AND operator
     * @param expectedList expected list after predicate has been applied
     */
    private void execute_parameterPredicate_test(int expectedNum, String userInput, String parameter,
                                                 boolean isIgnoreCase, boolean isAnd,
                                                 List<Person> expectedList) throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedNum);
        ContainsKeywordsPredicate predicate = preparePatientPredicate(userInput, parameter, isIgnoreCase, isAnd);
        PatientFindCommand command = new PatientFindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsKeywordsPredicate}.
     */
    private ContainsKeywordsPredicate preparePatientPredicate(String userInput, String parameter, boolean isIgnoreCase,
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

        case "drug":
            return new DrugAllergyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "sex":
            return new SexContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), isIgnoreCase, isAnd);

        case "desc":
            return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "kinN":
            return new KinNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "kinR":
            return new KinRelationContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "kinP":
            return new KinPhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        case "kinA":
            return new KinAddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                isIgnoreCase, isAnd);

        default:
            throw new ParseException("Invalid Sort Attribute.");
        }
    }
}
