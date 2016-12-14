package com.wenoun.based;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tsengvn.typekit.TypekitContextWrapper;


/**
 * Created by JeyHoon on 16. 6. 19..
 */
public class JActivity extends AppCompatActivity {
    protected Context ctx=null;
    protected static Dialog progressDialog=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        getValuesByIntent(getIntent());
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    /**
     * Get values By Intent Method
     * @param intent getIntent();
     */
    @Nullable
    protected void getValuesByIntent(Intent intent){

    }

    /*
     * Get Values By Intent.
     * @return is initValue's type.
     */
    protected String getIntentValue(@NonNull String key, String initValue){
        return getIntentValue(getIntent(),key,initValue);
    }
    protected long getIntentValue(@NonNull String key, long initValue){
        return getIntentValue(getIntent(),key,initValue);
    }
    protected int getIntentValue(@NonNull String key, int initValue){
        return getIntentValue(getIntent(),key,initValue);
    }
    protected boolean getIntentValue(@NonNull String key, boolean initValue){
        return getIntentValue(getIntent(),key,initValue);
    }
    protected Object getIntentValue(@NonNull String key, Object initValue){
        return getIntentValue(getIntent(),key,initValue);
    }
    protected static String getIntentValue(@NonNull Intent intent, @NonNull String key, String initValue){
        if(intent.hasExtra(key))
            return intent.getExtras().getString(key);
        else
            return initValue;
    }
    protected static long getIntentValue(@NonNull Intent intent, @NonNull String key, long initValue){
        if(intent.hasExtra(key))
            return intent.getExtras().getLong(key);
        else
            return initValue;
    }
    protected static int getIntentValue(@NonNull Intent intent, @NonNull String key, int initValue){
        if(intent.hasExtra(key))
            return intent.getExtras().getInt(key);
        else
            return initValue;
    }
    protected static boolean getIntentValue(@NonNull Intent intent, @NonNull String key, boolean initValue){
        if(intent.hasExtra(key))
            return intent.getExtras().getBoolean(key);
        else
            return initValue;
    }
    protected static Object getIntentValue(@NonNull Intent intent, @NonNull String key, Object initValue){
        if(intent.hasExtra(key))
            return intent.getExtras().get(key);
        else
            return initValue;
    }
    /*
     * Get Values By Intent End.
     */

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
            finish();
    }
    public void Log(String msg){
        Log.d("JTest,"+getLocalClassName(),msg);
    }
    public void Log(JError error){
        Log.d("JTest,"+getLocalClassName(),error.toString());
    }
    public void Log(Exception e){Log.e("JTest"+getLocalClassName(),"Exception : ",e);}
    public void hideSoftKeyboard(View view){
        JUtil.hideSoftKeyboard(ctx,view);
    }

    public Dialog getProgressDialog(final String msg){
        return ProgressDialog.show(ctx,"",
                msg,true);
    }
    public Dialog getProgressDialog(){
        return getProgressDialog("");
    }

    public void showProgressDialog(){
        showProgressDialog("");
    }

    public void showProgressDialog(final String msg){
//        Log("showProgressDialog");
        try {
            if (null == progressDialog)
                progressDialog = getProgressDialog(msg);
            if (!progressDialog.isShowing())
                progressDialog.show();
        }catch(Exception e){e.printStackTrace();}
    }
    public void dismissProgressDialog(){
//        Log("dismissProgressDialog");
        if(null!=progressDialog)
            progressDialog.dismiss();
    }

    public void goBack(View v){
        onBackPressed();
    }
    public void onBack(View v){
        onBackPressed();
    }


    public void regReceiver(final BroadcastReceiver receiver, final IntentFilter filter){
        try {
            LocalBroadcastManager.getInstance(ctx).registerReceiver(receiver, filter);
        }catch (Exception e){
            Log(e);
        }
    }

    public void unregReceiver(final BroadcastReceiver receiver){
        try{
            LocalBroadcastManager.getInstance(ctx).unregisterReceiver(receiver);
        }catch(Exception e){
            Log(e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
