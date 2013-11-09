import FunctionsAndSigns.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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


    public Calculator() {
        signsPriority = new HashMap<Character, Integer>();
        signsPriority.put('(', 1);
        signsPriority.put('+', 3);
        signsPriority.put('-', 3);
        signsPriority.put('*', 5);
        signsPriority.put('/', 5);
        signsPriority.put(')', 1);
        functions = new HashMap<>();
        functions.put("+", new Plus());
        functions.put("-", new Minus());
        functions.put("*", new Mult());
        functions.put("/", new Divide());
    }

    public String getErrors(String s) {
        String res = "";
        String t = checkBrackets(s);
        if (t != "") {
            res += t + "\n";
        }
        int indexOfError = checkUnknownSymbols(s);
        if (indexOfError >= 0) {
            res += "There is an unknown symbol in " + indexOfError + " position.\n";
        }
        indexOfError = checkWrongWords(s);
        if (indexOfError >= 0) {
            res += "There is an unknown function name in " + indexOfError + " position.\n";
        }

        return res;
    }

    public String convertToPolish(String str) {
        String res = "";
        char s[] = str.toCharArray();

        Stack<Character> stackOfSigns = new Stack<Character>();
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '(') {
                stackOfSigns.push(s[i]);
                continue;
            }
            if (s[i] == ')') {
                Character t = stackOfSigns.pop();
                while (t != '(') {
                    res += t;
                    res += ' ';
                    t = stackOfSigns.pop();
                }
                continue;
            }
            if (signsPriority.containsKey(s[i])) {
                if (stackOfSigns.empty()) {
                    stackOfSigns.push(s[i]);
                    continue;
                }
                Character t = stackOfSigns.peek();
                while (!stackOfSigns.empty() && signsPriority.get(t) >= signsPriority.get(s[i])) {
                    res += (t = stackOfSigns.pop());
                    res += ' ';
                }
                stackOfSigns.push(s[i]);
                continue;
            }
            String numberOrFunc = "";
            while (i < s.length && !signsPriority.containsKey(s[i])) {
                if (s[i] != ' ') {
                    numberOrFunc += s[i];
                }
                i++;
            }
            i--;
            res += numberOrFunc;
            res += ' ';
        }
        while (!stackOfSigns.empty()) {
            res += stackOfSigns.pop();
            res += ' ';
        }

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
                    return "There isn't any opening bracket for " + s[i] + " in " + i + " position.";
                }
                Character t = stack.pop().getKey();
                if ((s[i] == '}' || s[i] == '}' || s[i] == '>') && s[i] != t + 2) {
                    return "There isn't any opening bracket for " + s[i] + " in " + i + " position.";
                }
                if (s[i] == ')' && s[i] != t + 1) {
                    return "There isn't any opening bracket for " + s[i] + " in " + i + " position.";
                }
                continue;
            }
        }
        if (!stack.isEmpty()) {
            return "There is no closing bracket for " + stack.peek().getKey() + " in position " + stack.peek().getValue() + ".";
        }
        return "";
    }

    int checkUnknownSymbols(String s) {
        String res = "";
        char str[] = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (signsPriority.containsKey(str[i])) {
                continue;
            }
            if (str[i] <= 'z' && str[i] >= 'a') {
                continue;
            }
            return i;
        }
        return -1;
    }

    int checkWrongWords(String s) {
        char str[] = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            String currentWord = "";
            int maybeIndexOfError = i;
            while (str[i]>='a' && str[i]<='z' && i<str.length){
                currentWord +=str[i];
                i++;
            }
            if(!functions.containsKey(currentWord)){
                return maybeIndexOfError;
            }
        }
        return -1;
    }

    int checkSigns(String s){
        char str[] = s.toCharArray();
        for(int i = 0; i< str.length; i++){
            if(str[i] == ')' && i < str.length-1 && (!signsPriority.containsKey(str[i+1]) || str[i+1] == '(')){
                return i;
            }
            if(signsPriority.containsKey(str[i]) && str[i] != '-'){
                if(i == 0)
                    return i;
                if((!(str[i-1] >= '0' && str[i-1] <= '9') && str[i-1] != ')' )){
                    return i;
                }
            }
            if(signsPriority.containsKey(str[i])){
                if(i == str.length - 1)
                    return i;
                if((!(str[i+1] >= '0' && str[i+1] <= '9') && str[i+1] != '(' )){
                    return i;
                }
            }
        }
        return -1;
    }

}
