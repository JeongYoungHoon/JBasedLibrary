package com.wenoun.based;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by JeyHoon on 16. 4. 29..
 */
public class JUtil {
    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    public static float hypo(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
    public static final void hideSoftKeyboard(Context ctx,View view) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static final void showSoftKeyboard(Context ctx,View view){
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    public static final boolean isShowingSoftKeyboard(Context ctx,View view){
        SoftKeyboardStateWatcher watcher=new SoftKeyboardStateWatcher(ctx,view);
        return watcher.isSoftKeyboardOpened();
    }

    public static final boolean isNeedsVersionUpdate(final String mVersion, final String rVersion){
        final String[] mVer=mVersion.split("\\.");
        final String[] rVer=rVersion.split("\\.");
        final int max=mVer.length>rVer.length?mVer.length:rVer.length;

        for(int i=0; i<max; i++){
            if(i>=mVer.length){
                return true;
            }
            if(i>=rVer.length)
                return false;
            final int m=Integer.parseInt(mVer[i]);
            final int r=Integer.parseInt(rVer[i]);
            if(m<r)
                return true;
        }
        return false;
    }
    /**
     * Over Api Version 23.
     *
     * @return true is Over 21, false is Under 21.
     */
    public static boolean isOverM() {
        return Build.VERSION.SDK_INT >= 23;
    }

    /**
     * Over Api Version 21.
     *
     * @return true is Over 21, false is Under 21.
     */
    public static boolean isOverL() {
        return Build.VERSION.SDK_INT >= 21;
    }


}
