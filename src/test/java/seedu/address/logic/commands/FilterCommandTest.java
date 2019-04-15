package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *Contains unit tests for FilterCommand.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model model2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private CommandHistory commandHistory2 = new CommandHistory();
    private Model modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Explanation of process numbers:
     * 0 -> clear
     * 1 -> or
     * 2 -> and
     * 3 -> reverse

     * Indexes in the criterion array:
     * 1- Name
     * 2- Phone
     * 3- Email
     * 4- Address
     * 5- Skills
     * 6- Positions
     * 7- Gpa
     * 8- Education
     * 9- Endorsement
     * 10- Degree
     */

    @Test
    public void execute_oneConditionAtOneFilter_success() {

        // name condition - 2 peoples are listed: DANIEL, ELLE
        String[] criterion = {"el", null, null, null, null, null, null, null, null, null};
        FilterCommand commandOr = new FilterCommand(criterion, 1);
        FilterCommand commandAnd = new FilterCommand(criterion, 2);

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(criterion[0], null, null, null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(criterion[0], null, null, null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);


        // phone condition - 1 person listed: ALICE
        criterion[0] = null;
        criterion[1] = "9435";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, criterion[1], null, null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, criterion[1], null, null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // email condition - 7 people are listed: ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE
        criterion[1] = null;
        criterion[2] = "@example.com";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, criterion[2], null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, criterion[2], null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // address condition - 0 persons are listed
        criterion[2] = null;
        criterion[3] = "UTown Centre";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, criterion[3], null,
                null, null, null, null, null);
        assertEquals(Collections.emptyList(), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, criterion[3], null,
                null, null, null, null, null);
        assertEquals(Collections.emptyList(), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // skills tag condition - 3 persons are listed: ALICE, BENSON, DANIEL
        criterion[3] = null;
        criterion[4] = "Debugging";
        String[] skillArr = {"Debugging"};
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, skillArr,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, skillArr,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // multiple skills tags together condition
        criterion[4] = "C++, JavaScript";
        String[] skillMultipleInfoArr = {"C++", "JavaScript"};
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        // and condition - 1 person is listed: BENSON
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, skillMultipleInfoArr,
                null, null, null, null, null);
        assertEquals(Arrays.asList(BENSON), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        // or condition - 6 persons are listed: ALICE, BENSON, CARL, DANIEL, ELLE, FIONA
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, skillMultipleInfoArr,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // multiple position tags together condition
        criterion[4] = null;
        criterion[5] = "Developer, UI";
        String[] tagMultipleInfoArr = {"Developer", "UI"};
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        // and condition - 2 persons are listed: CARL, DANIEL
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                tagMultipleInfoArr, null, null, null, null);
        assertEquals(Arrays.asList(CARL, DANIEL), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        // or condition - 3 persons are listed: BENSON, CARL, DANIEL
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                tagMultipleInfoArr, null, null, null, null);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // gpa condition - 1 person listed: FIONA
        criterion[5] = null;
        criterion[6] = "4.0";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, criterion[6], null, null);
        assertEquals(Arrays.asList(FIONA), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                null, null, criterion[6], null, null);
        assertEquals(Arrays.asList(FIONA), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // education condition - 2 people are listed: FIONA, ELLE
        criterion[6] = null;
        criterion[7] = "harvard";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, null, criterion[7], null);
        assertEquals(Arrays.asList(ELLE, FIONA), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                null, null, null, criterion[7], null);
        assertEquals(Arrays.asList(ELLE, FIONA), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // gpa condition - 1 person listed: FIONA
        criterion[7] = null;
        criterion[8] = "2";
        commandOr = new FilterCommand(criterion, 1);
        commandAnd = new FilterCommand(criterion, 2);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                null, criterion[8], null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE, FIONA), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                null, criterion[8], null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE, FIONA), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);
    }

    @Test
    public void execute_filterClearAtOneFilterActive_success() {

        String[] criterion = {"el", null, null, null, null, null, "2.6", null, null, null};
        FilterCommand commandAnd = new FilterCommand(criterion, 2);
        FilterCommand commandOr = new FilterCommand(criterion, 1);
        FilterCommand command = new FilterCommand(criterion, 0);

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(criterion[0], null, null, null, null,
                null, null, criterion[6], null, null);
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        // filter cleared, all persons will be shown
        modelAnd.clearFilter();
        assertEquals(Arrays.asList(DANIEL, ALICE, BENSON, CARL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(criterion[0], null, null, null, null,
                null, null, criterion[6], null, null);
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);
        // filter cleared, all persons will be shown
        modelOr.clearFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, modelOr);
    }

    @Test
    public void execute_filterReverseAtOneFilterActive_success() {

        String[] criterion = {"el", null, null, null, null, null, null, null, null, null};
        FilterCommand commandAnd = new FilterCommand(criterion, 2);
        FilterCommand commandOr = new FilterCommand(criterion, 1);
        FilterCommand command = new FilterCommand(criterion, 3);

        // name condition - 2 peoples are listed: DANIEL, ELLE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(criterion[0], null, null, null, null,
                null, null, null, null, null);
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        // filter reversed, all persons will be shown except DANIEL and ELLE
        modelAnd.reverseFilter();
        modelAnd.commitAddressBook();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE), modelAnd.getFilteredPersonList());
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(criterion[0], null, null, null, null,
                null, null, null, null, null);
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // filter reversed, all persons will be shown except DANIEL and ELLE
        modelOr.reverseFilter();
        modelOr.commitAddressBook();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE), modelOr.getFilteredPersonList());
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, modelOr);
    }

    @Test
    public void execute_filterClearAtMultipleFiltersActive_success() {

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String[] skillArr = {"C++"};

        // Filter Level 1: ALICE, BENSON, CARL, FIONA will be remained
        String[] criterion1 = {null, null, null, null, null, null, "2.8", null, null, null};
        FilterCommand commandAnd = new FilterCommand(criterion1, 2);
        FilterCommand commandOr = new FilterCommand(criterion1, 1);

        modelAnd.filterAnd(null, null, null, null, null,
                null, null, criterion1[6], null, null);
        modelOr.filterOr(null, null, null, null, null,
                null, null, criterion1[6], null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // Filter Level 2
        String[] criterion2 = {null, null, null, null, skillArr[0], null, null, null, null, null};
        commandAnd = new FilterCommand(criterion2, 2);
        commandOr = new FilterCommand(criterion2, 1);
        modelAnd.filterOr(null, null, null, null, skillArr,
                null, null, null, null, null);
        modelOr.filterAnd(null, null, null, null, skillArr,
                null, null, null, null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);


        // Filter Level 3: ALICE will be remained
        String[] criterion3 = {null, null, "alice", null, null, null, null, null, null, null};
        commandAnd = new FilterCommand(criterion3, 2);
        commandOr = new FilterCommand(criterion3, 1);
        modelAnd.filterAnd(null, null, criterion3[2], null, null,
                null, null, null, null, null);
        modelOr.filterOr(null, null, criterion3[2], null, null,
                null, null, null, null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // Filter Clear Level: All persons will be restored
        String[] criterionClear = {null, null, null, null, null, null, null, null, null, null};
        FilterCommand command = new FilterCommand(criterionClear, 0);

        modelAnd.clearFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr.clearFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(command, model2, commandHistory2,
                FilterCommand.MESSAGE_CLEAR_FILTER_PERSON_SUCCESS, modelOr);
    }

    @Test
    public void execute_filterReverseAtMultipleFiltersActive_success() {

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String[] skillArr = {"C++"};

        // Filter Level 1: ALICE, BENSON, CARL, FIONA will be remained
        String[] criterion1 = {null, null, null, null, null, null, "2.8", null, null, null};
        FilterCommand commandAnd = new FilterCommand(criterion1, 2);
        FilterCommand commandOr = new FilterCommand(criterion1, 1);

        modelAnd.filterAnd(null, null, null, null, null,
                null, null, criterion1[6], null, null);
        modelOr.filterOr(null, null, null, null, null,
                null, null, criterion1[6], null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // Filter Level 2: ALICE, BENSON will be remained
        String[] criterion2 = {null, null, null, null, "C++", null, null, null, null, null};
        commandAnd = new FilterCommand(criterion2, 2);
        commandOr = new FilterCommand(criterion2, 1);

        modelAnd.filterOr(null, null, null, null, skillArr,
                null, null, null, null, null);
        modelOr.filterAnd(null, null, null, null, skillArr,
                null, null, null, null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // Filter Level 3: ALICE will be remained
        String[] criterion3 = {null, null, "alice", null, null, null, null, null, null, null};
        commandAnd = new FilterCommand(criterion3, 2);
        commandOr = new FilterCommand(criterion3, 1);

        modelAnd.filterAnd(null, null, criterion3[2], null, null,
                null, null, null, null, null);
        modelOr.filterOr(null, null, criterion3[2], null, null,
                null, null, null, null, null);

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);

        // Filter Reverse Level: All persons will be restored excluding ALICE
        String[] criterionReverse = {null, null, null, null, null, null, null, null, null, null};
        FilterCommand command = new FilterCommand(criterionReverse, 3);

        modelAnd.reverseFilter();
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr.reverseFilter();
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, modelAnd);
        assertCommandSuccess(command, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_REVERSE_SUCCESS, modelOr);
    }

    @Test
    public void execute_multipleConditionsAtOneFilter_success() {
        String[] criterion = {"a", "5", "n", null, null, null, null, null, null, null};
        FilterCommand commandAnd = new FilterCommand(criterion, 2);
        FilterCommand commandOr = new FilterCommand(criterion, 1);

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(criterion[0], criterion[1], criterion[2], null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(CARL, DANIEL), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(criterion[0], criterion[1], criterion[2], null, null,
                null, null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());

        modelAnd.commitAddressBook();
        modelOr.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);
        assertCommandSuccess(commandOr, model2, commandHistory2,
                FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);
    }

    @Test
    public void execute_allConditionsAtOneFilter_success() {

        String[] criterion = {"a", "5", "n", "street", "C++", "Developer", "2.5", "nus", "2", "2"};
        String[] skillArr = {"C++"};
        String[] posArr = {"Developer"};

        FilterCommand commandAnd = new FilterCommand(criterion, 2);
        FilterCommand commandOr = new FilterCommand(criterion, 1);

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(criterion[0], criterion[1], criterion[2], criterion[3], skillArr,
                posArr, criterion[8], criterion[6], criterion[7], criterion[8]);
        assertEquals(Arrays.asList(DANIEL), modelAnd.getFilteredPersonList());
        modelAnd.commitAddressBook();
        assertCommandSuccess(commandAnd, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelAnd);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(criterion[0], criterion[1], criterion[2], criterion[3], skillArr,
                posArr, criterion[8], criterion[6], criterion[7], criterion[8]);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), modelOr.getFilteredPersonList());
        modelOr.commitAddressBook();
        assertCommandSuccess(commandOr, model, commandHistory, FilterCommand.MESSAGE_FILTER_PERSON_SUCCESS, modelOr);
    }

    @Test
    public void execute_filterClearWhenNoFilterActive_failure() {
        String[] criterion = {null, null, null, null, null, null, null, null, null, null};
        FilterCommand command = new FilterCommand(criterion, 0);

        assertCommandFailure(command, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_CLEAR);
    }

    @Test
    public void execute_filterReverseWhenNoFilterActive_failure() {
        String[] criterion = {null, null, null, null, null, null, null, null, null, null};
        FilterCommand command = new FilterCommand(criterion, 3);

        assertCommandFailure(command, model, commandHistory, FilterCommand.MESSAGE_NO_FILTER_TO_REVERSE);
    }
}
