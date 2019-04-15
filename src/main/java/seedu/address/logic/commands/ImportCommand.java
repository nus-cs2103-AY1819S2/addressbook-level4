package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.model.Model;

/**
 * Appends a data file containing flashcards to the current data file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds flashcards from a text file to the existing "
        + "collection. \n"
        + "Parameters: "
        + "FILE_PATH\n";

    public static final String MESSAGE_IMPORT_SUCCESS = " flashcards successfully imported from files.";
    public static final String MESSAGE_IMPORT_PARTIAL_SUCCESS_INFO = "*Unsuccessfully imported flashcards may "
            + "have already existed locally OR may be corrupted*";
    public static final String MESSAGE_IMPORT_ERROR = "Unable to import flashcards from ";


    private final Logger logger = LogsCenter.getLogger(getClass());

    private final File toImport;

    /**
     * Creates an ImportCommand to upload the specified flashcards from {@code File}
     */
    public ImportCommand(File toImport) {
        requireNonNull(toImport);
        this.toImport = toImport;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Pair<Integer, Integer> result = importCards(model, history);
        int numSuccessfullyImported = result.getKey();
        int totalFlashcardsRead = result.getValue();
        if (numSuccessfullyImported == totalFlashcardsRead) {
            return new CommandResult("ALL" + MESSAGE_IMPORT_SUCCESS);
        } else {
            String numSuccessful = numSuccessfullyImported + " out of " + totalFlashcardsRead;
            return new CommandResult(String.format(numSuccessful + MESSAGE_IMPORT_SUCCESS + " %s",
                    toImport.getAbsolutePath() + "\n" + MESSAGE_IMPORT_PARTIAL_SUCCESS_INFO));
        }
    }

    /**
     * Reads flashcard declarations from {@code File} and uses the {@code AddCommand} to
     * import these cards locally.
     * Returns the number of flashcards successfully imported and the total number of flashcards read
     */
    private Pair importCards(Model model, CommandHistory history) throws CommandException {
        int numSuccessfullyImported = 0;
        int totalFlashcardsRead = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(toImport));
            String flashcardToAdd;
            while ((flashcardToAdd = br.readLine()) != null) {
                totalFlashcardsRead++;
                AddCommandParser parser = new AddCommandParser();
                try {
                    AddCommand command = parser.parse(flashcardToAdd);
                    command.execute(model, history);
                    numSuccessfullyImported++;
                } catch (Exception e) {
                    logger.info("Exception from adding flashcard using import command");
                }
            }
        } catch (IOException ioe) {
            logger.warning("IO failure reading from file " + toImport.getName());
            throw new CommandException(String.format(MESSAGE_IMPORT_ERROR, toImport.getAbsolutePath()));
        }

        return new Pair(numSuccessfullyImported, totalFlashcardsRead);
    }

}
