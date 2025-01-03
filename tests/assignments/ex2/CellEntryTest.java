package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @Test
    void isValid() {
        CellEntry s=new CellEntry("16");
        assertTrue(s.isValid());
    }

    @Test
    void deterX() {
    }
}