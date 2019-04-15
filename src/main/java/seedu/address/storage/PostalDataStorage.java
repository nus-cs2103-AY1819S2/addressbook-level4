package seedu.address.storage;

import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PostalDataSet;

/**
 * API of the PostalDataStorage component
 */
public interface PostalDataStorage {
    /**
     *
     * @return a set of all postalData
     */
    Optional<PostalDataSet> getPostalData() throws DataConversionException;
}
