package seedu.address.model.record;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * s
 */
public class ClinicStatistics {
    public static final LocalDate START_DATE = LocalDate.of(2019, 1, 1);
    private ArrayList<MonthStatistics> stats;

    public ClinicStatistics() {}

    public ClinicStatistics(ClinicStatistics toBeCopied) {
        this();

    }
}
