package seedu.address.model.epiggy;

import seedu.address.model.epiggy.item.Item;

import java.util.Date;

/**
 * Represents an Expense in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {
    private final Item item;
    private final Date date;

    public Expense(Item item, Date date) {
        this.item = item;
        this.date = date;
    }
}
