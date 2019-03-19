package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.RestaurantNotFoundException;

/**
 * Represents the in-memory model of the food diary data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFoodDiary versionedFoodDiary;
    private final UserPrefs userPrefs;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();
    private final PostalDataSet postalDataSet;

    /**
     * Initializes a ModelManager with the given foodDiary and userPrefs.
     */
    public ModelManager(ReadOnlyFoodDiary foodDiary, ReadOnlyUserPrefs userPrefs, PostalDataSet postalDataSet) {
        super();
        requireAllNonNull(foodDiary, userPrefs);

        logger.fine("Initializing with food diary: " + foodDiary + " and user prefs " + userPrefs);

        versionedFoodDiary = new VersionedFoodDiary(foodDiary);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRestaurants = new FilteredList<>(versionedFoodDiary.getRestaurantList());
        filteredRestaurants.addListener(this::ensureSelectedRestaurantIsValid);
        this.postalDataSet = postalDataSet;
    }

    public ModelManager() {
        this(new FoodDiary(), new UserPrefs(), new PostalDataSet());
    }

    //public PostalData checkPostal(int Postal);

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public int getSize() {
        return versionedFoodDiary.getSize();
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFoodDiaryFilePath() {
        return userPrefs.getFoodDiaryFilePath();
    }

    @Override
    public String getName() {
        return userPrefs.getName();
    }

    @Override
    public void setName(String name) {
        userPrefs.setName(name);
    }

    @Override
    public void setFoodDiaryFilePath(Path foodDiaryFilePath) {
        requireNonNull(foodDiaryFilePath);
        userPrefs.setFoodDiaryFilePath(foodDiaryFilePath);
    }

    //=========== FoodDiary ================================================================================

    @Override
    public void setFoodDiary(ReadOnlyFoodDiary foodDiary) {
        versionedFoodDiary.resetData(foodDiary);
    }

    @Override
    public ReadOnlyFoodDiary getFoodDiary() {
        return versionedFoodDiary;
    }

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return versionedFoodDiary.hasRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        versionedFoodDiary.removeRestaurant(target);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        versionedFoodDiary.addRestaurant(restaurant);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        versionedFoodDiary.setRestaurant(target, editedRestaurant);
    }

    //=========== Filtered Restaurant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Restaurant} backed by the internal list of
     * {@code versionedFoodDiary}
     */
    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurants;
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFoodDiary() {
        return versionedFoodDiary.canUndo();
    }

    @Override
    public boolean canRedoFoodDiary() {
        return versionedFoodDiary.canRedo();
    }

    @Override
    public void undoFoodDiary() {
        versionedFoodDiary.undo();
    }

    @Override
    public void redoFoodDiary() {
        versionedFoodDiary.redo();
    }

    @Override
    public void commitFoodDiary() {
        versionedFoodDiary.commit();
    }

    //=========== Selected restaurant ===========================================================================

    @Override
    public ReadOnlyProperty<Restaurant> selectedRestaurantProperty() {
        return selectedRestaurant;
    }

    @Override
    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant.getValue();
    }

    @Override
    public void setSelectedRestaurant(Restaurant restaurant) {
        if (restaurant != null && !filteredRestaurants.contains(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        selectedRestaurant.setValue(restaurant);
    }

    @Override
    public int getNumReviews() {
        int size = 0;
        for (Restaurant restaurant : versionedFoodDiary.getRestaurantList()) {
            size += restaurant.getReviews().size();
        }
        return size;
    }
    /**
     * Ensures {@code selectedRestaurant} is a valid restaurant in {@code filteredRestaurants}.
     */
    private void ensureSelectedRestaurantIsValid(ListChangeListener.Change<? extends Restaurant> change) {
        while (change.next()) {
            if (selectedRestaurant.getValue() == null) {
                // null is always a valid selected restaurant, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedRestaurantReplaced = change.wasReplaced() && change.getAddedSize()
                    == change.getRemovedSize() && change.getRemoved().contains(selectedRestaurant.getValue());
            if (wasSelectedRestaurantReplaced) {
                // Update selectedRestaurant to its new value.
                int index = change.getRemoved().indexOf(selectedRestaurant.getValue());
                selectedRestaurant.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRestaurantRemoved = change.getRemoved().stream()
                    .anyMatch(removedRestaurant -> selectedRestaurant.getValue().isSameRestaurant(removedRestaurant));
            if (wasSelectedRestaurantRemoved) {
                // Select the restaurant that came before it in the list,
                // or clear the selection if there is no such restaurant.
                selectedRestaurant.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedFoodDiary.equals(other.versionedFoodDiary)
                && userPrefs.equals(other.userPrefs)
                && filteredRestaurants.equals(other.filteredRestaurants)
                && Objects.equals(selectedRestaurant.get(), other.selectedRestaurant.get());
    }

}
