import FunctionsAndSigns.FuncOrSignAbstract;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 26.11.13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class StringValidator {
    int indexOfError;
    String errorMessage;

    String emptyError = "\nThe string is empty.";
    String unknownSymbolError = "\nThere is an unknown symbol. Position: ";
    String unknownFunctionError = "\nThere is an unknown function name. Position: ";
    String incorrectSignUsing = "\nIncorrect using of sign. Position: ";
    String numbersWithoutSign = "\nThere should be a sign after number. Position: ";
    String noOpeningBracket = "\nThere isn't any opening bracket for bracket. Position: ";
    String noClosingBracket = "\nThere is no closing bracket for bracket. Position: ";

    Map<Character, Integer> signsPriority;
    Map<String, FuncOrSignAbstract> functions;

    public StringValidator(Map<Character, Integer> signsPriority,Map<String, FuncOrSignAbstract> functions){
        indexOfError = -1;
        errorMessage = "";
        this.signsPriority = signsPriority;
        this.functions = functions;
    }

    public String getErrors(String s) {
        String res = "";
        if(s.isEmpty()){
            res+=emptyError;
        }
        String t = checkBrackets(s);
        if (t != "") {
            res += t + "\n";
        }
        int tempIndexOfError = checkUnknownSymbols(s);
        if (tempIndexOfError >= 0) {
            res += unknownSymbolError  + tempIndexOfError;
            indexOfError = tempIndexOfError;
        }
        tempIndexOfError = checkWrongWords(s);
        if (tempIndexOfError >= 0) {
            res += unknownFunctionError + tempIndexOfError;
            indexOfError = tempIndexOfError;
        }
        tempIndexOfError = checkSigns(s);
        if (tempIndexOfError >= 0) {
            res += incorrectSignUsing + tempIndexOfError;
            indexOfError = tempIndexOfError;
        }
        tempIndexOfError = checkTwoNumbersWithoutSign(s);
        if(tempIndexOfError >=0){
            res += numbersWithoutSign + tempIndexOfError;
            indexOfError = tempIndexOfError;
        }
        errorMessage = res;
        return res;
    }

    String checkBrackets(String brackets) {
        Stack<Map.Entry<Character, Integer>> stack = new Stack<>();
        char[] s = brackets.toCharArray();
        for (int i = 0; i < brackets.length(); i++) {
            if (s[i] == '(' || s[i] == '[' || s[i] == '{' || s[i] == '<') {
                stack.push(new AbstractMap.SimpleEntry<>(s[i], i));
                continue;
            }
            if (s[i] == ')' || s[i] == ']' || s[i] == '}' || s[i] == '>') {
                if (stack.isEmpty()) {
                    indexOfError = i;
                    return noOpeningBracket + i;
                }
                Character t = stack.pop().getKey();
                if ((s[i] == '}' || s[i] == '}' || s[i] == '>') && s[i] != t + 2) {
                    indexOfError = i;
                    return noOpeningBracket + i;
                }
                if (s[i] == ')' && s[i] != t + 1) {
                    indexOfError = i;
                    return noOpeningBracket + i;
                }
                continue;
            }
        }
        if (!stack.isEmpty()) {
            indexOfError = stack.peek().getValue();
            return noClosingBracket + stack.peek().getValue();
        }
        return "";
    }

    int checkUnknownSymbols(String s) {
        String res = "";
        char str[] = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == ' ')
                continue;
            if (signsPriority.containsKey(str[i]))
                continue;
            if (str[i] <= 'z' && str[i] >= 'a')
                continue;
            if (str[i] <= '9' && str[i] >= '0')
                continue;
            if (str[i] == '.')
                continue;
            return i;
        }
        return -1;
    }

    int checkWrongWords(String s) {
        char str[] = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            String currentWord = "";
            int maybeIndexOfError = i;
            while (i < str.length && str[i] >= 'a' && str[i] <= 'z') {
                currentWord += str[i];
                i++;
            }
            if (currentWord != "" && !functions.containsKey(currentWord)) {
                return maybeIndexOfError;
            }
        }
        return -1;
    }

    int checkSigns(String s) {
        char str[] = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '.') {
                if (i == 0 || i == str.length) {
                    return i;
                }
                if (str[i + 1] < '0' || str[i + 1] > '9')
                    return i;
                if (str[i - 1] < '0' || str[i - 1] > '9') {
                    return i;
                }
                continue;
            }
            int symbolBefore = i - 1, symbolAfter = i + 1;
            while (symbolBefore >= 0 && str[symbolBefore] == ' ')
                symbolBefore--;
            while (symbolAfter < str.length && str[symbolAfter] == ' ')
                symbolAfter++;
            if (symbolAfter == str.length) symbolAfter--;
            if (symbolBefore == -1) symbolBefore++;
            if (str[i] == ')' && i < str.length - 1 &&
                    (!signsPriority.containsKey(str[symbolAfter]) || str[symbolAfter] == '(') && str[symbolAfter] != ' ') {
                return i;
            }
            if(str[i] >= '0' && str[i] <= '9' && i < str.length-1 &&
                    (!signsPriority.containsKey(str[symbolAfter]) || str[symbolAfter] == '(') && str[symbolAfter] != ' ')
                return i;
            if (signsPriority.containsKey(str[i]) && str[i] != '-' && str[i] != '(') {
                if (i == 0)
                    return i;
                if ((!(str[symbolBefore] >= '0' && str[symbolBefore] <= '9') && str[symbolBefore] != ')')) {
                    return i;
                }
            }
            if (signsPriority.containsKey(str[i]) && str[i] != ')') {
                if (i == str.length - 1)
                    return i;
                if ((!(str[symbolAfter] >= '0' && str[symbolAfter] <= '9') && str[symbolAfter] != '(' && str[symbolAfter] != '-')) {
                    return i;
                }
            }
        }
        return -1;
    }

    int checkTwoNumbersWithoutSign(String s){
        char str[] = s.toCharArray();
        for(int i = 0; i<str.length; i++){
            if(str[i] >= '0' && str[i] <= '9' && i < str.length-1){
                int indexOfNumber = i;
                i++;
                char symbolAfterNumber;
                while (i<str.length && str[i] == ' '){
                    i++;
                }
                if(i == str.length || indexOfNumber == i){
                    break;
                }
                else{
                    symbolAfterNumber = str[i];
                }
                if(str[i] >='0' && str[i] <= '9'){
                    return indexOfNumber;
                }
            }
        }
        return -1;
    }
}
