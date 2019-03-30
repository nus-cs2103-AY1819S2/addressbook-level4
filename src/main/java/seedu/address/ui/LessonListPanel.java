package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;

/**
 * Panel containing the list of lessons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;

    public LessonListPanel(List<Lesson> lessons) {
        super(FXML);
        lessonListView.setItems(FXCollections.observableList(lessons));
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
        /*lessonListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in lesson list panel changed to : '" + newValue + "'");
            onSelectedLessonChange.accept(newValue);
        });
        selectedLesson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected lesson changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected lesson,
            // otherwise we would have an infinite loop.
            if (Objects.equals(lessonListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                lessonListView.getSelectionModel().clearSelection();
            } else {
                int index = lessonListView.getItems().indexOf(newValue);
                lessonListView.scrollTo(index);
                lessonListView.getSelectionModel().clearAndSelect(index);
            }
        });*/
    }

    public void updateLessonList(List<Lesson> lessons) {
        lessonListView.setItems(FXCollections.observableList(lessons));
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}..
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }

}
