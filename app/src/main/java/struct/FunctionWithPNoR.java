package struct;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public abstract class FunctionWithPNoR<Param> extends Funtion{

    public FunctionWithPNoR(String mFuntionName) {
        super(mFuntionName);
    }

    public abstract void function(Param data);


}
