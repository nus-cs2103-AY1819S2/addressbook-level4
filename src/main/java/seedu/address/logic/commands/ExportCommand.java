package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Exports records to a text file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports specific patients by index to .json or .pdf file in the \"data\" folder, "
            + "overwriting if filename exists \n"
            + "Parameters: FILENAME [INDEX_RANGE(must be a positive integer) OR all]\n"
            + "Example: " + COMMAND_WORD + " records1.json + 1-5"
            + "Example: " + COMMAND_WORD + " records1.pdf + 1,3,5"
            + "Example: " + COMMAND_WORD + " records1.json + 1,3-5"
            + "Example: " + COMMAND_WORD + " records1.pdf + all";

    public static final String MESSAGE_SUCCESS = "Exported the records!";

    private final ParsedInOut parsedInput;

    public ExportCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (parsedInput.getArgIsAll()) {
            new SaveCommand(parsedInput).execute(model, history);
        } else {
            try {
                writeFile(createTempAddressBook(model, parsedInput.getParsedIndex()));
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
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
                storage.saveAddressBook(model.getAddressBook());
            } else if (parsedInput.getType().equals("pdf")) {
                storage.saveAsPdf(model.getAddressBook());
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * createTempAddressBook() creates a temporary address book populated with the specified patients in parsedInput[1]
     * @param model the model which contains the current address book.
     * @param parsedIndex the hashset containing the indexes requested by the user.
     * @return A temporary model
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
