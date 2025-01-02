package assignments.ex2;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {

    @org.junit.jupiter.api.Test
    void isNumber() {
    }

    @org.junit.jupiter.api.Test
    void findIndOfMainOp() {
        assertEquals(SCell.findIndOfMainOp("(1+2)*3/(2/5)"),7);
    }

    @org.junit.jupiter.api.Test
    void eval() {
        assertEquals(SCell.eval("(1+2)*3/(2/5)"),22.5);
    }

    @org.junit.jupiter.api.Test
    void isFormula() {
    }

    @org.junit.jupiter.api.Test
    void getOrder() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void setData() {
    }

    @org.junit.jupiter.api.Test
    void getData() {
    }

    @org.junit.jupiter.api.Test
    void getType() {
    }

    @org.junit.jupiter.api.Test
    void setType() {
    }

    @org.junit.jupiter.api.Test
    void setOrder() {
    }
}