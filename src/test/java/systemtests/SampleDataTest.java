package systemtests;

import static seedu.knowitall.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.util.SampleDataUtil;
import seedu.knowitall.testutil.TestUtil;

public class SampleDataTest extends CardFolderSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected CardFolder getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void cardFolder_dataFileDoesNotExist_loadSampleData() {
        Card[] expectedList = SampleDataUtil.getSampleCards();
        assertListMatching(getCardListPanel(), expectedList);
    }
}
