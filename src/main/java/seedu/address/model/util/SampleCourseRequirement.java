package seedu.address.model.util;

import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;

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
    public static final String[] ULR_REGEXES = {"GES[0-9]{4}[A-Z]?", "GET[0-9]{4}[A-Z]?", "GEH[0-9]{4}[A-Z]?",
    "GER1000", "GEQ1000"};
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
    public static final String[] COMPUTER_GRAPHICS_PRIMARY = {
        "CS3241", "CS3242", "CS3247", "CS4247", "CS4350"
    };
    public static final String[] COMPUTER_GRAPHICS_PRIMARY_LEVEL_4 = Arrays
            .stream(COMPUTER_GRAPHICS_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] COMPUTER_GRAPHICS_ELECTIVE = {
        "CS3218", "CS3240", "CS3249", "CS3343", "CS4240", "CS4243", "CS4249", "CS4344", "CS4345",
        "CS4351", "CS5237", "CS5240", "CS5343", "CS5346"
    };
    public static final String[] COMPUTER_SECURITY_PRIMARY = {
        "CS2107", "CS3235", "CS4236", "CS4238", "CS4239"
    };
    public static final String[] COMPUTER_SECURITY_LEVEL_4 = Arrays
            .stream(COMPUTER_SECURITY_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] COMPUTER_SECURITY_ELECTIVE = {
        "CS3221", "CS4257", "CS5231", "CS5250", "CS5321", "CS5332", "IFS4101", "IFS4102"
    };
    public static final String[] DATABASE_SYSTEM_PRIMARY = {
        "CS2102", "CS3223", "CS4221", "CS4224", "CS4225"
    };
    public static final String[] DATABASE_SYSTEM_LEVEL_4 = Arrays
            .stream(DATABASE_SYSTEM_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] DATABASE_SYSTEM_ELECTIVE = {
        "CS4220", "CS5226", "CS5228", "CS5322"
    };
    public static final String[] MULTIMEDIA_PRIMARY = {
        "CS2108", "CS3245", "CS4242", "CS4248", "CS4347"
    };
    public static final String[] MULTIMEDIA_ELECTIVES = {
        "CS5246", "CS5241", "CS6242"
    };
    public static final String[] NETWORK_PRIMARY = {
        "CS2105", "CS3103", "CS4222", "CS4226", "CS4231"
    };
    public static final String[] NETWORK_PRIMARY_LEVEL_4 = Arrays
            .stream(NETWORK_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] NETWORK_ELECTIVE = {
        "CS4344", "CS5223", "CS5224", "CS5229", "CS5248", "CS5321"
    };
    public static final String[] PARALLEL_PRIMARY = {
        "CS3210", "CS3211", "CS4231", "CS4223"
    };
    public static final String[] PARALLEL_PRIMARY_LEVEL_4 = Arrays
            .stream(PARALLEL_PRIMARY)
            .filter(regex -> regex.matches("CS4[0-9]{3}"))
            .toArray(String[]::new);
    public static final String[] PARALLEL_ELECTIVE = {
        "CS4237", "CS4271", "CS4345", "CS5207", "CS5222", "CS5223", "CS5224", "CS5239", "CS5250"
    };
    public static final String[] PROGRAMMING_LANGUAGES_PRIMARY ={
        "CS2104", "CS3211", "CS4215", "CS4212"
    };
    public static final String[] PROGRAMMING_LANGUAGES_ELECTIVE = {
        "CS3234", "CS4216", "CS5232", "CS5214", "CS5125", "CS5218"
    };

    public static final String[] ALL_FOCUS_AREA = Stream
            .of(ALGORITHMS_PRIMARY, ALGORITHMS_ELECTIVE,
                    AI_PRIMARY, AI_ELECTIVE,
                    SOFTWARE_ENG_PRIMARY, SOFTWARE_ENG_ELECTIVE,
                    COMPUTER_GRAPHICS_PRIMARY, COMPUTER_GRAPHICS_ELECTIVE,
                    COMPUTER_SECURITY_PRIMARY, COMPUTER_SECURITY_ELECTIVE,
                    DATABASE_SYSTEM_PRIMARY, DATABASE_SYSTEM_ELECTIVE,
                    PARALLEL_PRIMARY, PARALLEL_ELECTIVE,
                    NETWORK_PRIMARY, NETWORK_ELECTIVE)
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
    public static final Condition AI_PASS_3_PRIMARY = new Condition(
            3, "Pass 3 Area Primary in AI", AI_PRIMARY
    );
    public static final Condition AI_PASS_1_LEVEL4_PRIMARY = new Condition(
            1, "Pass at least 1 level 4 Primary", AI_PRIMARY_LEVEL_4
    );
    public static final Condition SE_PASS_3_PRIMARY = new Condition(
            3, "Pass 3 Area Primary in AI", SOFTWARE_ENG_PRIMARY
    );
    public static final Condition SE_PASS_1_LEVEL4_PRIMARY = new Condition(
            1, "Pass at least 1 level 4 Primary", SOFTWARE_ENG_PRIMARY_LEVEL_4
    );
    public static final Condition AT_LEAST_3_MODS_LEVEL4_ABOVE = new Condition(
            3, "Pass at least 3 level 4 mods from any Focus Area", ALL_FOCUS_AREA_LEVEL_4
    );
    public static final Condition AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA = new Condition(
            6, "Pass at least 6 mods from any Focus Area", ALL_FOCUS_AREA
    );
    public static final CourseRequirement FOCUS_AREA_ALGORITHMS = new CompositeRequirement(
            new CompositeRequirement(new PrimitiveRequirement("Complete 3 Area Primary from Algorithms",
                    "Pass 3 Area Primary from Algorithms", ALGORITHMS_PASS_3_PRIMARY, CourseReqType.TE
            ) , new PrimitiveRequirement("At least 1 from level 4 in Algorithms",
                    "Pass at least 1 level 4 from primaries", ALGORITHMS_PASS_1_LEVEL4_PRIMARY, CourseReqType.TE),
                    CompositeRequirement.LogicalConnector.AND, CourseReqType.TE
            ), new CompositeRequirement(new PrimitiveRequirement("At least 3 level 4000 and above",
            "At least 3 mods taken must be level 4000 and above from other focus areas", AT_LEAST_3_MODS_LEVEL4_ABOVE, CourseReqType.TE),
            new PrimitiveRequirement("Complete 6 mods", "Complete 6 modules in all focus areas",
                    AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA, CourseReqType.TE), CompositeRequirement.LogicalConnector.AND,
            CourseReqType.TE),
            CompositeRequirement.LogicalConnector.AND, CourseReqType.TE);
    public static final CourseRequirement FOCUS_AREA_AI = new CompositeRequirement(
            new CompositeRequirement(new PrimitiveRequirement("Complete 3 Area Primary from AI",
                    "Pass 3 Area Primary from AI", AI_PASS_3_PRIMARY, CourseReqType.TE
            ) , new PrimitiveRequirement("At least 1 from level 4 in AI",
                    "Pass at least 1 level 4 from primaries", AI_PASS_1_LEVEL4_PRIMARY, CourseReqType.TE),
                    CompositeRequirement.LogicalConnector.AND, CourseReqType.TE
            ), new CompositeRequirement(new PrimitiveRequirement("At least 3 level 4000 and above",
            "At least 3 mods taken must be level 4000 and above from other focus areas", AT_LEAST_3_MODS_LEVEL4_ABOVE, CourseReqType.TE),
            new PrimitiveRequirement("Complete 6 mods", "Complete 6 modules in all focus areas",
                    AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA, CourseReqType.TE), CompositeRequirement.LogicalConnector.AND,
            CourseReqType.TE),
            CompositeRequirement.LogicalConnector.AND, CourseReqType.TE);
    public static final CourseRequirement FOCUS_AREA_SOFTWARE_ENG = new CompositeRequirement(
            new CompositeRequirement(new PrimitiveRequirement("Complete 3 Area Primary from Software Eng",
                    "Pass 3 Area Primary from AI", SE_PASS_3_PRIMARY, CourseReqType.TE
            ) , new PrimitiveRequirement("At least 1 from level 4 in Software Eng",
                    "Pass at least 1 level 4 from primaries", SE_PASS_1_LEVEL4_PRIMARY, CourseReqType.TE),
                    CompositeRequirement.LogicalConnector.AND, CourseReqType.TE
            ), new CompositeRequirement(new PrimitiveRequirement("At least 3 level 4000 and above",
            "At least 3 mods taken must be level 4000 and above from other focus areas", AT_LEAST_3_MODS_LEVEL4_ABOVE, CourseReqType.TE),
            new PrimitiveRequirement("Complete 6 mods", "Complete 6 modules in all focus areas",
                    AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA, CourseReqType.TE), CompositeRequirement.LogicalConnector.AND,
            CourseReqType.TE),
            CompositeRequirement.LogicalConnector.AND, CourseReqType.TE);

    public static final String[] SCIENCE_REGEXES = { "MA1521", "ST2334", "MA1101R",
        "(MA|ST|PC|LSM|CM|FST)[1-9][0-9]{3}[A-Z]?"};
    public static final Condition SCIENCE_CONDITION = new Condition("Complete MA1521, MA1101R, ST2334 and one other science mod",
            SCIENCE_REGEXES);
    public static final CourseRequirement SCIENCE_REQUIREMENT = new PrimitiveRequirement(
            "Science Requirements", "Complete MA1521, MA1101R, ST2334 and one other science mod",
            SCIENCE_CONDITION, UE);
    public static final Condition CONDITION_CS3203 = new Condition("Complete CS3203", "CS3203");
    public static final Condition CONDITION_CS3216_CS3217 = new Condition("Complete CS3216 and CS3217",
            "CS3216", "CS3217");
    public static final Condition CONDITION_CS3281_CS3282 = new Condition("Compelte CS3281 and CS3282",
            "CS3281", "CS3282");
    public static final CourseRequirement COMPUTER_SYSTEM_TEAM_PROJECT = new CompositeRequirement(
            new CompositeRequirement(
                    new PrimitiveRequirement("Complete CS3203", "Complete CS3203",
                            CONDITION_CS3203, CORE
                    ),
                    new PrimitiveRequirement("Complete CS3216 and CS3217",
                    "Complete CS3216 and CS3217", CONDITION_CS3216_CS3217, CORE),
                    CompositeRequirement.LogicalConnector.OR, CORE),
            new PrimitiveRequirement("Complete CS3281 and CS3282",
            "Complete CS3281 and CS3282", CONDITION_CS3281_CS3282, CORE),
            CompositeRequirement.LogicalConnector.OR, CORE);
    public static final Condition CP3880 = new Condition("Complete CP3880", "CS3880");
    public static final Condition CP3200_CP3202 = new Condition("Complete CP3200, CP3202 or CP3107",
            "CP3200", "CP3202|CP3107");
    public static final Condition IS4010 = new Condition("Complete IS4010", "IS4010");
    public static final CourseRequirement INDUSTRIAL_SYSTEM_EXPERIENCE = new PrimitiveRequirement(
            "Complete ATAP - Advanced Technology Attachment Program",
            "Complete 6 month internship thru ATAP", CP3880, CORE)
            .or(new PrimitiveRequirement("Complete 2 3 - months internships",
                    "Complete two 3-month internships", CP3200_CP3202, CORE))
                    .or(new PrimitiveRequirement("Complete IS4010", "Compelte IS4010",
                            IS4010, CORE));
    public static final Condition COMPLETE_40_MODULES = new Condition(40,
            "Complete any 40 modules", "[A-Z]{2,3}[0-9]{4}[A-Z]?");
    public static final CourseRequirement TOTAL_MODULE_COUNT = new PrimitiveRequirement(
            "Complete at least 40 modules", "At least 40 modules needed to graduate!",
            COMPLETE_40_MODULES, UE);
}
