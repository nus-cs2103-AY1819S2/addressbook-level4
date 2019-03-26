package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job ENGINEER = new JobBuilder().withName("Engineer").build();

    public static final Job PROFESSOR = new JobBuilder().withName("Professor").build();

    // Manually added
    public static final Job SOLDIER = new JobBuilder().withName("Soldier").build();

    public static final Job PASTOR = new JobBuilder().withName("Pastor").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Job TEACHER = new JobBuilder().withName(VALID_JOB_NAME_TEACHER).build();

    public static final String KEYWORD_MATCHING_PILOT = "Pilot"; // A keyword that matches PILOT

    private TypicalJobs() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical jobs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Job jobs : getTypicalJobs()) {
            ab.addJob(jobs);
        }
        return ab;
    }

    public static List<Job> getTypicalJobs() {
        return new ArrayList<>(Arrays.asList(TEACHER, PROFESSOR, SOLDIER, PASTOR, ENGINEER, ENGINEER));
    }
}
