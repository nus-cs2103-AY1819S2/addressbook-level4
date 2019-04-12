package seedu.address.model.job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;
import static seedu.address.testutil.TypicalObjects.ALICE;
import static seedu.address.testutil.TypicalObjects.ENGINEER;
import static seedu.address.testutil.TypicalObjects.TEACHER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.testutil.JobBuilder;

public class UniqueJobListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueJobList uniqueJobList = new UniqueJobList();

    @Test
    public void contains_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.contains(null);
    }

    @Test
    public void contains_jobNotInList_returnsFalse() {
        assertFalse(uniqueJobList.contains(TEACHER));
    }

    @Test
    public void contains_jobInList_returnsTrue() {
        uniqueJobList.add(TEACHER);
        assertTrue(uniqueJobList.contains(TEACHER));
    }

    @Test
    public void add_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.add(null);
    }

    @Test
    public void add_duplicateJob_throwsDuplicateJobException() {
        uniqueJobList.add(TEACHER);
        thrown.expect(DuplicateJobException.class);
        uniqueJobList.add(TEACHER);
    }

    @Test
    public void setJob_nullTargetJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJob(null, TEACHER);
    }

    @Test
    public void setJob_nullEditedJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.setJob(TEACHER, null);
    }

    @Test
    public void setJob_targetJobNotInList_throwsJobNotFoundException() {
        thrown.expect(JobNotFoundException.class);
        uniqueJobList.setJob(TEACHER, TEACHER);
    }

    @Test
    public void setJob_editedJobIsSameJob_success() {
        uniqueJobList.add(TEACHER);
        uniqueJobList.setJob(TEACHER, TEACHER);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(TEACHER);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setJob_editedJobHasSameIdentity_success() {
        uniqueJobList.add(TEACHER);
        Job editedTeacher = new JobBuilder(TEACHER).build();
        uniqueJobList.setJob(TEACHER, editedTeacher);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(TEACHER);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueJobList.add(TEACHER);
        uniqueJobList.setJob(TEACHER, ENGINEER);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        expectedUniqueJobList.add(ENGINEER);
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueJobList.remove(null);
    }

    @Test
    public void remove_jobDoesNotExist_throwsJobNotFoundException() {
        thrown.expect(JobNotFoundException.class);
        uniqueJobList.remove(TEACHER);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueJobList.add(TEACHER);
        uniqueJobList.remove(TEACHER);
        UniqueJobList expectedUniqueJobList = new UniqueJobList();
        assertEquals(expectedUniqueJobList, uniqueJobList);
    }

    @Test
    public void checkGetJob() {
        uniqueJobList.add(TEACHER);
        JobName name = new JobName(VALID_JOB_NAME_TEACHER);
        Job job = uniqueJobList.getJob(name);
        assertTrue(job.isSameJob(TEACHER));
    }

    @Test
    public void testAddPersonToJob() {
        Job job = new JobBuilder(TEACHER).withPersonInList(ALICE).build();

        assertTrue(job.contains(ALICE));
    }

    @Test
    public void testDeletePerson() {
        Job job = new JobBuilder(TEACHER).withPersonInList(ALICE).build();
        uniqueJobList.add(job);
        uniqueJobList.removePerson(ALICE);
        Job editedJob = uniqueJobList.getJob(new JobName(VALID_JOB_NAME_TEACHER));

        assertTrue(!editedJob.contains(ALICE));
    }

}
