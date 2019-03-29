package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_ANSWER;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_QUESTION;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_1;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_HEADER_2;
import static seedu.address.testutil.LessonBuilder.DEFAULT_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.quiz.QuizAnswerCommand;
import seedu.address.logic.commands.quiz.QuizStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.storage.CsvLessonsStorage;
import seedu.address.storage.CsvUserStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.LessonBuilder;

public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ManagementModel managementModel = new ManagementModelManager();
    private QuizModel quizModel = new QuizModelManager();
    private CommandHistory history = new CommandHistory();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        CsvLessonsStorage lessonsStorage = new CsvLessonsStorage(temporaryFolder.newFile().toPath());
        CsvUserStorage userStorage = new CsvUserStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, lessonsStorage, userStorage);
        logic = new LogicManager(managementModel, quizModel, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    /*@Test
    public void execute_startCommand_success() {
        // TODO change to session
        // this hardcoded values matched QuizStartCommand
        // when session is implemented then this will change to session instead
        final QuizCard card1 = new QuizCard("Japan", "Tokyo");
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        final QuizCard card4 = new QuizCard("中国", "北京");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2, card3, card4));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        QuizCard expectedCard = expectedModel.getNextCard();
        CommandResult expected = new CommandResult(String.format(QuizStartCommand.MESSAGE_QUESTION_ANSWER,
            expectedCard.getQuestion(), expectedCard.getAnswer()));

        assertCommandSuccess(QuizStartCommand.COMMAND_WORD, expected.getFeedbackToUser(), expectedModel);
    }*/

    @Test
    public void execute_quizCommand_success() {
        final String answer = "Budapest";
        // TODO change to session
        // this hardcoded values matched QuizStartCommand
        // when session is implemented then this will change to session instead
        final QuizCard card1 = new QuizCard("Japan", "Tokyo");
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        final QuizCard card4 = new QuizCard("中国", "北京");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2, card3, card4));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        CommandResult expected = new CommandResult("");

        quizModel.init(new Quiz(quizCards, QuizMode.LEARN));
        quizModel.getNextCard();

        assertCommandSuccess(answer, expected.getFeedbackToUser(), expectedModel);
    }

    @Test
    public void execute_quizStatusCommand_success() throws Exception {
        // TODO change to session
        // this hardcoded values matched StartCommand
        // when session is implemented then this will change to session instead
        final QuizCard card1 = new QuizCard("Japan", "Tokyo");
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        final QuizCard card4 = new QuizCard("中国", "北京");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2, card3, card4));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        expectedModel.getNextCard();

        CommandResult expected = new CommandResult(String.format(QuizStatusCommand.MESSAGE_RESULT,
            expectedModel.getQuizTotalAttempts(), expectedModel.getQuizTotalCorrectQuestions(),
            expectedModel.getCurrentProgress()));

        quizModel.init(new Quiz(quizCards, QuizMode.LEARN));
        quizModel.getNextCard();

        assertCommandSuccess("\\status", expected.getFeedbackToUser(), expectedModel);

        quizModel.getNextCard();
        quizModel.getNextCard();
        quizModel.getNextCard();
        quizModel.getNextCard();
        quizModel.getNextCard();

        assertTrue(new QuizAnswerCommand("someanswer").execute(quizModel, history).isShowQuiz());

    }

    @Test
    public void isShowHelp() {
        assertTrue(new HelpCommand().execute(managementModel, history).isShowHelp());
    }

    @Test
    public void isExit() {
        assertTrue(new ExitCommand().execute(managementModel, history).isExit());
    }

    @Test
    public void getMode() {
        // TODO change to session
        // this hardcoded values matched StartCommand
        // when session is implemented then this will change to session instead
        final QuizCard card1 = new QuizCard("Japan", "Tokyo");
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        final QuizCard card4 = new QuizCard("中国", "北京");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2, card3, card4));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.setDisplayFormatter(new QuizUiDisplayFormatter("question", "Hungary", "answer", "Budapest",
            QuizMode.PREVIEW));

        // before quiz starts
        assertEquals(LogicManager.Mode.MANAGEMENT, logic.getMode());

        quizModel.init(new Quiz(quizCards, QuizMode.LEARN));
        quizModel.getNextCard();

        // after quiz started
        assertCommandSuccess("", "", expectedModel);
        assertEquals(LogicManager.Mode.QUIZ, logic.getMode());
    }

    @Test
    public void getDisplayFormatter() {
        // TODO change to session
        // this hardcoded values matched StartCommand
        // when session is implemented then this will change to session instead
        final QuizCard card1 = new QuizCard("Japan", "Tokyo");
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final QuizCard card3 = new QuizCard("Christmas Island", "The Settlement");
        final QuizCard card4 = new QuizCard("中国", "北京");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2, card3, card4));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);

        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.setDisplayFormatter(new QuizUiDisplayFormatter("question", "Hungary", "answer", "Budapest",
            QuizMode.PREVIEW));

        quizModel.init(new Quiz(quizCards, QuizMode.LEARN));
        quizModel.getNextCard();

        assertCommandSuccess("", "", expectedModel);
        assertEquals(expectedModel.getDisplayFormatter(), logic.getDisplayFormatter());
    }
    @Test
    public void getHistory() {
        CommandHistory empty = new CommandHistory();
        assertEquals(empty.getHistory(), logic.getHistory());
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedManagementModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, ManagementModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ManagementModel expectedManagementModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedManagementModel);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, QuizModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, QuizModel expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, ManagementModel)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, ManagementModel)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, ManagementModel)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        ManagementModel expectedManagementModel = new ManagementModelManager();
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedManagementModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal management manager data are same as those in the {@code expectedManagementModel} <br>
     *      - {@code expectedManagementModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, ManagementModel expectedManagementModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertNull(expectedException);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedManagementModel, managementModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal management manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, QuizModel expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertNull(expectedException);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, quizModel);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    @Test
    public void testManagementInputOutput() {
        Lesson validLesson = new LessonBuilder().withNoOptionalHeaders().build();
        String command = AddLessonCommand.COMMAND_WORD + " "
                + PREFIX_LESSON_NAME + DEFAULT_NAME + " "
                + PREFIX_CORE_QUESTION + DEFAULT_CORE_HEADER_1 + " "
                + PREFIX_CORE_ANSWER + DEFAULT_CORE_HEADER_2;

        try {
            logic.execute(command);
        } catch (CommandException | ParseException e) {
            // Parsing and execution of AddLessonCommand should not fail due to
            // CommandException and ParseException given it is the correct format.
            throw new AssertionError("Parsing and execution of "
                    + "AddLessonCommand.COMMAND_WORD should succeed.", e);
        }
    }
}
