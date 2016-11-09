package com.wenoun.based.v4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenoun.based.JUtil;

//import android.app.Fragment;

/**
 * Created by JeyHoon on 16. 6. 19..
 */
public abstract class JFragment extends Fragment {
    protected Activity parentAct=null;
    protected Context ctx=null;
    protected static View root=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=getActivity().getBaseContext();
        parentAct = getActivity();
    }

//    @Override
//    public void onAttachFragment(Fragment childFragment) {
//        super.onAttachFragment(childFragment);
//    }
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }

    /**
     *
     * @param resId Layout's Resource Id e.g. R.layout.fragment_xx
     * @param inflater onCreateView param's LayoutInflater inflater
     * @param container onCreateView param's ViewGroup container
     */
    public void setRoot(int resId, LayoutInflater inflater, ViewGroup container){
        try {
            root = inflater.inflate(resId, container, false);
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * When you override this method, Must first call setRoot method
     * @param inflater onCreateView param's LayoutInflater inflater
     * @param container onCreateView param's ViewGroup container
     */
    protected abstract void onCreateFragment(LayoutInflater inflater, ViewGroup container);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        onCreateFragment(inflater, container);
        return root;
    }

    /**
     * Easy to findViewById Instead of root.findViewById
     * @param resId View's Resource Id
     * @return redId's View
     */
    public View findViewById(int resId){
        if(null==root){
            throw new NullPointerException("Root is null. Call setRoot method");
        }else
            return root.findViewById(resId);
    }

    /**
     * Easy to setOnClickListener Instead of root.findViewById(viewId).setOnClickListner
     * @param viewId apply View's id
     * @param listener OnClickListener
     */
    protected void setOnClickListener(int viewId, View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    public void startAct(Class<?> cls){
        startAct(new Intent(ctx,cls));
    }

    public void startActFinish(Class<?> cls){
        startActFinish(new Intent(ctx,cls));
    }

    public void startAct(Intent intent){
        startAct(intent,false);
    }

    public void startActFinish(Intent intent){
        startAct(intent,true);
    }

    public void startAct(Intent intent,boolean isFinish){
        startActivity(intent);
        if(isFinish)
            parentAct.finish();
    }
    public void Log(String msg){
        Log.d("Test,"+getClass().getSimpleName(),msg);
    }
    public void hideSoftKeyboard(View view){
        JUtil.hideSoftKeyboard(ctx,view);
    }
}
