package assignments.ex2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {
    Ex2Sheet sheet = new Ex2Sheet();
    HashSet<String> ref= new HashSet<>();
    @Test
    void isIn() {
    }

    @Test
    public void testIsCircular() {
        CellEntry c=new CellEntry(0,0);
        CellEntry c1=new CellEntry(0,1);
        CellEntry c2=new CellEntry("B0");

        sheet.set(0, 0, "B1"); // A1 -> B1
        sheet.set(0, 1, "A1"); // B1 -> A1

        assertTrue(sheet.isCircular(c1,ref), "Circular reference should be detected");

        sheet.set(c2.getX(), c2.getY(), "5"); // C1
        assertFalse(sheet.isCircular(c2,ref), "Non-circular reference should not be flagged");
    }

    @Test
    public void testCanBeComputed() {
        CellEntry c=new CellEntry(0,0);
        CellEntry c1=new CellEntry(0,1);
        CellEntry c2=new CellEntry(0,2);
        sheet.set(c.getX(), c.getY(), "5"); // A0
        assertTrue(sheet.canBeComputed(c), "A1 contains a value and should be computable");

        sheet.set(0, 1, "=+5"); // A1
        assertTrue(sheet.canBeComputed(c1), "B1 references A1 and should be computable");

        sheet.set(0, 2, "Text"); // A2
        assertFalse(sheet.canBeComputed(c2), "C1 contains text and should not be computable");
    }

    @Test
    public void testComputeForm() {
        sheet.set(0, 0, "5"); // A0
        sheet.set(0, 1, "A0+5"); // A1
        assertEquals(5, sheet.computeForm("A1"), "A1 should compute to 10");

        sheet.set(1, 0, "10*2"); // B0
        assertEquals(20, sheet.computeForm("B0"), "B0 should compute to 20");

        sheet.set(1, 1, "Text"); // D1
        assertThrows(IllegalArgumentException.class, () -> {
            sheet.computeForm("D1");
        }, "D1 contains text and should throw an exception");
    }

    @Test
    public void testSingleDepth() {
        int[][] dep= sheet.depth();;
        CellEntry c=new CellEntry(0,0);
        CellEntry c1=new CellEntry(0,1);
        CellEntry c2=new CellEntry(0,2);
        CellEntry c3=new CellEntry(0,3);
        CellEntry c4=new CellEntry(0,4);
        sheet.set(c.getX(), c.getY(), "5"); // A1
        assertEquals(0, sheet.singleDepth(c.getX(),c.getY(),dep), "A1 is a value and should have depth 0");

        // תא עם נוסחה פשוטה שתלויה בתא אחר
        sheet.set(0, 1, "A1+5"); // B1
        assertEquals(1, sheet.singleDepth(c1.getX(),c1.getY(),dep), "B1 references A1 and should have depth 1");

        // תא עם נוסחה מורכבת יותר
        sheet.set(0, 2, "B1+10"); // C1
        assertEquals(2, sheet.singleDepth(c2.getX(),c2.getY(),dep), "C1 references B1 (which references A1) and should have depth 2");

        // תא עם טקסט
        sheet.set(0, 3, "Text"); // D1
        assertEquals(-1, sheet.singleDepth(c3.getX(),c3.getY(),dep), "D1 contains text and should have depth -1");

        // תא ריק
        sheet.set(0, 4, ""); // E1
        assertEquals(-1, sheet.singleDepth(c4.getX(),c4.getY(),dep), "E1 is empty and should have depth -1");
    }

    @Test
    void findIndOfMainOp() {
    }

    @Test
    void isOp() {
    }

    @Test
    void isFormula() {
        Ex2Sheet table=new Ex2Sheet(5,5);
        table.set(0,0,"5");
        table.set(0,1,"10");
        assertTrue(table.isFormula("=a2"));
    }
}