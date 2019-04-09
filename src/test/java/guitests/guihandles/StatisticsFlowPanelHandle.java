package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.MonthlyRevenue;
import seedu.address.model.statistics.YearlyRevenue;

/**
 * A handler for the {@code StatisticsFlowPanel} of the UI.
 */
public class StatisticsFlowPanelHandle extends NodeHandle<FlowPane> {

    public static final String STATISTICS_FLOW_PANEL_ID = "#statisticsFlowPane";

    private static final String CARD_PANE_ID = "#cardPane";

    public StatisticsFlowPanelHandle(FlowPane statisticsPanelNode) {
        super(statisticsPanelNode);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the flowpane are definitely in the scene graph, while some nodes that are not
     * visible in the flowpane may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    private DailyRevenue getDailyRevenue(int index) { // TODO: figure out if we need this.
        return (DailyRevenue) getRootNode().getChildren();
    }

    private MonthlyRevenue getMonthlyRevenue(int index) {
        return (MonthlyRevenue) getRootNode().getChildren();
    }

    private YearlyRevenue getYearlyRevenue(int index) { // TODO: figure out if we need this.
        return (YearlyRevenue) getRootNode().getChildren();
    }

    /**
     * Returns the daily statistics card handle of a daily revenue associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public DailyStatisticsCardHandle getDailyStatisticsCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(DailyStatisticsCardHandle::new)
                .filter(handle -> handle.equals(getDailyRevenue(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the monthly statistics card handle of a monthly revenue associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MonthlyStatisticsCardHandle getMonthlyStatisticsCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(MonthlyStatisticsCardHandle::new)
                .filter(handle -> handle.equals(getMonthlyRevenue(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the yearly statistics card handle of a yearly revenue associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public YearlyStatisticsCardHandle getYearlyStatisticsCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(YearlyStatisticsCardHandle::new)
                .filter(handle -> handle.equals(getYearlyRevenue(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
