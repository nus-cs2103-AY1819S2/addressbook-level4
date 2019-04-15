package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;

/**
 * UI component for a user story.
 */
public class ProjectUserStories extends UiPart<Region> {

    public static final String FXML = "ProjectUserStories.fxml";

    public static final String STATUS_COLOUR_RED = "redCircle";
    public static final String STATUS_COLOUR_GREEN = "greenCircle";
    public static final String STATUS_COLOUR_ORANGE = "orangeCircle";
    public static final String STATUS_COLOUR_BLACK = "blackCircle";


    private ObservableList<UserStory> userStories;

    private ObservableList<UserStoryCell> userStoryCells;

    @FXML
    private TableView<UserStoryCell> userStoryTableView;

    @FXML
    private TableColumn<UserStoryCell, String> indexCol;

    @FXML
    private TableColumn<UserStoryCell, String> priorityCol;

    @FXML
    private TableColumn<UserStoryCell, String> userCol;

    @FXML
    private TableColumn<UserStoryCell, String> functionCol;

    @FXML
    private TableColumn<UserStoryCell, String> reasonCol;

    @FXML
    private TableColumn<UserStoryCell, Circle> statusCol;

    public ProjectUserStories(ObservableList<UserStory> stories) {
        super(FXML);
        this.userStories = stories;
        userStoryCells = getUserStoryCells(userStories);
        userStoryTableView.setItems(userStoryCells);

        indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("importance"));
        functionCol.setCellValueFactory(new PropertyValueFactory<>("function"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("circle"));
    }

    public ObservableList<UserStoryCell> getUserStoryCells(ObservableList<UserStory> stories) {
        ObservableList<UserStoryCell> newList = FXCollections.observableArrayList();
        for (int i = 0; i < stories.size(); i++) {
            UserStoryCell newCell = new UserStoryCell(stories.get(i), i + 1);
            newList.add(newCell);
        }
        return newList;
    }

    /**
     * A cell which contains a user story.
     */
    public class UserStoryCell {

        private UserStory story;
        private Integer index;
        private Circle circle;

        public UserStoryCell(UserStory s, int index) {
            this.story = s;
            this.index = index;
            instantiateColour(s.getUserStoryStatus());
        }

        /**
         * Instantiates a colour of the circle by using the hashcode value of {@code status}.
         */
        private void instantiateColour(String status) {
            this.circle = new Circle(0, 0, 10);

            switch (status) {
            case Status.STATUS_COMPLETE:
                circle.setId(STATUS_COLOUR_GREEN);
                break;
            case Status.STATUS_ON_HOLD:
                circle.setId(STATUS_COLOUR_RED);
                break;
            case Status.STATUS_ONGOING:
                circle.setId(STATUS_COLOUR_ORANGE);
                break;
            default:
                circle.setId(STATUS_COLOUR_BLACK);
            }
        }

        public Circle getCircle() {
            return this.circle;
        }

        public String getIndex() {
            return this.index.toString();
        }

        public String getImportance() {
            return story.getUserStoryImportance();
        }

        public String getFunction() {
            return story.getUserStoryFunction();
        }

        public String getUser() {
            return story.getUserStoryUser();
        }

        public String getReason() {
            return story.getUserStoryReason();
        }

        public String getStatus() {
            return story.getUserStoryStatus();
        }
    }

}
