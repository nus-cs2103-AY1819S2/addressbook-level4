package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.ParsedInOut;

/**
 * Exports data to a text file.
 */
public class ExportCommand extends OutCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the specified patients by index to .json or .pdf file in the \"data\" folder, "
            + "overwriting if filename exists \n"
            + "Parameters: FILEPATH [INDEX_RANGE(must be a positive integer) OR all]\n"
            + "Example: " + COMMAND_WORD + " folder/data1.json + 1-5\n"
            + "Example: " + COMMAND_WORD + " folder/data1.pdf + 1,3,5\n"
            + "Example: " + COMMAND_WORD + " data1.json + 1,3-5\n"
            + "Example: " + COMMAND_WORD + " data1.pdf + all";

    public static final String MESSAGE_SUCCESS = " exported!";

    public ExportCommand(ParsedInOut parsedInput) {
        super(parsedInput);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (parsedInput.getArgIsAll()) {
            new SaveCommand(parsedInput).execute(model, history);
        } else {
            try {
                fileValidation(parsedInput.getFile());
                writeFile(createTempAddressBook(model, parsedInput.getParsedIndex()));
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(parsedInput.getFile() + MESSAGE_SUCCESS);
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
        tempModel.setPatientList(new ArrayList<>());

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            if (parsedIndex.contains(i)) {
                tempModel.addPerson(model.getFilteredPersonList().get(i));
            }
        }

        return tempModel;
    }

    /**
     * For ExportCommandParserTest.
     * @return ParsedInOut
     */
    public ParsedInOut getParsedInOut() {
        return parsedInput;
    }
}
