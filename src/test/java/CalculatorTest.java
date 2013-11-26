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

    public CalculatorTest() {
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
    @Test
    public void testCalculateBracketsError(){
        calc.calculate("1+1 ) ");
        assertEquals("Doesn't find closing bracket error.","There isn't any opening bracket for ) in 4 position.\n",calc.errorMessage);
        calc.calculate(" ( 1+1");
        assertEquals("Doesn't find opening bracket error.","There is no closing bracket for ( in position 1.\n",calc.errorMessage);
    }
    @Test
    public void testCalculateUnknownSymbols(){
        calc.calculate(" ; 1+1");
        assertEquals("Doesn't find unknown symbols.","There is an unknown symbol in 1 position.\n",calc.errorMessage);
    }
    @Test
    public void testCalculateWrongWords(){
        calc.calculate("cin(1)");
        assertEquals("Doesn't find wrong words.","There is an unknown function name in 0 position.\n",calc.errorMessage);
    }
    @Test
    public void testCalculateWrongSigns(){
        calc.calculate("1++1");
        assertEquals("Doesn't find wrong signs.","Incorrect using of sign in 1 position.\n",calc.errorMessage);
        calc.calculate(" +1");
        assertEquals("Doesn't find wrong signs.","Incorrect using of sign in 1 position.\n",calc.errorMessage);
        calc.calculate("(1+1) (1+1)");
        assertEquals("Doesn't find wrong signs.","Incorrect using of sign in 4 position.\n",calc.errorMessage);
        calc.calculate("1+1 (1+1)");
        assertEquals("Doesn't find wrong signs.","Incorrect using of sign in 2 position.\n",calc.errorMessage);
    }

    @Test
    public void testConvertToPolishPriority() {
        assertEquals("Convector doesn't consider priority.", "2 3 4 * + ", calc.convertToPolish("2+3*4"));
    }
    @Test
    public void testConvertToPolishOneNumber() {
        assertEquals("Doesn't work with one number.", "1 ", calc.convertToPolish("1"));
        assertEquals("Doesn't work with one number in brackets.", "1 ", calc.convertToPolish("(1)"));
    }
    @Test
    public void testConvertToPolishBracketsPriority() {
        assertEquals("Doesn't consider brackets priority.", "2 3 + 4 * ", calc.convertToPolish("(2+3)*4"));
        assertEquals("Doesn't consider brackets priority.", "7 5 2 - + ", calc.convertToPolish("7+(5-2)"));
    }
    @Test
    public void testConvertToPolishLeftPriority() {
        assertEquals("Doesn't consider left priority.", "1 2 + 3 + 4 + ", calc.convertToPolish("1+2+3+4"));
        assertEquals("Doesn't consider left priority.", "2 3 / 4 * ", calc.convertToPolish("2/3*4"));
        assertEquals("Doesn't consider left priority.", "2 3 - 4 + ", calc.convertToPolish("2-3+4"));
    }
    @Test
    public void testConvertToPolishNumberContainsTwoSymbols() {
        assertEquals("Doesn't work with number, which contains 2 symbols.", "21 31 41 * + ", calc.convertToPolish("21+31*41"));
    }
}
