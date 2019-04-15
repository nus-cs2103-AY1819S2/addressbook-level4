package seedu.address.model.recmodule;

import static org.junit.Assert.assertTrue;

import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.FAC;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.IE;

import org.junit.Test;

import seedu.address.testutil.RecModuleBuilder;

public class RecModuleComparatorTest {

    private final RecModuleComparator comparator = new RecModuleComparator();
    private final RecModuleBuilder rmb = new RecModuleBuilder();

    @Test
    public void compareType() {
        RecModule core = rmb.create("CS2103T", CORE);
        RecModule bd = rmb.create("CS2103T", BD);
        RecModule ie = rmb.create("CS2103T", IE);
        RecModule fac = rmb.create("CS2103T", FAC);
        RecModule ge = rmb.create("CS2103T", GE);

        assertTrue(comparator.compare(core, bd) < 0);
        assertTrue(comparator.compare(bd, ie) < 0);
        assertTrue(comparator.compare(ie, fac) < 0);
        assertTrue(comparator.compare(fac, ge) < 0);
    }

    @Test
    public void compareLevel() {
        RecModule one = rmb.create("MA1521", CORE);
        RecModule two = rmb.create("CS2103T", CORE);
        RecModule three = rmb.create("PC3130", CORE);
        RecModule four = rmb.create("ACC4611", CORE);
        RecModule five = rmb.create("VM5101", CORE);
        RecModule six = rmb.create("BAA6001", CORE);

        assertTrue(comparator.compare(one, two) < 0);
        assertTrue(comparator.compare(two, three) < 0);
        assertTrue(comparator.compare(three, four) < 0);
        assertTrue(comparator.compare(four, five) < 0);
        assertTrue(comparator.compare(five, six) < 0);
    }

    @Test
    public void compareLex() {
        RecModule acc1002 = rmb.create("ACC1002", CORE);
        RecModule ma1101r = rmb.create("MA1101R", CORE);
        RecModule ma1521 = rmb.create("MA1521", CORE);
        RecModule ycc1111 = rmb.create("YCC1111", CORE);

        assertTrue(comparator.compare(acc1002, ma1101r) < 0);
        assertTrue(comparator.compare(ma1101r, ma1521) < 0);
        assertTrue(comparator.compare(ma1521, ycc1111) < 0);
    }

    @Test
    public void compareMultiple() {
        RecModule rm1 = rmb.create("BAA6001", CORE);
        RecModule rm2 = rmb.create("MA1521", FAC);
        /* different type -> compare type */
        assertTrue(comparator.compare(rm1, rm2) < 0);

        rm1 = rmb.create("YCC1111", FAC);
        rm2 = rmb.create("ACC4611", FAC);
        /* same type -> different level -> compare level */
        assertTrue(comparator.compare(rm1, rm2) < 0);

        rm1 = rmb.create("ACC1002", FAC);
        rm2 = rmb.create("YCC1111", FAC);
        /* same type -> same level -> compare lexicographical order */
        assertTrue(comparator.compare(rm1, rm2) < 0);
    }
}
