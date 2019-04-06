package seedu.finance.ui;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SummaryPanelHandle;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;

public class SummaryPanelTest extends GuiUnitTest {
    private SummaryPanelHandle summaryPanelHandle;

    @Before
    public void setUp() throws InterruptedException {
        SummaryPanel summaryPanel = new SummaryPanel(
                new LinkedHashMap<>(),
                SummaryPeriod.DAY,
                7
        );
        Thread.sleep(1000);
        uiPartRule.setUiPart(summaryPanel);
        summaryPanelHandle = new SummaryPanelHandle(summaryPanel.getRoot());
    }

    @Test
    public void noExpensesTextAppearsWhenNoExpenses() {
        assertTrue(summaryPanelHandle.isNoExpenseText());
    }

    @Test
    public void correctNoExpensesTextAppearsWhenNoExpenses() {
        setChartData(new LinkedHashMap<>(), SummaryPeriod.DAY, 1);
        assertTrue(summaryPanelHandle.isMatchingText("There are no recorded expenditures in the past day"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.DAY, 7);
        assertTrue(summaryPanelHandle.isMatchingText("There are no recorded expenditures in the past 7 days"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.MONTH, 1);
        assertTrue(summaryPanelHandle.isMatchingText("There are no recorded expenditures in the past month"));

        setChartData(new LinkedHashMap<>(), SummaryPeriod.MONTH, 7);
        assertTrue(summaryPanelHandle.isMatchingText("There are no recorded expenditures in the past 7 months"));
    }

    @Test
    public void categoryChartAppears() {
        LinkedHashMap<String, Double> mockData = new LinkedHashMap<>();
        mockData.put("Food", 6.00);
        setChartData(mockData, SummaryPeriod.DAY, 7);
        assertTrue(summaryPanelHandle.isCategoryChart());
    }

    private void setChartData(
            LinkedHashMap<String, Double> mockData,
            SummaryPeriod summaryPeriod,
            int periodAmount
    ) {
        SummaryPanel summaryPanel = new SummaryPanel(mockData, summaryPeriod, periodAmount);
        summaryPanelHandle = new SummaryPanelHandle(summaryPanel.getRoot());
    }

}
