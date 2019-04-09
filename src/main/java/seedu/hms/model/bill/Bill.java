package seedu.hms.model.bill;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Represents a Customer's bill in the system.
 */
public class Bill {

    private HashMap<ServiceType, Pair<Double, Integer>> bookingBill;
    private HashMap<RoomType, Pair<Double, Long>> reservationBill;
    private Customer customer;
    private double totalAmount;

    /**
     * Constructs a {@code Bill}.
     *
     * @param customer        Customer details.
     * @param bookingBill     Returns the entire booking bill
     * @param reservationBill Returns the entire reservation bill
     */

    public Bill(Customer customer, HashMap<ServiceType, Pair<Double,
        Integer>> bookingBill, HashMap<RoomType, Pair<Double, Long>> reservationBill) {

        this.customer = customer;
        this.bookingBill = bookingBill;
        this.reservationBill = reservationBill;
    }

    public Set<ServiceType> getServiceTypes() {
        return bookingBill.keySet();
    }

    public Set<RoomType> getRoomTypes() {
        return reservationBill.keySet();
    }

    /**
     * Returns the amount customer has to pay for all gym bookings
     */
    public double getServiceTypeBill(ServiceType st) {
        if (bookingBill.get(st) == null) {
            return 0;
        }
        return bookingBill.get(st).getKey();
    }

    /**
     * Returns the number of times the customer has used the gym
     */
    public int getServiceTypeBookingCount(ServiceType st) {
        if (bookingBill.get(st) == null) {
            return 0;
        }
        return bookingBill.get(st).getValue();
    }

    /**
     * Returns the amount customer has to pay for all single room reservations
     */
    public double getRoomTypeBill(RoomType rt) {
        if (reservationBill.get(rt) == null) {
            return 0;
        }
        return reservationBill.get(rt).getKey();
    }

    /**
     * Returns the number of single rooms the customer has reserved
     */
    public long getRoomTypeCount(RoomType rt) {
        if (reservationBill.get(rt) == null) {
            return 0;
        }
        return reservationBill.get(rt).getValue();
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
        double amount = 0.0;
        for (Map.Entry<RoomType, Pair<Double, Long>> entry: reservationBill.entrySet()) {
            amount += entry.getValue().getKey();
        }
        return amount;
    }

    /**
     * Returns the amount customer has to pay for all service bookings.
     */
    public double getAmountBooking() {
        double amount = 0.0;
        for (Map.Entry<ServiceType, Pair<Double, Integer>> entry: bookingBill.entrySet()) {
            amount += entry.getValue().getKey();
        }
        return amount;
    }

    /**
     * Returns the amount customer has to pay for all service bookings.
     */
    public double getAmountTotal() {
        return getAmountBooking() + getAmountReservation();
    }

    /**
     * Returns the count of reservations
     */
    public long getReservationCount() {
        long count = 0;
        for (Map.Entry<RoomType, Pair<Double, Long>> entry: reservationBill.entrySet()) {
            count += entry.getValue().getValue();
        }
        return count;
    }

    /**
     * Return the count of bookings
     */
    public long getBookingCount() {
        long count = 0;
        for (Map.Entry<ServiceType, Pair<Double, Integer>> entry: bookingBill.entrySet()) {
            count += entry.getValue().getValue();
        }
        return count;
    }

    @Override
    public String toString() {
        return " Amount to be paid by " + customer.getName() + ".";
    }
}
