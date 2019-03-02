package seedu.address.model.epiggy.item;

import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Represents an Item in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {
    private final Name name;
    private final Price price;
    private final Set<Tag> tags;

    public Item(Name name, Price price, Set<Tag> tags) {
        this.name = name;
        this.price = price;
        this.tags = tags;
    }
}
