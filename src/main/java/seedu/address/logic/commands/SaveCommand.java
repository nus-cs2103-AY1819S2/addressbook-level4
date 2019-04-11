package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Saves data to a text file.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the current data to a .json or .pdf file in the \"data\" folder, "
            + "overwriting if file path exists \n"
            + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " data1.json\n"
            + "Example: " + COMMAND_WORD + " folder/data1.pdf";

    public static final String MESSAGE_SUCCESS = " saved!";

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
        return new CommandResult(parsedInput.getFile() + MESSAGE_SUCCESS);
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

    /**
     * For SaveCommandParserTest.
     * @return file
     */
    public File getFile() {
        return parsedInput.getFile();
    }
}
