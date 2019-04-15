package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;

/**
 * Creates a text file consisting of a set of flashcards which contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";
    public static final String FILE_NAME = "flashcards.txt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a text file containing flashcards currently "
            + "being listed to a user defined directory.\n"
            + "Parameters: "
            + "DIRECTORY_PATH (optional - leaving path empty will prompt the File Explorer)";

    public static final String MESSAGE_SHARE_SUCCESS = "Successfully created ";
    public static final String MESSAGE_SHARE_FAILURE = "Could not create file at %1$s";
    private static final String MESSAGE_IN_QUIZ = "Cannot share in quiz mode.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private String path;

    public ShareCommand (String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }
        List<Flashcard> flashcardsToShare = model.getFilteredFlashcardList();
        String fileName = Paths.get(path).resolve(FILE_NAME).toFile().toString();
        boolean isSuccessful = generateFile(flashcardsToShare, fileName);
        if (isSuccessful) {
            return new CommandResult(MESSAGE_SHARE_SUCCESS + fileName);
        } else {
            throw new CommandException(String.format(MESSAGE_SHARE_FAILURE, path));
        }
    }

    /**
     * Creates a text file with the details of {@code flashcardsToShare}
     */
    private boolean generateFile(List<Flashcard> flashcardsToShare, String fileName) {
        final boolean isSuccessful = true;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            StringBuilder lineToAdd;
            for (Flashcard flashcard : flashcardsToShare) {
                lineToAdd = new StringBuilder(" ");

                //Front Face
                lineToAdd.append(PREFIX_FRONT_FACE);
                lineToAdd.append(flashcard.getFrontFace().text);
                lineToAdd.append(" ");

                //Back Face
                lineToAdd.append(PREFIX_BACK_FACE);
                lineToAdd.append(flashcard.getBackFace().text);
                lineToAdd.append(" ");

                //Tag
                for (Tag tag : flashcard.getTags()) {
                    lineToAdd.append(PREFIX_TAG);
                    lineToAdd.append(tag.tagName);
                    lineToAdd.append(" ");
                }

                //Remove last char
                lineToAdd.setLength(lineToAdd.length() - 1);
                lineToAdd.append(System.getProperty("line.separator"));

                bw.write(lineToAdd.toString());
            }

        } catch (IOException e) {
            logger.warning("IO failure creating file " + FILE_NAME);
            return !isSuccessful;
        }
        return isSuccessful;
    }
}
