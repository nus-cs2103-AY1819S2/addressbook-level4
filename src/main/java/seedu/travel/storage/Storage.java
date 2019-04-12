package seedu.travel.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.travel.commons.exceptions.DataConversionException;
import seedu.travel.model.ReadOnlyCountryChart;
import seedu.travel.model.ReadOnlyRatingChart;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.ReadOnlyUserPrefs;
import seedu.travel.model.ReadOnlyYearChart;
import seedu.travel.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TravelBuddyStorage, UserPrefsStorage, ChartBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTravelBuddyFilePath();

    @Override
    Path getCountryChartFilePath();

    @Override
    Path getRatingChartFilePath();

    @Override
    Path getYearChartFilePath();

    @Override
    Optional<ReadOnlyTravelBuddy> readTravelBuddy() throws DataConversionException, IOException;

    @Override
    void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException;

    @Override
    void saveCountryChart(ReadOnlyCountryChart countryChart);

    @Override
    void saveRatingChart(ReadOnlyRatingChart ratingChart);

    @Override
    void saveYearChart(ReadOnlyYearChart yearChart);
}
