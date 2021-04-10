/*
 * *
 *  * Created by WebKnight Infosystem on 14/5/20 10:52 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 14/5/20 10:37 AM
 *
 */

package com.webknight.filemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.webknight.filemanager.adapter.FolderListAdapter;
import com.webknight.filemanager.filter.entity.Directory;

import java.util.ArrayList;
import java.util.List;


public class FolderListHelper {
    private PopupWindow mPopupWindow;
    private View mContentView;
    private RecyclerView rv_folder;
    private FolderListAdapter mAdapter;

    public void initFolderListView(Context ctx) {
        if (mPopupWindow == null) {
            mContentView = LayoutInflater.from(ctx)
                    .inflate(R.layout.vw_layout_folder_list, null);
            rv_folder = (RecyclerView) mContentView.findViewById(R.id.rv_folder);
            mAdapter = new FolderListAdapter(ctx, new ArrayList<Directory>());
            rv_folder.setAdapter(mAdapter);
            rv_folder.setLayoutManager(new LinearLayoutManager(ctx));
            mContentView.setFocusable(true);
            mContentView.setFocusableInTouchMode(true);

            mPopupWindow = new PopupWindow(mContentView);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setTouchable(true);
        }
    }

    public void setFolderListListener(FolderListAdapter.FolderListListener listener) {
        mAdapter.setListener(listener);
    }

    public void fillData(List<Directory> list) {
        mAdapter.refresh(list);
    }

    public void toggle(View anchor) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mPopupWindow.showAsDropDown(anchor,
                    (anchor.getMeasuredWidth() - mContentView.getMeasuredWidth()) / 2,
                    0);
            mPopupWindow.update(anchor, mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        }
    }
}
