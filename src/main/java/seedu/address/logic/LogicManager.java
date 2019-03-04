package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.RestOrRantParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final RestOrRantParser restOrRantParser;
    private boolean addressBookModified;
    private boolean modeModified;
    private boolean ordersModified;
    private Mode mode;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        restOrRantParser = new RestOrRantParser();
        mode = Mode.RESTAURANT_MODE;

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getRestOrRant().addListener(observable -> addressBookModified = true);
        // Set modeModified to true whenever the models' mode is modified.
        model.getRestOrRant().addListener(observable -> modeModified = true);
        // Set ordersModified to true whenever the models' RestOrRant's orders is modified.
        model.getRestOrRant().getOrders().addListener(observable -> ordersModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;
        modeModified = false;
        ordersModified = false;

        CommandResult commandResult;
        try {
            Command command = restOrRantParser.parseCommand(mode, commandText);
            commandResult = command.execute(mode, model, history);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveRestOrRant(model.getRestOrRant());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (modeModified) {
            logger.info("Application mode modified, changing UI");
        }

        if (ordersModified) {
            logger.info("Orders modified, saving to file.");
            try {
                storage.saveOrders(model.getRestOrRant().getOrders());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public void changeMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public ReadOnlyRestOrRant getRestOrRant() {
        return model.getRestOrRant();
    }

    @Override
    public ObservableList<OrderItem> getFilteredOrderItemList() {
        return model.getFilteredOrderItemList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getOrdersFilePath() {
        return model.getOrdersFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<OrderItem> selectedOrderItemProperty() {
        return model.selectedOrderItemProperty();
    }

    @Override
    public void setSelectedOrderItem(OrderItem orderItem) {
        model.setSelectedOrderItem(orderItem);
    }
}
