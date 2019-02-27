package seedu.address.testutil;

import seedu.address.model.request.Request;

/**
 * A utility class containing {@code Request} objects to be used in tests.
 */
public class TypicalRequests {
    public static final String ALICE_ID = "aslkdjaslaskd";
    public static final String BENSON_ID = "slkajsfddasdf";
    public static final String CARL_ID = "poqweizsdfda";
    public static final String DANIEL_ID = "xzaiseqnwqd";

    public static final Request ALICE_REQUEST = new RequestBuilder()
            .withId(ALICE_ID)
            .withPatient(TypicalPatients.ALICE)
            .withConditions("Physiotherapy")
            .withDate("01-01-2019 10:00:00")
            .withHealthStaff(TypicalHealthWorkers.ANDY)
            .withStatus("PENDING")
            .build();

    public static final Request BENSON_REQUEST = new RequestBuilder()
            .withId(BENSON_ID)
            .withPatient(TypicalPatients.BENSON)
            .withConditions("Palliative")
            .withDate("02-01-2919 08:00:00")
            .withHealthStaff(TypicalHealthWorkers.BETTY)
            .withStatus("PENDING")
            .build();

    public static final Request CARL_REQUEST = new RequestBuilder()
            .withId(CARL_ID)
            .withPatient(TypicalPatients.CARL)
            .withConditions("Palliative")
            .withDate("02-01-2919 08:00:00")
            .withHealthStaff(TypicalHealthWorkers.CARLIE)
            .withStatus("PENDING")
            .build();

    public static final Request DANIEL = new RequestBuilder()
            .withId(DANIEL_ID)
            .withPatient(TypicalPatients.DANIEL)
            .withConditions("Palliative")
            .withDate("02-01-2919 08:00:00")
            .withHealthStaff(TypicalHealthWorkers.PANIEL)
            .withStatus("COMPLETED")
            .build();
}
