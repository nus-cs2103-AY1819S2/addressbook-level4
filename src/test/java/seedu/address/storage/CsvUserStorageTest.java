package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.user.User;

public class CsvUserStorageTest {
    private static final Path NO_VALID_FILE = Paths.get("src", "test", "data", "CsvUserStorageTest",
            "noValidFile");
    private static final Path TEST_DATA_FILE = Paths.get("src", "test", "data", "CsvUserStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Optional<User> readUser(Path userInTestDataFile) {
        return new CsvUserStorage(userInTestDataFile).readUser(userInTestDataFile);
    }

    private void saveUser(Path filePath, User user) {
        CsvUserStorage cls = new CsvUserStorage(filePath);
        cls.saveUser(user);
    }

    @Test
    public void getUserFilePath() {
        CsvUserStorage csvUserStorage = new CsvUserStorage(TEST_DATA_FILE);
        assertEquals(TEST_DATA_FILE, csvUserStorage.getUserFilePath());
    }

    @Test
    public void setUserFilePath_nullFilePath_throwsNullPointerException() {
        CsvUserStorage csvUserStorage = new CsvUserStorage(TEST_DATA_FILE);
        thrown.expect(NullPointerException.class);
        csvUserStorage.setUserFilePath(null);
    }

    @Test
    public void setUserFilePath_validFilePath() {
        CsvUserStorage csvUserStorage = new CsvUserStorage(TEST_DATA_FILE);
        csvUserStorage.setUserFilePath(NO_VALID_FILE);
        assertEquals(NO_VALID_FILE, csvUserStorage.getUserFilePath());
    }

    @Test
    public void readUser_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readUser(null);
    }

    @Test
    public void readUserTest_hashcodeZero_throwsIllegalValueException() {
        Assert.assertFalse(readUser(Paths.get("data\\user\\hashcodeZeroTest.csv")).isPresent());
    }

    @Test
    public void readUserTest_numberOfAttemptsNegative_throwsIllegalValueException() {
        Assert.assertFalse(readUser(Paths.get("data\\user\\numberOfAttemptsNegativeTest.csv")).isPresent());
    }

    @Test
    public void readUserTest_streakNegative_throwsIllegalValueException() {
        Assert.assertFalse(readUser(Paths.get("data\\user\\streakNegativeTest.csv")).isPresent());
    }

    @Test
    public void saveUser_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUser(null, new User());
    }

    @Test
    public void saveUser_nullUser_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveUser(testFolder.newFolder().toPath(), null);
    }
}
