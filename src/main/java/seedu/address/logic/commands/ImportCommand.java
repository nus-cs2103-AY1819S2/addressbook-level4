package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Imports records to a text file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports specific patients by index to text file in the \"data\" folder, "
            + "overwriting if filename exists \n"
            + "Parameters: FILENAME INDEX_RANGE(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " records1.json + 1-5"
            + "Example: " + COMMAND_WORD + " records1.json + 1,3,5";

    public static final String MESSAGE_SUCCESS = "Imported the records!";

    private final ParsedInOut parsedInput;

    public ImportCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        readFile(model, parsedInput.getParsedIndex());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * readFile() appends the current address book with the contents of the file.
     */
    private void readFile(Model model, HashSet<Integer> parsedIndex) {
        AddressBookStorage importStorage = new JsonAddressBookStorage(parsedInput.getFile().toPath());

        StorageManager importStorageManager = new StorageManager(importStorage, null);

        final Logger logger = LogsCenter.getLogger(MainApp.class);

        Optional<ReadOnlyAddressBook> importOptional;
        ReadOnlyAddressBook importData;

        try {
            importOptional = importStorageManager.readAddressBook();
            if (!importOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            importData = importOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format.");
            importData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file.");
            importData = new AddressBook();
        }

        for (int i = 0; i < importData.getPersonList().size(); i++) {
            if (parsedIndex.contains(i) && !model.hasPerson(importData.getPersonList().get(i))) {
                model.addPerson(importData.getPersonList().get(i));
            }
        }
    }
}
