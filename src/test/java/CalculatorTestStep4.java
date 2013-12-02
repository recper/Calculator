import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 27.11.13
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorTestStep4 {
    Calculator calculator = new Calculator();

    @Test
    public void testCalculatePow(){
        assertEquals("Does not raises a power", new Float(9),calculator.calculate("3^2"));
        assertEquals("does not raises a fractional power", new Float(4),calculator.calculate("16^(0.5)"));
        assertEquals("does not raises a negative power", new Float(0.5), calculator.calculate("2^(-1)"));
    }

    @Test
    public void testCalculateFloat(){
        assertEquals("Doesn't sum fractals.", new Float(5),calculator.calculate("2.5 + 2.5"));
        assertEquals("Doesn't multiply fractals.", new Float(4),calculator.calculate("8*0.5"));
        assertEquals("Doesn't divide fractals.", new Float(8),calculator.calculate("4/0.5"));
        assertEquals("Doesn't sum fractals.", new Float(3),calculator.calculate("3.778-0.778"));
    }
}
