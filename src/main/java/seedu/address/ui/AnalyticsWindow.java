package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.analytics.Analytics;

/**
 * Controller for a analytics page
 */
public class AnalyticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(AnalyticsWindow.class);
    private static final String FXML = "AnalyticsChart.fxml";

    @FXML
    private BarChart jobApplicationsChart;
    @FXML
    private BarChart interviewScoresChart;
    @FXML
    private TextArea gradeText;
    @FXML
    private PieChart genderChart;
    @FXML
    private PieChart raceChart;
    @FXML
    private BarChart schoolChart;
    @FXML
    private BarChart majorChart;
    @FXML
    private BarChart pastJobsChart;
    
    /**
     * Creates a new AnalyticsWindow.
     *
     * @param root Stage to use as the root of the AnalyticsWindow.
     */
    public AnalyticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new AnalyticsWindow.
     */
    public AnalyticsWindow() {
        this(new Stage());
    }

    /**
     * Shows the Analytics window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(Analytics analytics) {
        logger.fine("Showing analytics results.");
        getRoot().show();
        jobApplicationsChart.setData(analytics.generateJobApplicationData());
        interviewScoresChart.setData(analytics.generateInterviewScoresData());
        gradeText.setText("Mean Grade: " + analytics.generateMeanGradeData());
        genderChart.setData(analytics.generateGenderData());
        raceChart.setData(analytics.generateRaceData());
        schoolChart.setData(analytics.generateSchoolData());
        majorChart.setData(analytics.generateMajorData());
        pastJobsChart.setData(analytics.generateJobApplicationData());
    }

    /**
     * Returns true if the analytics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the analytics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the analytics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
