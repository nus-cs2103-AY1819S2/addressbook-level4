package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Exports records to a text file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports specific patients by index to text file in the \"data\" folder, "
            + "overwriting if filename exists \n"
            + "Parameters: FILENAME INDEX_RANGE(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " records1.json + 1-5"
            + "Example: " + COMMAND_WORD + " records1.json + 1,3,5";

    public static final String MESSAGE_SUCCESS = "Exported the records!";
    private static final String MESSAGE_FAILURE = "Problem while writing to the file.";

    private final ParsedInOut parsedInput;

    public ExportCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        writeFile(createTempAddressBook(model, parsedInput.getParsedIndex()));
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * writeFile() writes or overwrites a file with the contents of the current address book.
     */
    private void writeFile(Model model) {

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(parsedInput.getFile().toPath());

        StorageManager storage = new StorageManager(addressBookStorage, null);

        final Logger logger = LogsCenter.getLogger(MainApp.class);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException e) {
            logger.warning(MESSAGE_FAILURE);
        }
    }

    /**
     * createTempAddressBook() creates a temporary address book populated with the specified patients in parsedInput[1]
     * @param model
     * @param parsedIndex
     * @return
     */
    private ModelManager createTempAddressBook(Model model, HashSet<Integer> parsedIndex) {
        ModelManager tempModel = new ModelManager();

        tempModel.setAddressBook(model.getAddressBook());
        ArrayList<Person> deleteList = new ArrayList<>();

        for (int i = 0; i < tempModel.getFilteredPersonList().size(); i++) {
            if (!parsedIndex.contains(i)) {
                deleteList.add(tempModel.getFilteredPersonList().get(i));
            }
        }

        for (Person personToDelete : deleteList) {
            tempModel.deletePerson(personToDelete);
        }

        return tempModel;
    }
}
