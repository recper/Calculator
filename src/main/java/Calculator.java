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
        signsPriority.put(')', 1);
        functions = new HashMap<>();
        functions.put("+", new Plus());
        functions.put("-", new Minus());
        functions.put("*", new Mult());
        functions.put("/", new Divide());
    }

    public Float calculate(String s) throws Exception {
        Float res = null;
        String errors = getErrors(s);
        if (errors != "")
            throw new Exception(errors);
        //TODO: -1 => (0-1)
        s = replaceMinusesWithOminus(s);
        String polish = convertToPolish(s);
        res = calculatePolish(polish);
        return res;
    }


    public String getErrors(String s) {
        String res = "";
        String t = checkBrackets(s);
        if (t != "") {
            res += t + "\n";
        }
        int tempIndexOfError = checkUnknownSymbols(s);
        if (tempIndexOfError >= 0) {
            res += "There is an unknown symbol in " + tempIndexOfError + " position.\n";
            indexOfError = tempIndexOfError;
        }
        tempIndexOfError = checkWrongWords(s);
        if (tempIndexOfError >= 0) {
            res += "There is an unknown function name in " + tempIndexOfError + " position.\n";
            indexOfError = tempIndexOfError;
        }
        tempIndexOfError = checkSigns(s);
        if (tempIndexOfError >= 0) {
            res += "Incorrect using of sign in " + tempIndexOfError + " position.\n";
            indexOfError = tempIndexOfError;
        }
        errorMessage = res;
        return res;
    }

    public String convertToPolish(String str) {
        String res = "";
        char s[] = str.toCharArray();

        Stack<Character> stackOfSigns = new Stack<Character>();
        for (int i = 0; i < s.length; i++) {
            if (s[i] == ' ') {
                continue;
            }
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
            if (s[i] >= '0' && s[i] <= '9') {
                String number = "";
                while (i < s.length && !signsPriority.containsKey(s[i]) && s[i] != ' ') {
                    number += s[i];
                    i++;
                }
                i--;
                res += number;
                res += ' ';
            }
            if (s[i] >= 'a' && s[i] <= 'z') {
                String functionName = "";
                while (i < s.length && s[i] != ')') {
                    functionName += s[i];
                    i++;
                }
                i--;
                res += functionName;
                res += ' ';
            }
        }
        while (!stackOfSigns.empty()) {
            res += stackOfSigns.pop();
            res += ' ';
        }

        return res;
    }

    public float calculatePolish(String str) {
        Stack<Float> stackOfNumbers = new Stack<>();
        char s[] = str.toCharArray();
        for (int i = 0; i < s.length; i++) {
            if (signsPriority.containsKey(s[i])) {
                float secondNumber = stackOfNumbers.pop();
                float firstNumber = stackOfNumbers.pop();
                String func = "" + s[i];
                Float commandResult = functions.get(func).DoCom(new Float[]{firstNumber, secondNumber});
                if (commandResult != null)
                    stackOfNumbers.push(commandResult);
                else {
                    //TODO: wrong arguments
                }
            }
            if (s[i] >= '0' && s[i] <= '9') {
                String number = "";
                while (i < s.length && s[i] >= '0' && s[i] <= '9' || s[i] == '.') {
                    number += s[i];
                    i++;
                }
                i--;
                stackOfNumbers.push(Float.parseFloat(number));
            }
            if (s[i] >= 'a' && s[i] <= 'z') {
                String func = "";
                while (i < s.length && s[i] >= 'a' && s[i] <= 'z') {
                    func += s[i];
                    i++;
                }
                List<Float> arguments = new ArrayList<>();
                while (s[i] != ')') {
                    String arg = "";
                    while (s[i] != ',') {
                        arg += s[i];
                        i++;
                    }
                    arguments.add(Float.parseFloat(arg));
                    i++;
                }
                stackOfNumbers.push(functions.get(func).DoCom(arguments.toArray(new Float[1])));
            }
        }
        return stackOfNumbers.peek();
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
            if (str[i] == ')' && i < str.length - 1 && (!signsPriority.containsKey(str[symbolAfter]) || str[symbolAfter] == '(') && str[symbolAfter] != ' ') {
                return i;
            }
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

    String replaceMinusesWithOminus(String s) {
        String res = "";
        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '-') {
                boolean replaced = false;
                if (i == 0) {
                    res += "(0-";
                    replaced = true;

                } else {
                    int previousIndex = i - 1;
                    for (; previousIndex >= 0 && str[previousIndex] == ' '; previousIndex--) ;
                    if (previousIndex < 0) {
                        res += "(0-";
                        replaced = true;
                    }
                    else if (str[previousIndex] == ',' || str[previousIndex] == '(') {
                        res += "(0-";
                        replaced = true;
                    }
                }
                if (replaced) {
                    i++;
                    while (i < str.length && str[i] == ' ') {
                        res += str[i];
                        i++;
                    }
                    if (str[i] >= '0' && str[i] <= '9') {
                        while (i < str.length && ((str[i] >= '0' && str[i] <= '9') || str[i] == ',')) {
                            res += str[i];
                            i++;
                        }
                        res += ')';
                        i--;
                    }
                    if (str[i] == '(') {
                        res+=str[i];
                        int countOpenBrackets = 0;
                        i++;
                        for (; i < str.length; i++) {
                            res += str[i];
                            if (str[i] == '(') {
                                countOpenBrackets++;
                            }
                            if (str[i] == ')') {
                                countOpenBrackets--;
                                if (countOpenBrackets < 0) {
                                    res += ")";
                                    break;
                                }
                            }
                        }
                    }
                    if (str[i] >= 'a' && str[i] <= 'z') {
                        res+=str[i];
                        int countOpenBrackets = 0;
                        i++;
                        for (; i < str.length; i++) {
                            res += str[i];
                            if (str[i] == '(') {
                                countOpenBrackets++;
                            }
                            if (str[i] == ')') {
                                countOpenBrackets--;
                                if (countOpenBrackets <= 0) {
                                    res += ")";
                                    break;
                                }
                            }
                        }
                    }

                }
            } else {
                res += str[i];
            }
        }
        return res;
    }

}
