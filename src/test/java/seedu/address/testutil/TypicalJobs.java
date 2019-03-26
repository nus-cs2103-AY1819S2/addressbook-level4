package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWSCORES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWSCORES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSAPPLY_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSAPPLY_TRADER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KNOWNPROGLANG_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASTJOB_PROFESSSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job TEACHER = new JobBuilder().withName("Teacher").build();

    public static final Job PROFESSOR = new JobBuilder().withName("Professor").build();

    // Manually added
    public static final Job SOLDIER = new JobBuilder().withName("Soldier").build();

    public static final Job PASTOR = new JobBuilder().withName("Pastor").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Job ENGINEER = new JobBuilder().withName("Engineer").build();

    public static final Job CHEF = new JobBuilder().withName("Chef").build();

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
        return new ArrayList<>(Arrays.asList(TEACHER, PROFESSOR, SOLDIER, PASTOR, ENGINEER, ENGINEER, CHEF));
    }
}
