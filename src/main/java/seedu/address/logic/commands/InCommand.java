package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.ParsedInOut;

/**
 * Contains methods used by commands that read.
 */
public abstract class InCommand extends Command {

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            String result = readFile(model);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(result);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * readFile() overwrites the current address book with the contents of the file.
     */
    protected abstract String readFile(Model model) throws IOException;

    /**
     * fileValidation() checks if the file exists, is a file and can be read.
     * @param parsedInOut the ParsedInOut object which contains parsed information from the input.
     * @throws IOException if the file is not a .json type
     *                        if the file does not exist
     *                        if the file is not a file
     *                        if the file cannot be read
     */
    void fileValidation(ParsedInOut parsedInOut) throws IOException {
        if (!parsedInOut.getType().equals("json")) {
            throw new IOException("Only .json file type can be opened!");
        } else {
            if (!parsedInOut.getFile().exists()) {
                throw new IOException("File not found!");
            } else if (!parsedInOut.getFile().isFile()) {
                throw new IOException("File is invalid!");
            } else if (!parsedInOut.getFile().canRead()) {
                throw new IOException("File cannot be read!");
            }
        }
    }
}
