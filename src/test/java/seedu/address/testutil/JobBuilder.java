package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.predicate.UniqueFilterList;

/**
 * A utility class to help with building Person objects.
 */
public class JobBuilder {

    private static final int NUMBER_OF_LISTS = 4;
    public static final String DEFAULT_JOBNAME = "HELPER";


    private JobName name;
    private UniquePersonList personsInJob = new UniquePersonList();
    private ArrayList<UniquePersonList> personsList = new ArrayList<> (NUMBER_OF_LISTS);
    private ArrayList<Set<Nric>> personsNricList = new ArrayList<>(NUMBER_OF_LISTS);
    private ArrayList<UniqueFilterList> predicateList = new ArrayList<> (NUMBER_OF_LISTS);

    public JobBuilder() {
        name = new JobName(DEFAULT_JOBNAME);
        for (int i = 0; i < 4; i++) {
            personsList.add(new UniquePersonList());
            personsNricList.add(new HashSet<>());
        }
    }

    /**
     * Initializes the JobBuilder with the data of {@code jobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        name = jobToCopy.getName();
    }

    /**
     * Sets the {@code JobName} of the {@code Job} that we are building.
     */
    public JobBuilder withName(String name) {
        this.name = new JobName(name);
        return this;
    }

    public JobBuilder withPersonInList(Person person) {
        if(!personsInJob.contains(person)) {
            this.personsInJob.add(person);
        }
        for (int i = 0; i < 4; i++) {
            personsList.add(new UniquePersonList());
            personsNricList.add(new HashSet<>());
        }
        personsList.get(0).add(person);
        personsNricList.get(0).add(person.getNric());
        return this;
    }

    /***/
    public Job build() {
        return new Job(name, personsList, personsNricList, personsInJob);
    }

}
