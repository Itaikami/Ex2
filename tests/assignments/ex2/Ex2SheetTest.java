package assignments.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {

    @Test
    void isIn() {
    }

    @Test
    void canBeComputed() {
    }

    @Test
    void computeForm() {
        Ex2Sheet table=new Ex2Sheet(5,5);
        table.set(0,0,"a0");
        assertEquals(table.computeForm(table.eval(0,0)),0);;
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