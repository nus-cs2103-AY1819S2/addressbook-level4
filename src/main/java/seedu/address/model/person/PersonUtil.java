package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.SkillsTag;

/**
 * Space for general methods relating to the Person class
 */
public class PersonUtil {

    /**
     * counts the number of tags of a certain type denoted by the prefix
     *  Accepted prefixes: "s" = skills; "p" = positions; "e" = endorsements
     */
    public static int getTagTypeNumber(Set<SkillsTag> individualTags, String prefix) {
        int count = 0;
        for (SkillsTag tag : individualTags) {
            String tagString = tag.toString();
            //first element of string is "["
            if (tagString.substring(1, 2).equals(prefix)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * returns a list of tags of a certain type denoted by the prefix
     *  Accepted prefixes: "s" = skills; "p" = positions; "e" = endorsements
     */
    public static Set<SkillsTag> getTagsOfType(Set<SkillsTag> individualTags, String prefix) {
        Set<SkillsTag> tagsOfType = new HashSet<>();
        for (SkillsTag tag : individualTags) {
            String tagString = tag.toString();
            //first element of string is "["
            if (tagString.substring(1, 2).equals(prefix)) {
                tagsOfType.add(tag);
            }
        }
        return tagsOfType;
    }
}
