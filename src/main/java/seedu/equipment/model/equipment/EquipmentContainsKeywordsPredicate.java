package seedu.equipment.model.equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Equipment}'s {@code Name} matches any of the keywords given.
 */
public class EquipmentContainsKeywordsPredicate implements Predicate<Equipment> {
    private final List<String> nameKeywords;
    private final List<String> addressKeywords;
    private final List<String> dateKeywords;
    private final List<String> phoneKeywords;
    private final List<String> tagKeywords;
    private final List<String> serialNumberKeywords;


    public EquipmentContainsKeywordsPredicate(List<String> nameKeywords, List<String> addressKeywords,
                                              List<String> dateKeywords, List<String> phoneKeywords,
                                              List<String> tagKeywords, List<String> serialNumberKeywords) {
        this.nameKeywords = nameKeywords;
        this.addressKeywords = addressKeywords;
        this.dateKeywords = dateKeywords;
        this.phoneKeywords = phoneKeywords;
        this.tagKeywords = tagKeywords;
        this.serialNumberKeywords = serialNumberKeywords;
    }

    @Override
    public boolean test(Equipment equipment) {
        return (testAllMatch(equipment.getName().name, nameKeywords)
                || testAnyMatch(equipment.getName().name, nameKeywords))
                || (testAllMatch(equipment.getAddress().value, addressKeywords)
                || testAnyMatch(equipment.getAddress().value, addressKeywords))
                || (testAllMatch(equipment.getDate().toString(), dateKeywords)
                || testAnyMatch(equipment.getDate().toString(), dateKeywords))
                || (testAllMatch(equipment.getPhone().value, phoneKeywords)
                || testAnyMatch(equipment.getPhone().value, phoneKeywords))
                || (testAllMatch(equipment.getTags().toString(), tagKeywords)
                || testAnyMatch(equipment.getTags().toString(), tagKeywords))
                || (testAllMatch(equipment.getSerialNumber().serialNumber, serialNumberKeywords)
                || testAnyMatch(equipment.getSerialNumber().serialNumber, serialNumberKeywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EquipmentContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((EquipmentContainsKeywordsPredicate) other).nameKeywords)
                && addressKeywords.equals(((EquipmentContainsKeywordsPredicate) other).addressKeywords)
                && dateKeywords.equals(((EquipmentContainsKeywordsPredicate) other).dateKeywords)
                && phoneKeywords.equals(((EquipmentContainsKeywordsPredicate) other).phoneKeywords)
                && tagKeywords.equals(((EquipmentContainsKeywordsPredicate) other).tagKeywords)
                && serialNumberKeywords.equals(((EquipmentContainsKeywordsPredicate) other).serialNumberKeywords));
        // state check
    }
    /** Returns true if all keyword is found in the sentence, case insensitive. */
    private boolean testAllMatch(String sentence, List<String> keywords) {
        if (keywords.isEmpty()) {
            return false;
        }

        String processedSentence = sentence.toLowerCase();
        return keywords.stream().allMatch(keyword -> {
            return processedSentence.contains(keyword.toLowerCase()); });
    }

    /** Returns true if any keyword is found in the sentence, case insensitive. */
    private boolean testAnyMatch(String sentence, List<String> keywords) {
        if (keywords.isEmpty()) {
            return false;
        }

        List<String> ignoreList = new ArrayList<>();
        ignoreList.add(" ");
        ignoreList.add(".");
        ignoreList.add(",");

        List<String> filteredList = new ArrayList<>();

        for (String keyword : keywords) {
            if (!ignoreList.contains(keyword)) {
                filteredList.add(keyword);
            }
        }
        String processedSentence = sentence.toLowerCase();
        return filteredList.stream().anyMatch(keyword -> processedSentence.contains(keyword.toLowerCase()));
    }
}
