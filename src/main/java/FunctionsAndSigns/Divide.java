package FunctionsAndSigns;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.11.13
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class Divide extends FuncOrSignAbstract {
    @Override
    public float DoCom(float[] args) {
        return args[1]/args[2];
    }
}
