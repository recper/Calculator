import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorTestStep1 {
    Calculator calc;

    public CalculatorTestStep1() {
        calc = new Calculator();
    }

    @Test
    public void testCalculatePriority() {
        assertEquals("+ has got biggest priority then *.", new Float(5), calc.calculate("1+2*2"));
        assertEquals("Brackets do not influence on the priority.", new Float(4), calc.calculate("2*(1+1)"));
        assertEquals("Doesn't work left priority.", new Float(4), calc.calculate("2/2*4"));
        assertEquals("Brackets has got less priority then left priority.", new Float(0.25), calc.calculate("2/(2*4)"));
    }
    @Test
    public void testCalculateOneNumber() {
        assertEquals("Doesn't calculate one number.", new Float(1), calc.calculate("1"));
        assertEquals("Doesn't calculate one number.", new Float(1), calc.calculate("(1)"));
    }
    @Test
    public void testCalculateUnarMinuses() {
        assertEquals("Doesn't calculate unarn minus.", new Float(-1), calc.calculate("-1"));
        assertEquals("Doesn't calculate unarn minus twice.", new Float(1), calc.calculate("(-(-1))"));
        assertEquals("Doesn't calculate unarn minus before brackets.", new Float(-2), calc.calculate("-(1+1)"));
    }
    @Test
    public void testCalculateWithSpaces() {
        assertEquals("Doesn't calculate with spaces between numbers.", new Float(3), calc.calculate("   1   +   1  +   1  "));
        assertEquals("Doesn't calculate with spaces between bracket and number.", new Float(1.5), calc.calculate(" (  1 + 1  /   2  ) "));
        assertEquals("Doesn't calculate with spaces between unarn minus.", new Float(1), calc.calculate("   -    1  + 2 "));
    }
}
