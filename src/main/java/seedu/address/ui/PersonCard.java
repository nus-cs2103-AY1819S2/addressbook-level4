package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.pdf.Pdf;

/**
 * An UI component that displays information of a {@code Pdf}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on PdfBook level 4</a>
     */

    public final Pdf pdf;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    //@FXML
    //private Label phone;
    //@FXML
    //private Label address;
    //@FXML
    //private Label email;
    @FXML
    private Label deadline;
    @FXML
    private FlowPane tags;

    public PersonCard(Pdf pdf, int displayedIndex) {
        super(FXML);
        this.pdf = pdf;
        id.setText(displayedIndex + ". ");
        name.setText(pdf.getName().getFullName());

        if (pdf.getDeadline().exists()) {
            deadline.setPadding(new Insets(4, 0, 0, 0));

            deadline.setText(pdf.getDeadline().getValue().format(DateTimeFormatter.ofPattern("dd MMM uuuu")));
            //Green = #008060
            //Orange = #b36b00
            //Red = #b30000

            if (pdf.getDeadline().isMet()) {
                deadline.setStyle("-fx-text-fill: white;"
                        + "-fx-background-color: #2952a3;"
                        + "-fx-padding: 1;"
                        + "-fx-border-radius: 3;"
                        + "-fx-background-radius: 3;"
                        + "-fx-label-padding: 0;");
            } else {
                if (pdf.getDeadline().getDaysToDeadline() > 7) {
                    deadline.setStyle("-fx-text-fill: white;"
                            + "-fx-background-color: #008060;"
                            + "-fx-padding: 1;"
                            + "-fx-border-radius: 3;"
                            + "-fx-background-radius: 3;"
                            + "-fx-label-padding: 0;");

                } else if (pdf.getDeadline().getDaysToDeadline() > 3 && pdf.getDeadline().getDaysToDeadline() > 0) {
                    deadline.setStyle("-fx-text-fill: white;"
                            + "-fx-background-color: #b36b00;"
                            + "-fx-padding: 1;"
                            + "-fx-border-radius: 3;"
                            + "-fx-background-radius: 3;"
                            + "-fx-label-padding: 0;");
                } else if (pdf.getDeadline().getDaysToDeadline() <= 0) {
                    deadline.setStyle("-fx-text-fill: white;"
                            + "-fx-background-color: #cc0052;"
                            + "-fx-padding: 1;"
                            + "-fx-border-radius: 3;"
                            + "-fx-background-radius: 3;"
                            + "-fx-label-padding: 0;");
                }
            }

        }
        //phone.setText(pdf.getPhone().value);
        //address.setText(pdf.getAddress().value);
        //email.setText(pdf.getEmail().value);
        pdf.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && pdf.equals(card.pdf);
    }
}
