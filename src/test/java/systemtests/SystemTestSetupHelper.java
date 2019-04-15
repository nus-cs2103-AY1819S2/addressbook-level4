package systemtests;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.testfx.api.FxToolkit;

import javafx.stage.Stage;
import quickdocs.TestApp;
import quickdocs.model.QuickDocs;

/**
 * Contains helper methods that system tests require.
 */
public class SystemTestSetupHelper {
    private TestApp testApp;

    /**
     * Sets up a new {@code TestApp} and returns it.
     */
    public TestApp setupApplication(Supplier<QuickDocs> quickDocs, Path saveFileLocation) {
        try {
            FxToolkit.registerStage(Stage::new);
            FxToolkit.setupApplication(() -> testApp = new TestApp(quickDocs, saveFileLocation));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.", te);
        }

        return testApp;
    }

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
