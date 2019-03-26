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
import seedu.address.logic.parser.BookShelfParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
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
    private final BookShelfParser bookShelfParser;
    private boolean bookShelfModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        bookShelfParser = new BookShelfParser();

        // Set addressBookModified to true whenever the models' address book is modified.
        model.getBookShelf().addListener(observable -> bookShelfModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        bookShelfModified = false;

        CommandResult commandResult;
        try {
            Command command = bookShelfParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (bookShelfModified) {
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveBookShelf(model.getBookShelf());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBookShelf getAddressBook() {
        return model.getBookShelf();
    }

    @Override
    public ReadOnlyBookShelf getBookShelf() {
        return model.getBookShelf();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public ObservableList<Review> getFilteredReviewList() {
        return model.getFilteredReviewList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getBookShelfFilePath();
    }

    @Override
    public Path getBookShelfFilePath() {
        return model.getBookShelfFilePath();
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
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public ReadOnlyProperty<Book> selectedBookProperty() {
        return model.selectedBookProperty();
    }

    @Override
    public ReadOnlyProperty<Review> selectedReviewProperty() {
        return model.selectedReviewProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }

    @Override
    public void setSelectedBook(Book book) {
        model.setSelectedBook(book);
    }

    @Override
    public void setSelectedReview(Review review) {
        model.setSelectedReview(review);
    }
}
