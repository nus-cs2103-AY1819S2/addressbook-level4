package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * A panel class to display member's information.
 */
public class MemberDetailPanel extends UiPart<Region> {
    private static final String FXML = "MemberDetailPanel.fxml";

    private Logic logic;

    @FXML
    private Text name;

    @FXML
    private Text matricNumber;

    @FXML
    private Text phone;

    @FXML
    private Text email;

    @FXML
    private Text address;

    @FXML
    private Text gender;

    @FXML
    private Text yearOfStudy;

    @FXML
    private Text major;

    @FXML
    private Text statistics;

    @FXML
    private Text attendedActivitiesCounter;

    @FXML
    private Text participationRate;

    public MemberDetailPanel (ObservableValue<Person> selectedMember, Logic logic) {
        super(FXML);
        this.logic = logic;
        getRoot().setOnKeyPressed(Event::consume);

        // Load person page when selected person changes.
        selectedMember.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadMemberDetails(newValue);
        });

        loadDefaultPage();
    }
    /**
     * Set up all patient details into the display panel.
     * @param member The patient to be displayed.
     */
    private void loadMemberDetails(Person member) {

        name.setText(member.getName().toString());
        matricNumber.setText("Matric Number: " + member.getMatricNumber().toString());
        phone.setText("Phone: " + member.getPhone().toString());
        email.setText("Email: " + member.getEmail().toString());
        address.setText("Address: " + member.getAddress().toString());
        gender.setText("Gender: " + member.getGender().toString());
        yearOfStudy.setText("Year of study: " + member.getYearOfStudy().toString());
        major.setText("Major: " + member.getMajor().toString());
        statistics.setText("Member Statistics");
        int attendedCounter = logic.getAttendedActivitiesCounter(member);
        attendedActivitiesCounter.setText("Number of Activities Attended By Member: "
                + Integer.toString(attendedCounter));
        int memberParticipationRate = logic.getParticipationRate(member);
        String rateOfParticipation = Integer.toString(memberParticipationRate);
        rateOfParticipation = rateOfParticipation.concat("%");
        participationRate.setText("Participation Rate:\n"
                + "(Number of activities completed by member\n / total number of completed activities) = "
                + rateOfParticipation);
    }


    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        name.setText("");
        matricNumber.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        gender.setText("");
        yearOfStudy.setText("");
        major.setText("");
        statistics.setText("");
        attendedActivitiesCounter.setText("");
        participationRate.setText("");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        // state check
        MemberDetailPanel detail = (MemberDetailPanel) other;
        return name.getText().equals(detail.name.getText())
                && matricNumber.getText().equals(matricNumber);
    }

}
