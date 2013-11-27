import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 27.11.13
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class PolishConverterAndCalculatorTest {
    StringValidator valid = new Calculator().getValidator();

    @Test
    public void testCalculateBracketsError() {
        valid.getErrors("1+1 ) ");
        assertEquals("Doesn't find closing bracket error.", "There isn't any opening bracket for ) in 4 position.\n", valid.errorMessage);
        valid.getErrors(" ( 1+1");
        assertEquals("Doesn't find opening bracket error.", "There is no closing bracket for ( in position 1.\n", valid.errorMessage);
    }

    @Test
    public void testCalculateUnknownSymbols() {
        valid.getErrors(" ; 1+1");
        assertEquals("Doesn't find unknown symbols.", valid.unknownSymbolError+'1', valid.errorMessage);
    }

    @Test
    public void testCalculateWrongWords() {
        valid.getErrors("cin(1)");
        assertEquals("Doesn't find wrong words.", valid.unknownFunctionError+'0', valid.errorMessage);
    }

    @Test
    public void testCalculateWrongSigns() {
        valid.getErrors("1++1");
        assertEquals("Doesn't find wrong signs.", valid.incorrectSignUsing+1, valid.errorMessage);
        valid.getErrors(" +1");
        assertEquals("Doesn't find wrong signs.", valid.incorrectSignUsing+1, valid.errorMessage);
        valid.getErrors("(1+1) (1+1)");
        assertEquals("Doesn't find wrong signs.", valid.incorrectSignUsing+4, valid.errorMessage);
        valid.getErrors("1+1 (1+1)");
        assertEquals("Doesn't find wrong signs.", valid.incorrectSignUsing+2, valid.errorMessage);
    }
}

