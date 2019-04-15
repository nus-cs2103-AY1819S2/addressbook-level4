package seedu.finance.ui;

import java.util.LinkedHashMap;
import java.util.Set;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.finance.logic.commands.SummaryCommand.SummaryPeriod;

/**
 * The Summary Panel of the App.
 */
public class SummaryPanel extends UiPart<Region> {
    //Not sure about the page urls, KIV most likely have to edit again
    //Want to link to GraphPage.html which is currently under main/docs

    private static final String FXML = "SummaryPanel.fxml";

    private static final String[] PIE_CHART_COLOUR_STYLES =
        { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "gray", "purple",
            "coral", "tan", "turquoise", "lightGray", "lightSkyBlue", "hotPink", "peachPuff", "cadetBlue" };

    @FXML
    private StackPane chartArea;


    /**
     * Constructs a {@code SummaryPanel} object with parameters.
     *
     * @param summaryData   a map with key and value pairs representing data for the charts.
     * @param summaryPeriod a {@code SummaryPeriod} enum representing the summary period
     * @param periodAmount  an int representing the period amount
     */
    public SummaryPanel(
            LinkedHashMap<String, Double> summaryData,
            SummaryPeriod summaryPeriod,
            int periodAmount
    ) {
        super(FXML);
        setData(summaryData, summaryPeriod, periodAmount);
    }

    /**
     * Populates {@code chartArea} according to the parameters.
     * If {@code summaryData} is empty, {@code chartArea} will be a message
     * saying that there are no expenditures.
     *
     * @param summaryData   a map with key and value pairs representing data for the charts.
     * @param summaryPeriod a {@code SummaryPeriod} enum representing the summary period
     * @param periodAmount  an int representing the period amount
     */
    public void setData(
            LinkedHashMap<String, Double> summaryData,
            SummaryPeriod summaryPeriod,
            int periodAmount
    ) {
        chartArea.getChildren().clear();
        if (summaryData.size() == 0) {
            Text text = new Text();
            if (summaryPeriod == SummaryPeriod.DAY) {
                if (periodAmount == 1) {
                    text.setText("There are no recorded expenditures in the past day");
                } else {
                    text.setText("There are no recorded expenditures in the past " + periodAmount + " days");
                }
            } else {
                if (periodAmount == 1) {
                    text.setText("There are no recorded expenditures in the past month");

                } else {
                    text.setText("There are no recorded expenditures in the past " + periodAmount + " months");
                }
            }
            text.setStyle("-fx-font-size: 18;");
            text.setFill(Color.GRAY);
            chartArea.getChildren().add(text);
        } else {
            setSummaryData(summaryData);
        }
    }

    /**
     * Populates {@code chartArea} according to the parameters. {@code chartArea} will be a pie chart.
     *
     * @param summaryData a map with key and value pairs representing data for the charts.
     */
    public void setSummaryData(LinkedHashMap<String, Double> summaryData) {
        PieChart pieChart = new PieChart();

        Set<String> keySet = summaryData.keySet();
        for (String s : keySet) {
            pieChart.getData().add(new PieChart.Data(s, summaryData.get(s)));
        }


        for (int i = 0; i < pieChart.getData().size(); i++) {
            PieChart.Data data = pieChart.getData().get(i);
            data.getNode().getStyleClass().add(getPieChartColorStyleFor(data.getName()));
            data.nameProperty().bind(Bindings.concat(data.getName(), " - $",
                    String.format("%.2f", data.getPieValue())));
        }


        pieChart.setLegendSide(Side.BOTTOM);

        chartArea.getChildren().add(pieChart);
    }

    private String getPieChartColorStyleFor(String categoryName) {
        return PIE_CHART_COLOUR_STYLES[Math.abs(categoryName.hashCode() % 23) % PIE_CHART_COLOUR_STYLES.length];
    }

}
