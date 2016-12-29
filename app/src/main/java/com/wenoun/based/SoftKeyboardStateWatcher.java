package com.wenoun.based;

/**
 * Created by JeyHoon on 2016. 12. 29..
 */

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 소프트 키보드의 표시 여부를 확인하는 클래스.
 */
public class SoftKeyboardStateWatcher implements ViewTreeObserver.OnGlobalLayoutListener {

    // 키보드의 크기를 200픽셀로 가정한다. 보통 200은 그냥 넘으므로 늘리실분은 더 늘리셔도 됩니다.
    private static final int INVISIBLE_VIEW_SIZE_BY_PX = 200;

    private SoftKeyboardStateWatcher.SoftKeyboardStateListener listener;

    private final View rootView;

    private boolean isSoftKeyboardVisible;

    private Context ctx=null;

    public SoftKeyboardStateWatcher(Context ctx,View rootView) {
        this(ctx,rootView, false);
    }

    public SoftKeyboardStateWatcher(Context ctx,View rootView, boolean isSoftKeyboardOpened) {
        this.ctx=ctx;
        this.rootView = rootView;
        this.isSoftKeyboardVisible = isSoftKeyboardOpened;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        if (listener == null) {
            return;
        }

        int rootViewHeight = rootView.getHeight();

        Resources resources = ctx.getResources();
        // 위 코드에서 에러나실텐데 context를 생성자로부터 받으셔도 되고요, 저처럼 Application을 상속받아서
        // 싱글톤으로 구현하셔도 됩니다.
        int navigationBarId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (navigationBarId > 0) {
            rootViewHeight += resources.getDimensionPixelSize(navigationBarId); // 네비게이션 바의 높이를 더한다.
        }

        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0) {
            rootViewHeight += resources.getDimensionPixelSize(statusBarId); // 상태 바의 높이를 더한다.
        }


        int heightDiff = rootView.getRootView().getHeight() - rootViewHeight;

        if (heightDiff > INVISIBLE_VIEW_SIZE_BY_PX) {
            isSoftKeyboardVisible = true;
            listener.onSoftKeyboardOpened(heightDiff);
        } else if (isSoftKeyboardVisible && heightDiff < INVISIBLE_VIEW_SIZE_BY_PX) {
            isSoftKeyboardVisible = false;
            listener.onSoftKeyboardClosed();
        }
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardVisible;
    }

    public void clearListener() {
        this.listener = null;
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    public void setSoftKeyboardStateListener(SoftKeyboardStateWatcher.SoftKeyboardStateListener listener) {
        this.listener = listener;
    }

    public interface SoftKeyboardStateListener {

        void onSoftKeyboardOpened(int keyboardHeightInPx);

        void onSoftKeyboardClosed();
    }
}
