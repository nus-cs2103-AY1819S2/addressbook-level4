package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

/**
 * A class to access LessonList stored in the hard disk as a csv file
 */
public class CsvLessonListStorage implements LessonListStorage {
    public static final String DEFAULT_FIELD_NAME = "Unnamed";

    private static final Logger logger = LogsCenter.getLogger(CsvLessonListStorage.class);

    private static final String FULL_HEADER_CORE_QA = "TESTED";
    private static final String FULL_HEADER_CORE_NOT_QA = "NOT TESTED";
    private static final String FULL_HEADER_OPTIONAL = "HINT";

    private static final String HEADER_CORE_QA = "t";
    private static final String HEADER_CORE_NOT_QA = "n";
    private static final String HEADER_OPTIONAL = "h";

    private Path folderPath;

    public CsvLessonListStorage(Path folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public Path getLessonListFolderPath() {
        return folderPath;
    }

    @Override
    public void setLessonListFolderPath(Path folderPath) {
        requireNonNull(folderPath);
        this.folderPath = folderPath;
    }

    /**
     * Parses the given file at the path into a lesson, in the following order:
     * <p>
     * - Reads the file into a List of String arrays
     * - Parses the first String array as a header
     * -> Values marked as core using CORE_ESCAPE have the marker removed
     *      --> Values marked with QUESTION_ESCAPE and ANSWER_ESCAPE are assigned.
     *       - See documentation on save data. TODO
     * -> The count of cores is kept
     * - Name of lesson is read from filename without extension
     * - Fields of lesson read from modified header
     * - Cards are read from remainder of data.
     * <p>
     *
     * @param filePath Assumes not null.
     * @return The parsed lesson.
     */
    private Optional<Lesson> parseFileIntoLesson(Path filePath) {
        List<String[]> data;
        try {
            data = CsvUtil.readCsvFile(filePath);
        } catch (IOException e) {
            logger.warning("Unable to read lesson file at: " + filePath.toString());
            return Optional.empty();
        }
        if (data == null || data.size() < 2) {
            logger.warning("Empty/invalid file at: " + filePath.toString());
            return Optional.empty();
        }

        String lessonName = filePath.getFileName().toString();
        int extensionIndex = lessonName.lastIndexOf(".");
        lessonName = lessonName.substring(0, extensionIndex);


        String[] headerArray = data.get(0);
        String[] fieldNameArray = data.get(1);

        if (headerArray.length != fieldNameArray.length) {
            logger.warning("Lesson does not have matching number of fields and field types: " + lessonName);
            return Optional.empty();
        }

        int[] headerData = parseStringArrayToHeaderData(headerArray);

        int coreCount = headerData[0];
        if (coreCount == -1) {
            logger.warning("Lesson has invalid header values: " + lessonName);
            return Optional.empty();
        }
        if (coreCount < Card.MIN_CORE_COUNT) {
            logger.warning("Lesson does not have enough testable sides: " + lessonName);
            return Optional.empty();
        }
        int questionIndex = headerData[1];
        int answerIndex = headerData[2];
        if (questionIndex < 0 || answerIndex < 0) {
            logger.info("Invalid values marked for testing. Using defaults.");
            questionIndex = Lesson.DEFAULT_INDEX_QUESTION;
            answerIndex = Lesson.DEFAULT_INDEX_ANSWER;
        }

        List<String> fieldNames = parseStringArrayToFieldNames(fieldNameArray);

        Lesson newLesson = new Lesson(lessonName, coreCount, fieldNames);
        newLesson.setQuestionAnswerIndices(questionIndex, answerIndex);


        for (int i = 2; i < data.size(); i++) {
            try {
                newLesson.addCard(Arrays.asList(data.get(i)));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return Optional.of(newLesson);
    }

    /**
     * Returns an int array containing core count, question and answer index.
     *
     * @param headerArray
     * @return Header data values.
     */
    private int[] parseStringArrayToHeaderData(String[] headerArray) {
        int[] returnValues = new int[3];
        Arrays.fill(returnValues, -1);
        int questionIndex = -1;
        int answerIndex = -1;

        for (int i = 0; i < headerArray.length; i++) {
            if (headerArray[i].isEmpty()) {
                headerArray[i] = " ";
            }
        }

        int coreCount = 0;
        int index = 0;
        while (index < headerArray.length) {
            String headerChar = headerArray[index].toLowerCase().substring(0, 1);
            if (headerChar.equals(HEADER_CORE_QA)) {
                if (questionIndex == -1) {
                    questionIndex = index;
                } else if (answerIndex == -1) {
                    answerIndex = index;
                }
                coreCount++;
            } else if (headerChar.equals(HEADER_CORE_NOT_QA)) {
                coreCount++;
            } else if (!headerChar.equals(HEADER_OPTIONAL)) {
                return returnValues;
            }

            index++;
        }

        returnValues[0] = coreCount;
        returnValues[1] = questionIndex;
        returnValues[2] = answerIndex;
        return returnValues;
    }

    /**
     * Returns a list of Strings containing correctly formatted field names.
     *
     * If the field is empty, it is automatically renamed to DEFAULT_FIELD_NAME.
     *
     * @param fieldNameArray
     * @return List of field names.
     */
    private List<String> parseStringArrayToFieldNames(String[] fieldNameArray) {
        List<String> fieldNames;
        for (int i = 0; i < fieldNameArray.length; i++) {
            if (fieldNameArray[i].isEmpty()) {
                fieldNameArray[i] = DEFAULT_FIELD_NAME;
            }
        }
        fieldNames = Arrays.asList(fieldNameArray);

        return fieldNames;
    }

    /**
     * Returns a String[] containing correctly formatted strings for saving.
     * Appends QUESTION_ESCAPE and ANSWER_ESCAPE chars to the headers, then appends CORE_ESCAPE to all remaining core
     * values.
     *
     * @param lesson
     * @return Header data with relevant escape characters.
     */
    private String[] parseHeaderDataToStringArray(Lesson lesson) {
        String[] header;

        List<String> headerList = new ArrayList<>();
        for (int i = 0; i < lesson.getCoreHeaders().size(); i++) {
            if (i == lesson.getAnswerCoreIndex() || i == lesson.getQuestionCoreIndex()) {
                headerList.add(FULL_HEADER_CORE_QA);
            } else {
                headerList.add(FULL_HEADER_CORE_NOT_QA);
            }
        }
        for (int i = 0; i < lesson.getOptionalHeaders().size(); i++) {
            headerList.add(FULL_HEADER_OPTIONAL);
        }

        header = new String[headerList.size()];
        headerList.toArray(header);

        return header;
    }

    /**
     * Returns a String[] containing correctly formatted strings for saving.
     * Appends QUESTION_ESCAPE and ANSWER_ESCAPE chars to the headers, then appends CORE_ESCAPE to all remaining core
     * values.
     *
     * @param lesson
     * @return Header data with relevant escape characters.
     */
    private String[] parseFieldNamesToStringArray(Lesson lesson) {
        String[] fieldNames;

        List<String> fieldList = new ArrayList<>();

        fieldList.addAll(lesson.getCoreHeaders());
        fieldList.addAll(lesson.getOptionalHeaders());
        int headerSize = fieldList.size();

        fieldNames = new String[headerSize];
        fieldList.toArray(fieldNames);

        return fieldNames;
    }

    /**
     * Returns a String[] of all card fields in order.
     *
     * @param card
     * @return Formatted card data.
     */
    private String[] parseCardDataToStringArray(Card card) {
        String[] cardArray;

        List<String> cardData = new ArrayList<>();
        cardData.addAll(card.getCores());
        cardData.addAll(card.getOptionals());

        cardArray = new String[card.getCores().size() + card.getOptionals().size()];
        cardData.toArray(cardArray);

        return cardArray;
    }

    /**
     * TODO
     * @param lesson
     */
    private void saveLessonToFile(Lesson lesson, Path folderPath) throws IOException {
        List<String[]> data = new ArrayList<>();
        Path filePath = Paths.get(folderPath.toString(), lesson.getName() + ".csv");

        data.add(parseHeaderDataToStringArray(lesson));
        data.add(parseFieldNamesToStringArray(lesson));

        for (Card card : lesson.getCards()) {
            data.add(parseCardDataToStringArray(card));
        }

        CsvUtil.writeCsvFile(filePath, data);
    }

    private List<Path> getFilePathsInFolder(Path folderPath) {
        List<Path> paths = new ArrayList<>();
        try {
            Files.walk(folderPath, 1).filter(path ->
                path.toString().endsWith(".csv")).forEach(paths::add);
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return paths;
    }

    @Override
    public Optional<LessonList> readLessonList() {
        return readLessonList(folderPath);

    }

    @Override
    public Optional<LessonList> readLessonList(Path folderPath) {
        requireNonNull(folderPath);

        List<Path> paths = getFilePathsInFolder(folderPath);

        if (paths.isEmpty()) {
            return Optional.empty();
        }

        LessonList lessonList = new LessonList();
        for (Path filePath : paths) {
            Optional<Lesson> newLesson = parseFileIntoLesson(filePath);
            newLesson.ifPresent(lessonList::addLesson);
        }
        return Optional.of(lessonList);
    }

    @Override
    public int saveLessonList(LessonList lessonList) {
        return saveLessonList(lessonList, folderPath);
    }

    @Override
    public int saveLessonList(LessonList lessonList, Path folderPath) {
        requireNonNull(lessonList);
        requireNonNull(folderPath);

        int saveCount = 0;

        List<Lesson> allLessons = lessonList.getLessons();

        for (Lesson lesson : allLessons) {
            try {
                saveLessonToFile(lesson, folderPath);
                saveCount++;
            } catch (IOException e) {
                logger.warning("Lesson: \"" + lesson.getName() + "\" failed to save; IOException occurred");
            }
        }
        return saveCount;
    }
}
