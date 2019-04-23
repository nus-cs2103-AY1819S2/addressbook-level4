//package seedu.address.logic.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import seedu.address.logic.CommandHistory;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
//import seedu.address.testutil.PersonBuilder;
//
///**
// * Contains integration tests (interaction with the Model) for {@code AddPersonCommand}.
// */
//public class AddPersonCommandIntegrationTest {
//
//    private Model model;
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Before
//    public void setUp() {
//        model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
//                getTypicalRequestBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_newPerson_success() {
//        Person validPerson = new PersonBuilder().build();
//
//        Model expectedModel = new ModelManager(model.getAddressBook(),
//            model.getHealthWorkerBook(), model.getRequestBook(), new UserPrefs());
//        expectedModel.addPerson(validPerson);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(new AddPersonCommand(validPerson), model, commandHistory,
//                String.format(AddPersonCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Person personInList = model.getAddressBook().getPersonList().get(0);
//        assertCommandFailure(new AddPersonCommand(personInList), model, commandHistory,
//                AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//}
