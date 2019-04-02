package seedu.travel.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.chart.Chart;
import seedu.travel.model.place.Place;

/**
 * Panel containing the list of places.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayListPanel.class);

    @FXML
    private ListView<Chart> displayListView;

    public DisplayListPanel(ObservableList<Place> placeList) {
        super(FXML);

        if (placeList.size() == 0) {
            return;
        }
        ObservableList<Chart> charts = FXCollections.observableArrayList();
        charts.add(new Chart(placeList));
        displayListView.setItems(charts);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());
        System.out.println("generate outer");

        placeList.addListener(new ListChangeListener<Place>() {
            @Override
            public void onChanged(Change<? extends Place> c) {
                if (placeList.size() == 0) {
                    return;
                }
                ObservableList<Chart> charts = FXCollections.observableArrayList();
                charts.add(new Chart(placeList));
                displayListView.setItems(charts);
                displayListView.setCellFactory(listView -> new DisplayListViewCell());
                System.out.println("generate inner 1");
            }
        });

        charts.addListener(new ListChangeListener<Chart>() {
            @Override
            public void onChanged(Change<? extends Chart> c) {
                if (placeList.size() == 0) {
                    return;
                }
                ObservableList<Chart> charts = FXCollections.observableArrayList();
                charts.add(new Chart(placeList));
                displayListView.setItems(charts);
                displayListView.setCellFactory(listView -> new DisplayListViewCell());
                System.out.println("generate inner 2");
            }
        });

        // disables selection in list view
        displayListView.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Place} using a {@code DisplayCard}.
     */
    class DisplayListViewCell extends ListCell<Chart> {
        @Override
        protected void updateItem(Chart chart, boolean empty) {
            super.updateItem(chart, empty);

            if (empty || chart == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(chart).getRoot());
            }
        }
    }

}
