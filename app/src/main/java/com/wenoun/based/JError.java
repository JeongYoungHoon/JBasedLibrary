package com.wenoun.based;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JeyHoon on 2016. 9. 18..
 */
public class JError {
    private String error="#ERROR999";
    private String hint="";

    public JError() {
    }

    public JError(String error, String hint) {
        this.error = error;
        this.hint = hint;
    }

    public JError(int errorNo,String hint){
        setErrorNo(errorNo);
        this.hint=hint;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getErrorNo() {
        try {
            return Integer.parseInt(error.replace("#", "").replace("ERROR", ""));
        }catch(NumberFormatException e){
            return -1;
        }catch(Exception e){
            return -2;
        }
    }

    public void setErrorNo(int errorNo){
        if(errorNo<0){
            throw new IllegalArgumentException(
                    "Error Number must positive");
        }
        if(errorNo<100){
            setError("0"+String.valueOf(errorNo));
        }else if(errorNo<10){
            setError("00"+String.valueOf(errorNo));
        }else{
            setError(String.valueOf(errorNo));
        }
    }

    @Override
    public String toString() {
        return "{\"error\" : \""+error+"\", \"hint\" : \""+hint+"\"}";
    }

    public static String toString(final String error, final String hint){
        return "{\"error\" : \""+error+"\", \"hint\" : \""+hint+"\"}";
    }

    public void log(){
        Log.d("JTest",toString());
    }

    public static String toJSONString(final String error, final String hint) throws JSONException{
        final JSONObject obj=new JSONObject();
        obj.put("error",error);
        obj.put("hint",hint);
        return obj.toString();
    }

    public static JError fromJSONString(final String jsonStr) throws JSONException{
        final JSONObject obj=new JSONObject(jsonStr);
        return new JError(obj.getString("error"),obj.getString("hint"));
    }
}
