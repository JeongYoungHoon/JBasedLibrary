package com.wenoun.based.adapter;

import android.view.View;

/**
 * Created by JeyHoon on 16. 5. 26..
 */
public class JRecyclerAdapterInterface {
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener{
        public boolean onItemLongClick(View view, int position);
    }
}
