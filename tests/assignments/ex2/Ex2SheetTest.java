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
        sheet.set(c.getX(), c.getY(), "=(16505*30)+56/2+5"); // A0
        assertTrue(sheet.canBeComputed(c));

        sheet.set(0, 1, "=a0+5");
        assertTrue(sheet.canBeComputed(c1));

        sheet.set(0, 2, "Text"); // A2
        assertFalse(sheet.canBeComputed(c2));
    }

    @Test
    public void testComputeForm() {
        sheet.set(0, 0, "=(5*2)+3+(9-3)/2");
        sheet.set(0, 1, "=A0+5");
        assertEquals(sheet.computeForm("A0"),16);
        assertEquals(21, sheet.computeForm("A1"));

        sheet.set(1, 0, "5");
        assertEquals(5, sheet.computeForm("B0"));

        sheet.set(1, 1, "Text");
        assertThrows(IllegalArgumentException.class, () -> {
            sheet.computeForm("B1");
        }, "contains text and should throw an exception");
    }

    @Test
    public void testSingleDepth() {

        CellEntry c=new CellEntry(0,0);
        CellEntry c1=new CellEntry(0,1);
        CellEntry c2=new CellEntry(0,2);
        CellEntry c3=new CellEntry(0,3);
        CellEntry c4=new CellEntry(0,4);
        sheet.set(c.getX(), c.getY(), "5");
        sheet.set(0, 1, "A0+5");
        sheet.set(0, 2, "A1+10");
        sheet.set(0, 3, "Text");
        sheet.set(0, 4, "A5");
        int[][] dep= sheet.depth();
        assertEquals(0, sheet.singleDepth(c.getX(),c.getY(),dep));

        assertEquals(1, sheet.singleDepth(c1.getX(),c1.getY(),dep));

        assertEquals(2, sheet.singleDepth(c2.getX(),c2.getY(),dep) );



        assertEquals(0, sheet.singleDepth(c3.getX(),c3.getY(),dep) );



        assertEquals(-1,sheet.singleDepth(c4.getX(),c4.getY(),dep));
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
        assertTrue(table.isFormula("=a10"));
        assertFalse(table.isFormula("=a100"));
        assertTrue(table.isFormula("=(12*5)+4-5*(3-2)"));
    }
}