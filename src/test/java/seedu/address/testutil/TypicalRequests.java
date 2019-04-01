package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing {@code Request} objects to be used in tests.
 */
public class TypicalRequests {

    public static final Request ALICE_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Physiotherapy"))
        .withDate("01-01-2019 10:00:00")
        .withHealthStaff(TypicalHealthWorkers.ANDY)
        .withStatus("PENDING")
        .withAddress("123, Jurong West Ave 6, #08-111")
        .withPhone("94351253")
        .withNric("S9670515H")
        .withName("Alice Pauline")
        .build();

    public static final Request BENSON_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2919 08:00:00")
        .withHealthStaff(TypicalHealthWorkers.BETTY)
        .withStatus("PENDING")
        .withName("Benson Meier")
        .withNric("S9274100D")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withPhone("98765432")
        .build();

    public static final Request CARL_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2919 14:00:00")
        .withHealthStaff(TypicalHealthWorkers.CARLIE)
        .withStatus("PENDING")
        .withNric("S9328723A")
        .withName("Carl Kurz")
        .withPhone("87652533")
        .withAddress("wall street")
        .build();

    public static final Request DANIEL_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2919 18:00:00")
        .withHealthStaff(TypicalHealthWorkers.PANIEL)
        .withStatus("COMPLETED")
        .withNric("S2652663Z")
        .withPhone("82015737")
        .withAddress("10th street")
        .withName("Daniel Meier")
        .build();

    /**
     * Returns an {@code RequestBook} with all the typical persons.
     */
    public static RequestBook getTypicalRequestBook() {
        RequestBook requestBook = new RequestBook();
        for (Request request : getTypicalRequests()) {
            requestBook.addRequest(request);
        }
        return requestBook;
    }

    public static List<Request> getTypicalRequests() {
        return new ArrayList<>(Arrays.asList(ALICE_REQUEST, BENSON_REQUEST, CARL_REQUEST,
            DANIEL_REQUEST));
    }
}
