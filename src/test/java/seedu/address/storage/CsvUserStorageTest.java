package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.user.User;

public class CsvUserStorageTest {
    private static final Path NO_VALID_FILE = Paths.get("src", "test", "data", "CsvUserStorageTest",
            "noValidFile");
    private static final Path TEST_DATA_FILE = Paths.get("src", "test", "data", "CsvUserStorageTest");
    /*private static final Path SINGLE_TEST_DATA_FILE = Paths.get("src", "test", "data",
            "CsvLessonsUserTest", "singleTestUser");
    private static final Path EMPTY_USER_FILE_FOLDER = Paths.get("src", "test", "data", "CsvUserStorageTest",
            "emptyUserFile");
    private static final Path INVALID_VALUES_FOLDER = Paths.get("src", "test", "data",
            "CsvUserStorageTest", "invalidValues");
    private static final Path READ_ONLY_FILE = Paths.get("src", "test", "data", "CsvUserStorageTest",
            "readOnlyFile");*/
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
    /*
    private User getTestUser() {
        User user = new User();
        return user;
    }*/

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
    /*
    @Test
    public void readUser_validFile_successfullyRead() {
        User expected = getTestUser();
        User actual = readUser(SINGLE_TEST_DATA_FILE).get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUser_noValidFile() {
        User user = readUser(NO_VALID_FILE).get();
        assertEquals(new User(), user);
    }

    @Test
    public void readUser_emptyUserFile_emptyUser() {
        User user = readUser(EMPTY_USER_FILE_FOLDER).get();
        assertEquals(new User(), user);
    }


    @Test
    public void saveUser_readOnlyFile_catchesIoException() throws IOException {
        CsvUserStorage csvUserStorage = new CsvUserStorage(READ_ONLY_FILE);
        Files.walk(READ_ONLY_FILE).forEach(path -> {
            File file = new File(path.toString());
            file.setReadOnly();
        });
        User user = csvUserStorage.readUser().get();
        assertEquals(0, csvUserStorage.saveUser(user));
    }
    */
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
