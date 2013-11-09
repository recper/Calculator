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


    public Calculator() {
        signsPriority = new HashMap<Character, Integer>();
        signsPriority.put('(', 1);
        signsPriority.put('+', 3);
        signsPriority.put('-', 3);
        signsPriority.put('*', 5);
        signsPriority.put('/', 5);
        signsPriority.put(')', 1);
    }

    public String getErrors(String s){
        String res = "";
        String t = checkBrackets(s);
        if(t!=""){
            res+=t+"\n";
        }

        return  res;
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
            if(signsPriority.containsKey(s[i])){
                if(stackOfSigns.empty()){
                    stackOfSigns.push(s[i]);
                    continue;
                }
                Character t = stackOfSigns.peek();
                while (!stackOfSigns.empty() && signsPriority.get(t) >= signsPriority.get(s[i])){
                    res += (t = stackOfSigns.pop());
                    res += ' ';
                }
                stackOfSigns.push(s[i]);
                continue;
            }
            String numberOrFunc = "";
            while (  i < s.length && !signsPriority.containsKey(s[i])){
                if(s[i] != ' '){
                    numberOrFunc += s[i];
                }
                i++;
            }
            i--;
            res += numberOrFunc;
            res += ' ';
        }
        while (!stackOfSigns.empty()){
            res += stackOfSigns.pop();
            res += ' ';
        }

        return res;
    }

    String checkBrackets(String brackets){
        Stack<Map.Entry<Character,Integer>> stack = new Stack<>();
        char[] s = brackets.toCharArray();
        for(int i = 0; i< brackets.length(); i++){
            if(s[i] == '(' || s[i] == '[' || s[i] == '{' || s[i] =='<' ){
                stack.push(new AbstractMap.SimpleEntry<>(s[i],i));
                continue;
            }
            if(s[i] == ')' || s[i] == ']' || s[i] == '}' || s[i] == '>' ){
                if(stack.isEmpty()){
                    return "There isn't any opening bracket for "+s[i]+" in "+i+" position.";
                }
                Character t = stack.pop().getKey();
                if((s[i] == '}' || s[i] == '}' || s[i] == '>') && s[i] != t+2){
                    return "There isn't any opening bracket for "+s[i]+" in "+i+" position.";
                }
                if(s[i] == ')' && s[i]!=t+1){
                    return "There isn't any opening bracket for "+s[i]+" in "+i+" position.";
                }
                continue;
            }
        }
        if(!stack.isEmpty()){
            return "There is no closing bracket for "+stack.peek().getKey()+" in position "+stack.peek().getValue()+".";
        }
        return "";
    }
    String checkWrongWords(String s){
        String res = "";
        return res;
    }

}
