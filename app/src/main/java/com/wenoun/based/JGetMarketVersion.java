package com.wenoun.based;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JeyHoon on 2016. 10. 28..
 */

public class JGetMarketVersion extends AsyncTask<String, Void, Message> {
    public interface OnVersionListener {
        public static final int FAILED = 2;
        public static final int SUCCESS = 1;

        void onSuccess(final String versionCode);

        void onFailed(final JError error);

    }

    public static final int FAILED = OnVersionListener.FAILED;
    public static final int SUCCESS = OnVersionListener.SUCCESS;

    private String packageName;
    private static final String URL = "https://play.google.com/store/apps/details?id=";
    private String url="";
    private int timeOut=1000;

    public OnVersionListener checkHandler = null;


    protected Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            String jsonStr1 = (String) msg.obj;
//            Log.d("Test,CommuServer","jsonStr1 : "+jsonStr1);
            switch (msg.what) {

                case SUCCESS:
                    sendSuccess(jsonStr1);
//
//
//                    try {
////                        Log.d("Test,CommuServer",jsonStr1);
//                        JSONObject jsonObj = new JSONObject(jsonStr1);
////                        String result_msg = jsonObj.getString("result");
//                        int resultCode = jsonObj.getInt("result");
//                        if (resultCode == SUCCESS) {
//
//                        } else if (resultCode == FAILED) {
//                            String error = jsonObj.getString("error");
//                            String hint = jsonObj.getString("hint");
//                            sendFailed(error, hint);
//                        } else {
//                            sendFailed("ERROR#999", "HTML Error");
//
//                        }
//                        // JSONArray 객체 얻어오기
//
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        sendFailed("ERROR#444", "JSON Data has Exception");
//                    }
                    break;
                case FAILED:
                    try {
                        sendFailed(JError.fromJSONString(jsonStr1));
                    }catch(JSONException je){
                        sendFailed("ERROR#404", jsonStr1);
                    }
//                    sendFailed("ERROR#404", jsonStr1);
//                    sendCheckHandler(OnCheckHandler.HAS_NOT, null);
                    break;
            }
        }
    };

    public JGetMarketVersion(String packageName, OnVersionListener checkHandler) {
        this.checkHandler=checkHandler;
        this.packageName = packageName;
        this.url = URL + packageName;
    }
    public JGetMarketVersion(String packageName, OnVersionListener checkHandler, int timeOut) {
        this.checkHandler=checkHandler;
        this.packageName = packageName;
        this.url = URL + packageName;
        this.timeOut=timeOut;
    }

    @Override
    protected Message doInBackground(String... params) {
        String result = "";
        Message msg = new Message();
        StringBuilder html = new StringBuilder();
        HttpURLConnection conn = null;

        try {
            java.net.URL e = new URL(this.url);
            conn = (HttpURLConnection) e.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(timeOut);
                conn.setUseCaches(false);
                if (conn.getResponseCode() == 200) {
                    BufferedReader msg2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    while (true) {
                        String start = msg2.readLine();
                        if (start == null) {
                            msg2.close();
                            break;
                        }

                        html.append(start + '\n');
                    }
                    try {
                        int start1 = html.indexOf("softwareVersion") + "softwareVersion".length() + 2;
                        int end = html.indexOf("</div>", start1);
                        result = html.substring(start1, end);
                        result = result.replace(" ", "");
                    } catch (Exception var12) {
                        var12.printStackTrace();
                        result = "0.0.0";
                    }
                    msg=handler.obtainMessage();
                    msg.what = SUCCESS; //성공
                    msg.obj = result; //가져온 String Data를 저장
//                    Message msg1 = new Message();
//                    msg1.what = 0;
//                    msg1.obj = result;
//                    this.handler.sendMessage(msg1);
                }else{
                    msg=handler.obtainMessage();
                    msg.what = FAILED; //실패
                    try {
                        msg.obj = JError.toJSONString(String.valueOf(conn.getResponseCode()), conn.getResponseMessage());
                    }catch (JSONException je){
                        msg.obj = JError.toString(String.valueOf(conn.getResponseCode()), conn.getResponseMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            if(null!=e.getMessage()) {
                Log.e("Test,post 전송중 에러!", e.getMessage());
            }
            msg=handler.obtainMessage();
            msg.what = FAILED; //실패
            try {
                msg.obj = JError.toJSONString("ERROR#404", "No Response");
            }catch(JSONException je){
                msg.obj = JError.toString("ERROR#404", "No Response");
            }
//            handler.sendMessage(msg);
        } finally {
            if (null != conn)
                conn.disconnect();
        }


        return msg;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param msg The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Message msg) {
//        super.onPostExecute(object);
        final Message m=new Message();
        m.copyFrom(msg);
        handler.sendMessage(m);
    }

    public void run() {

    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    private void sendSuccess(final String obj) {
        if (null != checkHandler) {
            checkHandler.onSuccess(obj);
        }
    }

    private void sendFailed(final String error, final String hint) {
        if (null != checkHandler) {
            checkHandler.onFailed(new JError(error, hint));
        }
    }

    private void sendFailed(final JError error) {
        if (null != checkHandler) {
            checkHandler.onFailed(error);
        }
    }
    public AsyncTask<String,Void,Message> run(String... params){
        return execute(params);
    }
    public AsyncTask<String,Void,Message> start(String... params){
        return execute(params);
    }

}
