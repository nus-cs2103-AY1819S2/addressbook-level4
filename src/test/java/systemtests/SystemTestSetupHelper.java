package systemtests;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.testfx.api.FxToolkit;

import guitests.guihandles.MainWindowHandle;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.ReadOnlyRestOrRant;

/**
 * Contains helper methods that system tests require.
 */
public class SystemTestSetupHelper {
    private TestApp testApp;
    private MainWindowHandle mainWindowHandle;

    /**
     * Initializes TestFX.
     */
    public static void initialize() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Sets up a new {@code TestApp} and returns it.
     */
    public TestApp setupApplication(Supplier<ReadOnlyRestOrRant> restOrRant, Path tablesFileLocation,
                                    Path ordersFileLocation, Path menuFileLocation, Path statsFileLocation) {
        try {
            FxToolkit.registerStage(Stage::new);
            FxToolkit.setupApplication(() -> testApp =
                    new TestApp(restOrRant, tablesFileLocation, ordersFileLocation, menuFileLocation,
                            statsFileLocation));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.", te);
        }

        return testApp;
    }

    /**
     * Encapsulates the primary stage of {@code TestApp} in a {@code MainWindowHandle} and returns it.
     */
    public MainWindowHandle setupMainWindowHandle() {
        try {
            FxToolkit.setupStage((stage) -> {
                mainWindowHandle = new MainWindowHandle(stage);
                mainWindowHandle.focus();
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to set up.", te);
        }

        return mainWindowHandle;
    }

    /**
     * Tears down existing stages.
     */
    public void tearDownStage() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to tear down.", te);
        }
    }
}
