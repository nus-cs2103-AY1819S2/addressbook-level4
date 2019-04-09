package seedu.address.model.recmodule;

import static org.junit.Assert.assertTrue;

import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.FAC;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.IE;
import static seedu.address.model.course.CourseReqType.UE;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoTitle;
import seedu.address.testutil.RecModuleBuilder;

public class RecModuleComparatorTest {

    private RecModuleComparator comparator = new RecModuleComparator();

    private ModuleInfoCode ma1521 = new ModuleInfoCode("MA1521");
    private ModuleInfoCode ma1101r = new ModuleInfoCode("MA1101R");
    private ModuleInfoCode acc1002 = new ModuleInfoCode("ACC1002");
    private ModuleInfoCode ycc1111 = new ModuleInfoCode("YCC1111");
    private ModuleInfoCode cs2103t = new ModuleInfoCode("CS2103T");
    private ModuleInfoCode pc3130 = new ModuleInfoCode("PC3130");
    private ModuleInfoCode acc4611 = new ModuleInfoCode("ACC4611");
    private ModuleInfoCode vm5101 = new ModuleInfoCode("VM5101");
    private ModuleInfoCode baa6001 = new ModuleInfoCode("BAA6001");
    private ModuleInfoTitle title = new ModuleInfoTitle("title"); // dummy title

    @Test
    public void compareType() {
        RecModule core = new RecModuleBuilder(cs2103t, title).withType(CORE).build();
        RecModule bd = new RecModuleBuilder(cs2103t, title).withType(BD).build();
        RecModule ie = new RecModuleBuilder(cs2103t, title).withType(IE).build();
        RecModule fac = new RecModuleBuilder(cs2103t, title).withType(FAC).build();
        RecModule ge = new RecModuleBuilder(cs2103t, title).withType(GE).build();
        RecModule ue = new RecModuleBuilder(cs2103t, title).withType(UE).build();

        assertTrue(comparator.compare(core, bd) < 0);
        assertTrue(comparator.compare(bd, ie) < 0);
        assertTrue(comparator.compare(ie, fac) < 0);
        assertTrue(comparator.compare(fac, ge) < 0);
        assertTrue(comparator.compare(ge, ue) < 0);
    }

    @Test
    public void compareLevel() {
        RecModule one = new RecModuleBuilder(ma1521, title).withType(CORE).build();
        RecModule two = new RecModuleBuilder(cs2103t, title).withType(CORE).build();
        RecModule three = new RecModuleBuilder(pc3130, title).withType(CORE).build();
        RecModule four = new RecModuleBuilder(acc4611, title).withType(CORE).build();
        RecModule five = new RecModuleBuilder(vm5101, title).withType(CORE).build();
        RecModule six = new RecModuleBuilder(baa6001, title).withType(CORE).build();

        assertTrue(comparator.compare(one, two) < 0);
        assertTrue(comparator.compare(two, three) < 0);
        assertTrue(comparator.compare(three, four) < 0);
        assertTrue(comparator.compare(four, five) < 0);
        assertTrue(comparator.compare(five, six) < 0);
    }

    @Test
    public void compareLex() {
        RecModule acc1002 = new RecModuleBuilder(this.acc1002, title).withType(CORE).build();
        RecModule ma1101r = new RecModuleBuilder(this.ma1101r, title).withType(CORE).build();
        RecModule ma1521 = new RecModuleBuilder(this.ma1521, title).withType(CORE).build();
        RecModule ycc1111 = new RecModuleBuilder(this.ycc1111, title).withType(CORE).build();

        assertTrue(comparator.compare(acc1002, ma1101r) < 0);
        assertTrue(comparator.compare(ma1101r, ma1521) < 0);
        assertTrue(comparator.compare(ma1521, ycc1111) < 0);
    }

    @Test
    public void compareMultiple() {
        RecModule rm1 = new RecModuleBuilder(baa6001, title).withType(CORE).build();
        RecModule rm2 = new RecModuleBuilder(ma1521, title).withType(UE).build();
        /* different type -> compare type */
        assertTrue(comparator.compare(rm1, rm2) < 0);

        rm1 = new RecModuleBuilder(ycc1111, title).withType(FAC).build();
        rm2 = new RecModuleBuilder(acc4611, title).withType(FAC).build();
        /* same type -> different level -> compare level */
        assertTrue(comparator.compare(rm1, rm2) < 0);

        rm1 = new RecModuleBuilder(acc1002, title).withType(UE).build();
        rm2 = new RecModuleBuilder(ycc1111, title).withType(UE).build();
        /* same type -> same level -> compare lexicographical order */
        assertTrue(comparator.compare(rm1, rm2) < 0);
    }
}
