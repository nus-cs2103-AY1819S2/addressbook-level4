package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.interviews.Interviews;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.job.UniqueJobList;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueNricMap;
import seedu.address.model.person.UniquePersonList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueNricMap nrics;
    private final UniquePersonList persons;
    private final UniqueJobList jobs;
    private final Interviews interviews;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        nrics = new UniqueNricMap();
        jobs = new UniqueJobList();
        interviews = new Interviews();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        this.nrics.setNricMap(persons);
        indicateModified();
    }

    public void setInterviews(Interviews interviews) {
        this.interviews.setInterviews(interviews);
    }

    public void setJobs(List<Job> jobs) {
        this.jobs.setJobs(jobs);
        indicateModified();
    }

    public void setBlockOutDates(List<Calendar> blockOutDates) {
        this.interviews.setBlockOutDates(blockOutDates);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setInterviews(newData.getInterviews());
        setJobs(newData.getJobList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        nrics.add(p.getNric(), p);
        indicateModified();
    }

    /**
     * Adds a person to the job.
     * The person must not already exist in the job.
     * Adds to the first list
     */
    public boolean addPersonToJobByNric(Nric nric, JobName jobName) {
        Person person = persons.getPerson(nric);
        Job job = jobs.getJob(jobName);
        boolean status = job.add(person);
        this.jobs.setJob(job, job);
        indicateModified();

        return status;
    }

    /**
     * Adds a person to the job.
     * The person must not already exist in the job.
     * Adds to the first list
     * This version directly adds from job
     */
    public void addFilteredListToJob(FilteredList<Person> filteredPersons, JobName jobName) {
        Job job = jobs.getJob(jobName);
        job.addFilteredList(filteredPersons);
        this.jobs.setJob(job, job);
        indicateModified();
    }

    /**
     * Retrieves a job in the addressbook
     */
    public Job getJob(JobName jobName) {
        return jobs.getJob(jobName);
    }

    /**
     * Adds a person to the job.
     * The person must not already exist in the job.
     * Adds to the first list
     * This version directly adds from job
     */
    public void addPersonToJob(Person person, Job job) {
        job.add(person);
        indicateModified();
    }


    /**
     * Adds a job to the address book.
     * The job must not already exist in the address book.
     */
    public void addJob(Job j) {
        jobs.add(j);
        indicateModified();
    }

    /**
     * Deletes a job in the address book.
     * The job must exist in the address book.
     */
    public void deleteJob(Job j) {
        jobs.remove(j);
        indicateModified();
    }

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    public int movePerson(JobName jobName, Nric nric, int source, int dest) {
        requireNonNull(jobName);
        requireNonNull(nric);
        requireNonNull(source);
        requireNonNull(dest);

        Person person = persons.getPerson(nric);
        Job job = jobs.getJob(jobName);

        return job.move(person, source, dest);
    }

    /**
     * Returns true if a job with the same identity as {@code job} exists in the address book.
     */
    public boolean hasJob(Job job) {
        requireNonNull(job);
        return jobs.contains(job);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        nrics.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Replaces the given job {@code target} in the list with {@code editedJob}.
     * {@code target} must exist in the address book.
     */
    public void setJob(Job target, Job editedJob) {
        requireNonNull(editedJob);

        jobs.setJob(target, editedJob);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        nrics.remove(key.getNric());
        interviews.removePerson(key);
        indicateModified();
    }

    public ObservableList<Job> getJobList() {
        return jobs.asUnmodifiableObservableList();
    }

    public UniquePersonList getUniquePersonList() {
        return persons;
    }

    /**
     * Generates interviews
     */
    public void generateInterviews() {
        interviews.generate(getPersonList());
    }

    public Interviews getInterviews() {
        return interviews;
    }

    public void setMaxInterviewsADay(int maxInterviewsADay) {
        interviews.setMaxInterviewsADay(maxInterviewsADay);
    }

    public void clearInterviews() {
        interviews.clear();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    public ObservableList<Job> getAllJobList() {
        return jobs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressBook // instanceof handles nulls
            && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return nrics.hashCode();
    }
}

