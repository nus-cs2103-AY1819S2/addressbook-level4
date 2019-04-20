package seedu.address;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.user.User;
import seedu.address.storage.CsvLessonListStorage;
import seedu.address.storage.LessonListStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.UserStorage;
import seedu.address.testutil.TestUtil;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getAndCreateFilePathInSandBoxFolder("data");
    public static final Path SAVE_USER_LOCATION_FOR_TESTING = TestUtil.getAndCreateFilePathInSandBoxFolder("data/user");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<LessonList> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path saveUserFileLocation = SAVE_USER_LOCATION_FOR_TESTING;
    protected ManagementModel testManagementModel;

    public TestApp() {
    }

    public TestApp(Supplier<LessonList> initialDataSupplier, Path saveFileLocation, Path saveUserFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;
        this.saveUserFileLocation = saveUserFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            CsvLessonListStorage csvLessonListStorage = new CsvLessonListStorage(saveFileLocation);
            csvLessonListStorage.saveLessonList(initialDataSupplier.get());
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setLessonListFolderPath(saveFileLocation);
        userPrefs.setUserFilePath(saveUserFileLocation);
        return userPrefs;
    }

    @Override
    protected LessonList initLessonList(LessonListStorage storage) {
        return super.initLessonList(storage);
    }

    @Override
    protected User initUser(UserStorage storage) {
        return super.initUser(storage);
    }

    /**
     * Returns a defensive copy of the lesson list data stored inside the storage file.
     */
    public List<Lesson> readStorageLessonList() {
        LessonList lessonList = new LessonList();
        Optional<LessonList> optionalLessonList = storage.readLessonList();
        if (optionalLessonList.isPresent()) {
            lessonList = optionalLessonList.get();
        }

        return lessonList.getLessons();
    }
    /**
     * Returns a defensive copy of the management model.
     */
    public ManagementModel getManagementModel() {

        this.testManagementModel =
            new ManagementModelManager(new UserPrefs(), managementModel.getLessonList(), new User());
        return testManagementModel;
    }

    /**
     * Returns a defensive copy of the quiz model.
     */
    public QuizModel getQuizModel() {
        QuizModel quizModel = new QuizModelManager(new ManagementModelManager(new UserPrefs(), new LessonList(),
            managementModel.getUser()));
        return quizModel;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
