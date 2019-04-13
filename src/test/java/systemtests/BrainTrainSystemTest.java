package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_COMMANDS;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.LessonListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainPanelHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.TestApp;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.ui.CommandBox;

/**
 * A system test class for BrainTrain, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class BrainTrainSystemTest {
    private static final List<String> COMMAND_BOX_DEFAULT_STYLE =
            Arrays.asList("text-input", "text-field", "def-colour");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field",
                    "def-colour", CommandBox.STYLE_CLASS_ERROR);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation(), getUserDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected LessonList getInitialData() {
        return SampleDataUtil.getSampleBrainTrain();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    /**
     * Returns the directory of the user data file.
     */
    protected Path getUserDataFileLocation() {
        return TestApp.SAVE_USER_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public LessonListPanelHandle getLessonListPanel() {
        return mainWindowHandle.getLessonListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    public MainPanelHandle getMainPanel() {
        return mainWindowHandle.getMainPanel();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same LessonList objects as {@code expectedModel}.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());

        if (expectedModel instanceof ManagementModel) {
            ManagementModel managementModel = (ManagementModel) expectedModel;
            assertEquals(managementModel.getLessons(), testApp.readStorageLessonList());
        } else {
            QuizModel quizModel = (QuizModel) expectedModel;
            assertEquals(quizModel.getManagementModelUser(), testApp.getQuizModel().getManagementModelUser());
        }
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals(MESSAGE_LESSON_COMMANDS, getResultDisplay().getText());
    }

    /**
     * Returns a defensive copy of the current management model.
     */
    protected ManagementModel getManagementModel() {
        return testApp.getManagementModel();
    }

    /**
     * Returns a defensive copy of the current quiz model.
     */
    public QuizModel getQuizModel() {
        return testApp.getQuizModel();
    }
}
