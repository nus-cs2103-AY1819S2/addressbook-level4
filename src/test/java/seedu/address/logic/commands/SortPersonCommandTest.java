package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;


import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.logic.commands.PersonComparator;
import seedu.address.model.person.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class SortPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortPerson_sequence() throws ParseException {
        SetUp("name", "seq", Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        SetUp("salary", "seq", Arrays.asList(GEORGE, CARL, FIONA, BENSON, ELLE, ALICE, DANIEL));

    }

    @Test
    public void execute_sortPerson_reverse() throws ParseException {
        SetUp("name", "rev", Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
        SetUp("salary", "rev", Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }



    private void SetUp(String parameter, String sequence , List<Person> expectedResult) throws ParseException {

        Comparator<Person> function = PersonComparator.GetFunction(parameter);
        SortPersonCommand commandResult = new SortPersonCommand(function, parameter, sequence);
        expectedModel.sortPerson(function, sequence);
        assertCommandSuccess(commandResult, model, commandHistory, SortPersonCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(expectedResult, model.getFilteredPersonList());
    }
}
