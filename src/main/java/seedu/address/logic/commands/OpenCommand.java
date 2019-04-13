package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;

/**
 * Opens data from a text file.
 */
public class OpenCommand extends InCommand {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens a .json file in the \"data\" folder and overwrites the current data\n"
            + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " data1.json";

    public static final String MESSAGE_SUCCESS = "File opened!";

    private final ParsedInOut parsedInput;

    public OpenCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    /**
     * readFile() overwrites the current address book with the contents of the file.
     */
    @Override
    protected String readFile(Model model) {
        try {
            fileValidation(parsedInput);
        } catch (IOException e) {
            return e.getMessage();
        }

        AddressBookStorage openStorage = new InOutAddressBookStorage(parsedInput.getFile().toPath());

        final Logger logger = LogsCenter.getLogger(MainApp.class);

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook openData;

        try {
            addressBookOptional = openStorage.readAddressBook();
            // This should not happen after OpenCommandParser checks for file existence.
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            openData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            model.setAddressBook(openData);
            return MESSAGE_SUCCESS;
        } catch (DataConversionException e) {
            return "Data file is not in the correct format.";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /**
     * For OpenCommandParserTest.
     * @return file
     */
    public File getFile() {
        return parsedInput.getFile();
    }
}
