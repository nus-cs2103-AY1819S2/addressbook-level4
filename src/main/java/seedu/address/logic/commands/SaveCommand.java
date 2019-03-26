package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
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

    private final ParsedInOut parsedInput;

    public SaveCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            writeFile(model);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * writeFile() writes or overwrites a file with the contents of the current address book.
     */
    private void writeFile(Model model) throws IOException {
        AddressBookStorage addressBookStorage = new InOutAddressBookStorage(parsedInput.getFile().toPath());

        StorageManager storage = new StorageManager(addressBookStorage, null);

        try {
            if (parsedInput.getType().equals("json")) {
                storage.saveAddressBook(model.getAddressBook(), parsedInput.getFile().toPath());
            } else if (parsedInput.getType().equals("pdf")) {
                storage.saveAsPdf(model.getAddressBook(), parsedInput.getFile().toPath());
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
