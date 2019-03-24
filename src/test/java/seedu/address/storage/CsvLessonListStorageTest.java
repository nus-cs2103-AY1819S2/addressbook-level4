package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.Lesson;

public class CsvLessonListStorageTest {

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
    private static final Path MULTIPLE_FILES_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonsStorageTest", "multipleFiles");
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

    private Optional<LessonList> readLessons(Path lessonsInTestDataFolder) {
        return new CsvLessonsStorage(lessonsInTestDataFolder).readLessons(lessonsInTestDataFolder);
    }

    private void saveLessons(Path folderPath, LessonList lessons) {
        CsvLessonsStorage cls = new CsvLessonsStorage(folderPath);
        cls.saveLessons(lessons);
    }

    /*
    private Path addToTestDataPathIfNotNull(String lessonsInTestDataFolder) {
        return lessonsInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(lessonsInTestDataFolder)
            : null;
    }
    */

    private LessonList getTestLessons() {
        LessonList lessons = new LessonList();
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
        LessonList actual = readLessons(INVALID_CORE_CHAR_FIELD_DATA_FOLDER).get();
        assertEquals(lesson, actual.getLesson(0));
    }

    @Test
    public void readLessons_invalidCoreCountLesson_emptyLessons() {
        LessonList actual = readLessons(INVALID_CORE_COUNT_FOLDER).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessons_emptyFolder_emptyResult() throws IOException {
        LessonList actual = readLessons(testFolder.newFolder().toPath()).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessons_invalidData_cardIgnored() {
        LessonList lessons = readLessons(INVALID_VALUES_FOLDER).get();
        assertEquals(1, lessons.getLesson(0).getCards().size());
    }

    @Test
    public void readLessons_missingData_cardIgnored() {
        LessonList lessons = readLessons(MISSING_CORE_VALUE_FOLDER).get();
        assertEquals(0, lessons.getLesson(0).getCards().size());
    }

    @Test
    public void readLessons_nonDefaultQuestionAnswerIndex_successIndexSet() {
        LessonList actual = readLessons(NON_DEFAULT_QUESTION_ANSWER_INDEX_FOLDER).get();
        assertNotEquals(Lesson.DEFAULT_INDEX_QUESTION, actual.getLesson(0).getQuestionCoreIndex());
        assertNotEquals(Lesson.DEFAULT_INDEX_ANSWER, actual.getLesson(0).getAnswerCoreIndex());
    }

    @Test
    public void readLessons_validFile_successfullyRead() {
        LessonList expected = getTestLessons();
        LessonList actual = readLessons(SINGLE_TEST_DATA_FOLDER).get();
        assertEquals(expected, actual);
    }

    @Test
    public void readLessons_validMultipleFiles_successfullyRead() {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(MULTIPLE_FILES_FOLDER);
        LessonList lessons = csvLessonsStorage.readLessons().get();
        assertTrue(lessons.getLessons().size() > 1);
    }

    @Test
    public void readLessons_noValidFiles_emptyLessons() {
        LessonList actual = readLessons(NO_VALID_FILES_FOLDER).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessons_emptyLessonFile_emptyLessons() {
        LessonList actual = readLessons(EMPTY_LESSON_FILE_FOLDER).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void saveLessons_validLessonsAndPath_successfullySaved() throws IOException {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(testFolder.newFolder().toPath());
        LessonList lessons = csvLessonsStorage.readLessons(MULTIPLE_FILES_FOLDER).get();
        assertEquals(3, csvLessonsStorage.saveLessons(lessons));
    }

    @Test
    public void saveLessons_readOnlyFile_catchesIoException() throws IOException {
        CsvLessonsStorage csvLessonsStorage = new CsvLessonsStorage(READ_ONLY_FILE_FOLDER);
        Files.walk(READ_ONLY_FILE_FOLDER).forEach(path -> {
            File f = new File(path.toString());
            f.setReadOnly();
        });
        LessonList lessons = csvLessonsStorage.readLessons().get();
        assertEquals(0, csvLessonsStorage.saveLessons(lessons));
    }

    @Test
    public void saveLessons_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLessons(null, new LessonList());
    }

    @Test
    public void saveLessons_nullLessons_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveLessons(testFolder.newFolder().toPath(), null);
    }

}
