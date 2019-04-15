package seedu.address.model.util;

import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.FAC;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.IE;
import static seedu.address.model.course.CourseReqType.UE;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.model.course.CompositeRequirement;
import seedu.address.model.course.Condition;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.course.PrimitiveRequirement;

/**
 * Represents default Course Requirements in class
 */
public class SampleCourseRequirement {
    //University Level Requirement
    public static final String GEH_PATTERN = "GEH1[0-9]{3}[A-Z]?";
    public static final String GEQ_PATTERN = "(GEQ1000[A-Z]?)|(GEQ1917)";
    public static final String GER_PATTERN = "GER1000[A-Z]?";
    public static final String GES_PATTERN = "GES1[0-9]{3}[A-Z]?";
    public static final String GET_PATTERN = "GET1[0-9]{3}[A-Z]?";
    public static final Condition GEH_CONDITION = new Condition(1, GEH_PATTERN);
    public static final Condition GEQ_CONDITION = new Condition(1, GEQ_PATTERN);
    public static final Condition GER_CONDITION = new Condition(1, GER_PATTERN);
    public static final Condition GES_CONDITION = new Condition(1, GES_PATTERN);
    public static final Condition GET_CONDITION = new Condition(1, GET_PATTERN);
    public static final CourseRequirement UNIVERSITY_LEVEL_REQUIREMENT = new PrimitiveRequirement(
            "University Level Requirements",
            "Complete 1 module each with prefix GES, GET, GEQ, GEH, GER",
            GE, GEH_CONDITION, GEQ_CONDITION, GER_CONDITION, GES_CONDITION, GET_CONDITION);

    //IT Professionalism
    public static final String[] ITP_REGEXES = {"IS1103X?", "CS2101", "ES2660"};
    public static final Condition[] ITP_CONDITIONS = Stream.of(ITP_REGEXES)
            .map(Condition::new).toArray(Condition[]::new);
    public static final CourseRequirement IT_PROFESSIONALISM =
            new PrimitiveRequirement("IT Professionalism",
            "Complete IS1103/X, CS2101 and ES2660", CORE, ITP_CONDITIONS);

    //Computer Science Foundation
    public static final String[] CSF_REGEXES = {"(CS1010)|(CS1101S)", "CS1231", "CS2030", "CS2040", "CS2100",
        "CS2030", "CS2040", "CS2100", "CS2103T", "CS2105", "CS2106", "CS3230"};
    public static final Condition[] CSF_CONDITIONS = Stream.of(CSF_REGEXES)
            .map(Condition::new).toArray(Condition[]::new);
    public static final CourseRequirement COMPUTER_SCIENCE_FOUNDATION = new PrimitiveRequirement(
        "Computer Science Foundation",
        "Complete all of CS1010/CS1101S, CS1231, CS2030, CS2040, CS2100, CS2103T, CS2105, CS2106, CS3230",
        CORE, CSF_CONDITIONS);

    //Electives, Focus Area
    public static final String ALGORITHMS_PRIMARY =
            "(CS3230)|(CS3236)|(CS4231)|(CS4232)|(CS4234)";
    public static final String ALGORITHMS_PRIMARY_LEVEL_4 =
            "(CS4231)|(CS4232)|(CS4234)";
    public static final String ALGORITHMS_ELECTIVE =
        "(CS3233)|(CS4257)|(CS4268)|(CS5230)|(CS5234)|(CS5236)|(CS5237)|(CS5238)|(CS5330)";
    public static final String AI_PRIMARY =
        "(CS3243)|(CS3244)|(CS4243)|(CS4244)|(CS4246)|(CS4248)";
    public static final String AI_PRIMARY_LEVEL_4 = "(CS4243)|(CS4244)|(CS4246)|(CS4248)";

