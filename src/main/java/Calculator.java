import FunctionsAndSigns.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 8:51
 * To change this template use File | Settings | File Templates.
 */


public class Calculator {

    Map<Character, Integer> signsPriority;
    Map<String, FuncOrSignAbstract> functions;
    String errorMessage;
    int indexOfError;


    public Calculator() {
        errorMessage = "";
        indexOfError = -1;
        signsPriority = new HashMap<Character, Integer>();
        signsPriority.put('(', 1);
        signsPriority.put('+', 3);
        signsPriority.put('-', 3);
        signsPriority.put('*', 5);
        signsPriority.put('/', 5);
        signsPriority.put('^', 7);
        signsPriority.put(')', 1);
        functions = new HashMap<>();
        functions.put("+", new Plus());
        functions.put("-", new Minus());
        functions.put("*", new Mult());
        functions.put("/", new Divide());
        functions.put("^", new Power());
    }

    public Float calculate(String s){
        StringValidator validator = new StringValidator(signsPriority,functions);
        errorMessage = validator.getErrors(s);
        if(errorMessage != ""){
            indexOfError = validator.indexOfError;
            return null;
        }
        PolishConverterAndCalculator calculator = new PolishConverterAndCalculator(signsPriority,functions);
        return calculator.CalculateString(s);
    }
    public StringValidator getValidator(){
        return  new StringValidator(signsPriority,functions);
    }
}
