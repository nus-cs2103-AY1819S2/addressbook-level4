package seedu.address.model.person;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.sortmethods.SortUtil;
import seedu.address.model.tag.SkillsTag;

/**
 * Space for general methods relating to the Person class
 */
public class PersonUtil {
    private static final Logger logger = LogsCenter.getLogger(SortUtil.class);

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

    /**
     * Associates an integer to each degree level for ranking the 5 degree levels
     */
    public static int getEducationValue(String education) {
        int value;
        if ("high school".equals(education)) {
            value = 1;
        } else if ("associates".equals(education)) {
            value = 2;
        } else if ("bachelors".equals(education)) {
            value = 3;
        } else if ("masters".equals(education)) {
            value = 4;
        } else if ("phd".equals(education)) {
            value = 5;
        } else {
            value = 0;
            logger.info("No valid degree to compare");
        }
        return value;
    }
}
