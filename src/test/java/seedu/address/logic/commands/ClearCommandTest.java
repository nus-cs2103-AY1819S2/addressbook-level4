//package seedu.address.logic.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;
//
//import org.junit.Test;
//
//import seedu.address.logic.CommandHistory;
//import seedu.address.model.AddressBook;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//
//public class ClearCommandTest {
//
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void execute_emptyAddressBook_success() {
//        Model model = new ModelManager();
//        Model expectedModel = new ModelManager();
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_nonEmptyAddressBook_success() {
//        Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
//                getTypicalRequestBook(), new UserPrefs());
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
//                getTypicalRequestBook(), new UserPrefs());
//        expectedModel.setAddressBook(new AddressBook());
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//}
