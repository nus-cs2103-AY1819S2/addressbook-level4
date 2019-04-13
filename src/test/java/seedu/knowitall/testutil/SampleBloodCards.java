package seedu.knowitall.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects to be used in import command tests
 */
public class SampleBloodCards {

    public static final Card CARD_1 = new CardBuilder().withQuestion("Commonly used anti-fibrinolytic drug is _____, "
            + "which prevent activation of plasminogen to plasmin").withAnswer("tranexamic acid")
            .withHint("ph less than 7")
            .withOptions("hydrochloric acid", "sulfuric acid")
            .withScore("0/0").build();

    public static final Card CARD_2 = new CardBuilder().withQuestion("Myelodysplasia often regresses "
            + "into what blood disorder?")
            .withAnswer("Acute Leukaemia")
            .withScore("0/0").withHint("type of cancer").build();

    public static final Card CARD_3 = new CardBuilder().withQuestion("<something>cytosis means too _____")
            .withAnswer("many")
            .withScore("0/0")
            .build();

    public static final Card CARD_4 = new CardBuilder().withQuestion("Burkitt lymphoma is associated with what virus?")
            .withAnswer("EBV")
            .withScore("0/0")
            .build();

    public static final Card CARD_5 = new CardBuilder().withQuestion("For _____, besides systemic chemo, "
            + "treatment includes chemo injections into scrotum and CSF")
            .withAnswer("acute lymphoblastic leukemia")
            .withScore("0/0")
            .build();

    public static final Card CARD_6 = new CardBuilder().withQuestion("_____% of circulating neutrophils "
            + "are in marginating pool, hence not counted in full blood count")
            .withAnswer("50")
            .withScore("2/3")
            .withOptions("55")
            .build();

    public static final Card CARD_7 = new CardBuilder().withQuestion("If patient has raised WBC count, "
            + "raised platelet and raised Hb count, he is likely to have _____")
            .withAnswer("myeloproliferative neoplasm")
            .withScore("1/2")
            .build();

    // Manually added
    public static final Card CARD_8 = new CardBuilder().withQuestion("Patients with Acute lymphoblastic leukaemia with "
            + "philadelphia chromosome cytogenic changes will require what special treatment?")
            .withAnswer("Imatinib")
            .withScore("0/0")
            .build();

    public static final Card CARD_9 = new CardBuilder().withQuestion("<something>penia means too _____")
            .withAnswer("few")
            .withScore("0/0")
            .withOptions("some", "many", "full")
            .build();


    public static final Card CARD_10 = new CardBuilder().withQuestion("_____ is the iron-containing oxygen-transport "
            + "metalloprotein in the red blood cells")
            .withAnswer("haemoglobin")
            .withScore("0/0")
            .build();

    public static final String BLOOD_FOLDER_NAME = "Blood";

    public static final List<Double> BLOOD_FOLDER_SCORES = new ArrayList<>(Arrays.asList(0.9));



    /**
     * Returns an {@code CardFolder} with all the typical cards.
     */
    public static CardFolder getBloodFolder() {
        CardFolder folder = new CardFolder(getTypicalFolderName());
        folder.setFolderScores(BLOOD_FOLDER_SCORES);
        for (Card card : getSampleBloodCards()) {
            folder.addCard(card);
        }
        return folder;
    }

    public static List<Card> getSampleBloodCards() {
        return new ArrayList<>(Arrays.asList(CARD_1, CARD_2, CARD_3,
                CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_10));
    }

    public static String getTypicalFolderName() {
        return BLOOD_FOLDER_NAME;
    }

}
