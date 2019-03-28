package seedu.finance.logic.parser;

import java.util.Arrays;
import java.util.Comparator;

import seedu.finance.model.category.Category;
import seedu.finance.model.record.Record;

/**
 * A comparison function to sort the records by category in lexicographical order (case insensitive).
 */
public class RecordCategoryComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        Category[] r1Cat = r1.getCategories().toArray(new Category[r1.getCategories().size()]);
        Category[] r2Cat = r2.getCategories().toArray(new Category[r2.getCategories().size()]);
        CategoryComparator comparator = new CategoryComparator();
        Arrays.sort(r1Cat, comparator);
        Arrays.sort(r2Cat, comparator);

        return r1Cat[0].categoryName.compareToIgnoreCase(r2Cat[0].categoryName);
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
}
