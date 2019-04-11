package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InOutAddressBookStorage;
import seedu.address.storage.ParsedInOut;

/**
 * Contains methods used by commands that write.
 */
abstract class OutCommand extends Command {

    final ParsedInOut parsedInput;

    OutCommand(ParsedInOut parsedInput) {
        this.parsedInput = parsedInput;
    }

    /**
     * writeFile() writes or overwrites a file with the contents of the current address book.
     */
    void writeFile(Model model) throws IOException {
        AddressBookStorage addressBookStorage = new InOutAddressBookStorage(parsedInput.getFile().toPath());

        try {
            if (parsedInput.getType().equals("json")) {
                addressBookStorage.saveAddressBook(model.getAddressBook());
            } else if (parsedInput.getType().equals("pdf")) {
                addressBookStorage.saveAsPdf(model.getAddressBook());
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * fileValidation() checks if the file is writable if it exists.
     * @param file the file to be saved to.
     * @throws IOException if the user is trying to write to a read only file
     */
    void fileValidation(File file) throws IOException {
        if (file.exists() && !file.canWrite()) {
            throw new IOException("File is read only!");
        }
    }
}
