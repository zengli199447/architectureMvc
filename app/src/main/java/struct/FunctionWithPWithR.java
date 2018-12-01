package struct;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public abstract class FunctionWithPWithR<Result,Param> extends Funtion{

    public FunctionWithPWithR(String mFuntionName) {
        super(mFuntionName);
    }

    public abstract Result function(Param data);


}
