package seedu.address.testutil;

import java.util.Arrays;
import java.util.LinkedList;

import seedu.address.model.person.Hour;
import seedu.address.model.person.Workload;

/**
 * A class that stores some examples of Typical Modules
 */
public class TypicalWorkloads {

    public static final Workload CS1010Workload = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("2"));

    public static final Workload CS2030Workload = new Workload(new Hour("2"), new Hour("3"), new Hour("4"),
            new Hour("5"), new Hour("6"));

    public static final Workload CS2040Workload = new Workload(new Hour("1"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2040CWorkload = new Workload(new Hour("2"), new Hour("1"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload CS2103TWorkload = new Workload(new Hour("2"), new Hour("5"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload MA1512Workload = new Workload(new Hour("1"), new Hour("3"), new Hour("1"),
            new Hour("1"), new Hour("1"));

    public static final Workload LSM1301Workload = new Workload(new Hour("1"), new Hour("1"), new Hour("2"),
            new Hour("1"), new Hour("8"));

    private TypicalWorkloads() {
    }

    public static LinkedList<Workload> getTypicalWorkloads() {
        return new LinkedList<>(Arrays.asList(CS2030Workload, CS2040Workload, CS2040CWorkload,
                CS2103TWorkload, MA1512Workload, LSM1301Workload));
    }
}
