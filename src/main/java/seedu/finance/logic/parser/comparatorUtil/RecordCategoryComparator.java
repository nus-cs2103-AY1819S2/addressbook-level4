package seedu.finance.logic.parser.comparatorUtil;

import java.util.Comparator;

import seedu.finance.model.category.Category;
import seedu.finance.model.record.Record;

/**
 * A comparison function to sort the records by category in lexicographical order (case insensitive).
 */
public class RecordCategoryComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return r1.getCategory().toString().compareToIgnoreCase(r2.getCategory().toString());
    }

    /**
     * A comparison function to sort the categories in lexicographical order (case insensitive).
     */
    public static class CategoryComparator implements Comparator<Category> {
        @Override
        public int compare(Category c1, Category c2) {
            return c1.categoryName.compareToIgnoreCase(c2.categoryName);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
