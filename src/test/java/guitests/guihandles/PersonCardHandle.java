package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String RACE_FIELD_ID = "#race";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String SCHOOL_FIELD_ID = "#school";
    private static final String MAJOR_FIELD_ID = "#major";
    private static final String GRADE_FIELD_ID = "#grade";
    private static final String PASTJOBS_FIELD_ID = "#pastjobs";
    private static final String JOBSAPPLY_FIELD_ID = "#jobsApply";
    private static final String INTERVIEWSCORES_FIELD_ID = "#interviewScores";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label nricLabel;
    private final Label genderLabel;
    private final Label raceLabel;
    private final Label schoolLabel;
    private final Label majorLabel;
    private final Label gradeLabel;
    private final Label interviewScoresLabel;
    private final List<Label> pastjobLabels;
    private final List<Label> jobsApplyLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        schoolLabel = getChildNode(SCHOOL_FIELD_ID);
        majorLabel = getChildNode(MAJOR_FIELD_ID);
        raceLabel = getChildNode(RACE_FIELD_ID);
        nricLabel = getChildNode(NRIC_FIELD_ID);
        genderLabel = getChildNode(GENDER_FIELD_ID);
        gradeLabel = getChildNode(GRADE_FIELD_ID);
        interviewScoresLabel = getChildNode(INTERVIEWSCORES_FIELD_ID);

        Region pastjobsContainer = getChildNode(PASTJOBS_FIELD_ID);
        pastjobLabels = pastjobsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region jobsApplyContainer = getChildNode(JOBSAPPLY_FIELD_ID);
        jobsApplyLabel = jobsApplyContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getNric() {
        return nricLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }

    public String getRace() {
        return raceLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getSchool() {
        return schoolLabel.getText();
    }

    public List<String> getPastJobs() {
        return pastjobLabels
            .stream()
            .map(Label::getText)
            .collect(Collectors.toList());
    }

    public String getMajor() {
        return majorLabel.getText();
    }

    public String getGrade() {
        return gradeLabel.getText();
    }

    public String getInterviewScores() {
        return interviewScoresLabel.getText();
    }

    public List<String> getJobsApply() {
        return jobsApplyLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        return getName().equals(person.getName().fullName)
                && getAddress().equals(person.getAddress().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getNric().equals(person.getNric().value)
                && getGender().equals(person.getGender().value)
                && getRace().equals(person.getRace().value)
                && getSchool().equals(person.getSchool().value)
                && ImmutableMultiset.copyOf(getPastJobs()).equals(ImmutableMultiset.copyOf(person.getPastJobs().stream()
                .map(pastjob -> pastjob.value).collect(Collectors.toList())))
                && getMajor().equals(person.getMajor().value)
                && getGrade().equals(person.getGrade().value)
                && ImmutableMultiset.copyOf(getJobsApply()).equals(ImmutableMultiset
                .copyOf(person.getJobsApply().stream().map(jobsApply -> jobsApply.value).collect(Collectors.toList())))
                && getInterviewScores().equals(person.getInterviewScores().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(person.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));

    }
}
