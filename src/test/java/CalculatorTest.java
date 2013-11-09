import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorTest {
    Calculator calc;

    public CalculatorTest(){
        calc = new Calculator();
    }

    @Test
    public void testConvertToPolishPriority() {
        assertEquals("Convector doesn't consider priority.","2 3 4 * + ", calc.convertToPolish("2+3*4"));
    }
    @Test
    public void testConvertToPolishOneNumber() {
        assertEquals("Doesn't work with one number.","1 ", calc.convertToPolish("1"));
        assertEquals("Doesn't work with one number in brackets.","1 ", calc.convertToPolish("(1)"));
    }
    @Test
    public void testConvertToPolishBracketsPriority() {
        assertEquals("Doesn't consider brackets priority.","2 3 + 4 * ", calc.convertToPolish("(2+3)*4"));
        assertEquals("Doesn't consider brackets priority.","7 5 2 - + ", calc.convertToPolish("7+(5-2)"));
    }
    @Test
    public void testConvertToPolishLeftPriority() {
        assertEquals("Doesn't consider left priority.","1 2 + 3 + 4 + ", calc.convertToPolish("1+2+3+4"));
        assertEquals("Doesn't consider left priority.","2 3 / 4 * ", calc.convertToPolish("2/3*4"));
        assertEquals("Doesn't consider left priority.","2 3 - 4 + ", calc.convertToPolish("2-3+4"));
    }
    @Test
    public void testConvertToPolishNumberContainsTwoSymbols() {
        assertEquals("Doesn't work with number, which contains 2 symbols.","21 31 41 * + ", calc.convertToPolish("21+31*41"));
    }
}
