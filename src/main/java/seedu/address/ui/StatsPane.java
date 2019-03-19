package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;


/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatsPane extends UiPart<Region> {

    public static final String SYNC_STATUS_UPDATED = "Attacks Made: %s";

    //TOREMOVE private static Clock clock = Clock.systemDefaultZone();

    private static final String FXML = "StatsPane.fxml";
    // private PlayerStatistics playerStats = playerStats;
    private long startTime;

    @FXML
    private Label shipCount;
    @FXML
    private Label saveLocationStatus;

    //    private Timer myTimer = new Timer();
    //    private TimerTask task = new TimerTask() {
    //
    //        @Override
    //        public void run() {
    //            Platform.runLater(()-> {
    //                updateSyncStatus();
    //            });
    //        }
    //    };

    public StatsPane() { //StatsPane(PlayerStatistics playerStats)
        super(FXML);
        updateStats();
        //this.startTime = System.nanoTime();
        // Upon init of map, trigger this.
        //myTimer.scheduleAtFixedRate(task, 1000, 1000);
    }

    /**
     * Stops the internal Timer that increments the time display.
     */
    //  public void stopTimer() {
    //      myTimer.cancel();
    //  }

    /**
     * Updates elapsed time
     */
    private void updateStats() {
       // String elapsedTime = convert(getElapsedTime(System.nanoTime()));
       // shipCount.setText(String.format(SYNC_STATUS_UPDATED, "5"));
    }


    // command to get attacks made
    // private int getStats(){

    //     return
    // }
    /**
     *
     * @param endTime
     * @return
     */
    //    public long getElapsedTime(long endTime) {
    //        long elapsedTimeSeconds = (endTime - this.startTime) / (1000000000);
    //        return elapsedTimeSeconds;
    //    }

    /**
     * @param secondsToConvert in seconds to convert
     */



}
