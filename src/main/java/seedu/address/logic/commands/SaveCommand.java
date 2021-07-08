package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;

import seedu.address.storage.Storage;


/**
 * Lists all the commands entered by user from the start of app launch.
 *
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS_BETTER = "Statistics Analysis : Your accuracy improved!";
    public static final String MESSAGE_SUCCESS_WORST = "Statistics Analysis : Your accuracy was better last round!";
    public static final String MESSAGE_SUCCESS_SAME = "Statistics Analysis : Your accuracy is the same!";
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);
    private static PlayerStatistics playerStats;
    private Optional<PlayerStatistics> statisticsDataOptional;
    private Storage storage;

    public double getAccuracy(int hitCount, int missCount) {
        if (hitCount == 0 && missCount == 0) {
            return 0;
        }
        return (double) hitCount / (double) (hitCount + missCount);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);

        this.playerStats = model.getPlayerStats();
        this.storage = this.playerStats.getStorage();
        try {
            statisticsDataOptional = (this.storage).readStatisticsData();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Data will be reinitialized.");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Data will be reinitialized.");
        }
        // Retrieve previous statistics data (if any)
        int pastHit = (statisticsDataOptional.orElse(new PlayerStatistics())).getHitCount();
        int pastMiss = (statisticsDataOptional.orElse(new PlayerStatistics())).getMissCount();
        double pastAccuracy = getAccuracy(pastHit, pastMiss);

        try {
            this.playerStats.saveToStorage(this.playerStats);
        } catch (IOException ioe) {
            logger.warning("Problem while saving to filepath. Data will be reinitialized.");
        }
        return compareData(pastAccuracy, (this.playerStats).getAccuracy());
    }

    /**
     * Compares the previous and current data.
     * @param pastAccuracy
     * @param currentAccuracy
     * @return
     */
    CommandResult compareData(double pastAccuracy, double currentAccuracy) {
        if (pastAccuracy < currentAccuracy) {
            return new CommandResult(MESSAGE_SUCCESS_BETTER
                    + '\n'
                    + String.format("Current Game : %.1f%%", this.playerStats.getAccuracy() * 100)
                    + '\n'
                    + String.format("Previous Game : %.1f%%", pastAccuracy * 100));
        } else if (pastAccuracy == this.playerStats.getAccuracy()) {
            return new CommandResult(MESSAGE_SUCCESS_SAME
                    + '\n'
                    + String.format("Current Game : %.1f", pastAccuracy * 100));
        } else {
            return new CommandResult(MESSAGE_SUCCESS_WORST
                    + '\n'
                    + String.format("Current Game : %.1f%%", this.playerStats.getAccuracy() * 100)
                    + '\n'
                    + String.format("Previous Game : %.1f%%", pastAccuracy * 100));
        }
    }
}
