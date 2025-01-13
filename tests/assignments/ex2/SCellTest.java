package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {

    @Test
    void isText() {
        assertTrue(SCell.isText("asd"));
        assertTrue(SCell.isText("a5"));
        assertFalse(SCell.isText("123"));
        assertFalse(SCell.isText("=123"));

    }

    @Test
    void isNumber() {
        assertTrue(SCell.isNumber("123"));
        assertTrue(SCell.isNumber("888888888"));
        assertFalse(SCell.isNumber("asd"));
        assertFalse(SCell.isNumber("=123"));
    }

    @Test
    void isCellFormula() {
        assertTrue(SCell.isCellFormula("=123"));
        assertTrue(SCell.isCellFormula("=a0"));
        assertFalse(SCell.isCellFormula("123"));
        assertFalse(SCell.isCellFormula("a0"));
    }
}