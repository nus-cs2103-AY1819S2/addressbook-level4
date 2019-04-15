package guitests.guihandles;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * A handle to the {@code SummaryPanel} in the GUI.
 */
public class SummaryPanelHandle extends NodeHandle<Region> {

    public static final String CHART_AREA_ID = "#chartArea";

    private final StackPane chartArea;

    public SummaryPanelHandle(Region summaryPanelHandle) {
        super(summaryPanelHandle);

        chartArea = getChildNode(CHART_AREA_ID);
    }

    /**
     * Checks if child is a PieChart
     * @return true if child is an instance of PieChart
     */
    public boolean isCategoryChart() {
        return chartArea.getChildren().get(0) instanceof PieChart;
    }

    /**
     * Checks if child is Text
     * @return true if child is an instance of Text
     */
    public boolean isNoExpenseText() {
        return chartArea.getChildren().get(0) instanceof Text;
    }

    /**
     * Checks if child is Text and checks that it matches {@code matchingText}
     * @return true if child is an instance of Text and matches {@code matchingText}
     */
    public boolean isMatchingText(String matchingText) {
        if (isNoExpenseText()) {
            Text text = (Text) chartArea.getChildren().get(0);
            return matchingText.equals(text.getText());
        }
        return false;
    }
}
