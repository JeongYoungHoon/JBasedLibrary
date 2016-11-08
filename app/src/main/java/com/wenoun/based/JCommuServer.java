package com.wenoun.based;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by JeyHoon on 16. 5. 22..
 */
public class JCommuServer extends AsyncTask<String,Void,Message> {

    protected String url="";
    public static final int FAILED = OnCommuListener.FAILED;
    public static final int SUCCESS = OnCommuListener.SUCCESS;
    private Dialog progressDialog=null;

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Message doInBackground(String... params) {
        HttpURLConnection conn = null;
        StringBuilder builder = new StringBuilder();
        Message msg = new Message();
        try {
            URL url = new URL(this.url);
            conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {//정상접속이 되었다면
                conn.setConnectTimeout(1000);//최대 대기시간10초
                conn.setUseCaches(false);//캐쉬사용안함
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //InputStreamReader 객체 얻어오기
                    InputStreamReader isr =
                            new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    //반복문 돌면서 읽어오기
                    while (true) {
                        String line = br.readLine();
                        if (line == null) break;
                        //읽어온 문자열을 객체에 저장
                        builder.append(line);
                    }
                    br.close();
                }//if
            }//if

//            Message msg = new Message();
            msg=handler.obtainMessage();
            msg.what = SUCCESS; //성공
            msg.obj = builder.toString(); //가져온 String Data를 저장



        } catch (Exception e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            if(null!=e.getMessage()) {
                Log.e("Test,post 전송중 에러!", e.getMessage());
            }
            msg=handler.obtainMessage();
            msg.what = FAILED; //실패
            msg.obj = "No response.";
            handler.sendMessage(msg);
//            return msg;
        } finally {
            if (null != conn)
                conn.disconnect(); //접속 종료
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
        dismissProgressDialog();
        handler.sendMessage(m);
    }

    public interface OnCommuListener {
        public static final int FAILED = 2;
        public static final int SUCCESS = 1;

        void onSuccess(final JSONObject object);

        void onFailed(final JError error);

    }

    public OnCommuListener checkHandler = null;
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


                    try {
//                        Log.d("Test,CommuServer",jsonStr1);
                        JSONObject jsonObj = new JSONObject(jsonStr1);
//                        String result_msg = jsonObj.getString("result");
                        int resultCode=jsonObj.getInt("result");
                        if(resultCode==SUCCESS) {
                            sendSuccess(jsonObj);
                        }else if(resultCode==FAILED){
                            String error=jsonObj.getString("error");
                            String hint=jsonObj.getString("hint");
                            sendFailed(error,hint);
                        } else {
                            sendFailed("ERROR#999","HTML Error");

                        }
                        // JSONArray 객체 얻어오기

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        sendFailed("ERROR#444", "JSON Data has Exception");
                    }
                    break;
                case FAILED:
                    sendFailed("ERROR#404", jsonStr1);
//                    sendCheckHandler(OnCheckHandler.HAS_NOT, null);
                    break;
            }
        }
    };

    protected JCommuServer(){}

    public JCommuServer(String url, OnCommuListener checkHandler) {
        this.checkHandler = checkHandler;
        this.url = url;
    }
    public JCommuServer(String url, OnCommuListener checkHandler, JSONObject obj) {
        this(url,checkHandler);
        setParam(obj);
    }
    public JCommuServer setParam(JSONObject obj){
        String $value=obj.toString();
        addParam("json",$value,"UTF-8");
//        Log.d("Test,CommuServer",$value);
        return this;
    }
    public JCommuServer addParam(String key, String value){
        this.url=this.url+"&"+key+"="+value;
        return this;
    }
    public JCommuServer addParam(String key, boolean value){
        this.url=this.url+"&"+key+"="+value;
        return this;
    }
    public JCommuServer addParam(String key, int value){
        this.url=this.url+"&"+key+"="+String.valueOf(value);
        return this;
    }
    public JCommuServer addParam(String key, long value){
        this.url=this.url+"&"+key+"="+String.valueOf(value);
        return this;
    }
    public JCommuServer addParam(String key, float value){
        this.url=this.url+"&"+key+"="+String.valueOf(value);
        return this;
    }
    public JCommuServer addParamUTF8(String key, String value){
        return addParam(key,value,"UTF-8");
    }
    public JCommuServer addParam(String key, String $value, String charSet){
        String value=$value;
        try{
            value=URLEncoder.encode(value,charSet);
        }catch(Exception e){
            value=$value;
        }
        this.url=this.url+"&"+key+"="+value;
        return this;
    }
//
//    /**
//     * Calls the <code>run()</code> method of the Runnable object the receiver
//     * holds. If no Runnable is set, does nothing.
//     *
//     * @see Thread#start
//     */
//    @Override
//    public void run() {
//
//    }

    private void sendSuccess(JSONObject obj) {
        if (null != checkHandler) {
            checkHandler.onSuccess(obj);
        }
    }
    private void sendFailed(String error, String hint) {
        if (null != checkHandler) {
            checkHandler.onFailed(new JError(error,hint));
        }
    }
    public AsyncTask<String,Void,Message> run(String... params){
        return execute(params);
    }
    public AsyncTask<String,Void,Message> start(String... params){
        return execute(params);
    }

    public void showProgressDialog(){
//        Log("showProgressDialog");
        try {
            if (null == progressDialog)
            if (!progressDialog.isShowing())
                progressDialog.show();
        }catch(Exception e){e.printStackTrace();}
    }
    public void dismissProgressDialog(){
//        Log("dismissProgressDialog");
        try {
            if (null != progressDialog)
                progressDialog.dismiss();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
