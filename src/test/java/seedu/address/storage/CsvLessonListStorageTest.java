package seedu.address.storage;

import static org.junit.Assert.assertEquals;
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

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

public class CsvLessonListStorageTest {

    private static final Path EMPTY_LESSON_FILE_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest",
        "emptyLessonFile");
    private static final Path EMPTY_HEADER_VALUE_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest",
        "emptyHeaderValue");
    private static final Path EMPTY_FIELD_NAME_VALUE_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "emptyFieldNameValue");
    private static final Path INVALID_CORE_COUNT_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest",
        "invalidCoreCount");
    private static final Path INVALID_TESTED_FIELDS_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "invalidTestedFields");
    private static final Path MISMATCH_HEADER_AND_FIELD_COUNT_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "mismatchHeaderAndFieldCount");
    private static final Path MISSING_CORE_VALUE_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "missingCoreValues");
    private static final Path MULTIPLE_FILES_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "multipleFiles");
    private static final Path NO_VALID_FILES_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest",
        "noValidFiles");
    private static final Path READ_ONLY_FILE_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest",
        "readOnlyFile");
    private static final Path SINGLE_TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "CsvLessonListStorageTest", "singleTestLessons");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvLessonListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Optional<LessonList> readLessonList(Path lessonListInTestDataFolder) {
        return new CsvLessonListStorage(lessonListInTestDataFolder).readLessonList(lessonListInTestDataFolder);
    }

    private void saveLessonList(Path folderPath, LessonList lessonList) {
        CsvLessonListStorage cls = new CsvLessonListStorage(folderPath);
        cls.saveLessonList(lessonList);
    }

    private LessonList getTestLessonList() {
        LessonList lessonList = new LessonList();
        lessonList.addLesson(getTestLesson());
        return lessonList;
    }

    private Lesson getTestLesson() {
        ArrayList<String> testFields = new ArrayList<>();
        testFields.add("test 1");
        testFields.add("test 2");
        Lesson lesson = new Lesson("test", 2, testFields);

        return lesson;
    }

    @Test
    public void getLessonListFolderPath() {
        CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(TEST_DATA_FOLDER);
        assertEquals(TEST_DATA_FOLDER, csvLessonListStorage.getLessonListFolderPath());
    }

    @Test
    public void setLessonListFolderPath_nullFilePath_throwsNullPointerException() {
        CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        csvLessonListStorage.setLessonListFolderPath(null);
    }

    @Test
    public void setLessonListFolderPath_validFilePath_folderSet() {
        CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(TEST_DATA_FOLDER);
        csvLessonListStorage.setLessonListFolderPath(NO_VALID_FILES_FOLDER);
        assertEquals(NO_VALID_FILES_FOLDER, csvLessonListStorage.getLessonListFolderPath());
    }

    @Test
    public void readLessonList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readLessonList(null);
    }

    @Test
    public void readLessonList_invalidCoreCountLesson_emptyLessonList() {
        LessonList actual = readLessonList(INVALID_CORE_COUNT_FOLDER).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessonList_invalidTestedFields_defaultQuestionAnswerIndex() {
        LessonList actual = readLessonList(INVALID_TESTED_FIELDS_FOLDER).get();
        assertEquals(0, actual.getLesson(0).getQuestionCoreIndex());
        assertEquals(1, actual.getLesson(0).getAnswerCoreIndex());
    }

    @Test
    public void readLessonList_emptyFolder_emptyResult() throws IOException {
        LessonList actual = readLessonList(testFolder.newFolder().toPath()).orElse(new LessonList());
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessonList_emptyHeaderValue_lessonIgnored() {
        LessonList lessonList = readLessonList(EMPTY_HEADER_VALUE_FOLDER).get();
        assertEquals(0, lessonList.getLessons().size());
    }

    @Test
    public void readLessonList_emptyFieldNameValue_lessonIgnored() {
        LessonList lessonList = readLessonList(EMPTY_FIELD_NAME_VALUE_FOLDER).get();
        assertEquals(CsvLessonListStorage.DEFAULT_FIELD_NAME, lessonList.getLessons().get(0).getCoreHeaders().get(0));
    }

    @Test
    public void readLessonList_missingData_cardIgnored() {
        LessonList lessonList = readLessonList(MISSING_CORE_VALUE_FOLDER).get();
        assertEquals(0, lessonList.getLesson(0).getCards().size());
    }

    @Test
    public void readLessonList_mismatchHeaderAndFieldCount_lessonIgnored() {
        LessonList lessonList = readLessonList(MISMATCH_HEADER_AND_FIELD_COUNT_FOLDER).get();
        assertEquals(0, lessonList.getLessons().size());
    }

    @Test
    public void readLessonList_validFile_successfullyRead() {
        LessonList expected = getTestLessonList();
        LessonList actual = readLessonList(SINGLE_TEST_DATA_FOLDER).get();
        assertEquals(expected, actual);
    }

    @Test
    public void readLessonList_validMultipleFiles_successfullyRead() {
        CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(MULTIPLE_FILES_FOLDER);
        LessonList lessonList = csvLessonListStorage.readLessonList().orElse(new LessonList());
        assertTrue(lessonList.getLessons().size() > 1);
    }

    @Test
    public void readLessonList_noValidFiles_emptyLessonList() {
        LessonList actual = readLessonList(NO_VALID_FILES_FOLDER).orElse(new LessonList());
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void readLessonList_emptyLessonFile_emptyLessonList() {
        LessonList actual = readLessonList(EMPTY_LESSON_FILE_FOLDER).get();
        assertEquals(new LessonList(), actual);
    }

    @Test
    public void saveLessonList_validLessonListAndPath_successfullySaved() throws IOException {
        CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(testFolder.newFolder().toPath());
        LessonList lessonList = csvLessonListStorage.readLessonList(MULTIPLE_FILES_FOLDER).get();
        assertEquals(3, csvLessonListStorage.saveLessonList(lessonList));
    }

    @Test
    public void saveLessonList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLessonList(null, new LessonList());
    }

    @Test
    public void saveLessonList_nullLessonList_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveLessonList(testFolder.newFolder().toPath(), null);
    }
}
