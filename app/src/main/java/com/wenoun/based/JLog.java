package com.wenoun.based;

import android.util.Log;

/**
 * Created by JeyHoon on 2016. 11. 8..
 */

public final class JLog{
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;
    private static final String TAG="JTest";
    /**
     * Send a {@link #VERBOSE} log message.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int v(String msg) {
        return Log.v(TAG,msg);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int v(String msg, Throwable tr) {
        return Log.v(TAG,msg,tr);
    }

    /**
     * Send a {@link #DEBUG} log message.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int d(String msg) {
        return Log.d(TAG,msg);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int d(String msg, Throwable tr) {
        return Log.d(TAG,msg,tr);
    }

    /**
     * Send an {@link #INFO} log message.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int i(String msg) {
        return Log.i(TAG,msg);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int i(String msg, Throwable tr) {
        return Log.i(TAG,msg,tr);
    }

    /**
     * Send a {@link #WARN} log message.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int w(String msg) {
        return Log.w(TAG,msg);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int w(String msg, Throwable tr) {
        return Log.w(TAG,msg,tr);
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int w(Throwable tr) {
        return Log.w(TAG,tr);
    }

    /**
     * Send an {@link #ERROR} log message.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int e(String msg) {
        return Log.e(TAG,msg);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     * @return The number of bytes written.
     */
    public static int e(String msg, Throwable tr) {
        return Log.e(TAG,msg,tr);
    }

}
