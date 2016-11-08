package com.wenoun.based;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by JeyHoon on 2016. 10. 24..
 */

public abstract class JObject<T> implements Serializable {
    public abstract T loadFromJSONObject(final JSONObject object) throws JSONException;
    public T loadFromJSONString(final String jsonStr) throws JSONException{return loadFromJSONObject(new JSONObject(jsonStr));}
    public abstract JSONObject toJSONObject();
    public String toString(){return toJSONObject().toString();}
}
