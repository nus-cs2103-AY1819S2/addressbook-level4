package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
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
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;
import seedu.address.storage.StorageManager;

/**
 * Imports data to a text file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the specified patients by index from a .json file in the \"data\" folder, "
            + "and appends them to the current list. \n"
            + "Parameters: FILEPATH [INDEX_RANGE(must be a positive integer) OR all]\n"
            + "Example: " + COMMAND_WORD + " folder/data1.json + 1-5\n"
            + "Example: " + COMMAND_WORD + " folder/data1.json + 1,3,5\n"
            + "Example: " + COMMAND_WORD + " data1.json + 1,3-5\n"
            + "Example: " + COMMAND_WORD + " data1.json + all";

    public static final String MESSAGE_SUCCESS = "File imported!";

    private final ParsedInOut parsedInput;

    public ImportCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String result = readFile(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(result);
    }

    /**
     * readFile() appends the current address book with the contents of the file.
     */
    private String readFile(Model model) {
        AddressBookStorage importStorage = new InOutAddressBookStorage(parsedInput.getFile().toPath());

        StorageManager importStorageManager = new StorageManager(importStorage, null);

        final Logger logger = LogsCenter.getLogger(MainApp.class);

        Optional<ReadOnlyAddressBook> importOptional;
        ReadOnlyAddressBook importData;

        try {
            importOptional = importStorageManager.readAddressBook();
            // This should not happen after OpenCommandParser checks for file existence.
            if (!importOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            importData = importOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            return "Data file not in the correct format.";
        } catch (IOException e) {
            return "Problem while reading from the file.";
        }

        for (int i = 0; i < importData.getPersonList().size(); i++) {
            if (!model.hasPerson(importData.getPersonList().get(i))
                && (parsedInput.getArgIsAll() || parsedInput.getParsedIndex().contains(i))) {
                model.addPerson(importData.getPersonList().get(i));
            }
        }
        return MESSAGE_SUCCESS;
    }
}
