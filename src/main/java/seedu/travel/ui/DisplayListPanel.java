package seedu.travel.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.ChartBook;
import seedu.travel.model.place.Place;

/**
 * Panel containing the list of places.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayListPanel.class);
    private ObservableList<Place> placeList;

    @FXML
    private ListView<ChartBook> displayListView;

    public DisplayListPanel(ObservableList<Place> placeList) {
        super(FXML);
        this.placeList = placeList;
        ObservableList<ChartBook> chartBooks = FXCollections.observableArrayList();
        chartBooks.add(new ChartBook(placeList));
        displayListView.setItems(chartBooks);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());

        placeList.addListener(new ListChangeListener<Place>() {
            @Override
            public void onChanged(Change<? extends Place> c) {
                logger.info("Chart detected changes to the place list");
                ObservableList<ChartBook> chartBooks = FXCollections.observableArrayList();
                chartBooks.add(new ChartBook(placeList));
                displayListView.setItems(chartBooks);
                displayListView.setCellFactory(listView -> new DisplayListViewCell());
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Place} using a {@code DisplayCard}.
     */
    class DisplayListViewCell extends ListCell<ChartBook> {
        @Override
        protected void updateItem(ChartBook chartBook, boolean empty) {
            super.updateItem(chartBook, empty);

            if (empty || chartBook == null || placeList.isEmpty()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(chartBook).getRoot());
            }
        }
    }

}
