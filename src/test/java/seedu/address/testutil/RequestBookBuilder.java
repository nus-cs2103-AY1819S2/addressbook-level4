package seedu.address.testutil;

import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;

/**
 * A utility class to help with building Requestbook objects.
 * Example usage: <br>
 *     {@code RequestBook rb = new RequestBookBuilder().withRequest(TypicalRequests.ALICE_REQUEST)
 *     .build();}
 *
 * @@author daviddl9
 */
public class RequestBookBuilder {

    private RequestBook requestBook;

    public RequestBookBuilder() {
        requestBook = new RequestBook();
    }

    public RequestBookBuilder(RequestBook requestBook) {
        this.requestBook = requestBook;
    }

    /**
     * Adds a new {@code Request} to the {@code RequestBook} that we are building
     */
    public RequestBookBuilder withRequest(Request request) {
        requestBook.addRequest(request);
        return this;
    }

    public RequestBook build() {
        return requestBook;
    }

}
