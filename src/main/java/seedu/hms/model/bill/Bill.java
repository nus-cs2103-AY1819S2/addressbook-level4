package seedu.hms.model.bill;

import java.util.HashMap;

import javafx.util.Pair;
import seedu.hms.model.customer.Customer;

/**
 * Represents a Customer's bill in the system.
 */
public class Bill {

    private HashMap<String, Pair<Double, Integer>> bookingBill;
    private HashMap<String, Pair<Double, Integer>> reservationBill;
    private Customer customer;
    private double amountReservation;
    private double amountBooking;
    private double totalAmount;

    /**
     * Constructs a {@code Bill}.
     *
     * @param customer          Customer details.
     * @param amountBooking     Amount to be paid by customer for booking.
     * @param amountReservation Amount to be paid by the customer for reservation.
     * @param bookingBill       HashMap that stores the entire booking bill for the customer
     * @param reservationBill   HashMap that stores the entire reservation bill for the customer
     */

    public Bill(Customer customer, double amountReservation, double amountBooking, HashMap<String, Pair<Double,
        Integer>> bookingBill, HashMap<String, Pair<Double, Integer>> reservationBill) {

        this.customer = customer;
        this.amountBooking = amountBooking;
        this.amountReservation = amountReservation;
        totalAmount = this.amountBooking + this.amountReservation;
        this.bookingBill = bookingBill;
        this.reservationBill = reservationBill;
    }

    /**
     * Returns the amount customer has to pay for all gym bookings
     */
    public double getGymBill() {
        return bookingBill.get("GYM").getKey();
    }

    /**
     * Returns the number of times the customer has used the gym
     */
    public int getGymBookingCount() {
        return bookingBill.get("GYM").getValue();
    }

    /**
     * Returns the amount customer has to pay for all spa bookings
     */
    public double getSpaBill() {
        return bookingBill.get("SPA").getKey();
    }

    /**
     * Returns the number of times the customer has used the spa
     */
    public int getSpaBookingCount() {
        return bookingBill.get("SPA").getValue();
    }

    /**
     * Returns the amount customer has to pay for all swimming pool bookings
     */
    public double getSwimmingPoolBill() {
        return bookingBill.get("SWIMMING POOL").getKey();
    }

    /**
     * Returns the number of times the customer has used the swimming pool
     */
    public int getSwimmingPoolBookingCount() {
        return bookingBill.get("SWIMMING POOL").getValue();
    }

    /**
     * Returns the amount customer has to pay for all games room bookings
     */
    public double getGamesRoomBill() {
        return bookingBill.get("GAMES ROOM").getKey();
    }

    /**
     * Returns the number of times the customer has used the games room
     */
    public int getGamesRoomBookingCount() {
        return bookingBill.get("GAMES ROOM").getValue();
    }

    /**
     * Returns the amount customer has to pay for all single room reservations
     */
    public double getSingleRoomBill() {
        return reservationBill.get("SINGLE ROOM").getKey();
    }

    /**
     * Returns the number of single rooms the customer has reserved
     */
    public int getSingleRoomCount() {
        return reservationBill.get("SINGLE ROOM").getValue();
    }

    /**
     * Returns the amount customer has to pay for all double room reservations
     */
    public double getDoubleRoomBill() {
        return reservationBill.get("DOUBLE ROOM").getKey();
    }

    /**
     * Returns the number of double rooms the customer has reserved
     */
    public int getDoubleRoomCount() {
        return reservationBill.get("DOUBLE ROOM").getValue();
    }

    /**
     * Returns the amount customer has to pay for all single room reservations
     */
    public double getDeluxeRoomBill() {
        return reservationBill.get("DELUXE ROOM").getKey();
    }

    /**
     * Returns the number of single rooms the customer has reserved
     */
    public int getDeluxeRoomCount() {
        return reservationBill.get("DELUXE ROOM").getValue();
    }

    /**
     * Returns the amount customer has to pay for all single room reservations
     */
    public double getFamilySuiteBill() {
        return reservationBill.get("FAMILY SUITE").getKey();
    }

    /**
     * Returns the number of family suites the customer has reserved
     */
    public int getFamilySuiteCount() {
        return reservationBill.get("FAMILY SUITE").getValue();
    }

    /**
     * Returns the customer whose bill is to be calculated.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns the amount customer has to pay for all room reservations.
     */
    public double getAmountReservation() {
        return amountReservation;
    }

    /**
     * Returns the amount customer has to pay for all booking services.
     */
    public double getAmountBooking() {
        return amountBooking;
    }

    /**
     * Returns the total amount the customer has to pay for his stay in the hotel.
     */
    public double getTotalAmount() {
        return totalAmount;
    }


    @Override
    public String toString() {
        return " Amount to be paid: " + totalAmount
            + " by " + customer.getName();
    }
}
