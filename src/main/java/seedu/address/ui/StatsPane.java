package seedu.address.ui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatsPane extends UiPart<Region> {

    public static final String SYNC_STATUS_UPDATED = "Elapsed Time: %s";

    //TOREMOVE private static Clock clock = Clock.systemDefaultZone();

    private static final String FXML = "StatsPane.fxml";

    private long startTime;

    @FXML
    private Label syncStatus;
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

    public StatsPane() {
        super(FXML);
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
    private void updateSyncStatus() {
       // String elapsedTime = convert(getElapsedTime(System.nanoTime()));
       // syncStatus.setText(String.format(SYNC_STATUS_UPDATED, elapsedTime));
    }

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
//    private String convert(long secondsToConvert) {
//        long millis = secondsToConvert * 1000;
//        long hours = TimeUnit.MILLISECONDS.toHours(millis);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1);
//
//        String format = String.format("%02d:%02d:%02d", Math.abs(hours), Math.abs(minutes), Math.abs(seconds));
//
//        return format;
//    }


}
