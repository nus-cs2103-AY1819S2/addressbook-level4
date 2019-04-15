package seedu.address.logic.commands.comparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.PostalData;
import seedu.address.model.PostalDataSet;
import seedu.address.model.restaurant.Restaurant;

/**
 * Sorts Resturant based on distance from given postal code.
 */
public class SortDistanceComparator implements Comparator<Restaurant> {
    private HashMap<String, Double> distanceData;
    private double userX;
    private double userY;
    private PostalDataSet postalDataSet;
    public SortDistanceComparator(PostalDataSet postalDataSet, PostalData postalOfUser) {
        this.postalDataSet = postalDataSet;
        distanceData = new HashMap<>();
        userX = postalOfUser.getX();
        userY = postalOfUser.getY();
    }

    /**
     *
     * @param firstRestaurant {@code Restaurant} of the first restaurant
     * @param secondRestaurant {@code Restaurant} of the second restaurant
     * @return 1 if firstRestaurant is nearer to current than secondRestaurant 0 if equal or -1 otherwise
     */

    public int compare(Restaurant firstRestaurant, Restaurant secondRestaurant) {
        String postalA = firstRestaurant.getPostal().value;
        String postalB = secondRestaurant.getPostal().value;

        double distA = checkDistanceFromUser(postalA);
        double distB = checkDistanceFromUser(postalB);

        if (distA - distB > 0) {
            return 1;
        } else if (distA - distB < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param postal
     * check if a given postal is in the HashMap else put it in otherwise.
     */
    private double checkDistanceFromUser(String postal) {
        if (!distanceData.containsKey(postal)) {
            Optional<PostalData> postalData = postalDataSet.getPostalData(postal);
            if (postalData.isPresent()) {
                double restaurantX = postalData.get().getX();
                double restaurantY = postalData.get().getY();
                double distance = calculateDistance(restaurantX, restaurantY, userX, userY);
                distanceData.put(postal, distance);
            } else {
                distanceData.put(postal, Double.MAX_VALUE);
            }
        }
        return distanceData.get(postal);
    }
    private double calculateDistance(double firstX, double firstY, double secondX, double secondY) {
        return Math.pow((firstX - secondX), 2) + Math.pow((firstY - secondY), 2);
    }
}
