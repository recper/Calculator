import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {
    public static void main(String args[]) throws Exception {
        Calculator calc =new Calculator();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
//            try {
                String t = calc.replaceMinusesWithOminus(scanner.nextLine());
                System.out.print("\n"+t+"\n");
//            } catch (Exception e) {
//                System.out.print(e.getMessage());
//            }
        }
    }
}
