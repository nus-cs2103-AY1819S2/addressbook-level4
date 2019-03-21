package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Saves records to a text file.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the current patients to a .json or .pdf file in the \"data\" folder, "
            + "overwriting if filename exists \n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + " records1.json"
            + "Example: " + COMMAND_WORD + " records1.pdf";

    public static final String MESSAGE_SUCCESS = "Saved the records!";
    private static final String MESSAGE_FAILURE = "Problem while writing to the file.";

    private final ParsedInOut parsedInOut;

    public SaveCommand(ParsedInOut parsedInOut) {
        this.parsedInOut = parsedInOut;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        writeFile(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * writeFile() writes or overwrites a file with the contents of the current address book.
     */
    private void writeFile(Model model) {
        AddressBookStorage addressBookStorage = new InOutAddressBookStorage(parsedInOut.getFile().toPath());

        StorageManager storage = new StorageManager(addressBookStorage, null);

        final Logger logger = LogsCenter.getLogger(MainApp.class);

        try {
            storage.saveAddressBook(model.getAddressBook(), parsedInOut.getFile().toPath());
        } catch (IOException e) {
            logger.warning(MESSAGE_FAILURE);
        }
    }
}
