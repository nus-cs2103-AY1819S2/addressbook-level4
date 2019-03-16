package seedu.address.testutil;

import java.util.Arrays;
import java.util.LinkedList;

import seedu.address.model.module.Hour;
import seedu.address.model.module.Workload;

/**
 * A class that stores some examples of Typical Modules
 */
public class TypicalWorkloads {

    public static final Workload CS1010WORKLOAD = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("2"));

    public static final Workload CS2030WORKLOAD = new Workload(new Hour("2"), new Hour("3"), new Hour("4"),
            new Hour("5"), new Hour("6"));

    public static final Workload CS2040WORKLOAD = new Workload(new Hour("1"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2040CWORKLOAD = new Workload(new Hour("2"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2103TWORKLOAD = new Workload(new Hour("2"), new Hour("5"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload MA1512WORKLOAD = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload LSM1301WORKLOAD = new Workload(new Hour("1"), new Hour("1"), new Hour("2"),
            new Hour("1"), new Hour("8"));

    private TypicalWorkloads() {
    }

    public static LinkedList<Workload> getTypicalWorkloads() {
        return new LinkedList<>(Arrays.asList(CS2030WORKLOAD, CS2040WORKLOAD, CS2040CWORKLOAD,
                CS2103TWORKLOAD, MA1512WORKLOAD, LSM1301WORKLOAD));
    }
}
