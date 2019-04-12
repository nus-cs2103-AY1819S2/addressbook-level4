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
    private static final Logger logger = LogsCenter.getLogger(CsvLessonListStorage.class);

    private static final String CORE_ESCAPE = "*";
    private static final String QUESTION_ESCAPE = "?";
    private static final String ANSWER_ESCAPE = "@";

    private static final String FULL_HEADER_CORE_QA = "TESTED";
    private static final String FULL_HEADER_CORE_NOT_QA = "NOT TESTED";
    private static final String FULL_HEADER_OPTIONAL = "HINT";

    private static final String HEADER_CORE_QA = "t";
    private static final String HEADER_CORE_NOT_QA = "n";
    private static final String HEADER_OPTIONAL = "h";

    private static final String READ_WARNING_CORE_LABEL = "Core escape character [ "
            + CORE_ESCAPE
            + " ] was found after non-core column.";

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
        if (data == null) {
            logger.warning("Empty/invalid file at: " + filePath.toString());
            return Optional.empty();
        }

        String lessonName = filePath.getFileName().toString();
        int extensionIndex = lessonName.lastIndexOf(".");
        lessonName = lessonName.substring(0, extensionIndex);

        int[] headerData = parseStringArrayToHeaderData(data.get(0));

        int coreCount = headerData[0];
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

        List<String> fieldNames = parseStringArrayToFieldNames(data.get(1));

        /*
        String[] header = data.get(0);
        int coreCount = 0;
        int questionIndex = Lesson.DEFAULT_INDEX_QUESTION;
        int answerIndex = Lesson.DEFAULT_INDEX_ANSWER;
        boolean readingCores = true;

        for (int i = 0; i < header.length; i++) {
            String value = header[i];
            if (value.startsWith(CORE_ESCAPE)) {
                if (!readingCores) {
                    logger.warning("File " + filePath.toString() + ": " + READ_WARNING_CORE_LABEL);
                    continue;
                }
                coreCount++;
                header[i] = value.substring(CORE_ESCAPE.length());

                String substring = header[i];
                if (substring.startsWith(QUESTION_ESCAPE)) {
                    header[i] = substring.substring(QUESTION_ESCAPE.length());
                    questionIndex = i;
                } else if (substring.startsWith(ANSWER_ESCAPE)) {
                    header[i] = substring.substring(ANSWER_ESCAPE.length());
                    answerIndex = i;
                }
            } else {
                readingCores = false;
            }
        }
        if (coreCount < Card.MIN_CORE_COUNT) {
            return Optional.empty();
        }

        String lessonName = filePath.getFileName().toString();
        int extensionPos = lessonName.lastIndexOf(".");
        lessonName = lessonName.substring(0, extensionPos);
        List<String> fields = Arrays.asList(header);

        Lesson newLesson = new Lesson(lessonName, coreCount, fields);

        newLesson.setQuestionAnswerIndices(questionIndex, answerIndex);




        for (int i = 1; i < data.size(); i++) {
            try {
                newLesson.addCard(Arrays.asList(data.get(i)));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        */
        return Optional.empty();
        //return Optional.of(newLesson);
    }

    private int[] parseStringArrayToHeaderData(String[] header) {
        int[] returnValues = new int[3];
        Arrays.fill(returnValues, -1);
        int questionIndex = -1;
        int answerIndex = -1;

        int coreCount = 0;
        while (coreCount < header.length) {
            String headerChar = header[coreCount].toLowerCase().substring(0,1);
            if (headerChar.equals(HEADER_OPTIONAL)) {
                System.out.println(headerChar);
                break;
            }
            if (headerChar.equals(HEADER_CORE_QA)) {
                if (questionIndex == -1) {
                    questionIndex = coreCount;
                } else if (answerIndex == -1) {
                    answerIndex = coreCount;
                }
            }

            coreCount++;
        }

        returnValues[0] = coreCount;
        returnValues[1] = questionIndex;
        returnValues[2] = answerIndex;
        return returnValues;
    }

    private List<String> parseStringArrayToFieldNames(String[] fieldNames) {
        return null;
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
            return null;
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

        if (paths == null) {
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