    public static final String AI_ELECTIVE =
        "(CS4216)|(CS4220)|(CS5209)|(CS5215)|(CS5228)|(CS5242)|(CS5247)|(CS5340)|(CS5339)";
    public static final String SOFTWARE_ENG_PRIMARY =
            "(CS2103T?)|(CS3219)|(CS4211)|(CS4218)|(CS4239)";
    public static final String SOFTWARE_ENG_PRIMARY_LEVEL_4 = "(CS4211)|(CS4218)|(CS4239)";
    public static final String SOFTWARE_ENG_ELECTIVE =
        "(CS3216)|(CS3217)|(CS3226)|(CS3234)|(CS4217)|(CS5219)|(CS5232)|(CS5272)";
    public static final String COMPUTER_GRAPHICS_PRIMARY =
        "(CS3241)|(CS3242)|(CS3247)|(CS4247)|(CS4350)";
    public static final String COMPUTER_GRAPHICS_PRIMARY_LEVEL_4 =
            "(CS3247)|(CS4247)|(CS4350)";
    public static final String COMPUTER_GRAPHICS_ELECTIVE =
        "(CS3218)|(CS3240)|(CS3249)|(CS3343)|(CS4240)|(CS4243)|(CS4249)|(CS4344)|(CS4345)|"
        + "(CS4351)|(CS5237)|(CS5240)|(CS5343)|(CS5346)";
    public static final String COMPUTER_SECURITY_PRIMARY =
        "(CS2107)|(CS3235)|(CS4236)|(CS4238)|(CS4239)";
    public static final String COMPUTER_SECURITY_LEVEL_4 = "(CS4236)|(CS4238)|(CS4239)";
    public static final String COMPUTER_SECURITY_ELECTIVE =
        "(CS3221)|(CS4257)|(CS5231)|(CS5250)|(CS5321)|(CS5332)|(IFS4101)|(IFS4102)";
    public static final String DATABASE_SYSTEM_PRIMARY =
        "(CS2102)|(CS3223)|(CS4221)|(CS4224)|(CS4225)";
    public static final String DATABASE_SYSTEM_LEVEL_4 = "(CS4221)|(CS4224)|(CS4225)";
    public static final String DATABASE_SYSTEM_ELECTIVE = "(CS4220)|(CS5226)|(CS5228)|(CS5322)";
    public static final String MULTIMEDIA_PRIMARY = "(CS2108)|(CS3245)|(CS4242)|(CS4248)|(CS4347)";
    public static final String MULTIMEDIA_ELECTIVES = "(CS5246)|(CS5241)|(CS6242)";
    public static final String NETWORK_PRIMARY = "(CS2105)|(CS3103)|(CS4222)|(CS4226)|(CS4231)";
    public static final String NETWORK_PRIMARY_LEVEL_4 = "(CS4222)|(CS4226)|(CS4231)";
    public static final String NETWORK_ELECTIVE =
        "(CS4344)|(CS5223)|(CS5224)|(CS5229)|(CS5248)|(CS5321)";
    public static final String PARALLEL_PRIMARY =
        "(CS3210)|(CS3211)|(CS4231)|(CS4223)";
    public static final String PARALLEL_PRIMARY_LEVEL_4 = "(CS4231)|(CS4223)";
    public static final String PARALLEL_ELECTIVE =
        "(CS4237)|(CS4271)|(CS4345)|(CS5207)|(CS5222)|(CS5223)|(CS5224)|(CS5239)|(CS5250)";
    public static final String PROGRAMMING_LANGUAGES_PRIMARY =
        "(CS2104)|(CS3211)|(CS4215)|(CS4212)";
    public static final String PROGRAMMING_LANGUAGES_ELECTIVE =
        "(CS3234)|(CS4216)|(CS5232)|(CS5214)|(CS5125)|(CS5218)";
    public static final String ALL_FOCUS_AREA = ALGORITHMS_PRIMARY + "|" + ALGORITHMS_ELECTIVE
                    + "|" + AI_PRIMARY + "|" + AI_ELECTIVE
                    + "|" + SOFTWARE_ENG_PRIMARY + "|" + SOFTWARE_ENG_ELECTIVE
                    + "|" + COMPUTER_GRAPHICS_PRIMARY + "|" + COMPUTER_GRAPHICS_ELECTIVE
                    + "|" + COMPUTER_SECURITY_PRIMARY + "|" + COMPUTER_SECURITY_ELECTIVE
                    + "|" + DATABASE_SYSTEM_PRIMARY + "|" + DATABASE_SYSTEM_ELECTIVE
                    + "|" + PARALLEL_PRIMARY + "|" + PARALLEL_ELECTIVE
                    + "|" + NETWORK_PRIMARY + "|" + NETWORK_ELECTIVE;
    public static final String ALL_FOCUS_AREA_LEVEL_4 = "CS[4][0-9]{3}";
    public static final Condition ALGORITHMS_PASS_3_PRIMARY = new Condition(3, ALGORITHMS_PRIMARY);

    public static final Condition ALGORITHMS_PASS_1_LEVEL4_PRIMARY = new Condition(ALGORITHMS_PRIMARY_LEVEL_4);
    public static final Condition AI_PASS_3_PRIMARY = new Condition(3, AI_PRIMARY);
    public static final Condition AI_PASS_1_LEVEL4_PRIMARY = new Condition(1, AI_PRIMARY_LEVEL_4);
    public static final Condition SE_PASS_3_PRIMARY = new Condition(3, SOFTWARE_ENG_PRIMARY);
    public static final Condition SE_PASS_1_LEVEL4_PRIMARY = new Condition(SOFTWARE_ENG_PRIMARY_LEVEL_4);
    public static final Condition AT_LEAST_3_MODS_LEVEL4_ABOVE = new Condition(3, ALL_FOCUS_AREA_LEVEL_4);
    public static final Condition AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA = new Condition(6, ALL_FOCUS_AREA);

