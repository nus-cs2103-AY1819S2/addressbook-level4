package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.request.Request;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "requestbook")
public class JsonSerializableRequestBook {

    public static final String MESSAGE_DUPLICATE_REQUEST = "Request list contains duplicate "
        + "request(s).";

    private final List<JsonAdaptedRequest> requests = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRequestBook(@JsonProperty("requests") List<JsonAdaptedRequest> requests) {
        this.requests.addAll(requests);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableRequestBook(ReadOnlyRequestBook source) {
        requests.addAll(source.getRequestList().stream().map(JsonAdaptedRequest::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RequestBook toModelType() throws IllegalValueException {
        RequestBook requestBook = new RequestBook();
        for (JsonAdaptedRequest jsonAdaptedRequest : requests) {
            Request request = jsonAdaptedRequest.toModelType();
            if (requestBook.hasRequest(request)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REQUEST);
            }
            requestBook.addRequest(request);
        }
        return requestBook;
    }

}
