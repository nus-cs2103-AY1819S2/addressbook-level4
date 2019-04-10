package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * The Browser Panel of the App.
 */
public class AnalyzePanel extends UiPart<Region> {

    private static final String FXML = "AnalyzePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ScatterChart<?, ?> scatterChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    public AnalyzePanel(ObservableList<Person> personList) {
        super(FXML);
        plotScatter(personList);
    }

    public void plotScatter(ObservableList<Person> personList) {
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < personList.size(); i++) {
            series.getData().add(new XYChart.Data(i+1, personList.get(i)));
        }

        scatterChart.getData().addAll(series);

        //Scene scene  = new Scene(scatterChart);
        //Stage stage = new Stage();
        //stage.setScene(scene);
        //stage.show();
    }


}
