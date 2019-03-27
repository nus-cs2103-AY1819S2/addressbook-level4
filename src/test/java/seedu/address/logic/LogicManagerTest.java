package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.AddToMenuCommand;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.commands.BillCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Table;
import seedu.address.storage.JsonMenuStorage;
import seedu.address.storage.JsonOrdersStorage;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonTablesStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.StatisticsBuilder;
import seedu.address.testutil.TableBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonTablesStorage jsonTablesStorage = new JsonTablesStorage(temporaryFolder.newFile().toPath());
        JsonOrdersStorage jsonOrdersStorage = new JsonOrdersStorage(temporaryFolder.newFile().toPath());
        JsonMenuStorage jsonMenuStorage = new JsonMenuStorage(temporaryFolder.newFile().toPath());
        JsonStatisticsStorage jsonStatisticsStorage = new JsonStatisticsStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, jsonOrdersStorage, jsonMenuStorage,
                jsonStatisticsStorage, jsonTablesStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    //    @Test TODO: future use
    //    public void execute_commandExecutionError_throwsCommandException() {
    //        String deleteCommand = "delete 9";
    //        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //        assertHistoryCorrect(deleteCommand);
    //    }

    @Test
    public void execute_validCommand_success() {
        String helpCommand = HelpCommand.COMMAND_WORD;
        assertCommandSuccess(helpCommand, HelpCommand.SHOWING_HELP_MESSAGE, model);
        assertHistoryCorrect(helpCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonRestOrRantIoExceptionThrowingStub
        //        JsonRestOrRantStorage jsonRestOrRantStorage =
        //                new JsonRestOrRantIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonOrdersStorage jsonOrdersStorage =
                new JsonOrdersIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonMenuStorage jsonMenuStorage = new JsonMenuIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonStatisticsStorage jsonStatisticsStorage =
                new JsonStatisticsIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonTablesStorage jsonTablesStorage =
                new JsonTablesIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, jsonOrdersStorage, jsonMenuStorage,
                jsonStatisticsStorage, jsonTablesStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        //        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
        //                + ADDRESS_DESC_AMY;
        //        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        //        expectedModel.addPerson(expectedPerson);
        //        expectedModel.updateMode();
        //        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        //        assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);
        //        assertHistoryCorrect(addCommand);

        // Execute addTable command
        String addTableCommand = AddTableCommand.COMMAND_WORD + " 4";
        String addToMenuCommand = AddToMenuCommand.COMMAND_WORD + " n/Chicken Wings c/W09 p/3.99";
        String addToOrderCommand = AddToOrderCommand.COMMAND_WORD + " W09 3";
        String billCommand = BillCommand.COMMAND_WORD;
        Table expectedTable = new TableBuilder().build();
        Table occupiedTable = new TableBuilder().withTableStatus("3/4").build();
        OrderItem expectedOrderItem = new OrderItemBuilder().build();
        MenuItem expectedMenuItem = new MenuItemBuilder().build();
        DailyRevenue expectedDailyRevenue = new StatisticsBuilder()
                .withDay("26")
                .withMonth("03")
                .withYear("2019")
                .withTotalBill("11.97")
                .withReceipt("Table 1\n\nW09  Chicken Wings\n $3.99   x 3\n\nTotal Bill: $ 11.97\n\n")
                .build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTable(expectedTable);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addTableCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addTableCommand);

        // Execute addToMenu command
        logic.changeMode(Mode.MENU_MODE);
        expectedModel.addMenuItem(expectedMenuItem);
        assertCommandBehavior(CommandException.class, addToMenuCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addToMenuCommand + "\n" + HistoryCommand.COMMAND_WORD + "\n"
                + addTableCommand);

        // Execute addToOrder command
        logic.changeMode(Mode.TABLE_MODE);
        model.setSelectedTable(expectedTable);
        expectedModel.setSelectedTable(expectedTable);
        model.setTable(expectedTable, occupiedTable);
        expectedModel.setTable(expectedTable, occupiedTable);
        expectedModel.addOrderItem(expectedOrderItem);
        assertCommandBehavior(CommandException.class, addToOrderCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addToOrderCommand + "\n" + HistoryCommand.COMMAND_WORD + "\n"
                + addToMenuCommand + "\n" + HistoryCommand.COMMAND_WORD + "\n" + addTableCommand);

        // Execute addDailyRevenue command
        expectedModel.addDailyRevenue(expectedDailyRevenue);
        expectedModel.deleteOrderItem(expectedOrderItem);
        expectedModel.setSelectedTable(null);
        expectedModel.setTable(occupiedTable, expectedTable);
        assertCommandBehavior(CommandException.class, billCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(billCommand + "\n" + HistoryCommand.COMMAND_WORD + "\n"
                + addToOrderCommand + "\n" + HistoryCommand.COMMAND_WORD + "\n" + addToMenuCommand + "\n"
                + HistoryCommand.COMMAND_WORD + "\n" + addTableCommand);
    }

    @Test
    public void getFilteredOrderItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredOrderItemList().remove(0);
    }

    @Test
    public void getFilteredMenuItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredMenuItemList().remove(0);
    }

    @Test
    public void getFilteredTableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredTableList().remove(0);
    }

    @Test
    public void getFilteredDailyRevenueList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredDailyRevenueList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getRestOrRant(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s data was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonOrdersIoExceptionThrowingStub extends JsonOrdersStorage {
        private JsonOrdersIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveOrders(ReadOnlyOrders orders, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonMenuIoExceptionThrowingStub extends JsonMenuStorage {
        private JsonMenuIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonStatisticsIoExceptionThrowingStub extends JsonStatisticsStorage {
        private JsonStatisticsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveStatistics(ReadOnlyStatistics statistics, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonTablesIoExceptionThrowingStub extends JsonTablesStorage {
        private JsonTablesIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTables(ReadOnlyTables tables, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
