package seedu.address.logic.commands.comparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.model.PostalData;
import seedu.address.model.restaurant.Restaurant;

public class SortDistanceComparator implements Comparator<Restaurant> {
    HashMap<Integer,Double> distanceData;
    PostalData current;
    double x;
    double y;
    Model model;
    public SortDistanceComparator(Model model, int postal) {
        this.model = model;
        distanceData = new HashMap<>();
        current = model.getPostalData(postal).get();
        x = current.getX();
        y = current.getY();
    }

    /**
     *
     * @param firstRestaurant {@code Restaurant} of the first restaurant
     * @param secondRestaurant {@code Restaurant} of the second restaurant
     * @return 1 if firstRestaurant is nearer to current than secondRestaurant 0 if equal or -1 otherwise
     */

    public int compare(Restaurant firstRestaurant, Restaurant secondRestaurant) {
        int postalA = (Integer.parseInt(firstRestaurant.getPostal().value));
        int postalB = (Integer.parseInt(secondRestaurant.getPostal().value));

        checkHashMap(postalA);
        checkHashMap(postalB);


        double distA = distanceData.get(postalA);
        double distB = distanceData.get(postalB);


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
    private void checkHashMap(int postal) {
        if (!distanceData.containsKey(postal)) {
            Optional<PostalData> postalDataA = model.getPostalData(postal);
            if (postalDataA.isPresent()) {
                double aX = postalDataA.get().getX();
                double aY = postalDataA.get().getY();
                double distance = (aX - x) * (aX - x) + (aY - y) * (aY - y);
                distanceData.put(postal, distance);
            } else {
                distanceData.put(postal, Double.MAX_VALUE);
            }
        }
    }
}