    public static final CourseRequirement FOCUS_AREA_ALGORITHMS =
            new PrimitiveRequirement("Focus Area: Algorithms",
            "Complete 6 modules from any Focus Area, with at least 3 from Level 4 and \n"
            + "3 Area Primary from Algorithms with at least 1 from Level 4 ", BD, ALGORITHMS_PASS_3_PRIMARY,
            ALGORITHMS_PASS_1_LEVEL4_PRIMARY, AT_LEAST_3_MODS_LEVEL4_ABOVE, AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA);
    public static final CourseRequirement FOCUS_AREA_AI = new PrimitiveRequirement(
            "Focus Area: Artificial Intelligence",
            "Complete 6 modules from any Focus Area, with at least 3 from Level 4 and \n"
                    + "3 Area Primary from AI with at least 1 from Level 4 ", BD, AI_PASS_3_PRIMARY,
            AI_PASS_1_LEVEL4_PRIMARY, AT_LEAST_3_MODS_LEVEL4_ABOVE, AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA);
    public static final CourseRequirement FOCUS_AREA_SOFTWARE_ENG =
            new PrimitiveRequirement("Focus Area: Software Engineering",
            "Complete 6 modules from any Focus Area, with at least 3 from Level 4 and \n"
                    + "3 Area Primary from Software Engineering with at least 1 from Level 4 ", BD, SE_PASS_3_PRIMARY,
            SE_PASS_1_LEVEL4_PRIMARY, AT_LEAST_3_MODS_LEVEL4_ABOVE, AT_LEAST_6_MODS_FROM_ALL_FOCUS_AREA);

    public static final String[] MATH_REGEXES = {"MA1521", "ST2334", "MA1101R"};
    public static final Condition[] MATH_CONDITIONS = Stream.of(MATH_REGEXES)
            .map(Condition::new).toArray(Condition[]::new);
    public static final CourseRequirement MATH_REQUIREMENT =
            new PrimitiveRequirement("Mathematics", "Complete MA1521, MA1101R and ST2334",
                    CORE, MATH_CONDITIONS);
    public static final String SCIENCE_REGEX =
            "CM1121|CM1131|CM1417|LSM1102|LSM1105|LSM1106|LSM1301|LSM1302|PC1141|PC1142|PC1143|PC1144|PC1221|PC1222|"
                    + "PC1432|MA2213|MA2214|CM1101|CM1111|CM1161|CM1191|CM1401|CM1402|CM1501|CM1502|LSM1303|LSM1306|"
                    + "PC1421|PC1431|PC1433|MA1104|MA2104|MA2101|MA2108|MA2501|ST2132|ST2137";
    public static final Condition SCIENCE_CONDITION = new Condition(SCIENCE_REGEX);
    public static final CourseRequirement SCIENCE_REQUIREMENT = new PrimitiveRequirement(
            "Sciences", "Complete 1 Science module other than MA1521, MA1101R, ST2334",
                FAC, SCIENCE_CONDITION);

    //Computer Systems Team Project
    public static final Condition CONDITION_CS3203 = new Condition("CS3203");
    public static final Condition CONDITION_CS3216_CS3217 = new Condition(2, "CS3216|CS3217");
    public static final Condition CONDITION_CS3281_CS3282 = new Condition(2, "CS3281|CS3282");
    public static final CourseRequirement COMPUTER_SYSTEM_TEAM_PROJECT = new CompositeRequirement(
            new CompositeRequirement(
                    new PrimitiveRequirement("Computer Systems Team Project",
                            "(Complete CS3203)",
                            BD, CONDITION_CS3203),
                    new PrimitiveRequirement("Complete CS3216 and CS3217",
                    "Complete CS3216 and CS3217", BD, CONDITION_CS3216_CS3217),
                    CompositeRequirement.LogicalConnector.OR, BD),
            new PrimitiveRequirement("Complete CS3281 and CS3282",
            "Complete CS3281 and CS3282", BD, CONDITION_CS3281_CS3282),
            CompositeRequirement.LogicalConnector.OR, BD);

    //Industry Experience Requirement
    public static final Condition CP3880 = new Condition("CP3880");
    public static final Condition CP3200 = new Condition("CP3200");
    public static final Condition CP3202_CP3107 = new Condition("CP3202|CP3107");
    public static final Condition IS4010 = new Condition("IS4010");
    public static final CourseRequirement INDUSTRIAL_SYSTEM_EXPERIENCE = new PrimitiveRequirement(
            "Industrial Experience Requirement",
            "Complete 1 6-month Internship through ATAP", IE, CP3880)
            .or(new PrimitiveRequirement("Complete 2 3-month Internships",
                    "Complete CP3200 and CP3202", IE, CP3200, CP3202_CP3107))
            .or(new PrimitiveRequirement("Complete IS4010", "Complete IS4010", IE, IS4010));

    //Unrestricted Electives
    public static final Condition COMPLETE_40_MODULES = new Condition(40, ".*");
    public static final CourseRequirement TOTAL_MODULE_COUNT =
            new PrimitiveRequirement("Unrestricted Electives",
            "Complete at least 40 modules to graduate", UE, COMPLETE_40_MODULES);

    public static List<CourseRequirement> getTypicalRequirements() {
        return List.of(UNIVERSITY_LEVEL_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_ALGORITHMS,
                INDUSTRIAL_SYSTEM_EXPERIENCE, COMPUTER_SYSTEM_TEAM_PROJECT, IT_PROFESSIONALISM,
                MATH_REQUIREMENT, SCIENCE_REQUIREMENT, TOTAL_MODULE_COUNT);
    }

}
