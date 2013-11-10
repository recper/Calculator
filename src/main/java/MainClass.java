import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Calculator calc = new Calculator();
        while (true){
            System.out.print(calc.getErrors(scanner.nextLine())+"\n");
        }
    }
}
