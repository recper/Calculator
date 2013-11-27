import FunctionsAndSigns.FuncOrSignAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 27.11.13
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */
public class PolishConverterAndCalculator {
    Map<Character, Integer> signsPriority;
    Map<String, FuncOrSignAbstract> functions;

    public PolishConverterAndCalculator(Map<Character, Integer> signsPriority,Map<String, FuncOrSignAbstract> functions){
        this.signsPriority = signsPriority;
        this.functions = functions;
    }

    public Float CalculateString(String str){
        str = replaceMinusesWithOminus(str);
        String convertedToPolish = convertToPolish(str);
        Float res = calculatePolish(convertedToPolish);
        return res;
    }

    String convertToPolish(String str) {
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
                while (!stackOfSigns.empty() && signsPriority.get(stackOfSigns.peek()) >= signsPriority.get(s[i])) {
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

    Float calculatePolish(String str) {
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
                    //errorMessage = "Wrong function arguments."; // Не придумал способа полностью проверить правильность вводимых аргументов
                    // Здесь будет ошибка точно, но это не все случаи
                    return null;
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
                    String willBeAdded = "";
                    while (i < str.length && str[i] == ' ') {
                        res += str[i];
                        i++;
                    }
                    if (str[i] >= '0' && str[i] <= '9') {
                        while (i < str.length && ((str[i] >= '0' && str[i] <= '9') || str[i] == ',')) {
                            willBeAdded += str[i];
                            i++;
                        }
                        //res += ')';
                        i--;
                    }
                    if (str[i] == '(') {
                        willBeAdded+=str[i];
                        int countOpenBrackets = 0;
                        i++;
                        for (; i < str.length; i++) {
                            willBeAdded += str[i];
                            if (str[i] == '(') {
                                countOpenBrackets++;
                            }
                            if (str[i] == ')') {
                                countOpenBrackets--;
                                if (countOpenBrackets < 0) {
                                    //res += ")";
                                    break;
                                }
                            }
                        }
                    }
                    if (str[i] >= 'a' && str[i] <= 'z') {
                        willBeAdded+=str[i];
                        int countOpenBrackets = 0;
                        i++;
                        for (; i < str.length; i++) {
                            willBeAdded += str[i];
                            if (str[i] == '(') {
                                countOpenBrackets++;
                            }
                            if (str[i] == ')') {
                                countOpenBrackets--;
                                if (countOpenBrackets <= 0) {
                                    //res += ")";
                                    break;
                                }
                            }
                        }
                    }
                    res+=replaceMinusesWithOminus(willBeAdded);
                    res+=')';

                }
                else {
                    res+=str[i];
                }
            } else {
                res += str[i];
            }
        }
        return res;
    }
}
