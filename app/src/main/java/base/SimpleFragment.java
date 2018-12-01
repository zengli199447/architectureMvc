package base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nativeosproject.cn.mvcjobproject.activity.mian.MainActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import struct.FuntionManager;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public abstract class SimpleFragment extends SupportFragment {

    protected String TAG = getClass().getSimpleName();

    private Activity mActivity;
    private Context mContext;
    private View mView;
    private Unbinder mUnBinder;
    protected FuntionManager funtionManager;

    public void setFuntionManager(FuntionManager funtionManager){
        this.funtionManager = funtionManager;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);

        if (context instanceof MainActivity){
            MainActivity activity = (MainActivity) context;
            activity.setFunctionForFragment(getTag());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        function();
    }

    protected abstract int getLayoutId();

    protected abstract void function();

    protected void onTheCustom() {

    }

    protected void onUnSubscribe() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        onUnSubscribe();
        onTheCustom();
    }
}
