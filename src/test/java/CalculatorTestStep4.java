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
        assertEquals("", new Float(9),calculator.calculate("3^2"));
        assertEquals("", new Float(4),calculator.calculate("16^(0.5)"));
        assertEquals("", new Float(0.5), calculator.calculate("2^(-1)"));
    }

    @Test
    public void testCalculateFloat(){
        assertEquals("", new Float(5),calculator.calculate("2.5 + 2.5"));
        assertEquals("", new Float(4),calculator.calculate("8*0.5"));
        assertEquals("", new Float(8),calculator.calculate("4/0.5"));
        assertEquals("", new Float(3),calculator.calculate("3.778-0.778"));
    }
}
