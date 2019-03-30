package seedu.address.testutil;

import java.util.Arrays;
import java.util.LinkedList;

import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.Workload;

/**
 * A class that stores some examples of Typical Modules
 */
public class TypicalWorkloads {

    public static final Workload CS1010_WORKLOAD = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("2"));

    public static final Workload CS2030_WORKLOAD = new Workload(new Hour("2"), new Hour("3"), new Hour("4"),
            new Hour("5"), new Hour("6"));

    public static final Workload CS2040_WORKLOAD = new Workload(new Hour("1"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2040C_WORKLOAD = new Workload(new Hour("2"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2103T_WORKLOAD = new Workload(new Hour("2"), new Hour("5"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload MA1512_WORKLOAD = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload LSM1301_WORKLOAD = new Workload(new Hour("1"), new Hour("1"), new Hour("2"),
            new Hour("1"), new Hour("8"));

    private TypicalWorkloads() {
    }

    public static LinkedList<Workload> getTypicalWorkloads() {
        return new LinkedList<>(Arrays.asList(CS2030_WORKLOAD, CS2040_WORKLOAD, CS2040C_WORKLOAD,
                CS2103T_WORKLOAD, MA1512_WORKLOAD, LSM1301_WORKLOAD));
    }
}
