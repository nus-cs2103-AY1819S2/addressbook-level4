package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
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

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *Contains unit tests for FilterCommand.
 */
public class FilterCommandTest {

    private Model modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Explanation of process numbers:
     * 0 -> clear
     * 1 -> or
     * 2 -> and

     * Indexes in the criterion array:
     * 1- Name
     * 2- Phone
     * 3- Email
     * 4- Address
     * 5- Skills
     * 6- Positions
     * 7- Gpa
     * 8- Education
     */

    @Test
    public void execute_oneConditionAtOneFilter() {

        // name condition - 2 peoples are listed: DANIEL, ELLE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd("el", null, null, null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr("el", null, null, null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(DANIEL, ELLE), modelOr.getFilteredPersonList());


        // phone condition - 1 person listed: ALICE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, "9435", null, null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, "9435", null, null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE), modelOr.getFilteredPersonList());


        // email condition - 7 people are listed: ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, "@example.com", null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, "@example.com", null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());

        // address condition - 0 persons are listed
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, "UTown Centre", null,
                null, null, null, null);
        assertEquals(Collections.emptyList(), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, "UTown Centre", null,
                null, null, null, null);
        assertEquals(Collections.emptyList(), modelOr.getFilteredPersonList());

        // skills tag condition - 3 persons are listed: ALICE, BENSON, DANIEL
        String[] skillArr = {"friends"};
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, skillArr,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, skillArr,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), modelOr.getFilteredPersonList());

        // multiple skills tags together condition
        String[] skillMultipleInfoArr = {"friends", "owesmoney"};

        // and condition - 1 person is listed: BENSON
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, skillMultipleInfoArr,
                null, null, null, null);
        assertEquals(Arrays.asList(BENSON), modelAnd.getFilteredPersonList());

        // or condition - 3 persons are listed: ALICE, BENSON, DANIEL
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, skillMultipleInfoArr,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), modelOr.getFilteredPersonList());

        // TODO: Add Positions and Endorsement conditions after updating the person data

        // gpa condition - 1 person listed: FIONA
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, "4.0", null);
        assertEquals(Arrays.asList(FIONA), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                null, null, "4.0", null);
        assertEquals(Arrays.asList(FIONA), modelOr.getFilteredPersonList());

        // education condition - 2 people are listed: FIONA, ELLE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, null, "harvard");
        assertEquals(Arrays.asList(ELLE, FIONA), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr(null, null, null, null, null,
                null, null, null, "harvard");
        assertEquals(Arrays.asList(ELLE, FIONA), modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_filterClearAtOneFilterActive() {

        // name condition - 2 peoples are listed: DANIEL, ELLE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd("el", null, null, null, null,
                null, null, null, null);
        // filter cleared, all persons will be shown
        modelAnd.clearFilter();
        assertEquals(Arrays.asList(DANIEL, ELLE, ALICE, BENSON, CARL, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr("el", null, null, null, null,
                null, null, null, null);
        // filter cleared, all persons will be shown
        modelOr.clearFilter();
        assertEquals(Arrays.asList(DANIEL, ELLE, ALICE, BENSON, CARL, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_filterReverseAtOneFilterActive() {

        // name condition - 2 peoples are listed: DANIEL, ELLE
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd("el", null, null, null, null,
                null, null, null, null);

        // filter reversed, all persons will be shown except DANIEL and ELLE
        modelAnd.reverseFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr("el", null, null, null, null,
                null, null, null, null);

        // filter reversed, all persons will be shown except DANIEL and ELLE
        modelOr.reverseFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_filterClearAtMultipleFiltersActive() {

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String[] skillArr = {"friends"};

        // Filter Level 1: ALICE, BENSON, CARL, FIONA will be remained
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, "2.8", null);
        modelOr.filterOr(null, null, null, null, null,
                null, null, "2.8", null);

        // Filter Level 2: ALICE, BENSON will be remained
        modelAnd.filterOr(null, null, null, null, skillArr,
                null, null, null, null);
        modelOr.filterAnd(null, null, null, null, skillArr,
                null, null, null, null);


        // Filter Level 3: ALICE will be remained
        modelAnd.filterAnd(null, null, "alice", null, null,
                null, null, null, null);
        modelOr.filterOr(null, null, "alice", null, null,
                null, null, null, null);

        // Filter Clear Level: All persons will be restored
        modelAnd.clearFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr.clearFilter();
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_filterReverseAtMultipleFiltersActive() {

        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String[] skillArr = {"friends"};

        // Filter Level 1: ALICE, BENSON, CARL, FIONA will be remained
        modelAnd.filterAnd(null, null, null, null, null,
                null, null, "2.8", null);
        modelOr.filterOr(null, null, null, null, null,
                null, null, "2.8", null);

        // Filter Level 2: ALICE, BENSON will be remained
        modelAnd.filterOr(null, null, null, null, skillArr,
                null, null, null, null);
        modelOr.filterAnd(null, null, null, null, skillArr,
                null, null, null, null);


        // Filter Level 3: ALICE will be remained
        modelAnd.filterAnd(null, null, "alice", null, null,
                null, null, null, null);
        modelOr.filterOr(null, null, "alice", null, null,
                null, null, null, null);

        // Filter Reverse Level: All persons will be restored excluding ALICE
        modelAnd.reverseFilter();
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelAnd.getFilteredPersonList());

        modelOr.reverseFilter();
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_multipleConditionsAtOneFilter() {
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd("a", "5", "n", null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(CARL, DANIEL), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr("a", "5", "n", null, null,
                null, null, null, null);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                modelOr.getFilteredPersonList());
    }

    @Test
    public void execute_allConditionsAtOneFilter() {
        String[] skillArr = {"friends"};

        // TODO: include also endorsement and positions data here
        modelAnd = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelAnd.filterAnd("a", "5", "n", "street", skillArr,
                null, null, "2.5", "nus");
        assertEquals(Arrays.asList(DANIEL), modelAnd.getFilteredPersonList());

        modelOr = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        modelOr.filterOr("a", "5", "l", "street", skillArr,
                skillArr, null, "3.2", "NTU");
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), modelOr.getFilteredPersonList());
    }
}
