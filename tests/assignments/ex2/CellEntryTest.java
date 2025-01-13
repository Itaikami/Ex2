package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CellEntryTest {

    @Test
    void isValid() {
        CellEntry c=new CellEntry(0,5);
        CellEntry c1=new CellEntry("aa");
        CellEntry c2=new CellEntry(50,50);
        CellEntry c3=new CellEntry("d7");
        assertTrue(c.isValid());
        assertFalse(c1.isValid());
        assertFalse(c2.isValid());
        assertTrue(c3.isValid());

    }

    @Test
    void deterXfromnumber() {
        assertEquals(CellEntry.deterXfromnumber(0),"A");
        assertEquals(CellEntry.deterXfromnumber(4),"E");

    }

    @Test
    void deterXfromstring() {
        assertEquals(CellEntry.deterXfromstring("a"),0);
        assertEquals(CellEntry.deterXfromstring("C"),2);



    }
}