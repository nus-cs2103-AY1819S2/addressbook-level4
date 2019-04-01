package seedu.hms.model.bill;

import seedu.hms.model.customer.Customer;

/**
 * Represents a Customer's bill in the system.
 */
public class Bill {

    private Customer customer;
    private double amountReservation;
    private double amountBooking;

    /**
     * Constructs a {@code Bill}.
     *
     * @param customer   Customer details.
     * @param amountBooking Amount to be paid by customer for booking.
     * @param amountReservation Amount to be paid by the customer for reservation.
     */

    public Bill(Customer customer, double amountReservation, double amountBooking) {

        this.customer = customer;
        this.amountBooking = amountBooking;
        this.amountReservation = amountReservation;

    }

    /**
     * Returns the customer whose bill is to be calculated.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns the amount customer has to pay for reservation.
     */
    public double getAmountReservation() {
        return amountReservation;
    }

    /**
     * Returns the amount customer has to pay for booking.
     */
    public double getAmountBooking() {
        return amountBooking;
    }


    @Override
    public String toString() {
        return " Amount to be paid " + amountReservation + amountBooking
            + " by " + customer.getName();
    }
}
