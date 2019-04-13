package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing {@code Request} objects to be used in tests.
 *
 * @@author daviddl9
 */
public class TypicalRequests {

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Request ALICE_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Physiotherapy"))
        .withDate("01-01-2019 10:00:00")
        .withHealthWorker(TypicalHealthWorkers.ANDY)
        .withStatus("ONGOING")
        .withAddress("123, Jurong West Ave 6, #08-111")
        .withPhone("94351253")
        .withNric("S9670515H")
        .withName("Alice Pauline")
        .build();

    public static final Request BENSON_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 08:00:00")
        .withHealthWorker(TypicalHealthWorkers.BETTY)
        .withStatus("ONGOING")
        .withName("Benson Meier")
        .withNric("S9274100D")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withPhone("98765432")
        .build();

    public static final Request CARL_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("04-01-2019 14:00:00")
        .withHealthWorker(TypicalHealthWorkers.CARLIE)
        .withStatus("ONGOING")
        .withNric("S9328723A")
        .withName("Carl Kurz")
        .withPhone("87652533")
        .withAddress("wall street")
        .build();

    public static final Request DANIEL_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 18:00:00")
        .withHealthWorker(TypicalHealthWorkers.PANIEL)
        .withStatus("COMPLETED")
        .withNric("S2652663Z")
        .withPhone("82015737")
        .withAddress("10th street")
        .withName("Daniel Meier")
        .build();

    public static final Request EMMANUEL_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 18:00:00")
        .withStatus("PENDING")
        .withNric("S2862663Z")
        .withPhone("82205737")
        .withAddress("10th street")
        .withName("Emmanuel Meier")
        .build();

    public static final Request FRANCIS_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 18:00:00")
        .withStatus("PENDING")
        .withNric("S2122663Z")
        .withPhone("82655737")
        .withAddress("10th street")
        .withName("Francis Meier")
        .build();

    public static final Request GLADYS_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 19:00:00")
        .withStatus("PENDING")
        .withNric("S2176663Z")
        .withPhone("82659337")
        .withAddress("10th street")
        .withName("Gladys Meier")
        .build();

    public static final Request HEPZHI_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("02-01-2019 16:00:00")
        .withStatus("PENDING")
        .withNric("S2176698Z")
        .withPhone("82658937")
        .withAddress("10th street")
        .withName("Hepzhi Meier")
        .build();

    public static final Request INDIANA_REQUEST = new RequestBuilder()
        .withConditions(SampleDataUtil.getConditionSet("Palliative"))
        .withDate("01-01-2019 08:00:00")
        .withStatus("PENDING")
        .withNric("S2184698Z")
        .withPhone("82692937")
        .withAddress("10th street")
        .withName("Indiana Meier")
        .build();

    public static final Request JANE_REQUEST = new RequestBuilder()
            .withConditions(SampleDataUtil.getConditionSet("Palliative"))
            .withDate("02-01-2019 15:00:00")
            .withHealthWorker(TypicalHealthWorkers.ELLA)
            .withStatus("COMPLETED")
            .withNric("S2652663Z")
            .withPhone("82845737")
            .withAddress("10th street")
            .withName("Jane Romero")
            .build();

    public static final Request NEA_REQUEST = new RequestBuilder()
            .withConditions(SampleDataUtil.getConditionSet("Physiotherapy"))
            .withDate("25-03-2019 10:00:00")
            .withHealthWorker(TypicalHealthWorkers.ELLA)
            .withStatus("ONGOING")
            .withNric("S8875613T")
            .withPhone("88179352")
            .withAddress("480 Lorong 6 Toa Payoh")
            .withName("Nea Karlsson")
            .build();

    /**
     * Returns an {@code RequestBook} with all the typical requests.
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
            DANIEL_REQUEST, EMMANUEL_REQUEST, FRANCIS_REQUEST, GLADYS_REQUEST, HEPZHI_REQUEST,
            INDIANA_REQUEST, JANE_REQUEST, NEA_REQUEST));
    }
}
