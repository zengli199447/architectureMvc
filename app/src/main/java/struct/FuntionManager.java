package struct;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class FuntionManager {
    private static FuntionManager instance;
    private HashMap<String, FunctionNoPNoR> mFunctionNoPNoR;
    private HashMap<String, FunctionNoPWithR> mFunctionNoPWithR;
    private HashMap<String, FunctionWithPNoR> mFunctionWithPNoR;
    private HashMap<String, FunctionWithPWithR> mFunctionWithPWithR;

    public FuntionManager() {
        mFunctionNoPNoR = new HashMap<>();
        mFunctionNoPWithR = new HashMap<>();
        mFunctionWithPNoR = new HashMap<>();
        mFunctionWithPWithR = new HashMap<>();
    }

    public static FuntionManager getInstance() {
        if (instance == null) {
            instance = new FuntionManager();
        }
        return instance;
    }

    public FuntionManager addFunction(FunctionNoPNoR function) {
        mFunctionNoPNoR.put(function.mFuntionName, function);
        return this;
    }


    public void invokeFunc(String funcName) {
        if (TextUtils.isEmpty(funcName) == true){
            return;
        }

        if (mFunctionNoPNoR != null) {
            FunctionNoPNoR functionNoPNoR = mFunctionNoPNoR.get(funcName);
            if (functionNoPNoR!=null){
                functionNoPNoR.function();
            }
            if (functionNoPNoR == null){

            }
        }


    }


}
