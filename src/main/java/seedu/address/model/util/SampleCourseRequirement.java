package seedu.address.model.util;

import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.GE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.model.course.CompositeRequirement;
import seedu.address.model.course.Condition;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.course.PrimitiveRequirement;

/**
 * Represents default Course Requirements in class
 */
public class SampleCourseRequirement {
    //University Level Requirement
    public static final String[] ULR_REGEXES = {"(GES|GER|GET|GEQ|GEH)[0,9]{4}[A-Z]?"};
    public static final Condition ULR_CONDITION = new Condition(5, "pass 5 of each GES,GER,GET,GEQ and GEH",
            ULR_REGEXES);
    public static final CourseRequirement UNIVERSITY_LEVEL_REQUIREMENT = new PrimitiveRequirement(
            "University Level Requirements",
            "Pass one module each with prefix GES, GET, GEQ, GEH, GER",
            ULR_CONDITION, GE);
    //IT Professionalism
    public static final String[] ITP_REGEXES = {"IS1103X?", "CS2101", "ES2660"};
    public static final Condition ITP_CONDITION = new Condition("pass all",
            ITP_REGEXES);
    public static final CourseRequirement IT_PROFESSIONALISM = new PrimitiveRequirement(
            "IT Professionalism", "Pass all IS1103/X, CS2101 and ES2660",
            ITP_CONDITION, CORE);

    //Computer Science Foundation
    public static final String[] CSF_REGEXES = {"CS1101S", "CS1231", "CS2030", "CS2040", "CS2100",
        "CS2030", "CS2040", "CS2100", "CS2103T", "CS2105", "CS2106", "CS3230"};
    public static final Condition CSF_CONDITION = new Condition(
            "pass all", CSF_REGEXES
    );
    public static final CourseRequirement COMPUTER_SCIENCE_FOUNDATION = new PrimitiveRequirement(
            "Computer Science Foundation:",
            "Pass all of CS1101S, CS1231, CS2030, CS2040, CS2100, CS2103T, CS2105, CS2106, CS3230",
            CSF_CONDITION, CORE
    );
    //Algorithms Focus Area
    public static final String[] ALGORITHMS_PRIMARY = {"CS3230", "CS3236", "CS4231", "CS4232", "CS4234"};
    public static final String[] ALGORITHMS_PRIMARY_LEVEL_4 = Arrays
            .stream(ALGORITHMS_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] ALGORITHMS_ELECTIVE = {
        "CS3233", "CS4257", "CS4268", "CS5230", "CS5234", "CS5236", "CS5237", "CS5238", "CS5330"
    };
    public static final String[] AI_PRIMARY = {
        "CS3243", "CS3244", "CS4243", "CS4244", "CS4246", "CS4248"
    };
    public static final String[] AI_PRIMARY_LEVEL_4 = Arrays
            .stream(AI_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] AI_ELECTIVE = {
        "CS4216", "CS4220", "CS5209", "CS5215", "CS5228", "CS5242", "CS5247", "CS5340", "CS5339"
    };
    public static final String[] SOFTWARE_ENG_PRIMARY = {
        "CS2103T?", "CS3213", "CS3219", "CS4211", "CS4218", "CS4239"
    };
    public static final String[] SOFTWARE_ENG_PRIMARY_LEVEL_4 = Arrays
            .stream(SOFTWARE_ENG_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] SOFTWARE_ENG_ELECTIVE = {
        "CS3216", "CS3217", "CS3226", "CS3234", "CS4217", "CS5219", "CS5232", "CS5272"
    };
    public static final String[] ALL_FOCUS_AREA = Stream
            .of(ALGORITHMS_PRIMARY, ALGORITHMS_ELECTIVE,
                    AI_PRIMARY, AI_ELECTIVE,
                    SOFTWARE_ENG_PRIMARY, SOFTWARE_ENG_ELECTIVE)
            .flatMap(regexes -> Arrays.stream(regexes))
            .toArray(String[]::new);
    public static final String[] ALL_FOCUS_AREA_LEVEL_4 = Stream
            .of(ALL_FOCUS_AREA)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);

    public static final Condition ALGORITHMS_PASS_3_PRIMARY = new Condition(
            3, "Pass 3 Area Primary", ALGORITHMS_PRIMARY
    );
    public static final Condition ALGORITHMS_PASS_1_LEVEL4_PRIMARY = new Condition(
            1, "Pass at least 1 level 4 Primary", ALGORITHMS_PRIMARY_LEVEL_4
    );
    public static final Condition AT_LEAST_3_MODS_LEVEL4_ABOVE = new Condition(
            3, "Pass at least 3 level 4 mods from any Focus Area", ALL_FOCUS_AREA_LEVEL_4
    );

    public static final Condition AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA = new Condition(
            6, "Pass at least 6 mods from any Focus Area", ALL_FOCUS_AREA
    );

    public static final CourseRequirement FOCUS_AREA_ALGORITHMS = new CompositeRequirement(
            new CompositeRequirement(new PrimitiveRequirement("3 Area Primary from Algorithms",
                    "Pass 3 Area Primary from Algorithms", ALGORITHMS_PASS_3_PRIMARY, CourseReqType.TE
            ) , new PrimitiveRequirement("At least 1 level 4",
                    "Pass at least 1 level 4 from primaries", ALGORITHMS_PASS_1_LEVEL4_PRIMARY, CourseReqType.TE),
                    CompositeRequirement.LogicalConnector.AND, CourseReqType.TE
            ), new CompositeRequirement(new PrimitiveRequirement("At least 3 level 4000 and above",
            "At least 3 mods level 4000 and above", AT_LEAST_3_MODS_LEVEL4_ABOVE, CourseReqType.TE),
            new PrimitiveRequirement("At least 6 mods from any focus area", "blablabla",
                    AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA, CourseReqType.TE), CompositeRequirement.LogicalConnector.AND,
            CourseReqType.TE),
            CompositeRequirement.LogicalConnector.AND, CourseReqType.TE);

    public static final String[] SCIENCE_REGEXES = { "MA1521", "ST2334", "MA1101R",
        "(MA|ST|PC|LSM|CM)[1-9][0-9]{3}[A-Z]?"};
    public static final Condition SCIENCE_CONDITION = new Condition("Complete 4 science mods",
            SCIENCE_REGEXES);
    public static final CourseRequirement SCIENCE_REQUIREMENT = new PrimitiveRequirement(
            "Science Requirements", "Complete MA1521, MA1101R, ST2334 and one other science mod",
            SCIENCE_CONDITION, CourseReqType.UE);
}
