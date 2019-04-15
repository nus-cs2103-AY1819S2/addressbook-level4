package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class StatisticViewTest extends GuiUnitTest {
    private StatisticView statisticView;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> statisticView = new StatisticView(new Stage(), new XYChart.Series<>()));
        FxToolkit.registerStage(statisticView::getRoot);
    }

    @Test
    public void isShowing_statsViewIsShowing_returnsTrue() {
        guiRobot.interact(statisticView::show);
        assertTrue(statisticView.getRoot().isShowing());
    }

    @Test
    public void isShowing_statsViewIsHiding_returnsFalse() {
        assertFalse(statisticView.getRoot().isShowing());
    }

    @Test
    public void focus_helpWindowNotFocused_focused() throws Exception {
        // TODO: This test skip can be removed once this bug is fixed:
        // https://github.com/javafxports/openjdk-jfx/issues/50
        //
        // When there are two stages (stage1 and stage2) shown,
        // stage1 is in focus and stage2.requestFocus() is called,
        // we expect that stage1.isFocused() will return false while
        // stage2.isFocused() returns true. However, as reported in the bug report,
        // both stage1.isFocused() and stage2.isFocused() returns true,
        // which fails the test.
        assumeFalse("Test skipped in headless mode: Window focus behavior is buggy.", guiRobot.isHeadlessMode());
        guiRobot.interact(statisticView::show);

        // Focus on another stage to remove focus from the helpWindow
        guiRobot.interact(() -> {
            Stage temporaryStage = new Stage();
            temporaryStage.show();
            temporaryStage.requestFocus();
        });
        assertFalse(statisticView.getRoot().isFocused());

        guiRobot.interact(() -> statisticView.getRoot().requestFocus());
        assertTrue(statisticView.getRoot().isFocused());
    }
}
