package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.interviews.Interviews;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.UniqueFilterList;
import seedu.address.testutil.JobBuilder;

public class CreateJobCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJob_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateJobCommand(null);
    }

    @Test
    public void execute_jobAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingJobAdded modelStub = new ModelStubAcceptingJobAdded();
        Job validJob = new JobBuilder().build();

        CommandResult commandResult = new CreateJobCommand(validJob).execute(modelStub, commandHistory);

        assertEquals(String.format(CreateJobCommand.MESSAGE_SUCCESS, validJob), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validJob), modelStub.jobsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateJob_throwsCommandException() throws Exception {
        Job validJob = new JobBuilder().build();
        CreateJobCommand createJobCommand = new CreateJobCommand(validJob);
        ModelStub modelStub = new ModelStubWithJob(validJob);

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateJobCommand.MESSAGE_DUPLICATE_JOB);
        createJobCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Job teacher = new JobBuilder().withName("Teacher").build();
        Job professor = new JobBuilder().withName("Professor").build();
        CreateJobCommand addTeacherCommand = new CreateJobCommand(teacher);
        CreateJobCommand addProfessorCommand = new CreateJobCommand(professor);

        // same object -> returns true
        assertTrue(addTeacherCommand.equals(addTeacherCommand));

        // same values -> returns true
        CreateJobCommand addTeacherCommandCopy = new CreateJobCommand(teacher);
        assertTrue(addTeacherCommand.equals(addTeacherCommandCopy));

        // different types -> returns false
        assertFalse(addTeacherCommand.equals(1));

        // null -> returns false
        assertFalse(addTeacherCommand.equals(null));

        // different person -> returns false
        assertFalse(addTeacherCommand.equals(addProfessorCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public void setSelectedSelected(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getJobsList(JobListName listNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedAll(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedKiv(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedInterviewed(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueFilterList getPredicateLists(JobListName listName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getAllJobs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearJobFilteredLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearJobFilteredLists(JobListName listName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removePredicate(String predicateName, JobListName listName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonLists(JobListName listname) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyProperty<Job> selectedJobProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getIsAllJobScreen() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIsAllJobScreen(boolean staus) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateBaseFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void generateInterviews() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Interviews getInterviews() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void clearInterviews() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setMaxInterviewsADay(int maxInterviewsADay) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setBlockOutDates(List<Calendar> blockOutDates) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Analytics generateAnalytics(JobListName listName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Analytics generateAnalytics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFilteredPersonsToJob(JobName jobName, JobListName from, JobListName to) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPersonToJob(Job job, Person person, JobListName list) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void revertList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Integer movePerson(Job job, Person person, Integer source, Integer dest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Job getJob(JobName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getFilteredJobList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Job getActiveJob() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPredicate(String predicateName, Predicate<Person> predicate, JobListName listname) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getBaseFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePersonFromJobList(Person toRemove, JobName job, JobListName list) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithJob extends ModelStub {
        private final Job job;

        ModelStubWithJob(Job job) {
            requireNonNull(job);
            this.job = job;
        }

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return this.job.isSameJob(job);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingJobAdded extends ModelStub {
        final ArrayList<Job> jobsAdded = new ArrayList<>();

        @Override
        public boolean hasJob(Job job) {
            requireNonNull(job);
            return jobsAdded.stream().anyMatch(job::isSameJob);
        }

        @Override
        public void updateFilteredPersonLists(JobListName listname) {
        }

        @Override
        public void addJob(Job job) {
            requireNonNull(job);
            jobsAdded.add(job);
        }


        @Override
        public void addFilteredPersonsToJob(JobName jobName, JobListName to, JobListName from) {

        }

        public void updateFilteredPersonList(Predicate<Person> predicate) {
        }

        public void updateFilteredPersonList() {

        }

        @Override
        public ObservableList<Person> getBaseFilteredPersonList() {
            return null;
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
