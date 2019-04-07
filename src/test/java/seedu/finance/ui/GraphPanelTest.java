package seedu.finance.ui;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GraphPanelHandle;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;

public class GraphPanelTest extends GuiUnitTest {
    private GraphPanelHandle graphPanelHandle;

    @Before
    public void setUp() throws InterruptedException {
        GraphPanel graphPanel = new GraphPanel(
                new LinkedHashMap<>(),
                SummaryPeriod.DAY,
                7
        );
        Thread.sleep(1000);
        uiPartRule.setUiPart(graphPanel);
        graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
    }

    @Test
    public void noExpensesTextAppearsWhenNoExpenses() {
        assertTrue(graphPanelHandle.isNoExpenseText());
    }

    @Test
    public void correctNoExpensesTextAppearsWhenNoExpenses() {
        setChartData(new LinkedHashMap<>(), SummaryPeriod.DAY, 1);
        assertTrue(graphPanelHandle.isMatchingText("There are no recorded expenditures in the past day"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.DAY, 7);
        assertTrue(graphPanelHandle.isMatchingText("There are no recorded expenditures in the past 7 days"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.MONTH, 1);
        assertTrue(graphPanelHandle.isMatchingText("There are no recorded expenditures in the past month"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.MONTH, 7);
        assertTrue(graphPanelHandle.isMatchingText("There are no recorded expenditures in the past 7 months"));
    }

    @Test
    public void categoryChartAppears() {
        LinkedHashMap<String, Double> mockData = new LinkedHashMap<>();
        mockData.put("Food", 6.00);
        setChartData(mockData, SummaryPeriod.DAY, 7);
        assertTrue(graphPanelHandle.isCategoryChart());
    }

    private void setChartData(
            LinkedHashMap<String, Double> mockData,
            SummaryPeriod summaryPeriod,
            int periodAmount
    ) {
        GraphPanel graphPanel = new GraphPanel(mockData, summaryPeriod, periodAmount);
        graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
    }

}
