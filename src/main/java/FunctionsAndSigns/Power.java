package FunctionsAndSigns;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 27.11.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class Power extends FuncOrSignAbstract {
    @Override
    public Float DoCom(Float[] args) {
        if(args.length<2)
            return null;
        double res;
        double value = args[0].doubleValue();
        double power = args[1].doubleValue();
        res = Math.pow(value,power);
        return new Float(res);
    }
}
