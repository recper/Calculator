package FunctionsAndSigns;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class Minus extends FuncOrSignAbstract {
    @Override
    public float DoCom(float[] args) {
        return args[0]-args[1];
    }
}
