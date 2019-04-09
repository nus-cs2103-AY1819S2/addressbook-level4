package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Jackson-friendly version of {@link Job}.
 */
class JsonAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "job %s field is missing!";

    private final String jobName;
    private final List<JsonAdaptedJobPersonList> list1 = new ArrayList<>();
    private final List<JsonAdaptedJobPersonList> list2 = new ArrayList<>();
    private final List<JsonAdaptedJobPersonList> list3 = new ArrayList<>();
    private final List<JsonAdaptedJobPersonList> list4 = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedJob(@JsonProperty("jobName") String jobName,
                          @JsonProperty("list1") List<JsonAdaptedJobPersonList> list1,
                          @JsonProperty("list2") List<JsonAdaptedJobPersonList> list2,
                          @JsonProperty("list3") List<JsonAdaptedJobPersonList> list3,
                          @JsonProperty("list4") List<JsonAdaptedJobPersonList> list4) {

        this.jobName = jobName;
        if (list1 != null) {
            this.list1.addAll(list1);
        }
        if (list2 != null) {
            this.list2.addAll(list2);
        }
        if (list3 != null) {
            this.list3.addAll(list3);
        }
        if (list4 != null) {
            this.list4.addAll(list4);
        }
    }

    /**
     * Converts a given {@code Job} into this class for Jackson use.
     */
    public JsonAdaptedJob(Job source) {
        jobName = source.getName().fullName;
        list1.addAll(source.getPersonsNric(0).stream()
                .map(JsonAdaptedJobPersonList::new)
                .collect(Collectors.toList()));
        list2.addAll(source.getPersonsNric(1).stream()
                .map(JsonAdaptedJobPersonList::new)
                .collect(Collectors.toList()));
        list3.addAll(source.getPersonsNric(2).stream()
                .map(JsonAdaptedJobPersonList::new)
                .collect(Collectors.toList()));
        list4.addAll(source.getPersonsNric(3).stream()
                .map(JsonAdaptedJobPersonList::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Job toModelType(UniquePersonList Ab) throws IllegalValueException {
        final List<Nric> firstList = new ArrayList<>();
        final List<Nric> secondList = new ArrayList<>();
        final List<Nric> thirdList = new ArrayList<>();
        final List<Nric> fourthList = new ArrayList<>();

        for (JsonAdaptedJobPersonList nric : list1) {
            firstList.add(nric.toModelType());
        }
        Set<Nric> firstNricSet = new HashSet<>(firstList);
        UniquePersonList firstPList = new UniquePersonList();
        for (int i = 0; i < firstList.size(); i++) {
            Person tempPerson = Ab.getPerson(firstList.get(i));
            if (tempPerson == null) {
                continue;
            }
            firstPList.add(tempPerson);
        }

        for (JsonAdaptedJobPersonList nric : list2) {
            secondList.add(nric.toModelType());
        }
        Set<Nric> secondNricSet = new HashSet<>(secondList);
        UniquePersonList secondPList = new UniquePersonList();
        for (int i = 0; i < secondList.size(); i++) {
            Person tempPerson = Ab.getPerson(secondList.get(i));
            if (tempPerson == null) {
                continue;
            }
            secondPList.add(tempPerson);
        }

        for (JsonAdaptedJobPersonList nric : list3) {
            thirdList.add(nric.toModelType());
        }
        Set<Nric> thirdNricSet = new HashSet<>(thirdList);
        UniquePersonList thirdPList = new UniquePersonList();
        for (int i = 0; i < thirdList.size(); i++) {
            Person tempPerson = Ab.getPerson(thirdList.get(i));
            if (tempPerson == null) {
                continue;
            }
            thirdPList.add(tempPerson);
        }

        for (JsonAdaptedJobPersonList nric : list4) {
            fourthList.add(nric.toModelType());
        }
        Set<Nric> fourthNricSet = new HashSet<>(fourthList);
        UniquePersonList fourthPList = new UniquePersonList();
        for (int i = 0; i < fourthList.size(); i++) {
            Person tempPerson = Ab.getPerson(fourthList.get(i));
            if (tempPerson == null) {
                continue;
            }
            fourthPList.add(tempPerson);
        }

        ArrayList<UniquePersonList> personsHash = new ArrayList<> (4);
        personsHash.add(firstPList);
        personsHash.add(secondPList);
        personsHash.add(thirdPList);
        personsHash.add(fourthPList);
        ArrayList<Set<Nric>> personsNricList = new ArrayList<>(4);
        personsNricList.add(firstNricSet);
        personsNricList.add(secondNricSet);
        personsNricList.add(thirdNricSet);
        personsNricList.add(fourthNricSet);

        if (jobName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, JobName.class.getSimpleName()));
        }
        if (!JobName.isValidName(jobName)) {
            throw new IllegalValueException(JobName.MESSAGE_CONSTRAINTS);
        }
        final JobName modelName = new JobName(jobName);

        return new Job(modelName, personsHash, personsNricList);
    }

}
