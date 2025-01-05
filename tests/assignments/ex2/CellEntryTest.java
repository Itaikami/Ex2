package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @Test
    void isValid() {
        CellEntry s=new CellEntry("a16");
        assertTrue(s.isValid());
    }

    @Test
    void deterX() {
        assertEquals(CellEntry.deterX("a"),0);
    }
}