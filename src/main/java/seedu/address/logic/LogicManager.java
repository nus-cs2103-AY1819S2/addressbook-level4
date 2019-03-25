package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPdfBook;
import seedu.address.model.pdf.Pdf;
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
    private final AddressBookParser addressBookParser;
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getPdfBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;

        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (addressBookModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.savePdfBook(model.getPdfBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPdfBook getAddressBook() {
        return model.getPdfBook();
    }

    @Override
    public ObservableList<Pdf> getFilteredPersonList() {
        return model.getFilteredPdfList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getPdfBookFilePath();
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
    public ReadOnlyProperty<Pdf> selectedPersonProperty() {
        return model.selectedPdfProperty();
    }

    @Override
    public void setSelectedPerson(Pdf pdf) {
        model.setSelectedPdf(pdf);
    }

    @Override
    public List<Pdf> getDuePdfs() {
        ArrayList<Pdf> list = new ArrayList<>();
        PriorityQueue<Pdf> pq = new PriorityQueue<>(new Comparator<Pdf>() {
            @Override
            public int compare(Pdf o1, Pdf o2) {
                return Long.compare(o1.getDeadline().getDaysToDeadline(), o2.getDeadline().getDaysToDeadline());
            }
        });

        for (Pdf pdf : this.model.getPdfBook().getPdfList()) {
            if (pdf.getDeadline().exists() && !pdf.getDeadline().isDone()) {
                pq.add(pdf);
            }
        }

        while (list.size() < 4 && pq.size() > 0) {
            list.add(pq.poll());
        }

        return list;
    }
}
