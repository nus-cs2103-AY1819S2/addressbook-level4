package seedu.finance.ui;

import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.model.budget.Budget;

//@@author Jackimaru96
/**
 * This class represents the budget panel for the finance tracker.
 * Shows currentSpending/totalBudget and the progress bar changes colour
 * depending on how close the user is to the budget set
 */
public class BudgetPanel extends UiPart<Region> {

    public static final String FXML = "BudgetPanel.fxml";
    private static final double ANIMATION_DURATION = 0.2;
    private static final double SIZE_OF_FONT = 35.0;
    private final Logger logger = LogsCenter.getLogger(BudgetPanel.class);
    private Timeline timeline;
    @FXML
    private ProgressBar budgetBar;
    @FXML
    private Text budgetDisplay;
    @FXML
    private Text spendingDisplay;
    @FXML
    private TextFlow percentageDisplay;
    private double currentSpending;
    private double currentTotalBudget;

    public BudgetPanel(Budget budget) {
        super(FXML);
        budgetDisplay = new Text(" / $0");
        spendingDisplay = new Text("$0 ");
        currentTotalBudget = 0;
        currentSpending = 0;
        budgetDisplay.setStyle("-fx-fill: #ffffff;");
        budgetDisplay.setFont(Font.font("Abel", SIZE_OF_FONT));
        spendingDisplay.setFont(Font.font("Abel", SIZE_OF_FONT));
        percentageDisplay.getChildren().addAll(spendingDisplay, budgetDisplay);

        update(budget);
    }

    /**
     * Called when light themes are set so that the
     * fonts are updated to correct contrast colour
     */
    public void setLightThemes(String colour) {
        switch (colour) {
        case "Pink":
            budgetDisplay.setStyle("-fx-fill: #ff82c2;");
            break;
        case "Blue":
            budgetDisplay.setStyle("-fx-fill: #4a5aff;");
            break;
        default:
            budgetDisplay.setStyle("-fx-fill: #000000;");
            break;
        }
    }

    /**
     * Called when dark theme is set so that the
     * fonts are updated to correct contrast colour
     */
    public void setDarkTheme() {
        budgetDisplay.setStyle("-fx-fill:#ffffff;");
    }

    /**
     * Updates the totalBudget and currentSpending
     * @param budget to update bugetPanel with
     */
    public void update(Budget budget) {

        double totalBudget = budget.getTotalBudget();
        double currentTotalSpending = budget.getCurrentSpendings();
        updateTotalBudget(totalBudget, currentTotalSpending);
        setBudgetUiColours(totalBudget, currentTotalSpending);
    }

    /**
     * Updates the percentage
     * @param totalBudget the total budget
     * @param currentSpending the total spending
     */
    public void updateTotalBudget(double totalBudget, double currentSpending) {
        double currentPercentage = currentSpending / totalBudget;
        if (currentPercentage >= 1.00) {
            currentPercentage = 1.00;
        }
        if (totalBudget == 0) {
            currentPercentage = 1;
        }
        updateBudgetPanel(currentSpending, totalBudget, currentPercentage);
    }

    /**
     * Update budgetDisplay to the input budget
     * @param totalBudget to display on totalBudget
     */
    public void updateTotalBudgetTextDisplay(double totalBudget) {
        this.budgetDisplay.setText(" / $" + String.format("%.2f", totalBudget));
    }

    /**
     * Update spendingDisplay to input spending
     * @param spending to display on currentSpending
     */
    public void updateCurrentSpendingTextDisplay(double spending) {
        this.spendingDisplay.setText("$" + String.format("%.2f", spending));
    }

    /**
     * Changes the colours of spendingDisplay and budgetBar to red if over budget.
     * If spending is below budget, set the above to green.
     * @param totalBudget total budget now
     * @param currentSpending total spending now
     */
    public void setBudgetUiColours(double totalBudget, double currentSpending) {
        double percentage = currentSpending / totalBudget;
        // Exceeded budget, red for exceeding
        if (totalBudget < currentSpending) {
            spendingDisplay.setStyle("-fx-fill: #ae0807;");
            budgetBar.setStyle("-fx-accent: derive(#ae0807, 20%);");
            return;
        }

        // Close to exceeding budget; orange for warning
        if (percentage <= 1 && percentage >= 0.8) {
            spendingDisplay.setStyle("-fx-fill: #ff972d;");
            budgetBar.setStyle("-fx-accent: derive(#ff972d, 20%);");
            return;
        }
        // default, within budget green
        spendingDisplay.setStyle("-fx-fill: #14a124;");
        budgetBar.setStyle("-fx-accent: derive(#14a124, 20%);");
    }

    /**
     * method to update the budget panel to show updated budget
     * @param currentSpending the total current spending now
     * @param totalBudget the total budget for finance tracker
     * @param percentage the percentage (currentSpending/totalBudget0
     */
    public void updateBudgetPanel(double currentSpending, double totalBudget, double percentage) {
        timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(budgetBar.progressProperty(), budgetBar.getProgress())
                ),
                new KeyFrame(
                        Duration.seconds(ANIMATION_DURATION),
                        new KeyValue(budgetBar.progressProperty(), percentage)
                )
        );

        timeline.setOnFinished(stage -> {
            this.currentSpending = currentSpending;
            this.currentTotalBudget = totalBudget;
            updateCurrentSpendingTextDisplay(currentSpending);
            updateTotalBudgetTextDisplay(totalBudget);
            alterTextSize();
            timeline.stop();
        });

        timeline.playFromStart();

    }

    /**
     * Alter the size of texts
     */
    public void alterTextSize() {
        percentageDisplay.setScaleY(percentageDisplay.getMaxHeight() / percentageDisplay.getHeight());
        percentageDisplay.setScaleX(percentageDisplay.getMaxHeight() / percentageDisplay.getHeight());
    }
}
