package seedu.hms.logic.stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.ShowStatsCommand;
import seedu.hms.logic.stats.exceptions.ShownItemOutOfBoundException;
import seedu.hms.logic.stats.statsitems.CountPayersForReservations;
import seedu.hms.logic.stats.statsitems.CountPayersForServices;
import seedu.hms.logic.stats.statsitems.CountRoomTypes;
import seedu.hms.logic.stats.statsitems.CountServiceTypes;
import seedu.hms.logic.stats.statsitems.StatsItem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;

/**
 * Provide stats for a specific hms
 */
public class Stats {
    private final ReadOnlyHotelManagementSystem hms;
    private final ArrayList<StatsItem> statsitems;
    private ArrayList<Index> shown;
    private final ArrayList<Index> defaultShown;

    public Stats(ReadOnlyHotelManagementSystem hms) {
        this.hms = hms;
        this.statsitems = new ArrayList<>(Arrays.asList(
                new CountRoomTypes(this),
                new CountServiceTypes(this),
                new CountPayersForReservations(this),
                new CountPayersForServices(this)
        ));

        // initialize the max index for parsing commands
        ShowStatsCommand.setMaxIndex(this.statsitems.size());

        // initialize defaultShown
        ArrayList<Index> tempDefaultShown = new ArrayList<>();
        List<Integer> defaultShownIntList = IntStream.range(0, 4).boxed().collect(Collectors.toList());
        defaultShownIntList.forEach(n -> tempDefaultShown.add(Index.fromZeroBased(n)));
        this.defaultShown = new ArrayList<>(tempDefaultShown);

        // initialize shown
        this.shown = new ArrayList<>();
        this.shown.addAll(defaultShown);
    }

    /**
     * Generate a text report for all the StatsItems.
     * @return A string of the text report.
     */
    public String toTextReport() {
        try {
            final StringBuilder sb = new StringBuilder();
            for (Index i : shown) {
                StatsItem si = statsitems.get(i.getZeroBased());
                sb.append("*** " + i.getOneBased() + ". " + si.getTitle() + "\n");
                sb.append(si.toTextReport());
                sb.append("\n");
            }
            return sb.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new ShownItemOutOfBoundException();
        }
    }

    /**
     * Update all StatsItems.
     */
    public void update(Optional<ArrayList<Index>> optionalIndexList) {
        if (optionalIndexList.isPresent()) {
            shown = optionalIndexList.get();
        } else {
            shown = defaultShown;
        }
        for (StatsItem si : statsitems) {
            si.updateMap();
        }
    }

    public ArrayList<Index> getShown() {
        return this.shown;
    }

    public ReadOnlyHotelManagementSystem getHms() {
        return hms;
    }

    public List<StatsItem> getStatsitems() {
        return statsitems;
    }
}
