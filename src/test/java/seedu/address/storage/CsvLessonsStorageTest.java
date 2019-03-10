package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.Lessons;
import seedu.address.model.lesson.Lesson;

public class CsvLessonsStorageTest {

    private static final Path EMPTY_LESSON_FILE_FOLDER = Paths.get("src", "test", "data", "CsvLessonsStorageTest",
        "emptyLessonFile");
    private static final Path INVALID_CORE_CHAR_FIELD_DATA_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "invalidCoreCharInField");
    private static final Path INVALID_CORE_COUNT_FOLDER = Paths.get("src", "test", "data", "CsvLessonsStorageTest",
        "invalidCoreCount");
    private static final Path INVALID_VALUES_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "invalidValues");
    private static final Path MISSING_CORE_VALUE_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "missingCoreValues");
    private static final Path NO_VALID_FILES_FOLDER = Paths.get("src", "test", "data", "CsvLessonsStorageTest",
        "noValidFiles");
    private static final Path NON_DEFAULT_QUESTION_ANSWER_INDEX_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "nonDefaultQuestionAnswerIndex");
    private static final Path READ_ONLY_FILE_FOLDER = Paths.get("src", "test", "data", "CsvLessonsStorageTest",
        "readOnlyFile");
    private static final Path SINGLE_TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "singleTestLessons");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvLessonsStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Optional<Lessons> readLessons(Path lessonsInTestDataFolder) {
        return new CsvLessonsStorage(lessonsInTestDataFolder).readLessons(lessonsInTestDataFolder);
    }

    /*
    private Path addToTestDataPathIfNotNull(String lessonsInTestDataFolder) {
        return lessonsInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(lessonsInTestDataFolder)
            : null;
    }
    */

    private Lessons getTestLessons() {
        Lessons lessons = new Lessons();
        lessons.addLesson(getTestLesson());
        return lessons;
    }

    private Lesson getTestLesson() {
        ArrayList<String> testFields = new ArrayList<>();
        testFields.add("test 1");
        testFields.add("test 2");
        Lesson lesson = new Lesson("test", 2, testFields);

        return lesson;
    }

    @Test
    public void getLessonsFolderPath() {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(TEST_DATA_FOLDER);
        assertEquals(TEST_DATA_FOLDER, csvLessonsStorage.getLessonsFolderPath());
    }

    @Test
    public void setLessonsFolderPath_nullFilePath_throwsNullPointerException() {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        csvLessonsStorage.setLessonsFolderPath(null);
    }

    @Test
    public void setLessonsFolderPath_validFilePath_folderSet() {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(TEST_DATA_FOLDER);
        csvLessonsStorage.setLessonsFolderPath(NO_VALID_FILES_FOLDER);
        assertEquals(NO_VALID_FILES_FOLDER, csvLessonsStorage.getLessonsFolderPath());
    }

    @Test
    public void readLessons_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readLessons(null);
    }

    @Test
    public void readLessons_invalidCoreCharInFields_escapeCharInFieldOfLesson() {
        ArrayList<String> testFields = new ArrayList<>();
        testFields.add("test 1");
        testFields.add("test 2");
        testFields.add("not core");
        testFields.add("*not core with escape");
        Lesson lesson = new Lesson("test-invalid-core-char-in-field", 2, testFields);
        Lessons actual = readLessons(INVALID_CORE_CHAR_FIELD_DATA_FOLDER).get();
        assertEquals(lesson, actual.getLesson(0));
    }

    @Test
    public void readLessons_invalidCoreCountLesson_emptyLessons() {
        Lessons actual = readLessons(INVALID_CORE_COUNT_FOLDER).get();
        assertEquals(new Lessons(), actual);
    }

    @Test
    public void readLessons_emptyFolder_emptyResult() throws IOException {
        Lessons actual = readLessons(testFolder.newFolder().toPath()).get();
        assertEquals(new Lessons(), actual);
    }

    @Test
    public void readLessons_invalidData_cardIgnored() {
        Lessons lessons = readLessons(INVALID_VALUES_FOLDER).get();
        assertEquals(1, lessons.getLesson(0).getCards().size());
    }

    @Test
    public void readLessons_missingData_cardIgnored() {
        Lessons lessons = readLessons(MISSING_CORE_VALUE_FOLDER).get();
        assertEquals(0, lessons.getLesson(0).getCards().size());
    }

    @Test
    public void readLessons_nonDefaultQuestionAnswerIndex_successIndexSet() {
        Lessons actual = readLessons(NON_DEFAULT_QUESTION_ANSWER_INDEX_FOLDER).get();
        assertNotEquals(Lesson.DEFAULT_INDEX_QUESTION, actual.getLesson(0).getQuestionCoreIndex());
        assertNotEquals(Lesson.DEFAULT_INDEX_ANSWER, actual.getLesson(0).getAnswerCoreIndex());
    }

    @Test
    public void readLessons_validFile_successfullyRead() {
        Lessons expected = getTestLessons();
        Lessons actual = readLessons(SINGLE_TEST_DATA_FOLDER).get();
        assertEquals(expected, actual);
    }

    //note: readLessons reads into all files regardless of folder.
    @Test
    public void readLessons_validMultipleFiles_successfullyRead() {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(TEST_DATA_FOLDER);
        Lessons lessons = csvLessonsStorage.readLessons().get();
        assertTrue(lessons.getLessons().size() > 1);
        //TODO Make this test not hot garbage
    }

    @Test
    public void readLessons_noValidFiles_emptyLessons() {
        Lessons actual = readLessons(NO_VALID_FILES_FOLDER).get();
        assertEquals(new Lessons(), actual);
    }

    @Test
    public void readLessons_emptyLessonFile_emptyLessons() {
        Lessons actual = readLessons(EMPTY_LESSON_FILE_FOLDER).get();
        assertEquals(new Lessons(), actual);
    }

    /*
    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLessons(null, "SomeFile.json");
    }

    @Test
    public void saveLessons_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLessons(new Lessons(), null);
    }
    */
    /**
     * Saves {@code lessons} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    /*
    private void saveLessons(Lessons lessons, String prefsFileInTestDataFolder) {
        try {
            new CsvLessonsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                .saveLessons(lessons);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    */
    /*
    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(1200, 200, 0, 2));

        Path pefsFilePath = testFolder.getRoot().toPath().resolve("TempPrefs.json");
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readLessons().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(new GuiSettings(5, 5, 5, 5));
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readLessons().get();
        assertEquals(original, readBack);
    }
    */
}
