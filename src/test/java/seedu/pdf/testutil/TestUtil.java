package seedu.pdf.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.model.Model;
import seedu.pdf.model.pdf.Pdf;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder value and returns the resulting value.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the pdf in the {@code model}'s pdf list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPdfList().size() / 2);
    }

    /**
     * Returns the last index of the pdf in the {@code model}'s pdf list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPdfList().size());
    }

    /**
     * Returns the pdf in the {@code model}'s pdf list at {@code index}.
     */
    public static Pdf getPdf(Model model, Index index) {
        return model.getFilteredPdfList().get(index.getZeroBased());
    }
}
